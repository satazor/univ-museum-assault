package museumassault.chief;

import java.util.List;
import museumassault.common.Configuration;
import museumassault.common.exception.ComException;
import museumassault.common.exception.ShutdownException;
import museumassault.corridor.client.CorridorClient;
import museumassault.logger.client.LoggerClient;
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
    protected LoggerClient logger;

    /**
     * Class constructor.
     *
     * @param id   the id of the chief
     * @param site the chief control site (exterior)
     */
    public Chief(int id, SharedSiteChiefClient site, LoggerClient logger, Configuration configuration)
    {
        this.id = id;
        this.site = site;
        this.logger = logger;
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
                System.out.println("[Chief #" + this.id + "] Appraising sit..");
                Integer roomId = this.site.appraiseSit(this.id);

                if (roomId != null) {

                    System.out.println("[Chief #" + this.id + "] There is still rooms to rob, preparing assault to room #" + roomId + "..");

                    Integer teamId = this.site.prepareAssaultParty(this.id, roomId);
                    if (teamId != null) {
                        System.out.println("[Chief #" + this.id + "] Sending party #" + teamId + "..");
                        this.site.sendAssaultParty(this.id, teamId);
                        System.out.println("[Chief #" + this.id + "] Party #" + teamId + " sent..");
                        continue;
                    } else {
                        System.out.println("[Chief #" + this.id + "] Preparation of assault to #" + roomId + " aborted, no teams available..");
                    }
                }

                System.out.println("[Chief #" + this.id + "] Taking a rest waiting for thieves..");
                Integer thiefId = this.site.takeARest(this.id);
                if (thiefId == null) {
                    if (roomId == null) {
                        break;
                    }
                } else {
                    System.out.println("[Chief #" + this.id + "] Arrived thief #" + thiefId + "..");
                    System.out.println("[Chief #" + this.id + "] Collecting canvas of thief #" + thiefId + "..");
                    this.site.collectCanvas(this.id, thiefId);
                }

            }

            int totalCanvas = this.site.sumUpResults(this.id);
            System.out.println("[Chief #" + this.id + "] Total canvas collected: " + totalCanvas);

            // Shutdown the services
            String shutdownPassword = this.configuration.getShutdownPassword();
            this.site.shutdown(shutdownPassword);

            List<Integer> ids = this.configuration.getRoomIds();
            int nrRooms = ids.size();
            for (int x = 0; x < nrRooms; x++) {
                int roomId = ids.get(x);
                int corridorId = this.configuration.getRoomCorridorId(roomId);
                CorridorClient corridorClient = new CorridorClient(this.configuration.getCorridorHost(corridorId), this.configuration.getCorridorPort(corridorId));
                corridorClient.shutdown(shutdownPassword);

                RoomClient roomClient = new RoomClient(this.configuration.getRoomHost(roomId), this.configuration.getRoomPort(roomId));
                roomClient.shutdown(shutdownPassword);
            }

            this.logger.shutdown(this.configuration.getShutdownPassword(), totalCanvas);
        } catch (ShutdownException ex) {
            System.err.println("Service was shutted down.");
        } catch (ComException ex) {
            System.err.println(ex.getMessage());
        }
    }
}
