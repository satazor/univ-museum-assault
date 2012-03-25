package museumassault.custom_message;

import museumassault.message_broker.Message;

/**
 * Message class.
 *
 * @author Andre Cruz <andremiguelcruz@ua.pt>
 */
public class PrepareAssaultMessage extends Message
{
    protected int roomId;
    protected int teamId;
    /**
     * Constructor of a PrepareAssaultMessage
     * @param action - the action to be performed
     * @param teamId - the id of the team that is going to take action
     * @param roomId - the id of the room 
     */
    public PrepareAssaultMessage(int action, int teamId, int roomId)
    {
        super(action);

        this.teamId = teamId;
        this.roomId = roomId;
    }

    /**
     * Method that returns the id of the room to rob
     * @return Integer - Returns the id of the room
     */
    public int getRoomId() {
        return this.roomId;
    }

    /**
     * Method that returns the id of the team that is going to rob
     * @return Integer - Returns the id of the team
     */
    public int getTeamId() {
        return this.teamId;
    }
}
