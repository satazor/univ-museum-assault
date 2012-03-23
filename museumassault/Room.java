package museumassault;

/**
 *
 * @author André
 */
public class Room
{
    protected boolean beingRobed = false;
    protected boolean hasCanvas = true;
    protected int id;

    /**
     *
     * @param id
     */
    public Room(int id)
    {
        this.id = id;
    }

    /**
     *
     * @return
     */
    public int getId()
    {
        return this.id;
    }

    /**
     *
     * @return
     */
    public boolean isBeingRobed()
    {
        return this.beingRobed;
    }

    /**
     *
     * @return
     */
    public boolean isBeingRobed(boolean robed)
    {
        return this.beingRobed = robed;
    }

    /**
     *
     * @return
     */
    public boolean stillHasCanvas()
    {
        return this.hasCanvas;
    }

    /**
     *
     * @return
     */
    public boolean stillHasCanvas(boolean hasCanvas)
    {
        return this.hasCanvas = hasCanvas;
    }
}
