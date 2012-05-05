package museumassault.corridor.server;

import java.util.Random;
import museumassault.common.Configuration;
import museumassault.common.ServerCom;

/**
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

        // Read corridor id
        if (args.length < 1) {
            System.err.println("Please pass the corridor id as first argument.");
            System.exit(1);
        }

        int corridorId = Integer.parseInt(args[0]);

        // Initialize the server connection
        Integer port = configuration.getCorridorPort(corridorId);
        if (port == null) {
            System.err.println("Unknown corridor id: " + corridorId + ".");
            System.exit(1);
        }

        ServerCom con = new ServerCom(port);
        con.start();

        // Initialize the corridor
        Corridor corridor = new Corridor(corridorId, (random.nextInt(configuration.getMaxDistanceBetweenRoomAndOutside() - 1) + 1), configuration.getMaxDistanceBetweenThieves());

        System.out.println("Corridor #" + corridorId);
        System.out.println("Now listening for thieves requests..");

        // Accept connections
        while (true) {
            ServerCom newCon = con.accept();

            System.out.println("New connection accepted from a thief, creating thread to handle it..");

            RequestHandler handler = new RequestHandler(newCon, corridor);
            handler.start();
        }
    }
}
