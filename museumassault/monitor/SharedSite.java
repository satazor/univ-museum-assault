package museumassault.monitor;

import java.util.HashMap;
import museumassault.Team;
import museumassault.custom_message.HandCanvasMessage;
import museumassault.custom_message.PrepareAssaultMessage;
import museumassault.message_broker.Message;
import museumassault.message_broker.MessageBroker;

/**
 *
 * @author Andre Cruz <andremiguelcruz@ua.pt>
 */
public class SharedSite implements ChiefControlSite, ThievesConcentrationSite
{
    public static final int PREPARE_ASSAULT_ACTION = 1;
    public static final int SEND_ASSAULT_PARTY_ACTION = 2;
    public static final int THIEF_HAND_CANVAS_ACTION = 3;
    public static final int THIEF_ARRIVE_ACTION = 4;
    public static final int THIEF_READY_FOR_DEPARTURE_ACTION = 5;

    protected final MessageBroker chiefBroker = new MessageBroker();
    protected final MessageBroker thievesBroker = new MessageBroker();

    protected Room[] rooms;
    protected HashMap roomsHash = new HashMap();
    protected HashMap roomsStatus = new HashMap();

    protected Team[] teams;
    protected HashMap teamsHash = new HashMap();
    protected HashMap teamsBroker = new HashMap();

    protected int nrRoomsToBeRobed;
    protected int nrCollectedCanvas = 0;
    protected boolean multipleMasters = false;

    protected Logger logger;

    /**
     * Constructor of the SharedSite for a single chief
     * @param rooms - the number of rooms in the museum
     * @param teams - the number of teams that are going to rob
     * @param logger - the logger used to store the process
     */
    public SharedSite(Room[] rooms, Team[] teams, Logger logger)
    {
        this.initialize(rooms, teams, logger);
    }

    /**
     * Constructor of the SharedSite for multiple chiefs
     * @param rooms - the number of rooms in the museum
     * @param teams - the number of teams that are going to rob
     * @param logger - the logger used to store the process
     * @param multipleMasters  - true if we want multiple chiefs to control the operation
     */
    public SharedSite(Room[] rooms, Team[] teams, Logger logger, boolean multipleMasters)
    {
        this.initialize(rooms, teams, logger);
        this.multipleMasters = multipleMasters;
    }

    /**
     * Method that initializes the SharedSite, starting the rooms and teams
     * @param rooms - the number of rooms in the museum
     * @param teams - the number of teams that are going to rob
     * @param logger - the logger used to store the process
     */
    protected final void initialize(Room[] rooms, Team[] teams, Logger logger)
    {
        this.teams = teams;
        int nrTeams = teams.length;
        for (int x = 0; x < nrTeams; x++) {
            this.teamsHash.put(teams[x].getId(), teams[x]);
            this.teamsBroker.put(teams[x].getId(), new MessageBroker());
        }

        this.rooms = rooms;
        this.nrRoomsToBeRobed = rooms.length;
        for (int x = 0; x < this.nrRoomsToBeRobed; x++) {
            this.roomsHash.put(rooms[x].getId(), rooms[x]);
            this.roomsStatus.put(rooms[x].getId(), true);
        }

        this.logger = logger;
    }

    /**
     *
     */
    public int getNrCollectedCanvas()
    {
        return this.nrCollectedCanvas;
    }

    /**
     * Method that decides if there are still rooms to rob
     * @param chiefId - the id of the chief
     * @return Integer - Returns the id of the room that is being robbed
     */
    @Override
    public Integer appraiseSit(int chiefId)
    {
        synchronized (this.chiefBroker) {

            this.logger.setChiefStatus(chiefId, Logger.CHIEF_STATUS.DECIDING_WHAT_TO_DO);

            if (this.nrRoomsToBeRobed > 0) {

                int nrRooms = this.rooms.length;
                for (int x = 0; x < nrRooms; x++) {
                    if ((boolean) this.roomsStatus.get(this.rooms[x].getId()) && !this.rooms[x].isBeingRobed()) {
                        this.rooms[x].isBeingRobed(true);
                        return this.rooms[x].getId();
                    }
                }
            }

            return null;
        }
    }

