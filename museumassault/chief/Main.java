package museumassault.chief;

import museumassault.common.Configuration;
import museumassault.shared_site.client.SharedSiteChiefClient;

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
        Configuration configuration = new Configuration();

        Integer chiefId = args.length > 0 ? Integer.parseInt(args[0]) : null;
        SharedSiteChiefClient site;
        Chief chief;

        if (chiefId == null) {
            // Simulate all the chiefs in this computer
            int nrTotalChiefs = configuration.getNrChiefs();
            Chief[] chiefs = new Chief[nrTotalChiefs];
            for (int x = 0; x < nrTotalChiefs; x++) {

                // Initialize the shared site client api for the chiefs
                site = new SharedSiteChiefClient(configuration.getSharedChiefsSiteConnectionString());

                chief = new Chief(x + 1, site);
                chiefs[x] = chief;
                chiefs[x].start();
            }

            // Wait for the chiefs to join
            for (int x = 0; x < nrTotalChiefs; x++) {
                try {
                    chiefs[x].join();
                } catch (InterruptedException e) {}
            }
        } else {
            // Simulate the chief with the passed id
            site = new SharedSiteChiefClient(configuration.getSharedThievesSiteConnectionString());
            chief = new Chief(chiefId, site);

            chief.start();

            // Wait for the chief to join
            try {
                chief.join();
            } catch (InterruptedException e) {}
        }
    }
}
