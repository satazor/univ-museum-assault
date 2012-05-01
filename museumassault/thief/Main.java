package museumassault.thief;

import java.util.Random;
import museumassault.common.Configuration;
import museumassault.shared_site.SharedSiteThiefClient;

/**
 *
 * @author Andre Cruz <andremiguelcruz@ua.pt>
 */
public class Main
{
    /**
     * Program entry point.
     *
     * @param args the command line arguments
     */
    public static void main(String[] args)
    {
        Random random = new Random();

        // Initialize the shared site client api for the thieves
        SharedSiteThiefClient site = new SharedSiteThiefClient(Configuration.getSharedSiteConnectionString());

        // Simulate all the thieves in this computer
        int nrTotalThieves = Configuration.getNrThieves();
        Thief[] thieves = new Thief[nrTotalThieves];
        for (int x = 0; x < nrTotalThieves; x++) {
            Thief thief = new Thief(x + 1, random.nextInt(Configuration.getMaxPowerPerThief() - 1) + 1, site);
            thieves[x] = thief;
            thieves[x].start();
        }

        // Wait for the thieves to join
        for (int x = 0; x < nrTotalThieves; x++) {
            try {
                thieves[x].join();
            } catch (InterruptedException e) {}
        }

        System.exit(0);
    }
}
