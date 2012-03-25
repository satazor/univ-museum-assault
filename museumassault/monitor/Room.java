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
     * Constructor of a Room
     * @param id - the id of the room
     * @param corridor - the corridor that leads to this room 
     * @param logger - the logger used to store the process
     */
    public Room(int id, int nrCanvas, Corridor corridor, Logger logger)
    {
        this.id = id;
        this.nrCanvas = nrCanvas;
        this.corridor = corridor;
        this.logger = logger;
    }

    /**
     * Method that returns the id of this room
     * @return Integer - Returns the id of this room
     */
    public int getId()
    {
        return this.id;
    }

    /**
     * Method that returns the corridor that leads to this room
     * @return Corridor - Returns the corridor that leads to this room
     */
    public Corridor getCorridor()
    {
        return this.corridor;
    }
    /**
     * Method that returns this TargetCorridor
     * @return TargetCorridor - Returns the TargetCorridor that leads to this room
     */
    @Override
    public TargetCorridor getTargetCorridor()
    {
        return (TargetCorridor) this.corridor;
    }

    /**
     * Method that checks if the room is being robbed
     * @return boolean - Returns true if this room is being robbed
     */
    public boolean isBeingRobed()
    {
        return this.beingRobed;
    }

    /**
     * Method that checks if the room is being robbed
     * @param robed - true if we want to check i the room is being robbed
     * @return boolean - Returns true if this room is being robbed
     */
    public boolean isBeingRobed(boolean robed)
    {
        return this.beingRobed = robed;
    }

    /**
     * Method that removes a canvas from this room
     * @param thiefId - the id of the thief that robbed this canvas
     * @return boolean - Returns true if a canvas was successfully removed
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
