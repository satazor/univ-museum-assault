package museumassault.chief;

import museumassault.common.Configuration;
import museumassault.shared_site.SharedSiteChiefClient;

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

        // Simulate all the thieves in this computer
        int nrTotalChiefs = Configuration.getNrChiefs();
        Chief[] chiefs = new Chief[nrTotalChiefs];
        for (int x = 0; x < nrTotalChiefs; x++) {

            // Initialize the shared site client api for the chiefs
            SharedSiteChiefClient site = new SharedSiteChiefClient(Configuration.getSharedSiteConnectionString());

            Chief chief = new Chief(x + 1, site);
            chiefs[x] = chief;
            chiefs[x].start();
        }

        // Wait for the chiefs to join
        for (int x = 0; x < nrTotalChiefs; x++) {
            try {
                chiefs[x].join();
            } catch (InterruptedException e) {}
        }

        System.exit(0);
    }
}
