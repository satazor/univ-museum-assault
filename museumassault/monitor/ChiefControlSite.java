package museumassault.monitor;

/**
 * ChiefControlSite interface.
 *
 * This interfaces exposes all the methods that a chief should be aware off to
 * complete its life cycle.
 *
 * @author Andre Cruz <andremiguelcruz@ua.pt>
 */
public interface ChiefControlSite
{
    /**
     * Decide if the chief should sit or rob a room.
     *
     * @param chiefId the id of the chief
     *
     * @return the id of the room that should be robed or null if the chief should sit
     */
    public Integer appraiseSit(int chiefId);

    /**
     * Prepares an assault team, assigning a team that will be responsible for it.
     *
     * @param chiefId the id of the chief
     * @param roomId  the room that will be robbed
     *
     * @return returns the id of the assigned team or null if none is free
     */
    public Integer prepareAssaultParty(int chiefId, int roomId);

    /**
     * Method that sends a previously prepared team to rob the designated room.
     * This method should guarantee that all the thieves depart at the same time.
     *
     * @param chiefId the id of the chief
     * @param teamId  the team to be sent
     */
    public void sendAssaultParty(int chiefId, int teamId);

    /**
     * Takes a rest, waiting for a thief arrival.
     *
     * @param chiefId the id of the chief
     *
     * @return the id of the thief that arrived or null if there is no remaining thieves
     */
    public Integer takeARest(int chiefId);

    /**
     * Collects the canvas of thief that arrived.
     *
     * @param chiefId the id of the chief
     * @param thiefId the id of the thief that handed the canvas
     */
    public void collectCanvas(int chiefId, int thiefId);

    /**
     * Sums up the total canvas stolen.
     *
     * @param chiefId the id of the chief
     *
     * @return the total number of canvas stolen
     */
    public int sumUpResults(int chiefId);
}
