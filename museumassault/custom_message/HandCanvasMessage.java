package museumassault.custom_message;

import museumassault.message_broker.Message;

/**
 * Message class.
 *
 * @author Andre Cruz <andremiguelcruz@ua.pt>
 */
public class HandCanvasMessage extends Message
{
    protected boolean gotCanvas;
    protected int teamId;

    /**
     *
     * @param action
     */
    public HandCanvasMessage(int action, int thiefId, int teamId, boolean gotCanvas)
    {
        super(action, thiefId);

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
