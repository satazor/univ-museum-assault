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

    /**
     *
     * @param id
     * @param site
     */
    public Thief(int id, ThievesConcentrationSite site)
    {
        this.id = id;
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

            // Crall in
            while (!room.getTargetCorridor().crallIn(this.id, 1)) {}

            System.out.println("[Thief #" + this.id + "] Rolling canvas..");
            boolean rolledCanvas = room.rollACanvas();

            System.out.println("[Thief #" + this.id + "] Started crawling out..");

            // Crall out
            while (!room.getTargetCorridor().crallOut(this.id, 1)) {}

            // Hand the canvas
            System.out.println("[Thief #" + this.id + "] Handing canvas..");
            this.site.handACanvas(this.id, this.teamId, rolledCanvas);
        }
    }
}
