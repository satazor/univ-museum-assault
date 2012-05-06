package museumassault.corridor.client;

import java.util.Random;
import museumassault.common.ClientCom;
import museumassault.common.Message;
import museumassault.common.exception.ComException;
import museumassault.common.exception.ShutdownException;
import museumassault.corridor.ICorridorMessageConstants;

/**
 *
 * @author Hugo Oliveira <hugo.oliveira@ua.pt>
 * @author Andre Cruz <andremiguelcruz@ua.pt>
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
    public boolean crawlOut(int thiefId, int increment) throws ShutdownException, ComException
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
            //System.err.println("Unexpected message type sent by the server: " + response.getType());
            //System.exit(1);
            throw new ComException("Unexpected message type sent by the server: " + response.getType());
        }

    }

    /**
     *
     * @param thiefId
     * @param increment
     *
     * @return
     */
    public boolean crawlIn(int thiefId, int increment) throws ShutdownException, ComException
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
            //System.err.println("Unexpected message type sent by the server: " + response.getType());
            //System.exit(1);
            throw new ComException("Unexpected message type sent by the server: " + response.getType());
        }
    }

    /**
     * Shutdowns the server.
     *
     * @param password
     *
     * @return
     */
    public boolean shutdown(String password) throws ComException
    {
        try {
            int nrAttempts = 0;
            while (!this.con.open() && nrAttempts < 10) {                           // Try until the server responds
                try {
                    Thread.sleep(this.random.nextInt(100) + 100);
                } catch (InterruptedException e) {}

                nrAttempts++;
            }

            if (nrAttempts >= 10) {
                System.out.println("Assumed that the server is shutted down.");
                return true;
            }

            this.con.writeMessage(new Message(SHUTDOWN_TYPE, password));

            Message response = this.con.readMessage();

            if (response.getType() == SHUTDOWN_COMPLETED_TYPE) {
                return true;
            } else if (response.getType() == WRONG_SHUTDOWN_PASSWORD_TYPE) {
                return false;
            } else {
                //System.err.println("Unexpected message type sent by the server: " + response.getType());
                //System.exit(1);
                throw new ComException("Unexpected message type sent by the server: " + response.getType());
            }
        } catch (ShutdownException ex) {
            return true;
        }
    }
}
