package museumassault;

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

            try {
                this.teamId = this.site.amINeeded(this.id);

                // Prepare for excursion
                this.site.prepareExcursion(this.teamId);

                System.out.println("[Thief #" + this.id + "] Started craling in..");

                // Simulate crawling
                sleep((long) (1 + 1000 * Math.random()));

                System.out.println("[Thief #" + this.id + "] Started craling out..");

                sleep((long) (1 + 1000 * Math.random()));

                // Hand the canvas
                System.out.println("[Thief #" + this.id + "] Handing canvas..");
                this.site.handACanvas(this.id, this.teamId, Math.random() < 0.9);

            } catch (InterruptedException ex) {}
        }
    }
}
