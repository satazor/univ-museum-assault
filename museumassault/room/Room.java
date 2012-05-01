package museumassault.room;

import museumassault.room.ITargetRoom;
import museumassault.corridor.ITargetCorridor;
import museumassault.corridor.Corridor;
import museumassault.logger.Logger;

/**
 * Room class.
 *
 * This class represents a museum room.
 *
 * @author Andre Cruz <andremiguelcruz@ua.pt>
 */
public class Room implements ITargetRoom
{
    protected boolean beingRobed = false;
    protected int id;
    protected int nrCanvas;
    protected Corridor corridor;
    protected Logger logger;

    /**
     * Class constructor.
     *
     * @param id       the id of the room
     * @param nrCanvas the number of canvas the room has initially
     * @param corridor the corridor that leads to the room
     * @param logger   the logger to log the program state
     */
    public Room(int id, int nrCanvas, Corridor corridor, Logger logger)
    {
        this.id = id;
        this.nrCanvas = nrCanvas;
        this.corridor = corridor;
        this.logger = logger;
    }

    /**
     * Get the room id.
     *
     * @return the id of the room
     */
    public int getId()
    {
        return this.id;
    }

    /**
     * Get the current number of canvas in the room.
     *
     * @return the number of canvas currently in the room
     */
    public int getNrCanvas()
    {
        return this.nrCanvas;
    }

    /**
     * Get the corridor that leads to the room (concrete).
     *
     * @return the corridor
     */
    public Corridor getCorridor()
    {
        return this.corridor;
    }

    /**
     * Get the corridor that leads to the room.
     *
     * @return the corridor
     */
    @Override
    public ITargetCorridor getTargetCorridor()
    {
        return (ITargetCorridor) this.corridor;
    }

    /**
     * Check if the rooms is being robed.
     *
     * @return true if this room is being robbed, false otherwise
     */
    public boolean isBeingRobed()
    {
        return this.beingRobed;
    }

    /**
     * Sets if a room is being robed.
     *
     * @param robed true to set the robed state to true, false otherwise
     */
    public void isBeingRobed(boolean robed)
    {
        this.beingRobed = robed;
    }

    /**
     * Attempts to roll a canvas from the room.
     *
     * @param thiefId the thief id
     *
     * @return true if a canvas was successfully stolen, false otherwise
     */
    @Override
    public synchronized boolean rollACanvas(int thiefId)
    {
        boolean rolledCanvas;

        this.logger.setThiefStatus(thiefId, Logger.THIEF_STATUS.AT_A_ROOM);

        if (this.nrCanvas > 0) {
            this.nrCanvas--;
            rolledCanvas = true;
        } else {
            rolledCanvas = false;
        }

        this.logger.setThiefStatus(thiefId, Logger.THIEF_STATUS.AT_ROOM_EXIT);

        return rolledCanvas;
    }
}
