package museumassault.monitor;

/**
 *
 * @author Andre Cruz <andremiguelcruz@ua.pt>
 */
public interface ThievesConcentrationSite
{
    /**
     * Method that checks if the given thief is needed for a team. While the
     * thief isn't needed he waits
     *
     * @param thiefId - the thief that needs to know if is needed
     * @return Integer - Returns the id of the team that needs this thief
     */
    public Integer amINeeded(int thiefId);

    /**
     * Method that prepares the team to rob a room. Guarantees that all thieves
     * are sent at the same time
     *
     * @param teamId - the team that is going to rob
     * @param thiefId - the thief that is going to rob, associated with the
     * given team
     * @return TargetRoom - Returns the room assigned to the given team
     */
    public TargetRoom prepareExcursion(int thiefId, int teamId);

    /**
     * Method that notifies the chief that a canvas has been handed by the given
     * thief
     *
     * @param thiefId - the thief that handed the canvas
     * @param teamId - the team where that thief belongs
     * @param rolledCanvas - true if a canvas was really stolen
     */
    public void handACanvas(int thiefId, int teamId, boolean rolledCanvas);
}
