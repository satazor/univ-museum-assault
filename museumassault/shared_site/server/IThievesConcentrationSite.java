package museumassault.shared_site.server;

import java.rmi.Remote;
import java.rmi.RemoteException;

/**
 * IThievesConcentrationSite interface.
 *
 * @author Andre Cruz <andremiguelcruz@ua.pt>
 */
public interface IThievesConcentrationSite extends Remote
{
    public final String RMI_NAME_ENTRY = "SharedSiteThievesSD0201";

    /**
     * Waits until the thief is needed.
     *
     * @param thiefId the thief id
     *
     * @return the id of the team in which this thief is needed
     */
    public Integer amINeeded(int thiefId) throws RemoteException;

    /**
     * Prepares the thief for the excursion.
     * This method should signal the master that the thief is ready and only depart
     * when notified.
     *
     * @param thiefId the thief id
     * @param teamId  the team in which the thief belongs
     *
     * @return the id of the room assigned to the team
     */
    public Integer prepareExcursion(int thiefId, int teamId) throws RemoteException;

    /**
     * Hands a canvas to the chief.
     *
     * @param thiefId      the thief id
     * @param teamId       the team where that thief belongs
     * @param rolledCanvas true if a canvas was stolen, false otherwise
     */
    public void handACanvas(int thiefId, int teamId, boolean rolledCanvas) throws RemoteException;
}