    /**
     * Method that prepares an assault team, assigning to that team the given room
     * and waking up the thieves that will form the team
     * @param chiefId - the id of the chief
     * @param roomId - the room to be robbed
     * @return Integer - Returns the id of the team prepared to rob
     */
    @Override
    public Integer prepareAssaultParty(int chiefId, int roomId)
    {
        Room room = (Room) this.roomsHash.get(roomId);
        if (room == null) {
            throw new RuntimeException("Unknown room with id #" + roomId);
        }

        synchronized (this.chiefBroker) {

            this.logger.setChiefStatus(chiefId, Logger.CHIEF_STATUS.ASSEMBLING_A_GROUP);

            int nrTeams = this.teams.length;

            for (int x = 0; x < nrTeams; x++) {

                if (!this.teams[x].isBusy() && !this.teams[x].isPrepared()) {

                    this.teams[x].isPrepared(true);
                    this.teams[x].setAssignedRoom(room);
                    int nrThieves = this.teams[x].getCapacity();
                    for (int y = 0; y < nrThieves; y++) {
                        this.thievesBroker.writeMessage(new PrepareAssaultMessage(PREPARE_ASSAULT_ACTION, this.teams[x].getId(), roomId));
                        synchronized (this.thievesBroker) {
                            this.thievesBroker.notify();
                        }
                    }

                    room.getCorridor().clearPositions();
                    return this.teams[x].getId();
                }
            }

            room.isBeingRobed(false);

            return null;
        }
    }

    /**
     * Method that sends the previously prepared team (from method prepareAssaultParty(roomID))
     * to rob the designated room
     * @param chiefId - the id of the chief
     * @param teamId - the team to be sent
     */
    @Override
    public void sendAssaultParty(int chiefId, int teamId)
    {
        Team team = (Team) this.teamsHash.get(teamId);
        if (team == null) {
            throw new RuntimeException("Unknown team with id #" + teamId);
        }

        synchronized (this.chiefBroker) {

            if (!team.isPrepared()) {
                throw new RuntimeException("Team with id #" + teamId + " is not prepared yet.");
            }

            team.isBusy(true);

            MessageBroker broker = (MessageBroker) this.teamsBroker.get(teamId);
            int nrThieves = team.getCapacity();
            for (int x = 0; x < nrThieves; x++) {

                while (broker.readMessage(THIEF_READY_FOR_DEPARTURE_ACTION) == null) {
                    Thread.yield();
                }

                broker.writeMessage(new Message(SEND_ASSAULT_PARTY_ACTION));
            }

            System.out.println("[Chief] Notifying all thieves of the party to start crawling to room #" + team.getAssignedRoom().getId() + "..");

            synchronized (broker) {
                broker.notifyAll();
            }
        }
    }

    /**
     * Method that checks if the assault is over. If it isn't the chief is told to wait
     * @param chiefId - the id of the chief
     * @return Integer - Returns null if no team is robbing
     */
    @Override
    public Integer takeARest(int chiefId)
    {
        while (true) {

            synchronized (this.chiefBroker) {

                this.logger.setChiefStatus(chiefId, Logger.CHIEF_STATUS.WAITING_FOR_ARRIVAL);

                Message message = this.chiefBroker.readMessage(THIEF_ARRIVE_ACTION);
                if (message != null) {
                    return message.getOriginId();
                }

                int nrTeams = this.teams.length;
                boolean assaultRunning = false;
                for (int x = 0; x < nrTeams; x++) {
                    if (this.teams[x].isBusy()) {
                        assaultRunning = true;
                        break;
                    }
                }

                if (!assaultRunning) {
                    return null;
                }

                try {
                    this.chiefBroker.wait();
                } catch (InterruptedException ex) {}
            }
        }
    }

