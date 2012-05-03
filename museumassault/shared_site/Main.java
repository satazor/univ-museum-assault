package museumassault.shared_site;

import museumassault.common.Configuration;
import museumassault.common.ServerCom;
import museumassault.common.Team;

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
        // Initialize the server connection
        ServerCom con = new ServerCom(Configuration.getSharedSitePort());
        con.start();

        // Initialize the teams
        int nrTeams = Configuration.getNrTeams();
        Team[] teams = new Team[nrTeams];
        for (int x = 0; x < nrTeams; x++) {
            teams[x] = new Team(x + 1, Configuration.getNrThievesPerTeam());
        }

        // Initialize the shared site
        SharedSite site = new SharedSite(Configuration.getRoomIds(), teams, Configuration.getNrChiefs() > 1);

        System.out.println("Now listening for requests..");
        
        // Initialize the server
        while (true) {
            ServerCom newCon = con.accept();

            System.out.println("New connection accepted from a client, creating thread to handle it..");

            RequestHandler handler = new RequestHandler(newCon, site);
            handler.start();
        }
    }
}
