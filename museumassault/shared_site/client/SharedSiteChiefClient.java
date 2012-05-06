package museumassault.shared_site.client;

import java.util.Random;
import museumassault.common.ClientCom;
import museumassault.common.Message;
import museumassault.common.exception.ComException;
import museumassault.common.exception.ShutdownException;
import museumassault.shared_site.IChiefMessageConstants;

/**
 *
 * @author Andre Cruz <andremiguelcruz@ua.pt>
 */
public class SharedSiteChiefClient implements IChiefMessageConstants
{
    protected ClientCom con;
    protected Random random = new Random();

    /**
     * Constructor.
     */
    public SharedSiteChiefClient(String connectionString)
    {
        this.con = new ClientCom(connectionString);
    }

    /**
     * Decide if the chief should sit or rob a room.
     *
     * @param chiefId the id of the chief
     *
     * @return the id of the room that should be robed or null if the chief should sit
     */
    public Integer appraiseSit(int chiefId) throws ShutdownException, ComException
    {
        while (!this.con.open()) {                           // Try until the server responds
            try {
                Thread.sleep(this.random.nextInt(500) + 500);
            } catch (InterruptedException e) {}
        }

        System.out.println("Sending message");
        this.con.writeMessage(new Message(APPRAISE_SIT_TYPE, chiefId));

        System.out.println("Message sent!");
        Message response = this.con.readMessage();
        this.con.close();

        if (response.getType() == APPRAISED_SIT_TYPE) {
            return (Integer) response.getExtra();
        } else {
            //System.err.println("Unexpected message type sent by the server: " + response.getType());
            //System.exit(1);
            throw new ComException("Unexpected message type sent by the server: " + response.getType());
        }
    }

    /**
     * Prepares an assault team, assigning a team that will be responsible for it.
     *
     * @param chiefId the id of the chief
     * @param roomId  the room that will be robbed
     *
     * @return returns the id of the assigned team or null if none is free
     */
    public Integer prepareAssaultParty(int chiefId, int roomId) throws ShutdownException, ComException
    {
        while (!this.con.open()) {                           // Try until the server responds
            try {
                Thread.sleep(this.random.nextInt(500) + 500);
            } catch (InterruptedException e) {}
        }

        this.con.writeMessage(new Message(PREPARE_ASSAULT_PARTY_TYPE, chiefId, roomId));

        Message response = this.con.readMessage();
        this.con.close();

        if (response.getType() == ASSAULT_PARTY_PREPARED_TYPE) {
            return (Integer) response.getExtra();
        } else {
            //System.err.println("Unexpected message type sent by the server: " + response.getType());
            //System.exit(1);
            throw new ComException("Unexpected message type sent by the server: " + response.getType());
        }
    }

    /**
     * Method that sends a previously prepared team to rob the designated room.
     * This method should guarantee that all the thieves depart at the same time.
     *
     * @param chiefId the id of the chief
     * @param teamId  the team to be sent
     */
    public void sendAssaultParty(int chiefId, int teamId) throws ShutdownException, ComException
    {
        while (!this.con.open()) {                           // Try until the server responds
            try {
                Thread.sleep(this.random.nextInt(500) + 500);
            } catch (InterruptedException e) {}
        }

        this.con.writeMessage(new Message(SEND_ASSAULT_PARTY_TYPE, chiefId,teamId));

        Message response = this.con.readMessage();

        if (response.getType() != ASSAULT_PARTY_SENT_TYPE) {
            //System.err.println("Unexpected message type sent by the server: " + response.getType());
            //System.exit(1);
            throw new ComException("Unexpected message type sent by the server: " + response.getType());
        }
    }

    /**
     * Takes a rest, waiting for a thief arrival.
     *
     * @param chiefId the id of the chief
     *
     * @return the id of the thief that arrived or null if there is no remaining thieves
     */
    public Integer takeARest(int chiefId) throws ShutdownException, ComException
    {
        while (!this.con.open()) {                           // Try until the server responds
            try {
                Thread.sleep(this.random.nextInt(500) + 500);
            } catch (InterruptedException e) {}
        }

        this.con.writeMessage(new Message(TAKE_A_REST_TYPE, chiefId));

        Message response = this.con.readMessage();

        if (response.getType() == TOOK_A_REST_TYPE) {
            return (Integer) response.getExtra();
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
            this.con.close();

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

    /**
     * Collects the canvas of thief that arrived.
     *
     * @param chiefId the id of the chief
     * @param thiefId the id of the thief that handed the canvas
     */
    public void collectCanvas(int chiefId, int thiefId) throws ShutdownException, ComException
    {
        while (!this.con.open()) {                           // Try until the server responds
            try {
                Thread.sleep(this.random.nextInt(500) + 500);
            } catch (InterruptedException e) {}
        }

        this.con.writeMessage(new Message(COLLECT_CANVAS_TYPE, chiefId, thiefId));

        Message response = this.con.readMessage();

        if (response.getType() != COLLECTED_CANVAS_TYPE) {
            //System.err.println("Unexpected message type sent by the server: " + response.getType());
            //System.exit(1);
            throw new ComException("Unexpected message type sent by the server: " + response.getType());
        }
    }

    /**
     * Sums up the total canvas stolen.
     *
     * @param chiefId the id of the chief
     *
     * @return the total number of canvas stolen
     */
    public Integer sumUpResults(int chiefId) throws ShutdownException, ComException
    {
        while (!this.con.open()) {                           // Try until the server responds
            try {
                Thread.sleep(this.random.nextInt(500) + 500);
            } catch (InterruptedException e) {}
        }

        this.con.writeMessage(new Message(SUM_UP_RESULTS, chiefId));

        Message response = this.con.readMessage();

        if (response.getType() == SUMMED_UP_RESULTS) {
            return (Integer) response.getExtra();
        } else {
            //System.err.println("Unexpected message type sent by the server: " + response.getType());
            //System.exit(1);
            throw new ComException("Unexpected message type sent by the server: " + response.getType());
        }
    }
}
