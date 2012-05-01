package museumassault.corridor;

/**
 * TargetCorridor interface.
 *
 * This interface exposes all the methods that a thief should known in order to
 * crawl in it.
 *
 * @author Andre Cruz <andremiguelcruz@ua.pt>
 */
public interface ITargetCorridor
{
    /**
     * Moves the thief towards the room.
     *
     * @param thiefId   the id of the thief that is moving
     * @param increment the number of positions the thief is attempting to crawl
     *
     * @return True if the thief arrived the room entrance, false otherwise
     */
    public boolean crawlOut(int thiefId, int increment);

    /**
     * Moves the thief towards the outside.
     *
     * @param thiefId   the id of the thief that is moving
     * @param increment the number of positions the thief is attempting to crawl
     *
     * @return True if the thief arrived the outside entrance, false otherwise
     */
    public boolean crawlIn(int thiefId, int increment);
}
