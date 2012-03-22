package museumassault;

/**
 *
 * @author André
 *
 * @TODO: There should be two exterior interfaces.. one for the
 * chief and one for the thiefs that only exposes their meaningfull members..
 */
public class Thief extends Thread {

    public static final int ARRIVED_ACTION = 1;
    protected ExteriorSite exterior;
    protected int id;

    public Thief(int id, ExteriorSite exterior)
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
        this.exterior.handACanvas(this.id);
    }
}
