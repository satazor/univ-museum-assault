package museumassault.logger.server;

import java.rmi.Remote;
import java.rmi.RemoteException;
import museumassault.logger.*;

/**
 * IRoom interface.
 *
 * @author Andre Cruz <andremiguelcruz@ua.pt>
 */
public interface ILogger extends Remote, ILoggerStatusConstants
{
    public final String RMI_NAME_ENTRY = "LoggerSD0201";

    /**
     * Logs a change of a chief status.
     *
     * @param chiefId the id of the chief
     * @param status the status to be logged
     */
    public void setChiefStatus(int chiefId, CHIEF_STATUS status) throws RemoteException;

    /**
     * Logs a change of a thief status.
     *
     * @param thiefId the thief id
     * @param status the status to be logged
     */
    public void setThiefStatus(int thiefId, THIEF_STATUS status) throws RemoteException;

    /**
     * Sets a thief details.
     *
     * @param details the details
     */
    public void setThiefDetails(ThiefDetails details) throws RemoteException;

    /**
     * Sets a room details.
     *
     * @param details the details
     */
    public void setRoomDetails(RoomDetails details) throws RemoteException;

    /**
     * Sets a corridor details.
     *
     * @param details the details
     */
    public void setCorridorDetails(CorridorDetails details) throws RemoteException;

    /**
     * Sets a team details.
     *
     * @param details the details
     */
    public void setTeamDetails(TeamDetails details) throws RemoteException;

    /**
     * Attempts to shutdown the server with the given password.
     *
     * @param shutdownPassword the password
     * @param totalCanvas      the total number of canvas stolen
     *
     * @return true if successfully, false otherwise
     */
    public boolean shutdown(String shutdownPassword, int totalCanvas) throws RemoteException;
}
