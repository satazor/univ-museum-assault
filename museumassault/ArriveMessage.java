package museumassault;

/**
 * Message class.
 *
 * @author André
 */
public class ArriveMessage extends Message
{
    protected boolean gotCanvas;
    protected int teamId;

    /**
     *
     * @param action
     */
    public ArriveMessage(int action, int teamId, boolean gotCanvas)
    {
        super(action);

        this.gotCanvas = gotCanvas;
        this.teamId = teamId;
    }

    /**
     *
     */
    public int getTeamId() {
        return this.teamId;
    }

    /**
     *
     */
    public boolean rolledCanvas() {
        return this.gotCanvas;
    }
}
