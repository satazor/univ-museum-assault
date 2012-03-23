package museumassault;

/**
 * Message class.
 *
 * @author André
 */
public class ArriveMessage extends Message
{
    protected boolean rolledCanvas;
    protected int roomId;

    /**
     *
     * @param action
     */
    public ArriveMessage(int action, int roomId, boolean rolledCanvas)
    {
        super(action);

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
