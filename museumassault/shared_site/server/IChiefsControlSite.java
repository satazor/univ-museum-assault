package museumassault.shared_site.server;

import java.rmi.Remote;
import java.rmi.RemoteException;

/**
 * IChiefsControlSite interface.
 *
 * @author Andre Cruz <andremiguelcruz@ua.pt>
 */
public interface IChiefsControlSite extends Remote
{
    public final String RMI_NAME_ENTRY = "SharedSiteChiefsSD0201";

    /**
     * Decide if the chief should sit or rob a room.
     *
     * @param chiefId the id of the chief
     *
     * @return the id of the room that should be robed or null if the chief should sit
     */
    public Integer appraiseSit(int chiefId) throws RemoteException;

    /**
     * Prepares an assault team, assigning a team that will be responsible for it.
     *
     * @param chiefId the id of the chief
     * @param roomId  the room that will be robbed
     *
     * @return returns the id of the assigned team or null if none is free
     */
    public Integer prepareAssaultParty(int chiefId, int roomId) throws RemoteException;

    /**
     * Method that sends a previously prepared team to rob the designated room.
     * This method guarantees that all the thieves depart at the same time.
     *
     * @param chiefId the id of the chief
     * @param teamId  the team to be sent
     */
    public void sendAssaultParty(int chiefId, int teamId) throws RemoteException;

    /**
     * Collects the canvas of thief that arrived.
     *
     * @param chiefId the id of the chief
     * @param thiefId the id of the thief that handed the canvas
     */
    public void collectCanvas(int chiefId, int thiefId) throws RemoteException;

    /**
     * Takes a rest, waiting for a thief arrival.
     *
     * @param chiefId the id of the chief
     *
     * @return the id of the thief that arrived or null if there is no remaining thieves
     */
    public Integer takeARest(int chiefId)  throws RemoteException;

    /**
     * Sums up the total canvas stolen.
     *
     * @param chiefId the id of the chief
     *
     * @return the total number of canvas stolen
     */
    public Integer sumUpResults(int chiefId) throws RemoteException;

    /**
     * Attempts to shutdown the server with the given password.
     *
     * @param shutdownPassword the password
     *
     * @return true if successfully, false otherwise
     */
    public boolean shutdown(String shutdownPassword) throws RemoteException;
}
