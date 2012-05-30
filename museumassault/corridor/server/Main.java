package museumassault.corridor.server;

import java.rmi.RMISecurityManager;
import java.rmi.RemoteException;
import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;
import java.rmi.server.UnicastRemoteObject;
import java.util.Random;
import museumassault.common.Configuration;
import museumassault.common.IShutdownHandler;
import museumassault.logger.client.LoggerClient;

/**
 * @author Hugo Oliveira <hugo.oliveira@ua.pt>
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
        final Random random = new Random();
        Configuration configuration = new Configuration();

        // Read corridor id
        if (args.length < 1) {
            System.err.println("Please pass the corridor id as first argument.");
            System.exit(1);
        }

        int corridorId = Integer.parseInt(args[0]);

        System.out.println("Corridor #" + corridorId);

        // Get the port.
        Integer port = configuration.getCorridorPort(corridorId);
        if (port == null) {
            System.err.println("Unknown corridor id: " + corridorId + ".");
            System.exit(1);
        }

        // Initialize the logger.
        LoggerClient logger = new LoggerClient(configuration.getLoggerHost(), configuration.getLoggerPort());

        // Initialize the corridor & corridor adapter.
        Corridor corridor = new Corridor(corridorId, (random.nextInt(configuration.getMaxDistanceBetweenRoomAndOutside() - 1) + 1), configuration.getMaxDistanceBetweenThieves(), logger);
        CorridorAdapter corridorAdapter = new CorridorAdapter(corridor, configuration.getShutdownPassword(), new IShutdownHandler() {
            @Override
            public void onShutdown() {
                shutdown = true;
                synchronized (random) {
                    random.notify();
                }
            }
        });

        // Initialize the security manager.
        if (System.getSecurityManager () == null) {
            System.setSecurityManager(new RMISecurityManager());
        }

        // Initialize the remote objects.
        ICorridor corridordAdapterInt = null;
        try {
            corridordAdapterInt = (ICorridor) UnicastRemoteObject.exportObject(corridorAdapter, 0);
        } catch (RemoteException e) {
            System.err.println("Unable to initialize remote object: " + e.getMessage());
            System.exit(1);
        }

        // Get the RMI registry for the given host & ports and start to listen.
        try {
            registry = LocateRegistry.createRegistry(port);

            // Wait until the registry is created.. (this is ugly but works).
            try {
                Thread.sleep(100);
            } catch (InterruptedException ex) {}

            registry.bind(ICorridor.RMI_NAME_ENTRY, corridordAdapterInt);
        } catch (Exception e) {
            System.err.println("Error while attempting to initialize the server: " + e.getMessage());
            System.exit(1);
        }

        System.out.println("Now listening for requests in port " + port + "..");

        // Wait until we receive the shutdown.
        do {
            synchronized (random) {
                try {
                    random.wait();
                } catch (InterruptedException ex) {}
            }
        } while (!shutdown);

        System.out.println("Exiting..");

        // Gracefully stop RMI.
        try {
            registry.unbind(ICorridor.RMI_NAME_ENTRY);
            UnicastRemoteObject.unexportObject(corridorAdapter, true);
        } catch (Exception e) {
            System.err.println(e.getMessage());
        }

        System.exit(1);
    }
}
