package museumassault.logger.client;

import java.rmi.AccessException;
import java.rmi.RemoteException;
import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;
import java.util.Random;
import museumassault.common.exception.ComException;
import museumassault.logger.*;
import museumassault.logger.server.ILogger;

/**
 * LoggerClient class.
 * This class allows access to a logger service.
 * It encapsulates all the communication logic.
 *
 * This client is different from others in the sense that no exceptions are thrown (except for the shutdown).
 *
 * @author Andre Cruz <andremiguelcruz@ua.pt>
 */
public class LoggerClient implements ILoggerStatusConstants
{
    protected ILogger logger = null;
    protected String host;
    protected int port;
    protected Random random = new Random();

    /**
     * Constructor.
     *
     * @param host the server host
     * @param port the server port
     */
    public LoggerClient(String host, int port)
    {
        this.host = host;
        this.port = port;
    }

    /**
     * Initializes the RMI connection.
     */
    protected void initialize() /*throws ComException*/
    {
        if (this.logger == null) {
            do {
                try {
                    Registry registry = LocateRegistry.getRegistry(this.host, this.port);
                    this.logger = (ILogger) registry.lookup(ILogger.RMI_NAME_ENTRY);
                } catch (AccessException e) {
                    //throw new ComException("Server refused connection: " + e.getMessage());
                    System.err.println("Access denied to the logger.. (" + e.getMessage() + ")");
                    break;
                } catch (Exception e) {
                    System.err.println("Server seems to be down, retrying in a while..");

                    try {
                        Thread.sleep(this.random.nextInt(100) + 100);
                    } catch (InterruptedException ex) {}
                }

            } while (this.logger == null);
        }
    }

    /**
     * Logs a change of a chief status.
     *
     * @param chiefId the chief id
     * @param status the status to be logged
     */
    public void setChiefStatus(int chiefId, CHIEF_STATUS status) /*throws ComException, ShutdownException*/
    {
        this.initialize();

        try {
            this.logger.setChiefStatus(chiefId, status);
        } catch (RemoteException e) {
            System.err.println("Unable to write to log: " + e.getMessage());
            //throw new ShutdownException();
        }
    }

    /**
     * Logs a change of a thief status.
     *
     * @param thiefId the thief id
     * @param status the status to be logged
     */
    public void setThiefStatus(int thiefId, THIEF_STATUS status) /*throws ComException, ShutdownException*/
    {
        this.initialize();

        try {
            this.logger.setThiefStatus(thiefId, status);
        } catch (RemoteException e) {
            System.err.println("Unable to write to log: " + e.getMessage());
            //throw new ShutdownException();
        }
    }

    /**
     * Sets a thief details.
     *
     * @param details the details
     */
    public void setThiefDetails(ThiefDetails details) /*throws ComException, ShutdownException*/
    {
        this.initialize();

        try {
            this.logger.setThiefDetails(details);
        } catch (RemoteException e) {
            System.err.println("Unable to write to log: " + e.getMessage());
            //throw new ShutdownException();
        }
    }

    /**
     * Sets a team details.
     *
     * @param details the details
     */
    public void setTeamDetails(TeamDetails details) /*throws ComException, ShutdownException*/
    {
        this.initialize();

        try {
            this.logger.setTeamDetails(details);
        } catch (RemoteException e) {
            System.err.println("Unable to write to log: " + e.getMessage());
            //throw new ShutdownException();
        }
    }

    /**
     * Sets a room details.
     *
     * @param details the details
     */
    public void setRoomDetails(RoomDetails details) /*throws ComException, ShutdownException*/
    {
        this.initialize();

        try {
            this.logger.setRoomDetails(details);
        } catch (RemoteException e) {
            System.out.println("Unable to write to log: " + e.getMessage());
            //throw new ShutdownException();
        }
    }

    /**
     * Sets a corridor details.
     *
     * @param details the details
     */
    public void setCorridorDetails(CorridorDetails details) /*throws ComException, ShutdownException*/
    {
        this.initialize();

        try {
            this.logger.setCorridorDetails(details);
        } catch (RemoteException e) {
            System.out.println("Unable to write to log: " + e.getMessage());
            //throw new ShutdownException();
        }
    }

    /**
     * Shutdowns the server.
     *
     * @param password    the password
     * @param totalCanvas the total of canvas stolen
     *
     * @return true if it was shutted down, false otherwise (password failed)
     *
     * @throws ComException if an error occured in the communication
     */
    public boolean shutdown(String password, int totalCanvas) throws ComException
    {
        this.initialize();

        try {
            return this.logger.shutdown(password, totalCanvas);
        } catch(RemoteException e) {
            System.err.println("Unable to write to log: " + e.getMessage());
            return true;
        }
    }
}
