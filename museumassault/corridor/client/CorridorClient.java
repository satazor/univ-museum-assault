package museumassault.corridor.client;

import java.rmi.AccessException;
import java.rmi.RemoteException;
import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;
import java.util.Random;
import museumassault.common.exception.ComException;
import museumassault.common.exception.ShutdownException;
import museumassault.corridor.server.ICorridor;

/**
 * CorridorClient class.
 * This class allows access to a corridor service.
 * It encapsulates all the communication logic.
 *
 * @author Hugo Oliveira <hugo.oliveira@ua.pt>
 * @author Andre Cruz <andremiguelcruz@ua.pt>
 */
public class CorridorClient
{
    protected ICorridor corridor = null;
    protected String host;
    protected int port;
    protected Random random = new Random();

    /**
     * Constructor.
     *
     * @param host the server host
     * @param port the server port
     */
    public CorridorClient(String host, int port)
    {
        this.host = host;
        this.port = port;
    }

    /**
     * Initializes the RMI connection.
     */
    protected void initialize() throws ComException
    {
        if (this.corridor == null) {
            do {
                try {
                    Registry registry = LocateRegistry.getRegistry(this.host, this.port);
                    this.corridor = (ICorridor) registry.lookup(ICorridor.RMI_NAME_ENTRY);
                } catch (AccessException e) {
                    throw new ComException("Server refused connection: " + e.getMessage());
                } catch (Exception e) {
                    System.err.println("Server seems to be down, retrying in a while.. ");

                    try {
                        Thread.sleep(this.random.nextInt(100) + 100);
                    } catch (InterruptedException ex) {}
                }

            } while (this.corridor == null);
        }
    }

    /**
     * Moves the thief towards the room.
     *
     * @param thiefId   the id of the thief that is moving
     * @param increment the number of positions the thief is attempting to crawl
     *
     * @return true if the thief arrived the room entrance, false otherwise
     */
    public boolean crawlOut(int thiefId, int increment) throws ShutdownException, ComException
    {
        this.initialize();

        try {
            return this.corridor.crawlOut(thiefId, increment);
        } catch (RemoteException e) {
            throw new ShutdownException();
        }
    }

    /**
     * Moves the thief towards the outside.
     *
     * @param thiefId   the id of the thief that is moving
     * @param increment the number of positions the thief is attempting to crawl
     *
     * @return true if the thief arrived the outside entrance, false otherwise
     */
    public boolean crawlIn(int thiefId, int increment) throws ShutdownException, ComException
    {
        this.initialize();

        try {
            return this.corridor.crawlIn(thiefId, increment);
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
            return this.corridor.shutdown(password);
        } catch(RemoteException e) {
            return true;
        }
    }
}
