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
    protected static Registry thievesRegistry;    // Registry must be static so it won't get GC'ed
    protected static Registry chiefsRegistry;    // Registry must be static so it won't get GC'ed
    protected static boolean shutdown = false;

    /**
     * Program entry point.
     *
     * @param args the command line arguments
     */
    public static void main(String[] args)
    {
        Configuration configuration = new Configuration();
        final Object object = new Object();

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
                shutdown = true;
                synchronized (object) {
                    object.notify();
                }
            }
        });
        SharedSiteThievesAdapter thievesSharedSiteAdapter = new SharedSiteThievesAdapter(site);

        // Initialize the security manager.
        if (System.getSecurityManager () == null) {
            System.setSecurityManager(new RMISecurityManager());
        }

        // Initialize the remote objects.
        IChiefsControlSite chiefsSharedSite = null;
        IThievesConcentrationSite thievesSharedSiteS = null;
        try {
            chiefsSharedSite = (IChiefsControlSite) UnicastRemoteObject.exportObject(chiefsSharedSiteAdapter, 0);
            thievesSharedSiteS = (IThievesConcentrationSite) UnicastRemoteObject.exportObject(thievesSharedSiteAdapter, 0);
        } catch (RemoteException e) {
            System.err.println("Unable to initialize remote objects: " + e.getMessage());
            System.exit(1);
        }

        // Get the RMI registry for the given host & ports and start to listen.
        try {
            chiefsRegistry = LocateRegistry.createRegistry(configuration.getSharedChiefsSitePort());
            chiefsRegistry.bind(IChiefsControlSite.RMI_NAME_ENTRY, chiefsSharedSite);
        } catch (Exception e) {
            System.err.println("Error while attempting to initialize the server: " + e.getMessage());
            System.exit(1);
        }

        System.out.println("Now listening for chiefs requests in port " + configuration.getSharedChiefsSitePort() + "..");


        try {
            thievesRegistry = LocateRegistry.createRegistry(configuration.getSharedThievesSitePort());
            thievesRegistry.bind(IThievesConcentrationSite.RMI_NAME_ENTRY, thievesSharedSiteS);
        } catch (Exception e) {
            System.err.println("Error while attempting to initialize the server: " + e.getMessage());
            System.exit(1);
        }

        System.out.println("Now listening for thieves requests in " + configuration.getSharedThievesSitePort() + "..");

        // Wait until we receive the shutdown.
        do {
            synchronized (object) {
                try {
                    object.wait();
                } catch (InterruptedException ex) {}
            }
        } while (!shutdown);

        System.out.println("Exiting..");

        // Gracefully stop RMI.
        try {
            thievesRegistry.unbind(IThievesConcentrationSite.RMI_NAME_ENTRY);
            chiefsRegistry.unbind(IChiefsControlSite.RMI_NAME_ENTRY);

            UnicastRemoteObject.unexportObject(chiefsSharedSiteAdapter, true);
            UnicastRemoteObject.unexportObject(thievesSharedSiteAdapter, true);
        } catch (Exception e) {
            System.err.println(e.getMessage());
        }

        System.exit(1);
    }
}
