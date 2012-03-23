package museumassault;

/**
 *
 * @author André
 *
 * @TODO: There should be two exterior interfaces.. one for the
 * chief and one for the thiefs that only exposes their meaningfull members..
 */
public class Thief extends Thread
{
    public static final int ARRIVED_ACTION = 1;
    protected SharedSite exterior;
    protected int id;
    protected int teamId;

    /**
     *
     * @param id
     * @param exterior
     */
    public Thief(int id, SharedSite exterior)
    {
        this.id = id;
        this.exterior = exterior;
    }

    /**
     *
     */
    @Override
    public void run()
    {
        while (true) {

            try {
                this.teamId = this.exterior.amINeeded(this.id);

                // Prepare for excursion
                this.exterior.prepareExcursion(this.teamId);

                System.out.println("[Thief #" + this.id + "] Started craling in..");
                sleep(20000);

                // Simulate crawling
                sleep((long) (1 + 100 * Math.random()));

                // Hand the canvas
                this.exterior.handACanvas(this.id, this.teamId, true);

            } catch (InterruptedException ex) {}
        }
    }
}
