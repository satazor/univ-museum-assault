package museumassault.logger.server;

import java.rmi.RMISecurityManager;
import java.rmi.RemoteException;
import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;
import java.rmi.server.UnicastRemoteObject;
import museumassault.common.Configuration;
import museumassault.common.IShutdownHandler;

/**
 * Logger main class.
 *
 * @author Andre Cruz <andremiguelcruz@ua.pt>
 */
public class Main
{
    protected static Registry registry;
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

        // Initialize the loggerList<Integer> chiefIds, List<Integer> thiefIds,
        Logger logger = new Logger(configuration.getLogFileName(), configuration.getChiefIds(),
                configuration.getThiefIds(), configuration.getTeamIds(), configuration.getRoomIds(), configuration.getCorridorIds(), configuration.getNrThievesPerTeam());
        LoggerAdapter loggerAdapter = new LoggerAdapter(logger, configuration.getShutdownPassword(), new IShutdownHandler() {
            @Override
            public void onShutdown() {
                shutdown = true;
                synchronized (object) {
                    object.notify();
                }
            }
        });

        // Initialize the security manager.
        if (System.getSecurityManager () == null) {
            System.setSecurityManager(new RMISecurityManager());
        }

        // Initialize the remote objects.
        ILogger loggerAdapterInt = null;
        try {
            loggerAdapterInt = (ILogger) UnicastRemoteObject.exportObject(loggerAdapter, configuration.getLoggerPort() + 1);
        } catch (RemoteException e) {
            System.err.println("Unable to initialize remote object: " + e.getMessage());
            System.exit(1);
        }

        // Get the RMI registry for the given host & ports and start to listen.
        try {
            LocateRegistry.createRegistry(configuration.getLoggerPort());
            registry = LocateRegistry.getRegistry(configuration.getLoggerHost(), configuration.getLoggerPort());
            registry.rebind(ILogger.RMI_NAME_ENTRY, loggerAdapterInt);
        } catch (Exception e) {
            System.err.println("Error while attempting to initialize the server: " + e.getMessage());
            System.exit(1);
        }

        System.out.println("Now listening for requests in port " + configuration.getLoggerPort() + "..");

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
            registry.unbind(ILogger.RMI_NAME_ENTRY);
            UnicastRemoteObject.unexportObject(loggerAdapter, true);
        } catch (Exception e) {
            System.err.println(e.getMessage());
        }

        System.exit(1);
    }
}
