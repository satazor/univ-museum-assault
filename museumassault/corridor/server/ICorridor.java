package museumassault.corridor.server;

import java.rmi.Remote;
import java.rmi.RemoteException;

/**
 * ICorridor interface.
 *
 * @author Andre Cruz <andremiguelcruz@ua.pt>
 */
public interface ICorridor extends Remote
{
    public final String RMI_NAME_ENTRY = "CorridorSD0201";

    /**
     * Moves the thief towards the room.
     *
     * @param thiefId   the id of the thief that is moving
     * @param increment the number of positions the thief is attempting to crawl
     *
     * @return true if the thief arrived the room entrance, false otherwise
     */
    public boolean crawlOut(int thiefId, int increment) throws RemoteException;

    /**
     * Moves the thief towards the outside.
     *
     * @param thiefId   the id of the thief that is moving
     * @param increment the number of positions the thief is attempting to crawl
     *
     * @return true if the thief arrived the outside entrance, false otherwise
     */
    public boolean crawlIn(int thiefId, int increment) throws RemoteException;

    /**
     * Attempts to shutdown the server with the given password.
     *
     * @param shutdownPassword the password
     *
     * @return true if successfully, false otherwise
     */
    public boolean shutdown(String shutdownPassword) throws RemoteException;
}