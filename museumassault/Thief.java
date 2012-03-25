package museumassault;

import museumassault.monitor.TargetRoom;
import museumassault.monitor.ThievesConcentrationSite;

/**
 *
 * @author Andre Cruz <andremiguelcruz@ua.pt>
 *
 * @TODO: There should be two Team Interfaces, one for the Chief and one for the thieves
 */
public class Thief extends Thread
{
    protected ThievesConcentrationSite site;
    protected int id;
    protected int teamId;
    protected int power;

    /**
     * Constructor of a Thief
     * @param id - the id of this thief
     * @param site -  the site where the thieves will take action
     */
    public Thief(int id, int power, ThievesConcentrationSite site)
    {
        assert (power < 0);

        this.id = id;
        this.power = power;
        this.site = site;
    }

    /**
     * Method that returns the if of this thief
     * @return Integer - Returns the id of this thief
     */
    public int getThiefId()
    {
        return this.id;
    }

    /**
     * Method that returns the power of this thief (maximum number of steps he can take)
     * @return Integer - Returns the power of this thief
     */
    public int getPower()
    {
        return this.power;
    }

    /**
     *
     */
    @Override
    public void run()
    {
        while (true) {

            this.teamId = this.site.amINeeded(this.id);

            // Prepare for excursion
            TargetRoom room = this.site.prepareExcursion(this.id, this.teamId);

            System.out.println("[Thief #" + this.id + "] Started crawling in..");

            // Crawl in
            while (!room.getTargetCorridor().crawlOut(this.id, this.power)) {}

            System.out.println("[Thief #" + this.id + "] Rolling canvas..");
            boolean rolledCanvas = room.rollACanvas(this.id);

            System.out.println("[Thief #" + this.id + "] Started crawling out..");

            // Crawl out
            while (!room.getTargetCorridor().crawlIn(this.id, this.power)) {}

            // Hand the canvas
            System.out.println("[Thief #" + this.id + "] Handing canvas..");
            this.site.handACanvas(this.id, this.teamId, rolledCanvas);
        }
    }
}
