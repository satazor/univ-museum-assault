package museumassault.monitor;

/**
 *
 * @author Andre Cruz <andremiguelcruz@ua.pt>
 */
public interface TargetRoom
{

    /**
     * Method that returns this TargetCorridor
     *
     * @return TargetCorridor - Returns the TargetCorridor that leads to this
     * room
     */
    public TargetCorridor getTargetCorridor();

    /**
     * Method that removes a canvas from this room
     *
     * @param thiefId - the id of the thief that robbed this canvas
     * @return boolean - Returns true if a canvas was successfully removed
     */
    public boolean rollACanvas(int thiefId);
}
