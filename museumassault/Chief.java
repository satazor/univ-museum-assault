package museumassault;

import java.util.HashMap;
import museumassault.monitor.ChiefControlSite;

/**
 *
 * @author Andre Cruz <andremiguelcruz@ua.pt>
 */
public class Chief extends Thread
{

    ChiefControlSite site;
    HashMap roomsStatus = new HashMap();
    protected int id;

    /**
     * Constructor of a Chief
     * @param id - the id of the chief
     * @param site - the site where the chief will control the thieves
     */
    public Chief(int id, ChiefControlSite site)
    {
        this.id = id;
        this.site = site;
    }

    /**
     *
     */
    public int getChiefId()
    {
        return this.id;
    }

    /**
     *
     */
    @Override
    public void run()
    {
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
                break;
            }

            System.out.println("[Chief] Arrived thief #" + thiefId + "..");
            System.out.println("[Chief] Collecting canvas of thief #" + thiefId + "..");
            this.site.collectCanvas(this.id, thiefId);
        }

        System.out.println("[Chief] Total canvas collected: " + this.site.sumUpResults(this.id));
    }
}
