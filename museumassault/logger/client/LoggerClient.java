package museumassault.logger.client;

import java.util.HashMap;
import java.util.Random;
import museumassault.common.ClientCom;
import museumassault.common.Message;
import museumassault.common.exception.ComException;
import museumassault.common.exception.ShutdownException;
import museumassault.logger.*;


/**
 * LoggerClient class.
 *
 * This client is different from others in the sense that no exception are thrown
 * in the set status functions.
 *
 * @author Andre Cruz <andremiguelcruz@ua.pt>
 */
public class LoggerClient implements ILoggerMessageConstants, ILoggerStatusConstants
{
    protected ClientCom con;
    protected Random random = new Random();

    /**
     * Constructor.
     */
    public LoggerClient(String connectionString)
    {
        this.con = new ClientCom(connectionString);
    }

    /**
     *
     * @param chiefId
     * @param status
     */
    public void setChiefStatus(int chiefId, CHIEF_STATUS status)
    {
        this.setStatus(new Message(SET_CHIEF_STATUS_TYPE, chiefId, status));
    }

    /**
     *
     * @param thiefId
     * @param status
     */
    public void setThiefStatus(int thiefId, THIEF_STATUS status)
    {
        this.setStatus(new Message(SET_THIEF_STATUS_TYPE, thiefId, status));
    }

    /**
     *
     * @param details
     */
    public void setThiefDetails(ThiefDetails details)
    {
        this.setDetails(new Message(SET_THIEF_DETAILS_TYPE, details));
    }

    /**
     *
     * @param details
     */
    public void setTeamDetails(TeamDetails details)
    {
        this.setDetails(new Message(SET_TEAM_DETAILS_TYPE, details));
    }

    /**
     *
     * @param details
     */
    public void setRoomDetails(RoomDetails details)
    {
        this.setDetails(new Message(SET_ROOM_DETAILS_TYPE, details));
    }

    /**
     *
     * @param details
     */
    public void setCorridorDetails(CorridorDetails details)
    {
        this.setDetails(new Message(SET_CORRIDOR_DETAILS_TYPE, details));
    }

    /**
     *
     * @param message
     */
    protected void setStatus(Message message)
    {
        try {
            while (!this.con.open()) {                           // Try until the server responds
                try {
                    Thread.sleep(this.random.nextInt(500) + 500);
                } catch (InterruptedException e) {
                }
            }

            this.con.writeMessage(message);

            Message response = this.con.readMessage();
            this.con.close();

            if (response.getType() != STATUS_UPDATED_TYPE) {
                //System.err.println("Unexpected message type sent by the server: " + response.getType());
                //System.exit(1);
                throw new ComException("Unexpected message type sent by the server: " + response.getType());
            }
        } catch (ShutdownException ex) {
            System.err.println("Logger server is shutdown.");
        } catch (ComException e) {
            System.err.println(e.getMessage());
        }
    }

    /**
     *
     * @param message
     */
    protected void setDetails(Message message)
    {
        try {
            while (!this.con.open()) {                           // Try until the server responds
                try {
                    Thread.sleep(this.random.nextInt(500) + 500);
                } catch (InterruptedException e) {
                }
            }

            this.con.writeMessage(message);

            Message response = this.con.readMessage();
            this.con.close();

            if (response.getType() != DETAILS_UPDATED_TYPE) {
                //System.err.println("Unexpected message type sent by the server: " + response.getType());
                //System.exit(1);
                throw new ComException("Unexpected message type sent by the server: " + response.getType());
            }
        } catch (ShutdownException ex) {
            System.err.println("Logger server is shutdown.");
        } catch (ComException e) {
            System.err.println(e.getMessage());
        }
    }

    /**
     * Shutdowns the server.
     *
     * @param password
     * @param totalCanvas
     *
     * @return
     */
    public boolean shutdown(String password, int totalCanvas) throws ComException
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

            HashMap<String, Object> extra = new HashMap<String, Object>();
            extra.put("password", password);
            extra.put("total_canvas", totalCanvas);

            this.con.writeMessage(new Message(SHUTDOWN_TYPE, extra));

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
