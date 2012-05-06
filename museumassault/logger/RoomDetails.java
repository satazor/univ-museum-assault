package museumassault.logger;

import java.io.Serializable;

/**
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
     *
     * @param roomId
     * @param nrCanvas
     */
    public RoomDetails(int roomId, int corridorId, Integer nrCanvas)
    {
        this.roomId = roomId;
        this.corridorId = corridorId;
        this.nrCanvas = nrCanvas;
    }

    /**
     *
     * @return
     */
    public int getRoomId()
    {
        return this.roomId;
    }

    /**
     *
     * @return
     */
    public int getCorridorId()
    {
        return this.corridorId;
    }

    /**
     *
     * @return
     */
    public Integer getNrCanvas()
    {
        return this.nrCanvas;
    }
}
