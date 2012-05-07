package museumassault.logger;

import java.io.Serializable;

/**
 * RoomDetails class.
 * This class has the details of a room in order to be saved into the log.
 *
 * @author Andre Cruz <andremiguelcruz@ua.pt>
 */
public class RoomDetails implements Serializable
{
    private static final long serialVersionUID = 1000L;

    protected int roomId;
    protected int corridorId;
    protected Integer nrCanvas;

    /**
     * Constructor.
     *
     * @param roomId
     * @param corridorId
     * @param nrCanvas
     */
    public RoomDetails(int roomId, int corridorId, Integer nrCanvas)
    {
        this.roomId = roomId;
        this.corridorId = corridorId;
        this.nrCanvas = nrCanvas;
    }

    /**
     * Get the room id.
     *
     * @return
     */
    public int getRoomId()
    {
        return this.roomId;
    }

    /**
     * Get the corridor id associated with this room.
     *
     * @return
     */
    public int getCorridorId()
    {
        return this.corridorId;
    }

    /**
     * Get the number of canvas the room still has.
     * 
     * @return
     */
    public Integer getNrCanvas()
    {
        return this.nrCanvas;
    }
}
