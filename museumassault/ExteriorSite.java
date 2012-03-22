package museumassault;

/**
 *
 * @author André
 */
public class ExteriorSite
{
    public static final int CHIEF_ORIGIN_TYPE = 1;
    public static final int THIEF_ORIGIN_TYPE = 2;

    public static final int THIEF_ARRIVED_ACTION = 1;

    protected MessageBroker broker;
    protected final Object chiefMonitor = new Object();
    protected final Object thiefsMonitor = new Object();

    /**
     *
     */
    public ExteriorSite(MessageBroker broker)
    {
        this.broker = broker;
    }

    /**
     *
     * @return
     */
    public Integer takeARest()
    {
        while (true) {

            synchronized(this.chiefMonitor) {
                try {
                    this.chiefMonitor.wait();
                } catch (InterruptedException ex) {
                    return null;
                }
            }

            Message message = this.broker.readMessage(THIEF_ORIGIN_TYPE, THIEF_ARRIVED_ACTION);
            if (message != null) {
                return message.getOriginId();
            }
        }
    }

    /**
     *
     */
    public void handACanvas(int thiefId)
    {
        this.broker.writeMessage(THIEF_ORIGIN_TYPE, new Message(thiefId, THIEF_ARRIVED_ACTION));

        synchronized(this.chiefMonitor) {
            System.out.println("[Thief #" + thiefId + "] Handing canvas..");
            this.chiefMonitor.notify();
        }
    }
}
