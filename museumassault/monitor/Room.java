package museumassault.monitor;

/**
 *
 * @author Andre Cruz <andremiguelcruz@ua.pt>
 */
public class Room implements TargetRoom
{
    protected boolean beingRobed = false;
    protected int id;
    protected int nrCanvas;
    protected Corridor corridor;
    protected Logger logger;

    /**
     *
     * @param id
     */
    public Room(int id, int nrCanvas, Corridor corridor, Logger logger)
    {
        this.id = id;
        this.nrCanvas = nrCanvas;
        this.corridor = corridor;
        this.logger = logger;
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
    public synchronized boolean rollACanvas(int thiefId)
    {
        this.logger.setThiefStatus(thiefId, Logger.THIEF_STATUS.AT_A_ROOM);

        if (this.nrCanvas > 0) {
            this.nrCanvas--;
            return true;
        }
        return false;
    }
}
