package museumassault.room.server;

import java.util.Random;
import museumassault.common.Configuration;
import museumassault.common.ServerCom;

/**
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

        // Read room id
        if (args.length < 1) {
            throw new IllegalArgumentException("Please pass the room id as first argument.");
        }

        int roomId = Integer.parseInt(args[0]);

        // Initialize the corridor
        Room room = new Room(roomId, random.nextInt(Configuration.getMaxCanvasInRoom()), roomId);

        // Initialize the server connection
        ServerCom con = new ServerCom(Configuration.getRoomPort(roomId));
        con.start();

        System.out.println("Now listening for thieves requests..");

        // Accept connections
        while (true) {
            ServerCom newCon = con.accept();

            System.out.println("New connection accepted from a thief, creating thread to handle it..");

            RequestHandler handler = new RequestHandler(newCon, room);
            handler.start();
        }
    }
}
