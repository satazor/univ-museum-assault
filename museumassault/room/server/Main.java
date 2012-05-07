package museumassault.room.server;

import java.util.Random;
import museumassault.common.Configuration;
import museumassault.common.ServerCom;
import museumassault.common.exception.ComException;
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

        // Read room id
        if (args.length < 1) {
            System.err.println("Please pass the room id as first argument.");
            System.exit(1);
        }

        int roomId = Integer.parseInt(args[0]);

        // Initialize the server connection
        Integer port = configuration.getRoomPort(roomId);
        if (port == null) {
            System.err.println("Unknown room id: " + roomId + ".");
            System.exit(1);
        }

        // Initialize the logger
        LoggerClient logger = new LoggerClient(configuration.getLoggerConnectionString());

        // Initialize the room
        Room room = new Room(roomId, random.nextInt(configuration.getMaxCanvasInRoom()), configuration.getRoomCorridorId(roomId), logger);

        // Start accepting for requests
        ServerCom con = new ServerCom(port);
        try {
            con.start();
        } catch (ComException ex) {
            System.err.println(ex.getMessage());
            System.exit(1);
        }

        System.out.println("Room #" + roomId);
        System.out.println("Now listening for thieves requests in port " + con.getServerPort() + "..");

        // Accept connections
        while (true) {
            ServerCom newCon;

            try {
                 newCon = con.accept();
            } catch (ComException ex) {
                if (con.isEnded()) {
                    break;
                }

                System.err.println(ex.getMessage());
                continue;
            }

            System.out.println("New connection accepted from a thief, creating thread to handle it..");

            RequestHandler handler = new RequestHandler(newCon, room, configuration.getShutdownPassword());
            handler.start();
        }

        System.exit(0);
    }
}
