package museumassault.shared_site.server;

import museumassault.common.Configuration;
import museumassault.common.ServerCom;

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
        final Configuration configuration = new Configuration();

        // Initialize the teams
        int nrTeams = configuration.getNrTeams();
        Team[] teams = new Team[nrTeams];
        for (int x = 0; x < nrTeams; x++) {
            teams[x] = new Team(x + 1, configuration.getNrThievesPerTeam());
        }

        // Initialize the shared site
        final SharedSite site = new SharedSite(configuration.getRoomIds(), teams, configuration.getNrChiefs() > 1);

        /**
         * Inline class to listen for the thieves requests.
         */
        class ThievesListener extends Thread {
            @Override
            public void run() {
                // Initialize the server connection
                ServerCom con = new ServerCom(configuration.getSharedThievesSitePort());
                con.start();

                System.out.println("Now listening for thieves requests..");

                // Accept connections
                while (true) {
                    ServerCom newCon = con.accept();

                    System.out.println("New connection accepted from a thief, creating thread to handle it..");

                    RequestHandler handler = new RequestHandler(newCon, RequestHandler.REQUEST_TYPE.THIEF, site);
                    handler.start();
                }
            }
        }

        /**
         * Inline class to listen for the chiefs requests.
         */
        class ChiefsListener extends Thread {
            @Override
            public void run() {

                // Initialize the server connection
                ServerCom con = new ServerCom(configuration.getSharedChiefsSitePort());
                con.start();

                System.out.println("Now listening for chiefs requests..");

                // Initialize the server
                while (true) {
                    ServerCom newCon = con.accept();

                    System.out.println("New connection accepted from a chief, creating thread to handle it..");

                    RequestHandler handler = new RequestHandler(newCon, RequestHandler.REQUEST_TYPE.CHIEF, site);
                    handler.start();
                }
            }
        }

        System.out.println("SharedSite");

        // Start the thieves listener
        ThievesListener thievesListener = new ThievesListener();
        thievesListener.start();

        // Start the chiefs listener
        ChiefsListener chiefsListener = new ChiefsListener();
        chiefsListener.start();
    }
}
