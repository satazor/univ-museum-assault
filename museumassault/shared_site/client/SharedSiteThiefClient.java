package museumassault.shared_site.client;

import java.rmi.AccessException;
import java.rmi.RemoteException;
import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;
import java.util.Random;
import museumassault.common.exception.ComException;
import museumassault.common.exception.ShutdownException;
import museumassault.shared_site.server.IThievesConcentrationSite;

/**
 * SharedSiteChiefClient class.
 * This class allows access from a thief to a shared site service.
 * It encapsulates all the communication logic.
 *
 * @author Hugo Oliveira <hugo.oliveira@ua.pt>
 */
public class SharedSiteThiefClient
{
    protected IThievesConcentrationSite site;
    protected String host;
    protected int port;
    protected Random random = new Random();

    /**
     * Constructor.
     *
     * @param host the server host
     * @param port the server port
     */
    public SharedSiteThiefClient(String host, int port)
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
                    this.site = (IThievesConcentrationSite) registry.lookup(IThievesConcentrationSite.RMI_NAME_ENTRY);
                } catch (AccessException e) {
                    throw new ComException("Server refused connection: " + e.getMessage());
                } catch (Exception e) {
                    System.err.println("Server seems to be down, retrying in a while.. (" + e.getMessage() + ")");

                    try {
                        Thread.sleep(this.random.nextInt(100) + 100);
                    } catch (InterruptedException ex) {}
                }

            } while (this.site == null);
        }
    }

    /**
     * Waits until the thief is needed.
     *
     * @param thiefId the thief id
     *
     * @return the id of the team in which this thief is needed
     */
    public Integer amINeeded(int thiefId) throws ShutdownException, ComException
    {
        this.initialize();

        try {
            return this.site.amINeeded(thiefId);
        } catch(RemoteException e) {
            throw new ShutdownException();
        }
    }

    /**
     * Prepares the thief for the excursion. This method should signal the
     * master that the thief is ready and only depart when notified.
     *
     * @param thiefId the thief id
     * @param teamId  the team in which the thief belongs
     *
     * @return the room assigned to the team
     */
    public Integer prepareExcursion(int thiefId, int teamId) throws ShutdownException, ComException
    {
        this.initialize();

        try {
            return this.site.prepareExcursion(thiefId, teamId);
        } catch(RemoteException e) {
            throw new ShutdownException();
        }
    }

    /**
     * Hands a canvas to the chief.
     *
     * @param thiefId      the thief id
     * @param teamId       the team where that thief belongs
     * @param rolledCanvas true if a canvas was stolen, false otherwise
     */
    public void handACanvas(int thiefId, int teamId, boolean rolledCanvas) throws ShutdownException, ComException
    {
        this.initialize();

        try {
            this.site.handACanvas(thiefId, teamId, rolledCanvas);
        } catch(RemoteException e) {
            throw new ShutdownException();
        }
    }
}
