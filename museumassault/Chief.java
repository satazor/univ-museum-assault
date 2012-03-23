package museumassault;

import java.util.HashMap;

/**
 *
 * @author André
 */
public class Chief extends Thread
{
    static final int CHIEF_ID = -1;

    SharedSite exterior;
    HashMap roomsStatus = new HashMap();

    /**
     *
     * @param exterior
     */
    public Chief (SharedSite exterior)
    {
        this.exterior = exterior;
    }

    /**
     *
     */
    @Override
    public void run()
    {
        while (true) {

            Integer roomId = this.exterior.appraiseSit();
            if (roomId != null) {

                System.out.println("[Chief] There is still rooms to rob, preparing assault to room #" + roomId + "..");

                Integer teamId = this.exterior.prepareAssaultParty(roomId);
                if (teamId != null) {
                    System.out.println("[Chief] Sending party #" + teamId + "..");
                    this.exterior.sendAssaultParty(teamId);
                } else {
                    System.out.println("[Chief] No free party available, taking a rest..");
                    this.exterior.takeARest();
                }
            } else {
                System.out.println("[Chief] All rooms robbed, waiting for remaining thiefs arrival..");
                this.exterior.takeARest();
            }
        }
    }
}
