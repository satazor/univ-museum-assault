package museumassault.thief;

import museumassault.room.RoomClient;
import museumassault.shared_site.SharedSiteThiefClient;

/**
 * Thief class.
 *
 * This class represents a thief that runs in a thread.
 *
 * @author Andre Cruz <andremiguelcruz@ua.pt>
 */
public class Thief extends Thread
{
    protected SharedSiteThiefClient site;
    protected int id;
    protected int teamId;
    protected int power;

    /**
     * Class constructor.
     *
     * @param id    the id of the thief
     * @param power the number of maximum positions a thief can crawl
     * @param site  the site where the thieves will concentrate (exterior)
     */
    public Thief(int id, int power, SharedSiteThiefClient site)
    {
        if (power <= 0) {
            throw new IllegalArgumentException("Thief power must be greater then zero.");
        }

        this.id = id;
        this.power = power;
        this.site = site;
    }

    /**
     * Get the thief id.
     *
     * @return The thief id
     */
    public int getThiefId()
    {
        return this.id;
    }

    /**
     * Get the power of the thief.
     * The power is associated to the maximum of positions allowed to crawl at once.
     *
     * @return the power of this thief
     */
    public int getPower()
    {
        return this.power;
    }

    /**
     * Runs the thief life cycle.
     */
    @Override
    public void run()
    {
        while (true) {

            this.teamId = this.site.amINeeded(this.id);

            // Prepare for excursion
            RoomClient room = this.site.prepareExcursion(this.id, this.teamId);
            break;
            //System.out.println("[Thief #" + this.id + "] Started crawling in..");

            // Crawl in
            //while (!room.getTargetCorridor().crawlOut(this.id, this.power)) {}

            //System.out.println("[Thief #" + this.id + "] Rolling canvas..");
            //boolean rolledCanvas = room.rollACanvas(this.id);

            //System.out.println("[Thief #" + this.id + "] Started crawling out..");

            // Crawl out
            //while (!room.getTargetCorridor().crawlIn(this.id, this.power)) {}

            // Hand the canvas
            //System.out.println("[Thief #" + this.id + "] Handing canvas..");
            //this.site.handACanvas(this.id, this.teamId, rolledCanvas);
        }
    }
}
