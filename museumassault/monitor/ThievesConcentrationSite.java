package museumassault.monitor;

/**
 * ThievesConcentrationSite interface.
 *
 * This interfaces exposes all the methods that a thief should be aware off to
 * complete its lifecycle.
 *
 * @author Andre Cruz <andremiguelcruz@ua.pt>
 */
public interface ThievesConcentrationSite
{
    /**
     * Waits until the thief is needed.
     *
     * @param thiefId the thief id
     *
     * @return the id of the team in which this thief is needed
     */
    public Integer amINeeded(int thiefId);

    /**
     * Prepares the thief for the excursion.
     * This method should signal the master that the thief is ready and only depart
     * when notified.
     *
     * @param thiefId the thief id
     * @param teamId  the team in which the thief belongs
     *
     * @return the room assigned to the team
     */
    public TargetRoom prepareExcursion(int thiefId, int teamId);

    /**
     * Hands a canvas to the chief.
     *
     * @param thiefId      the thief id
     * @param teamId       the team where that thief belongs
     * @param rolledCanvas true if a canvas was stolen, false otherwise
     */
    public void handACanvas(int thiefId, int teamId, boolean rolledCanvas);
}