    /**
     * Method that collects the canvas handed by a thief, and checks if the room is already empty or not
     * @param chiefId - the id of the chief
     * @param thiefId - the id of the thief that handed the canvas
     */
    @Override
    public void collectCanvas(int chiefId, int thiefId)
    {
        while (true) {

            HandCanvasMessage message = (HandCanvasMessage) this.chiefBroker.readMessage(THIEF_HAND_CANVAS_ACTION, thiefId);
            if (message != null) {

                Team team = (Team) this.teamsHash.get(message.getTeamId());
                if (team == null) {
                    throw new RuntimeException("Unknown team with id #" + message.getTeamId());
                }

                synchronized (this.chiefBroker) {

                    if (message.rolledCanvas()) this.nrCollectedCanvas++;

                    Room room = team.getAssignedRoom();
                    if ((boolean) this.roomsStatus.get(room.getId()) && !message.rolledCanvas()) {
                        this.roomsStatus.put(room.getId(), false);
                        this.nrRoomsToBeRobed--;
                    }
                }

                break;
            }
        }
    }

    /**
     * Method that returns the total number of canvas stolen
     * @param chiefId - the id of the chief
     * @return Integer - Returns the number of canvas stolen
     */
    @Override
    public int sumUpResults(int chiefId) {

        synchronized (this.chiefBroker) {
            this.logger.setChiefStatus(chiefId, Logger.CHIEF_STATUS.PRESENTING_THE_REPORT);

            return this.nrCollectedCanvas;
        }
    }

    /**
     * Method that checks if the given thief is needed for a team.
     * While the thief isn't needed he waits
     * @param thiefId - the thief that needs to know if is needed
     * @return Integer - Returns the id of the team that needs this thief
     */
    @Override
    public Integer amINeeded(int thiefId)
    {
        while (true) {

            synchronized (this.thievesBroker) {

                System.out.println("[Thief #" + thiefId + "] Waiting for orders..");
                try {
                    this.thievesBroker.wait();
                } catch (InterruptedException ex) {}
            }

            PrepareAssaultMessage message = (PrepareAssaultMessage) this.thievesBroker.readMessage(PREPARE_ASSAULT_ACTION);
            if (message != null) {
                System.out.println("[Thief #" + thiefId + "] I am needed on team " + message.getTeamId() + "..");
                return message.getTeamId();
            }
        }
    }


    /**
     * Method that prepares the team to rob a room. Guarantees that all thieves are sent at the same time
     * @param teamId -  the team that is going to rob
     * @param thiefId - the thief that is going to rob, associated with the given team
     * @return TargetRoom - Returns the room assigned to the given team
     */
    @Override
    public TargetRoom prepareExcursion(int thiefId, int teamId) {

        MessageBroker broker = (MessageBroker) this.teamsBroker.get(teamId);
        if (broker == null) {
            throw new RuntimeException("Unknown team with id #" + teamId);
        }

        broker.writeMessage(new Message(THIEF_READY_FOR_DEPARTURE_ACTION));

        while (true) {

            Team team;
            synchronized (broker) {

                team = (Team) this.teamsHash.get(teamId);
                team.addThief(thiefId);

                try {
                    broker.wait();
                } catch (InterruptedException ex) {}
            }

            Message message = broker.readMessage(SEND_ASSAULT_PARTY_ACTION);
            if (message != null) {
                return team.getAssignedRoom();
            }
        }
    }

    /**
     * Method that notifies the chief that a canvas has been handed by the given thief
     * @param thiefId - the thief that handed the canvas
     * @param teamId - the team where that thief belongs
     * @param rolledCanvas - true if a canvas was really stolen
     */
    @Override
    public void handACanvas(int thiefId, int teamId, boolean rolledCanvas)
    {
        Team team = (Team) this.teamsHash.get(teamId);
        if (team == null) {
            throw new RuntimeException("Unknown team with id #" + teamId);
        }

        MessageBroker broker = (MessageBroker) this.teamsBroker.get(teamId);
        synchronized (this.chiefBroker) {

            synchronized (broker) {
                this.logger.setThiefStatus(thiefId, Logger.THIEF_STATUS.OUTSIDE);
                team.removeThief(thiefId);
            }

            this.chiefBroker.writeMessage(new Message(THIEF_ARRIVE_ACTION, thiefId));
            this.chiefBroker.writeMessage(new HandCanvasMessage(THIEF_HAND_CANVAS_ACTION, thiefId, team.getId(), rolledCanvas));

            if (this.multipleMasters) {
                this.chiefBroker.notifyAll();
            } else {
                this.chiefBroker.notify();
            }
        }
    }
}
