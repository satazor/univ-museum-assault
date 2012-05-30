package museumassault.room.server;

import java.rmi.Remote;
import java.rmi.RemoteException;

/**
 * IRoom interface.
 *
 * @author Andre Cruz <andremiguelcruz@ua.pt>
 */
public interface IRoom extends Remote
{
    public final String RMI_NAME_ENTRY = "Room";

    /**
     * Attempts to roll a canvas from the room.
     *
     * @param thiefId the thief id
     *
     * @return true if a canvas was successfully stolen, false otherwise
     */
    public boolean rollACanvas(int thiefId) throws RemoteException;

    /**
     * Attempts to shutdown the server with the given password.
     *
     * @param shutdownPassword the password
     *
     * @return true if successfully, false otherwise
     */
    public boolean shutdown(String shutdownPassword) throws RemoteException;
}
