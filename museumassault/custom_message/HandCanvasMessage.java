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
     * Constructor of a HandCanvasMessage
     * @param action - the action performed
     * @param thiefId - the if of the thief that stole (or not) the canvas
     * @param gotCanvas -  true if the thief stole a canvas
     * @param teamId - the id of the team that was robbing the room
     */
    public HandCanvasMessage(int action, int thiefId, int teamId, boolean gotCanvas)
    {
        super(action, thiefId);

        this.gotCanvas = gotCanvas;
        this.teamId = teamId;
    }

    /**
     * Method that returns the id of the team that got the canvas
     * @return Integer - Returns the id of the team robbing the room
     */
    public int getTeamId() {
        return this.teamId;
    }

    /**
     * Method that checks if a canvas was stolen from the room
     * @return boolean - true if a canvas was really stolen
     */
    public boolean rolledCanvas() {
        return this.gotCanvas;
    }
}
