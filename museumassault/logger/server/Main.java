package museumassault.logger.server;

import museumassault.common.Configuration;
import museumassault.common.ServerCom;
import museumassault.common.exception.ComException;

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
        Configuration configuration = new Configuration();

        // Initialize the loggerList<Integer> chiefIds, List<Integer> thiefIds,
        Logger logger = new Logger(configuration.getLogFileName(), configuration.getChiefIds(),
                configuration.getThiefIds(), configuration.getTeamIds(), configuration.getRoomIds(), configuration.getCorridorIds(), configuration.getNrThievesPerTeam());

        // Initialize the server connection
        ServerCom con = new ServerCom(configuration.getLoggerPort());
        try {
            con.start();
        } catch (ComException ex) {
            System.err.println(ex.getMessage());
            System.exit(1);
        }

        System.out.println("Logger");
        System.out.println("Now listening for requests..");

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

            System.out.println("New connection accepted from a client, creating thread to handle it..");

            RequestHandler handler = new RequestHandler(newCon, logger, configuration.getShutdownPassword());
            handler.start();
        }

        System.exit(0);
    }
}
