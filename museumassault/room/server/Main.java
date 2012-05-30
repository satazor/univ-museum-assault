package museumassault.room.server;

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
 * Room main class.
 *
 * @author Hugo Oliveira <hugo.oliveira@ua.pt>
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

        // Read room id.
        if (args.length < 1) {
            System.err.println("Please pass the room id as first argument.");
            System.exit(1);
        }

        int roomId = Integer.parseInt(args[0]);

        System.out.println("Room #" + roomId);

        // Initialize the server connection.
        Integer port = configuration.getRoomPort(roomId);
        if (port == null) {
            System.err.println("Unknown room id: " + roomId + ".");
            System.exit(1);
        }

        // Initialize the logger.
        LoggerClient logger = new LoggerClient(configuration.getLoggerHost(), configuration.getLoggerPort());

        // Initialize the room & room adapter
        Room room = new Room(roomId, random.nextInt(configuration.getMaxCanvasInRoom()), configuration.getRoomCorridorId(roomId), logger);
        RoomAdapter roomAdapter = new RoomAdapter(room, configuration.getShutdownPassword(), new IShutdownHandler() {
            @Override
            public void onShutdown() {
                System.exit(1);
            }
        });

        // Initialize the security manager.
        if (System.getSecurityManager () == null) {
            System.setSecurityManager(new RMISecurityManager());
        }

        // Initialize the remote objects.
        IRoom roomAdapterInt = null;
        try {
            roomAdapterInt = (IRoom) UnicastRemoteObject.exportObject(roomAdapter, 0);
        } catch (RemoteException e) {
            System.err.println("Unable to initialize remote object: " + e.getMessage());
            System.exit(1);
        }

        // Get the RMI registry for the given host & ports and start to listen.
        Registry registry;

        try {
            registry = LocateRegistry.createRegistry(configuration.getRoomPort(roomId));
            registry.bind(IRoom.RMI_NAME_ENTRY, roomAdapterInt);
        } catch (Exception e) {
            System.err.println("Error while attempting to initialize the server: " + e.getMessage());
            System.exit(1);
        }

        System.out.println("Now listening for requests in " + configuration.getSharedThievesSitePort() + "..");
    }
}
