package museumassault.shared_site.server;

import java.rmi.RMISecurityManager;
import java.rmi.RemoteException;
import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;
import java.rmi.server.UnicastRemoteObject;
import museumassault.common.Configuration;
import museumassault.common.IShutdownHandler;
import museumassault.logger.client.LoggerClient;

/**
 * Shared site main class.
 *
 * @author Andre Cruz <andremiguelcruz@ua.pt>
 */
public class Main
{
    public static Registry thievesRegistry;    // Registry must be static so it won't get GC'ed
    public static Registry chiefsRegistry;    // Registry must be static so it won't get GC'ed

    /**
     * Program entry point.
     *
     * @param args the command line arguments
     */
    public static void main(String[] args)
    {
        final Configuration configuration = new Configuration();

        // Initialize the teams.
        int nrTeams = configuration.getNrTeams();
        Team[] teams = new Team[nrTeams];
        for (int x = 0; x < nrTeams; x++) {
            teams[x] = new Team(x + 1, configuration.getNrThievesPerTeam());
        }

        // Initialize the logger client.
        LoggerClient logger = new LoggerClient(configuration.getLoggerHost(), configuration.getLoggerPort());

        // Initialize the shared site & adapters.
        SharedSite site = new SharedSite(configuration.getRoomIds(), teams, logger, configuration.getNrChiefs() > 1);
        SharedSiteChiefsAdapter chiefsSharedSiteAdapter = new SharedSiteChiefsAdapter(site, configuration.getShutdownPassword(), new IShutdownHandler() {
            @Override
            public void onShutdown() {
                System.out.println("Exiting..");
                System.exit(1);
            }
        });
        SharedSiteThievesAdapter thievesSharedSiteAdapter = new SharedSiteThievesAdapter(site);

        // Initialize the security manager.
        if (System.getSecurityManager () == null) {
            System.setSecurityManager(new RMISecurityManager());
        }

        // Initialize the remote objects.
        IChiefsControlSite chiefsSharedSite = null;
        IThievesConcentrationSite thievesConcentrationSite = null;
        try {
            chiefsSharedSite = (IChiefsControlSite) UnicastRemoteObject.exportObject(chiefsSharedSiteAdapter, 0);
            thievesConcentrationSite = (IThievesConcentrationSite) UnicastRemoteObject.exportObject(thievesSharedSiteAdapter, 0);
        } catch (RemoteException e) {
            System.err.println("Unable to initialize remote objects: " + e.getMessage());
            System.exit(1);
        }

        // Get the RMI registry for the given host & ports and start to listen.
        try {
            chiefsRegistry = LocateRegistry.createRegistry(configuration.getSharedThievesSitePort());

            try {
                Thread.sleep(100);
            } catch (InterruptedException ex) {}

            chiefsRegistry.bind(IThievesConcentrationSite.RMI_NAME_ENTRY, thievesConcentrationSite);
        } catch (Exception e) {
            System.err.println("Error while attempting to initialize the server: " + e.getMessage());
            System.exit(1);
        }

        System.out.println("Now listening for thieves requests in " + configuration.getSharedThievesSitePort() + "..");

        try {
            thievesRegistry = LocateRegistry.createRegistry(configuration.getSharedChiefsSitePort());

            try {
                Thread.sleep(100);
            } catch (InterruptedException ex) {}

            thievesRegistry.bind(IChiefsControlSite.RMI_NAME_ENTRY, chiefsSharedSite);
        } catch (Exception e) {
            System.err.println("Error while attempting to initialize the server: " + e.getMessage());
            System.exit(1);
        }
        
        System.out.println("Now listening for chiefs requests in port " + configuration.getSharedChiefsSitePort() + "..");
    }
}
