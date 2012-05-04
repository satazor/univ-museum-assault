package museumassault.corridor.client;

import java.util.Random;
import museumassault.common.ClientCom;
import museumassault.common.Message;
import museumassault.corridor.ICorridorMessageConstants;

/**
 *
 * @author Hugo Oliveira <hugo.oliveira@ua.pt>
 */
public class CorridorClient implements ICorridorMessageConstants
{
    protected ClientCom con;
    protected Random random = new Random();

    /**
     * Constructor.
     */
    public CorridorClient(String connectionString)
    {
        this.con = new ClientCom(connectionString);
    }

    /**
     * 
     * @param thiefId
     * @param increment
     * 
     * @return 
     */
    public boolean crawlOut(int thiefId, int increment)
    {
        while (!this.con.open()) {                           // Try until the server responds
            try {
                Thread.sleep(this.random.nextInt(500) + 500);
            } catch (InterruptedException e) {
            }
        }

        this.con.writeMessage(new Message(CRAWL_OUT_TYPE, thiefId, (Integer) increment));

        Message response = this.con.readMessage();
        this.con.close();

        if (response.getType() == CRAWLED_OUT_TYPE) {
            return (boolean) response.getExtra();
        } else {
            System.err.println("Unexpected message type sent by the server: " + response.getType());
            System.exit(1);

            return false;
        }

    }

    /**
     * 
     * @param thiefId
     * @param increment
     * 
     * @return 
     */
    public boolean crawlIn(int thiefId, int increment)
    {
        while (!this.con.open()) {                           // Try until the server responds
            try {
                Thread.sleep(this.random.nextInt(500) + 500);
            } catch (InterruptedException e) {
            }
        }

        this.con.writeMessage(new Message(CRAWL_IN_TYPE, thiefId, (Integer) increment));

        Message response = this.con.readMessage();
        this.con.close();

        if (response.getType() == CRAWLED_IN_TYPE) {
            return (boolean) response.getExtra();
        } else {
            System.err.println("Unexpected message type sent by the server: " + response.getType());
            System.exit(1);

            return false;
        }
    }
}
