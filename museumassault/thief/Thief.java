package museumassault.thief;

import museumassault.common.IThievesConfiguration;
import museumassault.common.exception.ComException;
import museumassault.common.exception.ShutdownException;
import museumassault.corridor.client.CorridorClient;
import museumassault.room.client.RoomClient;
import museumassault.shared_site.client.SharedSiteThiefClient;

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
    protected IThievesConfiguration configuration;

    /**
     * Class constructor.
     *
     * @param id            the id of the thief
     * @param power         the number of maximum positions a thief can crawl
     * @param site          the site where the thieves will concentrate (exterior)
     * @param configuration the configuration so that a thief can extract the room/corridor addresses
     */
    public Thief(int id, int power, SharedSiteThiefClient site, IThievesConfiguration configuration)
    {
        if (power <= 0) {
            throw new IllegalArgumentException("Thief power must be greater then zero.");
        }

        this.id = id;
        this.power = power;
        this.site = site;
        this.configuration = configuration;
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
        try {
            while (true) {

                this.teamId = this.site.amINeeded(this.id);

                // Prepare for excursion
                Integer roomId = this.site.prepareExcursion(this.id, this.teamId);
                System.out.println("Excursion to " + roomId);
                System.out.println("[Thief #" + this.id + "] Started crawling in..");

                Integer corridorId = this.configuration.getRoomCorridorId(roomId);
                if (corridorId == null) {
                    System.err.println("Unknown corridor id sent by the server: " + corridorId);
                    return;
                }

                CorridorClient corridor = new CorridorClient(this.configuration.getCorridorConnectionString(corridorId));
                RoomClient room = new RoomClient(this.configuration.getRoomConnectionString(roomId));

                // Crawl in
                while (!corridor.crawlOut(this.id, this.power)) {}

                //System.out.println("[Thief #" + this.id + "] Rolling canvas..");
                boolean rolledCanvas = room.rollACanvas(this.id);

                System.out.println("[Thief #" + this.id + "] Started crawling out..");

                // Crawl out
                while (!corridor.crawlIn(this.id, this.power)) {}

                // Hand the canvas
                System.out.println("[Thief #" + this.id + "] Handing canvas..");
                this.site.handACanvas(this.id, this.teamId, rolledCanvas);
            }
        } catch (ShutdownException ex) {
            System.err.println("Service was shutted down.");
        } catch (ComException ex) {
            System.err.println(ex.getMessage());
        }
    }
}
