package museumassault.monitor;

/**
 *
 * @author André
 */
public class Room implements TargetRoom
{
    protected boolean beingRobed = false;
    protected int id;
    protected int nrCanvas;
    protected Corridor corridor;

    /**
     *
     * @param id
     */
    public Room(int id, int nrCanvas, Corridor corridor)
    {
        this.id = id;
        this.nrCanvas = nrCanvas;
        this.corridor = corridor;
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
    public Corridor getCorridor()
    {
        return this.corridor;
    }
    /**
     *
     * @return
     */
    @Override
    public TargetCorridor getTargetCorridor()
    {
        return (TargetCorridor) this.corridor;
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
    @Override
    public synchronized boolean rollACanvas()
    {
        if (this.nrCanvas > 0) {
            this.nrCanvas--;
            return true;
        }
        return false;
    }
}
