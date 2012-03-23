package museumassault;

import java.util.HashMap;

/**
 *
 * @author André
 */
public class SharedSite
{
    public static final int PREPARE_ASSAULT_ACTION = 1;
    public static final int SEND_ASSAULT_PARTY_ACTION = 2;
    public static final int THIEF_ARRIVE_ACTION = 3;
    public static final int THIEF_READY_FOR_DEPARTURE_ACTION = 4;

    protected final MessageBroker chiefBroker = new IndexedMessageBroker();
    protected final MessageBroker thiefsBroker = new IndexedMessageBroker();

    protected Room[] rooms;
    protected HashMap roomsHash = new HashMap();

    protected Team[] teams;
    protected HashMap teamsHash = new HashMap();
    protected HashMap teamsBroker = new HashMap();

    protected int nrRoomsToBeRobed;
    protected int nrCanvasCollected = 0;

    /**
     *
     */
    public SharedSite(Room[] rooms, Team[] teams)
    {
        this.teams = teams;
        int nrTeams = teams.length;
        for (int x = 0; x < nrTeams; x++) {
            this.teamsHash.put(teams[x].getId(), teams[x]);
            this.teamsBroker.put(teams[x].getId(), new IndexedMessageBroker());
        }

        this.rooms = rooms;
        this.nrRoomsToBeRobed = rooms.length;
        for (int x = 0; x < this.nrRoomsToBeRobed; x++) {
            this.roomsHash.put(rooms[x].getId(), rooms[x]);
        }
    }

    /**
     *
     */
    public Integer appraiseSit()
    {
        synchronized (this.chiefBroker) {

            if (this.nrRoomsToBeRobed > 0) {

                int nrRooms = this.rooms.length;
                for (int x = 0; x < nrRooms; x++) {
                    if (!this.rooms[x].isBeingRobed()) {
                        this.rooms[x].isBeingRobed(true);
                        return this.rooms[x].getId();
                    }
                }
            }

            return null;
        }
    }

    /**
     *
     */
    public Integer prepareAssaultParty(int roomId)
    {
        synchronized (this.chiefBroker) {

            int nrTeams = this.teams.length;

            for (int x = 0; x < nrTeams; x++) {

                if (!this.teams[x].isBusy() && !this.teams[x].isPrepared()) {

                    this.teams[x].isPrepared(true);
                    int nrThiefs = this.teams[x].getNrThiefs();
                    for (int y = 0; y < nrThiefs; y++) {
                        this.thiefsBroker.writeMessage(new PrepareAssaultMessage(PREPARE_ASSAULT_ACTION, this.teams[x].getId(), roomId));
                        synchronized (this.thiefsBroker) {
                            this.thiefsBroker.notify();
                        }
                    }

                    return this.teams[x].getId();
                }
            }

            return null;
        }
    }

    /**
     *
     */
    public void sendAssaultParty(int teamId)
    {
        synchronized (this.chiefBroker) {

            Team team = (Team) this.teamsHash.get(teamId);
            if (team == null) {
                throw new RuntimeException("Unknown team with id #" + teamId);
            }

            if (!team.isPrepared()) {
                throw new RuntimeException("Team with id #" + teamId + " is not prepared yet.");
            }

            if (team.isBusy()) {
                throw new RuntimeException("Team with id #" + teamId + " is busy.");
            }

            team.isBusy(true);

            MessageBroker broker = (MessageBroker) this.teamsBroker.get(teamId);
            int nrThiefs = team.getNrThiefs();
            for (int x = 0; x < nrThiefs; x++) {

                while (broker.readMessage(THIEF_READY_FOR_DEPARTURE_ACTION) == null) {
                    Thread.yield();
                }

                broker.writeMessage(new Message(SEND_ASSAULT_PARTY_ACTION));
            }

            synchronized (broker) {
                broker.notifyAll();
            }
        }
    }

    /**
     *
     * @return
     */
    public void takeARest()
    {
        while (true) {

            synchronized (this.chiefBroker) {
                try {
                    this.chiefBroker.wait();
                } catch (InterruptedException ex) {}
            }

            ArriveMessage message = (ArriveMessage) this.chiefBroker.readMessage(THIEF_ARRIVE_ACTION);
            while (message != null) {
                if (message.rolledCanvas()) this.nrCanvasCollected++;
                message = (ArriveMessage) this.chiefBroker.readMessage(THIEF_ARRIVE_ACTION);
            }
        }
    }

    /**
     *
     */
    public Integer amINeeded(int thiefId)
    {
        while (true) {

            synchronized (this.thiefsBroker) {

                System.out.println("[Thief #" + thiefId + "] Waiting for orders..");
                try {
                    this.thiefsBroker.wait();
                } catch (InterruptedException ex) {}

            }

            PrepareAssaultMessage message = (PrepareAssaultMessage) this.thiefsBroker.readMessage(PREPARE_ASSAULT_ACTION);
            if (message != null) {
                System.out.println("[Thief #" + thiefId + "] I am needed on team " + message.getTeamId() + "..");
                return message.getTeamId();
            }
        }
    }


    /**
     *
     */
    public void prepareExcursion(int teamId) {

        MessageBroker broker = (MessageBroker) this.teamsBroker.get(teamId);
        if (broker == null) {
            throw new RuntimeException("Unknown team with id #" + teamId);
        }

        broker.writeMessage(new Message(THIEF_READY_FOR_DEPARTURE_ACTION));

        while (true) {

            synchronized (broker) {
                try {
                    broker.wait();
                } catch (InterruptedException ex) {}
            }

            Message message = broker.readMessage(SEND_ASSAULT_PARTY_ACTION);
            if (message != null) {
                break;
            }
        }
    }

    /**
     *
     */
    public void handACanvas(int thiefId, int roomId, boolean rolledCanvas)
    {
        synchronized (this.thiefsBroker) {

            System.out.println("[Thief #" + thiefId + "] " + (rolledCanvas ? "" : "NOT ") + "Handing canvas..");
            Room room = (Room) this.roomsHash.get(roomId);
            if (room == null) {
                throw new RuntimeException("Unknown room with id #" + roomId);
            }

            if (room.stillHasCanvas() && !rolledCanvas) {
                room.stillHasCanvas(false);
                this.nrRoomsToBeRobed--;
            }
        }

        this.chiefBroker.writeMessage(new ArriveMessage(THIEF_ARRIVE_ACTION, roomId, rolledCanvas));
    }
}
