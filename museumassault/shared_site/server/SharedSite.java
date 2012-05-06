package museumassault.shared_site.server;

import java.util.HashMap;
import java.util.List;
import museumassault.common.Message;
import museumassault.common.MessageRepository;
import museumassault.logger.client.LoggerClient;
import museumassault.shared_site.IChiefMessageConstants;
import museumassault.shared_site.IThiefMessageConstants;

/**
 * SharedSite class.
 *
 * This class implements the chief control site and the thieves concentration site
 * interfaces.
 *
 * @see ChiefControlSite
 * @see ThievesConcentrationSite
 * @author Andre Cruz <andremiguelcruz@ua.pt>
 */
public class SharedSite implements IChiefMessageConstants, IThiefMessageConstants
{
    public static final int PREPARE_ASSAULT_ACTION = 1;
    public static final int SEND_ASSAULT_PARTY_ACTION = 2;
    public static final int THIEF_HAND_CANVAS_ACTION = 3;
    public static final int THIEF_ARRIVE_ACTION = 4;
    public static final int THIEF_READY_FOR_DEPARTURE_ACTION = 5;

    protected final MessageRepository chiefRepository = new MessageRepository();
    protected final MessageRepository thievesRepository = new MessageRepository();

    protected List<Integer> roomIds;
    protected HashMap<Integer, Boolean> roomsEngagedStatus = new HashMap<>();
    protected HashMap<Integer, Boolean> roomsCanvasStatus = new HashMap<>();

    protected Team[] teams;
    protected HashMap<Integer, Team> teamsHash = new HashMap<>();
    protected HashMap<Integer, MessageRepository> teamsRepository = new HashMap<>();

    protected int nrRoomsToBeRobed;
    protected int nrCollectedCanvas = 0;
    protected boolean multipleMasters;
    protected int nrIdleThieves = 0;

    protected LoggerClient logger;

    /**
     * Class constructor.
     *
     * @param roomIds the museum room ids
     * @param teams   the teams
     * @param logger  the logger
     */
    public SharedSite(List<Integer> roomIds, Team[] teams, LoggerClient logger)
    {
        this.initialize(roomIds, teams, logger);
        this.multipleMasters = false;
    }

    /**
     * Class constructor.
     *
     * @param roomIds         the museum room ids
     * @param teams           the teams
     * @param logger          the logger
     * @param multipleMasters true if muliple chiefs (masters) coexist, false otherwise
     */
    public SharedSite(List<Integer> roomIds, Team[] teams, LoggerClient logger, boolean multipleMasters)
    {
        this.initialize(roomIds, teams, logger);
        this.multipleMasters = multipleMasters;
    }

    /**
     * Initializes the instance.
     *
     * @param roomIds the museum rooms
     * @param teams   the teams
     * @param logger  the logger to log the program state
     */
    protected final void initialize(List<Integer> roomIds, Team[] teams, LoggerClient logger)
    {
        this.teams = teams;
        int nrTeams = teams.length;
        for (int x = 0; x < nrTeams; x++) {
            this.teamsHash.put(teams[x].getId(), teams[x]);
            this.teamsRepository.put(teams[x].getId(), new MessageRepository());
        }

        this.roomIds = roomIds;
        this.nrRoomsToBeRobed = roomIds.size();
        for (int x = 0; x < this.nrRoomsToBeRobed; x++) {
            this.roomsEngagedStatus.put(this.roomIds.get(x), false);
            this.roomsCanvasStatus.put(this.roomIds.get(x), true);
        }

        this.logger = logger;
    }

    /**
     * Get the total number of canvas collected.
     *
     * @return the number of canvas
     */
    public int getNrCollectedCanvas()
    {
        return this.nrCollectedCanvas;
    }

    /**
     * Decide if the chief should sit or rob a room.
     *
     * @param chiefId the id of the chief
     *
     * @return the id of the room that should be robed or null if the chief should sit
     */
    public Integer appraiseSit(int chiefId)
    {
        synchronized (this.chiefRepository) {

            this.logger.setChiefStatus(chiefId, LoggerClient.CHIEF_STATUS.DECIDING_WHAT_TO_DO);

            if (this.nrRoomsToBeRobed > 0) {

                int nrRooms = this.roomIds.size();
                for (int x = 0; x < nrRooms; x++) {
                    int roomId = this.roomIds.get(x);
                    if ((boolean) this.roomsCanvasStatus.get(roomId) && !this.roomsEngagedStatus.get(roomId)) {
                        this.roomsEngagedStatus.put(roomId, true);
                        return roomId;
                    }
                }
            }

            return null;
        }
    }

