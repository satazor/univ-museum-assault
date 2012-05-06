package museumassault.thief;

import java.util.Random;
import museumassault.common.Configuration;
import museumassault.logger.client.LoggerClient;
import museumassault.shared_site.client.SharedSiteThiefClient;

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
        Configuration configuration = new Configuration();

        Integer thiefId = args.length > 0 ? Integer.parseInt(args[0]) : null;
        SharedSiteThiefClient site;
        LoggerClient logger;
        Thief thief;

        if (thiefId == null) {
            // Simulate all the thieves in this computer
            int nrTotalThieves = configuration.getNrThieves();
            Thief[] thieves = new Thief[nrTotalThieves];
            for (int x = 0; x < nrTotalThieves; x++) {

                site = new SharedSiteThiefClient(configuration.getSharedThievesSiteConnectionString());
                logger = new LoggerClient(configuration.getLoggerConnectionString());

                thief = new Thief(x + 1, random.nextInt(configuration.getMaxPowerPerThief() - 1) + 1, site, logger, configuration);
                thieves[x] = thief;
                thieves[x].start();
            }

            // Wait for the thieves to join
            for (int x = 0; x < nrTotalThieves; x++) {
                try {
                    thieves[x].join();
                } catch (InterruptedException e) {}
            }
        } else {

            if (!configuration.getThiefIds().contains(thiefId)) {
                System.err.println("Invalid thief id.");
                System.exit(1);
            }

            // Simulate the thief with the passed id
            site = new SharedSiteThiefClient(configuration.getSharedThievesSiteConnectionString());
            logger = new LoggerClient(configuration.getLoggerConnectionString());
            thief = new Thief(thiefId, random.nextInt(configuration.getMaxPowerPerThief() - 1) + 1, site, logger, configuration);

            thief.start();

            // Wait for the thief to join
            try {
                thief.join();
            } catch (InterruptedException e) {}
        }
    }
}
