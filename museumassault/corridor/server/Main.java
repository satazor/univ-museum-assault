package museumassault.corridor.server;

import java.util.Random;
import museumassault.common.Configuration;
import museumassault.common.ServerCom;

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
        Random random = new Random();

        // Read room id
        if (args.length < 1) {
            throw new IllegalArgumentException("Please pass the corridor id as first argument.");
        }

        String corridorId = args[0];

        // Initialize the corridor
        Corridor corridor = new Corridor((random.nextInt(Configuration.getMaxDistanceBetweenRoomAndOutside() - 1) + 1), Configuration.getMaxDistanceBetweenThieves());

        // Initialize the server connection
        // TODO: the port bellow should be read from the configuration
        ServerCom con = new ServerCom(10);
        con.start();

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
