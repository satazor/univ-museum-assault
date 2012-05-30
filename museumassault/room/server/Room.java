package museumassault.room.server;

import museumassault.logger.RoomDetails;
import museumassault.logger.client.LoggerClient;

/**
 * Room class.
 *
 * This class represents a museum room.
 *
 * @author Andre Cruz <andremiguelcruz@ua.pt>
 */
public class Room
{
    protected boolean beingRobed = false;
    protected int id;
    protected int nrCanvas;
    protected int corridorId;
    protected LoggerClient logger;

    /**
     * Class constructor.
     *
     * @param id         the id of the room
     * @param nrCanvas   the number of canvas the room has initially
     * @param corridorId the corridor that leads to the room
     * @param logger     the logger to log the program state
     */
    public Room(int id, int nrCanvas, int corridorId, LoggerClient logger)
    {
        this.id = id;
        this.nrCanvas = nrCanvas;
        this.corridorId = corridorId;
        this.logger = logger;

        this.setRoomDetails();
    }

    /**
     * Sets the room details into the logger.
     */
    protected final void setRoomDetails()
    {
        this.logger.setRoomDetails(new RoomDetails(this.id, this.corridorId, this.nrCanvas));
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
     * Get the corridor id that leads to the room.
     *
     * @return the corridor
     */
    public int getCorridorId()
    {
        return this.corridorId;
    }

    /**
     * Attempts to roll a canvas from the room.
     *
     * @param thiefId the thief id
     *
     * @return true if a canvas was successfully stolen, false otherwise
     */
    public synchronized boolean rollACanvas(int thiefId)
    {
        boolean rolledCanvas;

        this.logger.setThiefStatus(thiefId, LoggerClient.THIEF_STATUS.AT_A_ROOM);

        if (this.nrCanvas > 0) {
            this.nrCanvas--;
            rolledCanvas = true;
        } else {
            rolledCanvas = false;
        }

        this.setRoomDetails();
        this.logger.setThiefStatus(thiefId, LoggerClient.THIEF_STATUS.AT_ROOM_EXIT);

        return rolledCanvas;
    }
}
