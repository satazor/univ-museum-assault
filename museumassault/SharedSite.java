package museumassault;

import java.util.HashMap;

/**
 *
 * @author André
 */
public class SharedSite
{
    public static final int CHIEF_ORIGIN_TYPE = 1;
    public static final int THIEF_ORIGIN_TYPE = 2;

    public static final int THIEF_ARRIVED_ACTION = 1;
    public static final int THIEF_HANDED_A_CANVAS_ACTION = 2;
    public static final int THIEF_NOT_HANDED_A_CANVAS_ACTION = 3;

    public static final int PREPARE_ASSAULT_ACTION = 4;

    protected final MessageBroker chiefBroker = new IndexedMessageBroker();
    protected final MessageBroker thiefsBroker = new IndexedMessageBroker();

    protected HashMap roomsStatus = new HashMap();
    protected HashMap groupStatus = new HashMap();

    protected Team[] teams;
    protected HashMap teamsHash = new HashMap();
    protected HashMap teamsBroker = new HashMap();

    protected int nrRoomsToBeRobed;

    /**
     *
     */
    public SharedSite(int nrRooms, Team[] teams)
    {
        this.nrRoomsToBeRobed = nrRooms;
        this.teams = teams;

        int nrTeams = teams.length;
        for (int x = 0; x < nrTeams; x++) {
            this.teamsHash.put(teams[x].getId(), teams[x]);
            this.teamsBroker.put(teams[x].getId(), new IndexedMessageBroker());
        }
    }

    /**
     *
     */
    public Integer prepareAssaultParty()
    {
        synchronized (this.chiefBroker) {
            int nrTeams = this.teams.length;

            for (int x = 0; x < nrTeams; x++) {

                if (!this.teams[x].isBusy() && !this.teams[x].isPrepared()) {

                    this.teams[x].isPrepared(true);

                    MessageBroker broker = (MessageBroker) this.teamsBroker.get(this.teams[x].getId());
                    int nrThiefs = this.teams[x].getNrThiefs();

                    for (int y = 0; y < nrThiefs; y++) {
                        broker.writeMessage(new Message(PREPARE_ASSAULT_ACTION));
                        synchronized (broker) {
                            broker.notify();
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
    public void sendAssaultParty(int id)
    {
        synchronized (this.chiefBroker) {
            Team team = (Team) this.teamsHash.get(id);
            if (team == null) {
                throw new RuntimeException("Unknown team with id #" + id);
            }

            if (!team.isPrepared()) {
                throw new RuntimeException("Team with id #" + id + " is not prepared yet.");
            }

            if (team.isBusy()) {
                throw new RuntimeException("Team with id #" + id + " is busy.");
            }

            team.isBusy(true);
        }
    }

    /**
     *
     * @return
     */
    public int takeARest()
    {
        while (true) {

            synchronized (this.chiefBroker) {
                try {
                    this.chiefBroker.wait();
                } catch (InterruptedException ex) {}
            }

            int nrArrived = 0;
            Message message = this.chiefBroker.readMessage(THIEF_ARRIVED_ACTION);
            while (message != null) {
                nrArrived++;
                message = this.chiefBroker.readMessage(THIEF_ARRIVED_ACTION);
            }

            if (nrArrived > 0) {
                return nrArrived;
            }
        }
    }

    /**
     *
     */
    public boolean collectCanvas()
    {
        HandCanvasMessage message = (HandCanvasMessage) this.chiefBroker.readMessage(THIEF_HANDED_A_CANVAS_ACTION);
        return message.rolledCanvas();
    }

    /**
     *
     */
    public int getNrRoomsToBeRobed() {
        return this.nrRoomsToBeRobed;
    }

    /**
     *
     */
    public int prepareExcursion() {
        return 1;
    }

    /**
     *
     */
    public void handACanvas(int thiefId, int roomId, boolean rolledCanvas)
    {
        synchronized (this.thiefsBroker) {

            System.out.println("[Thief #" + thiefId + "] " + (rolledCanvas ? "" : "NOT ") + "Handing canvas..");
            if (!this.roomsStatus.containsKey(roomId)) {
                this.roomsStatus.put(roomId, !rolledCanvas);
            } else if (!rolledCanvas) {
                this.roomsStatus.put(roomId, true);
                this.nrRoomsToBeRobed--;
            }
        }

        this.chiefBroker.writeMessage(new HandCanvasMessage(thiefId, THIEF_HANDED_A_CANVAS_ACTION, roomId, rolledCanvas));
    }

    /**
     *
     */
    public boolean amINeeded(int thiefId, int teamId)
    {
        while (true) {

            MessageBroker broker = (MessageBroker) this.teamsBroker.get(teamId);
            if (broker == null) {
                throw new RuntimeException("Unknown team with id #" + teamId);
            }

            synchronized (broker) {

                System.out.println("[Thief #" + thiefId + "] Waiting for orders..");
                try {
                    broker.wait();
                } catch (InterruptedException ex) {}

                System.out.println("[Thief #" + thiefId + "] I am needed..");
            }
        }
    }
}
