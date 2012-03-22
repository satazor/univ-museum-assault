package museumassault;

/**
 * Message class.
 *
 * @author André
 */
public class HandCanvasMessage extends Message
{

    protected boolean rolledCanvas;
    protected int roomId;

    /**
     *
     * @param action
     */
    public HandCanvasMessage(int originId, int action, int roomId, boolean rolledCanvas)
    {
        super(originId, action);

        this.rolledCanvas = rolledCanvas;
        this.roomId = roomId;
    }

    /**
     *
     */
    public int getRoomId() {
        return this.roomId;
    }

    /**
     *
     */
    public boolean rolledCanvas() {
        return this.rolledCanvas;
    }
}
