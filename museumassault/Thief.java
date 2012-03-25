package museumassault;

import museumassault.monitor.TargetRoom;
import museumassault.monitor.ThievesConcentrationSite;

/**
 *
 * @author André
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
     *
     * @param id
     * @param site
     */
    public Thief(int id, int power, ThievesConcentrationSite site)
    {
        if (power < 0) {
            throw new RuntimeException("Power must be greater than zero.");
        }

        this.id = id;
        this.power = power;
        this.site = site;
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
            TargetRoom room = this.site.prepareExcursion(this.teamId);

            System.out.println("[Thief #" + this.id + "] Started crawling in..");

            // Crawl in
            while (!room.getTargetCorridor().crawlOut(this.id, this.power)) {}

            System.out.println("[Thief #" + this.id + "] Rolling canvas..");
            boolean rolledCanvas = room.rollACanvas();

            System.out.println("[Thief #" + this.id + "] Started crawling out..");

            // Crawl out
            while (!room.getTargetCorridor().crawlIn(this.id, this.power)) {}

            // Hand the canvas
            System.out.println("[Thief #" + this.id + "] Handing canvas..");
            this.site.handACanvas(this.id, this.teamId, rolledCanvas);
        }
    }
}
