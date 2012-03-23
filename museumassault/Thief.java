package museumassault;

/**
 *
 * @author André
 *
 * @TODO: There should be two Site interfaces.. one for the
 *        chief and one for the thiefs that only exposes their meaningfull members..
 *
 * @TODO: There should be two Team Interfaces, one for the Chief and one for the Thiefs
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

                // Simulate crawling
                sleep((long) (1 + 1000 * Math.random()));

                System.out.println("[Thief #" + this.id + "] Started craling out..");

                sleep((long) (1 + 1000 * Math.random()));

                // Hand the canvas
                System.out.println("[Thief #" + this.id + "] Handing canvas..");
                this.exterior.handACanvas(this.id, this.teamId, Math.random() < 0.9);

            } catch (InterruptedException ex) {}
        }
    }
}
