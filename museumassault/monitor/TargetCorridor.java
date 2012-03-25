package museumassault.monitor;

/**
 *
 * @author Andre Cruz <andremiguelcruz@ua.pt>
 */
public interface TargetCorridor
{
    /**
     * Method that moves the thief towards the room
     * @param thiefId - the id of the thief that is moving
     * @param increment - the number of steps that thief is trying to move
     * @return boolean - Returns true if the thief managed to move to the wanted position
     */
    public boolean crawlOut(int thiefId, int increment);

    /**
     * Method that moves the thief from the room to the outside
     * @param thiefId - the id of the thief that is moving
     * @param increment - the number of steps that thief is trying to move
     * @return boolean - Returns true if the thief managed to move to the wanted position
     */
    public boolean crawlIn(int thiefId, int increment);
}
