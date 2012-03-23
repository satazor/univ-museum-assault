package museumassault;

import java.util.HashMap;
import museumassault.monitor.ChiefControlSite;

/**
 *
 * @author André
 */
public class Chief extends Thread
{

    ChiefControlSite site;
    HashMap roomsStatus = new HashMap();
    protected int id;

    /**
     *
     * @param site
     */
    public Chief (int id, ChiefControlSite site)
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

            System.out.println("[Chief] Aprraising sit..");
            Integer roomId = this.site.appraiseSit();
            if (roomId != null) {

                System.out.println("[Chief] There is still rooms to rob, preparing assault to room #" + roomId + "..");

                Integer teamId = this.site.prepareAssaultParty(roomId);
                if (teamId != null) {
                    System.out.println("[Chief] Sending party #" + teamId + "..");
                    this.site.sendAssaultParty(teamId);
                    System.out.println("[Chief] Party #" + teamId + " sent..");
                    continue;
                } else {
                    System.out.println("[Chief] Preparation of assault to #" + roomId + " aborted, no teams available..");
                }
            }

            System.out.println("[Chief] Taking a rest waiting for thieves..");
            Integer thiefId = this.site.takeARest();
            if (thiefId == null) {
                break;
            }

            System.out.println("[Chief] Arrived thief #" + thiefId + "..");
            System.out.println("[Chief] Collecting canvas of thief #" + thiefId + "..");
            this.site.collectCanvas(thiefId);
        }
    }
}
