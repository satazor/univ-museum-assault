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

    public static final int PREPARE_ASSAULT_ACTION = 2;

    protected final MessageBroker chiefBroker = new IndexedMessageBroker();
    protected final MessageBroker thiefsBroker = new IndexedMessageBroker();

    protected HashMap roomsStatus = new HashMap();
    protected HashMap groupStatus = new HashMap();

    protected int nrRoomsToBeRobed;

    /**
     *
     */
    public SharedSite (int nrRooms)
    {
        this.nrRoomsToBeRobed = nrRooms;
    }

    /**
     *
     */
    public void preparseAssaultParty() {

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
                } catch (InterruptedException ex) { return 0; }
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
    public boolean amINeeded(int thiefId)
    {
        synchronized (this.chiefBroker) {

            this.chiefBroker.notify();

            while (true) {

                synchronized (this.thiefsBroker) {

                    System.out.println("[Thief #" + thiefId + "] Waiting for orders..");
                    try {
                        this.thiefsBroker.wait();
                    } catch (InterruptedException ex) {
                        return false;
                    }

                    Message message = this.thiefsBroker.readMessage(PREPARE_ASSAULT_ACTION);
                    if (message != null) {
                        return true;
                    }
                }
            }
        }
    }
}
