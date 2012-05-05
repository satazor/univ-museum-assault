package museumassault.shared_site.server;

import museumassault.common.Configuration;
import museumassault.common.ServerCom;
import museumassault.common.exception.ComException;

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
        class ThievesListener extends Thread
        {
            protected boolean ended = false;
            protected ServerCom con;
            /**
             *
             * @param com
             */
            public ThievesListener(ServerCom con)
            {
                this.con = con;
            }

            /**
             *
             */
            @Override
            public void run() {

                // Initialize the server connection
                try {
                    this.con.start();
                } catch (ComException ex) {
                    System.err.println(ex.getMessage());
                    return;
                }

                System.out.println("Now listening for thieves requests..");

                // Accept connections
                while (true) {
                    ServerCom newCon;

                    try {
                        newCon = this.con.accept();
                    } catch (ComException ex) {
                        if (this.con.isEnded()) {
                            break;
                        }

                        System.err.println(ex.getMessage());
                        continue;
                    }

                    System.out.println("New connection accepted from a thief, creating thread to handle it..");

                    RequestHandler handler = new RequestHandler(newCon, RequestHandler.REQUEST_TYPE.THIEF, site, configuration.getShutdownPassword());
                    handler.start();
                }
            }
        }

        /**
         * Inline class to listen for the chiefs requests.
         */
        class ChiefsListener extends Thread
        {
            protected ServerCom con;
            /**
             *
             * @param com
             */
            public ChiefsListener(ServerCom con)
            {
                this.con = con;
            }

            /**
             *
             */
            @Override
            public void run() {

                // Initialize the server connection
                try {
                    this.con.start();
                } catch (ComException ex) {
                    System.err.println(ex.getMessage());
                    return;
                }

                System.out.println("Now listening for chiefs requests..");

                // Initialize the server
                while (true) {
                    ServerCom newCon;

                    try {
                        newCon = this.con.accept();
                    } catch (ComException ex) {
                        if (this.con.isEnded()) {
                            break;
                        }

                        System.err.println(ex.getMessage());
                        continue;
                    }

                    System.out.println("New connection accepted from a chief, creating thread to handle it..");

                    RequestHandler handler = new RequestHandler(newCon, RequestHandler.REQUEST_TYPE.CHIEF, site, configuration.getShutdownPassword());
                    handler.start();
                }
            }
        }

        System.out.println("SharedSite");

        ServerCom thievesCon = new ServerCom(configuration.getSharedThievesSitePort());
        ServerCom chiefsCon = new ServerCom(configuration.getSharedChiefsSitePort());

        // Start the thieves listener
        ThievesListener thievesListener = new ThievesListener(thievesCon);
        thievesListener.start();

        // Start the chiefs listener
        ChiefsListener chiefsListener = new ChiefsListener(chiefsCon);
        chiefsListener.start();

        try {
            chiefsListener.join();
        } catch (InterruptedException e) {}

        try {
            thievesCon.end();
        } catch (ComException ex) {}

        try {
            thievesListener.join();
        } catch (InterruptedException e) {}

        System.exit(0);
    }
}
