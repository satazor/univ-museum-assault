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
    public Thief(int id, int teamId, SharedSite exterior)
    {
        this.id = id;
        this.teamId = teamId;
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
                // Check if the thief is still needed
                if (!this.exterior.amINeeded(this.id, this.teamId)) {
                    break;
                }

                // Prepare for excursion
                this.exterior.prepareExcursion();

                sleep(20000);

                // Simulate crawling
                sleep((long) (1 + 100 * Math.random()));

                // Hand the canvas
                this.exterior.handACanvas(this.id, this.teamId, true);

            } catch (InterruptedException ex) {}
        }
    }
}
