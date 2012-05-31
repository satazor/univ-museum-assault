package museumassault.shared_site.client;

import java.rmi.AccessException;
import java.rmi.NotBoundException;
import java.rmi.RemoteException;
import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;
import java.util.Random;
import museumassault.common.exception.ComException;
import museumassault.common.exception.ShutdownException;
import museumassault.shared_site.server.IChiefsControlSite;

/**
 * SharedSiteChiefClient class.
 * This class allows access from a chief to a shared site service.
 * It encapsulates all the communication logic.
 *
 * @author Andre Cruz <andremiguelcruz@ua.pt>
 */
public class SharedSiteChiefClient
{
    protected IChiefsControlSite site = null;
    protected String host;
    protected int port;
    protected Random random = new Random();

    /**
     * Constructor.
     *
     * @param host the server host
     * @param port the server port
     */
    public SharedSiteChiefClient(String host, int port)
    {
        this.host = host;
        this.port = port;
    }

    /**
     * Initializes the RMI connection.
     */
    protected void initialize() throws ComException
    {
        if (this.site == null) {
            do {
                try {
                    Registry registry = LocateRegistry.getRegistry(this.host, this.port);
                    this.site = (IChiefsControlSite) registry.lookup(IChiefsControlSite.RMI_NAME_ENTRY);
                } catch (AccessException e) {
                    throw new ComException("Server refused connection: " + e.getMessage());
                } catch (Exception e) {
                    System.err.println("Server seems to be down, retrying in a while..");

                    try {
                        Thread.sleep(this.random.nextInt(100) + 100);
                    } catch (InterruptedException ex) {}
                }

            } while (this.site == null);
        }
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
        this.initialize();

        try {
            return this.site.appraiseSit(chiefId);
        } catch(RemoteException e) {
            throw new ShutdownException();
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
        this.initialize();

        try {
            return this.site.prepareAssaultParty(chiefId, roomId);
        } catch(RemoteException e) {
            throw new ShutdownException();
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
        this.initialize();

        try {
            this.site.sendAssaultParty(chiefId, teamId);
        } catch(RemoteException e) {
            throw new ShutdownException();
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
        this.initialize();

        try {
            return this.site.takeARest(chiefId);
        } catch(RemoteException e) {
            throw new ShutdownException();
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
        this.initialize();

        try {
            this.site.collectCanvas(chiefId, thiefId);
        } catch(RemoteException e) {
            throw new ShutdownException();
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
        this.initialize();

        try {
            return this.site.sumUpResults(chiefId);
        } catch(RemoteException e) {
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
            return this.site.shutdown(password);
        } catch(RemoteException e) {
            return true;
        }
    }
}