    /**
     * Prepares an assault team, assigning a team that will be responsible for it.
     *
     * @param chiefId the id of the chief
     * @param roomId  the room that will be robbed
     *
     * @return returns the id of the assigned team or null if none is free
     */
    public Integer prepareAssaultParty(int chiefId, int roomId)
    {
        if (!this.roomsCanvasStatus.containsKey(roomId)) {
            throw new IllegalArgumentException("Unknown room with id #" + roomId);
        }

        synchronized (this.chiefRepository) {

            this.logger.setChiefStatus(chiefId, LoggerClient.CHIEF_STATUS.ASSEMBLING_A_GROUP);

            int nrTeams = this.teams.length;

            for (int x = 0; x < nrTeams; x++) {

                if (!this.teams[x].isBusy() && !this.teams[x].isBeingPrepared() && this.nrIdleThieves >= this.teams[x].getCapacity()) {

                    this.teams[x].isBeingPrepared(true);
                    this.teams[x].setAssignedRoomId(roomId);
                    // TODO: check this
                    //room.getCorridor().clearPositions();

                    int nrThieves = this.teams[x].getCapacity();
                    for (int y = 0; y < nrThieves; y++) {
                        this.thievesRepository.writeMessage(new Message(PREPARE_ASSAULT_ACTION, (Integer) this.teams[x].getId()));
                        synchronized (this.thievesRepository) {
                            this.thievesRepository.notify();
                        }
                    }

                    return this.teams[x].getId();
                }
            }

            this.roomsEngagedStatus.put(roomId, false);

            return null;
        }
    }

    /**
     * Method that sends a previously prepared team to rob the designated room.
     * This method guarantees that all the thieves depart at the same time.
     *
     * @param chiefId the id of the chief
     * @param teamId  the team to be sent
     */
    public void sendAssaultParty(int chiefId, int teamId)
    {
        Team team = (Team) this.teamsHash.get(teamId);
        if (team == null) {
            throw new IllegalArgumentException("Unknown team with id #" + teamId);
        }

        synchronized (this.chiefRepository) {

            if (!team.isBeingPrepared()) {
                throw new IllegalStateException("Team with id #" + teamId + " is not being prepared.");
            }

            MessageRepository repository = (MessageRepository) this.teamsRepository.get(teamId);
            int nrThieves = team.getCapacity();
            for (int x = 0; x < nrThieves; x++) {

                while (repository.readMessage(THIEF_READY_FOR_DEPARTURE_ACTION) == null) {
                    Thread.yield();
                }

                repository.writeMessage(new Message(SEND_ASSAULT_PARTY_ACTION));
            }

            //System.out.println("[Chief] Notifying all thieves of the party to start crawling to room #" + team.getAssignedRoom().getId() + "..");

            synchronized (repository) {
                repository.notifyAll();
            }
        }
    }

    /**
     * Takes a rest, waiting for a thief arrival.
     *
     * @param chiefId the id of the chief
     *
     * @return the id of the thief that arrived or null if there is no remaining thieves
     */
    public Integer takeARest(int chiefId)
    {
        while (true) {

            synchronized (this.chiefRepository) {

                this.logger.setChiefStatus(chiefId, LoggerClient.CHIEF_STATUS.WAITING_FOR_ARRIVAL);

                Message message = this.chiefRepository.readMessage(THIEF_ARRIVE_ACTION);
                if (message != null) {

                    Integer teamId = (Integer) message.getExtra();
                    Team team = (Team) this.teamsHash.get(teamId);
                    if (team == null) {
                        throw new IllegalArgumentException("Unknown team with id #" + teamId);
                    }

                    MessageRepository repository = (MessageRepository) this.teamsRepository.get(teamId);

                    synchronized (repository) {
                        this.logger.setThiefStatus(message.getOriginId(), LoggerClient.THIEF_STATUS.OUTSIDE);
                        team.removeThief(message.getOriginId());

                        if (!team.isBusy()) {
                            this.roomsEngagedStatus.put(team.getAssignedRoomId(), false);
                        }
                    }

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
                    this.chiefRepository.wait();
                } catch (InterruptedException ex) {}
            }
        }
    }

