package museumassault.room.client;

import java.rmi.RemoteException;
import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;
import museumassault.common.exception.ComException;
import museumassault.common.exception.ShutdownException;
import museumassault.room.server.IRoom;

/**
 * RoomClient class.
 * This class allows access to a room service.
 * It encapsulates all the communication logic.
 *
 * @author Andre Cruz <andremiguelcruz@ua.pt>
 */
public class RoomClient
{
    protected IRoom room = null;
    protected String host;
    protected int port;

    /**
     * Constructor.
     *
     * @param host the server host
     * @param port the server port
     */
    public RoomClient(String host, int port)
    {
        this.host = host;
        this.port = port;
    }

    /**
     * Initializes the RMI connection.
     */
    protected void initialize() throws ComException
    {
        if (this.room == null) {
            try {
                Registry registry = LocateRegistry.getRegistry(this.host, this.port);
                this.room = (IRoom) registry.lookup(IRoom.RMI_NAME_ENTRY);
            } catch (Exception e) {
                throw new ComException("Unable to connect to remote server: " + e.getMessage());
            }
        }
    }

    /**
     * Attempts to roll a canvas from the room.
     *
     * @param thiefId the thief id
     *
     * @return true if a canvas was successfully stolen, false otherwise
     */
    public boolean rollACanvas(int thiefId) throws ShutdownException, ComException
    {
        this.initialize();

        try {
            return this.room.rollACanvas(thiefId);
        } catch (RemoteException e) {
            throw new ShutdownException();
        }
    }

    /**
     * Shutdowns the server.
     *
     * @param password the password
     *
     * @return true if it was shutted down, false otherwise (password failed)
     *
     * @throws ComException if an error occured in the communication
     */
    public boolean shutdown(String password) throws ComException
    {
        this.initialize();

        try {
            return this.room.shutdown(password);
        } catch(RemoteException e) {
            return true;
        }
    }
}
