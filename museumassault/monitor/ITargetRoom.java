package museumassault.monitor;

/**
 * TargetRoom interface.
 *
 * This interface exposes all the methods that a thief should known in order to
 * enter in it and to grab the its corridor.
 *
 * @author Andre Cruz <andremiguelcruz@ua.pt>
 */
public interface ITargetRoom
{
    /**
     * Get the corridor of the room.
     *
     * @return the corridor that leads to the room
     */
    public ITargetCorridor getTargetCorridor();

    /**
     * Attempts to roll a canvas from the room.
     *
     * @param thiefId the thief id
     *
     * @return true if a canvas was successfully stolen, false otherwise
     */
    public boolean rollACanvas(int thiefId);
}
