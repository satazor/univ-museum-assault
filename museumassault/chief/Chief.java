package museumassault.chief;

import java.util.List;
import museumassault.common.Configuration;
import museumassault.common.exception.ComException;
import museumassault.common.exception.ShutdownException;
import museumassault.corridor.client.CorridorClient;
import museumassault.room.client.RoomClient;
import museumassault.shared_site.client.SharedSiteChiefClient;

/**
 * Chief class.
 *
 * This class represents a chief that runs in a thread.
 * The thread will live until the rooms are empty and all the canvasses are collected.
 *
 * @author Andre Cruz <andremiguelcruz@ua.pt>
 */
public class Chief extends Thread
{
    protected SharedSiteChiefClient site;
    protected int id;
    protected Configuration configuration;

    /**
     * Class constructor.
     *
     * @param id   the id of the chief
     * @param site the chief control site (exterior)
     */
    public Chief(int id, SharedSiteChiefClient site, Configuration configuration)
    {
        this.id = id;
        this.site = site;
        this.configuration = configuration;
    }

    /**
     * Get the thief id.
     *
     * @return the id of the chief
     */
    public int getChiefId()
    {
        return this.id;
    }

    /**
     * Runs the chief life cycle.
     */
    @Override
    public void run()
    {
        try {
            while (true) {
                System.out.println("[Chief] Appraising sit..");
                Integer roomId = this.site.appraiseSit(this.id);

                if (roomId != null) {

                    System.out.println("[Chief] There is still rooms to rob, preparing assault to room #" + roomId + "..");

                    Integer teamId = this.site.prepareAssaultParty(this.id, roomId);
                    if (teamId != null) {
                        System.out.println("[Chief] Sending party #" + teamId + "..");
                        this.site.sendAssaultParty(this.id, teamId);
                        System.out.println("[Chief] Party #" + teamId + " sent..");
                        continue;
                    } else {
                        System.out.println("[Chief] Preparation of assault to #" + roomId + " aborted, no teams available..");
                    }
                }

                System.out.println("[Chief] Taking a rest waiting for thieves..");
                Integer thiefId = this.site.takeARest(this.id);
                if (thiefId == null) {
                    if (roomId == null) {
                        break;
                    }
                } else {
                    System.out.println("[Chief] Arrived thief #" + thiefId + "..");
                    System.out.println("[Chief] Collecting canvas of thief #" + thiefId + "..");
                    this.site.collectCanvas(this.id, thiefId);
                }

            }

            System.out.println("[Chief] Total canvas collected: " + this.site.sumUpResults(this.id));

            // Shutdown the services
            String shutdownPassword = this.configuration.getShutdownPassword();
            this.site.shutdown(shutdownPassword);

            List<Integer> ids = this.configuration.getRoomIds();
            int nrRooms = ids.size();
            for (int x = 0; x < nrRooms; x++) {
                CorridorClient corridorClient = new CorridorClient(this.configuration.getCorridorConnectionString(this.configuration.getRoomCorridorId(ids.get(x))));
                corridorClient.shutdown(shutdownPassword);

                RoomClient roomClient = new RoomClient(this.configuration.getRoomConnectionString(ids.get(x)));
                roomClient.shutdown(shutdownPassword);
            }

        } catch (ShutdownException ex) {
            System.err.println("Service was shutted down.");
        } catch (ComException ex) {
            System.err.println(ex.getMessage());
        }
    }
}