    /**
     * Collects the canvas of thief that arrived.
     *
     * @param chiefId the id of the chief
     * @param thiefId the id of the thief that handed the canvas
     */
    public void collectCanvas(int chiefId, int thiefId)
    {
        while (true) {

            Message message = (Message) this.chiefRepository.readMessage(THIEF_HAND_CANVAS_ACTION, thiefId);

            if (message != null) {
                HashMap <String, Object> extra = (HashMap <String, Object>) message.getExtra();
                Integer teamId = (Integer) extra.get("teamId");
                Boolean rolledCanvas = (Boolean) extra.get("rolledCanvas");

                Team team = (Team) this.teamsHash.get(teamId);

                if (team == null) {
                    throw new IllegalArgumentException("Unknown team with id #" + teamId);
                }

                synchronized (this.chiefRepository) {

                    if (rolledCanvas) this.nrCollectedCanvas++;

                    int roomId = team.getAssignedRoomId();
                    if ((boolean) this.roomsCanvasStatus.get(roomId) && !rolledCanvas) {
                        this.roomsCanvasStatus.put(roomId, false);
                        System.out.println("this.nrRoomsToBeRobed " + this.nrRoomsToBeRobed +  " " + roomId);
                        this.nrRoomsToBeRobed--;
                    }
                }

                break;
            }
        }
    }

    /**
     * Sums up the total canvas stolen.
     *
     * @param chiefId the id of the chief
     *
     * @return the total number of canvas stolen
     */
    public Integer sumUpResults(int chiefId) {

        synchronized (this.chiefRepository) {
            this.logger.setChiefStatus(chiefId, LoggerClient.CHIEF_STATUS.PRESENTING_THE_REPORT);

            return this.nrCollectedCanvas;
        }
    }

    /**
     * Waits until the thief is needed.
     *
     * @param thiefId the thief id
     *
     * @return the id of the team in which this thief is needed
     */
    public Integer amINeeded(int thiefId)
    {
        while (true) {

            synchronized (this.thievesRepository) {

                //System.out.println("[Thief #" + thiefId + "] Waiting for orders..");
                this.nrIdleThieves++;
                try {
                    this.thievesRepository.wait();
                } catch (InterruptedException ex) {}


                Message message = (Message) this.thievesRepository.readMessage(PREPARE_ASSAULT_ACTION);
                if (message != null) {
                    //System.out.println("[Thief #" + thiefId + "] I am needed on team " + ((Integer) message.getExtra()) + "..");
                    this.nrIdleThieves--;
                    return (Integer) message.getExtra();
                }
            }
        }
    }

    /**
     * Prepares the thief for the excursion.
     * This method should signal the master that the thief is ready and only depart
     * when notified.
     *
     * @param thiefId the thief id
     * @param teamId  the team in which the thief belongs
     *
     * @return the id of the room assigned to the team
     */
    public Integer prepareExcursion(int thiefId, int teamId) {

        MessageRepository repository = (MessageRepository) this.teamsRepository.get(teamId);
        if (repository == null) {
            throw new IllegalArgumentException("Unknown team with id #" + teamId);
        }

        boolean sentMessage = false;
        while (true) {

            Team team;
            synchronized (repository) {

                if (!sentMessage) {
                    sentMessage = true;
                    repository.writeMessage(new Message(THIEF_READY_FOR_DEPARTURE_ACTION));
                }

                team = (Team) this.teamsHash.get(teamId);
                team.addThief(thiefId);

                try {
                    repository.wait();
                } catch (InterruptedException ex) {}
            }

            Message message = repository.readMessage(SEND_ASSAULT_PARTY_ACTION);
            if (message != null) {
                return team.getAssignedRoomId();
            }
        }
    }

    /**
     * Hands a canvas to the chief.
     *
     * @param thiefId      the thief id
     * @param teamId       the team where that thief belongs
     * @param rolledCanvas true if a canvas was stolen, false otherwise
     */
    public void handACanvas(int thiefId, int teamId, boolean rolledCanvas)
    {
        Team team = (Team) this.teamsHash.get(teamId);
        if (team == null) {
            throw new IllegalArgumentException("Unknown team with id #" + teamId);
        }

        synchronized (this.chiefRepository) {

            this.chiefRepository.writeMessage(new Message(THIEF_ARRIVE_ACTION, thiefId, teamId));
            HashMap <String, Object> extra = new HashMap<>();
            extra.put("teamId", team.getId());
            extra.put("rolledCanvas", rolledCanvas);

            this.chiefRepository.writeMessage(new Message(THIEF_HAND_CANVAS_ACTION, thiefId, extra));

            if (this.multipleMasters) {
                this.chiefRepository.notifyAll();
            } else {
                this.chiefRepository.notify();
            }
        }
    }
}
