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

            if (this.exterior.getNrRoomsToBeRobed() > 0) {

                System.out.println("[Chief] There is still rooms to rob..");

                Integer teamId = this.exterior.prepareAssaultParty();

                if (teamId != null) {
                    System.out.println("[Chief] There is at least one free team, assembling party #" + teamId + "..");
                    this.exterior.sendAssaultParty(teamId);
                } else {
                    System.out.println("[Chief] No free party available, taking a rest..");
                    this.waitForArrival();
                }
            } else {
                System.out.println("[Chief] All rooms robbed, waiting for remaining thiefs arrival..");
                this.waitForArrival();
            }
        }
    }

    /**
     *
     */
    protected void waitForArrival()
    {
        int nrArrived = this.exterior.takeARest();

        for (int x = 0; x < nrArrived; x++) {
            if (this.exterior.collectCanvas()) {
                System.out.println("[Chief] Collected canvas..");
            } else {
                System.out.println("[Chief] Thief didn't robed any canvas..");
            }
        }
    }
}
