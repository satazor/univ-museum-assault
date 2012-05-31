package museumassault.room.client;

import java.rmi.AccessException;
import java.rmi.RemoteException;
import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;
import java.util.Random;
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
    protected Random random = new Random();

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
            do {
                try {
                    Registry registry = LocateRegistry.getRegistry(this.host, this.port);
                    this.room = (IRoom) registry.lookup(IRoom.RMI_NAME_ENTRY);
                } catch (AccessException e) {
                    throw new ComException("Server refused connection: " + e.getMessage());
                } catch (Exception e) {
                    System.err.println("Server seems to be down, retrying in a while..");

                    try {
                        Thread.sleep(this.random.nextInt(100) + 100);
                    } catch (InterruptedException ex) {}
                }

            } while (this.room == null);
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
