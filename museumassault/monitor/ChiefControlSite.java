package museumassault.monitor;

/**
 *
 * @author Andre Cruz <andremiguelcruz@ua.pt>
 */
public interface ChiefControlSite
{
	/**
     * Method that decides if there are still rooms to rob
     * @param chiefID - the id of the chief
     * @return Integer - Returns the id of the room that is being robbed
     */
    public Integer appraiseSit(int chiefId);

    /**
     * Method that prepares an assault team, assigning to that team the given room
     * and waking up the thieves that will form the team
     * @param chiefId - the id of the chief
     * @param roomId - the room to be robbed
     * @return Integer - Returns the id of the team prepared to rob
     */
    public Integer prepareAssaultParty(int chiefId, int roomId);

    /**
     * Method that sends the previously prepared team (from method prepareAssaultParty(roomID))
     * to rob the designated room
     * @param chiefId - the id of the chief
     * @param teamId - the team to be sent
     */
    public void sendAssaultParty(int chiefId, int teamId);

    /**
     * Method that checks if the assault is over. If it isn't the chief is told to wait
     * @param chiefId - the id of the chief
     * @return Integer - Returns null if no team is robbing
     */
    public Integer takeARest(int chiefId);

    /**
     * Method that collects the canvas handed by a thief, and checks if the room is already empty or not
     * @param chiefId - the id of the chief
     * @param thiefId - the id of the thief that handed the canvas
     */
    public void collectCanvas(int chiefId, int thiefId);

    /**
     * Method that returns the total number of canvas stolen
     * @param chiefId - the id of the chief
     * @return Integer - Returns the number of canvas stolen
     */
    public int sumUpResults(int chiefId);
}
