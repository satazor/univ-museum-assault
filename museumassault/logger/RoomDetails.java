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
    protected int nrCanvas;

    /**
     *
     * @param roomId
     * @param nrCanvas
     */
    public RoomDetails(int roomId, int corridorId, int nrCanvas)
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
    public int getNrCanvas()
    {
        return this.nrCanvas;
    }
}
