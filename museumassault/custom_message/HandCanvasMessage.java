package museumassault.custom_message;

import museumassault.message_broker.Message;

/**
 * HandCanvasMessage class.
 *
 * This custom message is used when the thief hands a canvas.
 *
 * @author Andre Cruz <andremiguelcruz@ua.pt>
 */
public class HandCanvasMessage extends Message
{
    protected boolean gotCanvas;
    protected int teamId;

    /**
     * Class constructor.
     *
     * @param action    the action to be performed
     * @param thiefId   the id of the thief that handed the canvas
     * @param teamId    the id of the team that the thief belongs to
     * @param gotCanvas true if the thief rolled a canvas, false otherwise
     */
    public HandCanvasMessage(int action, int thiefId, int teamId, boolean gotCanvas)
    {
        super(action, thiefId);

        this.gotCanvas = gotCanvas;
        this.teamId = teamId;
    }

    /**
     * Get the id of the team.
     *
     * @return the id of the team robbing the room
     */
    public int getTeamId() {
        return this.teamId;
    }

    /**
     * Checks if the thief rolled a canvas.
     *
     * @return true if a canvas was stolen, false otherwise
     */
    public boolean rolledCanvas() {
        return this.gotCanvas;
    }
}
