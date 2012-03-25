package museumassault.custom_message;

import museumassault.message_broker.Message;

/**
 * PrepareAssaultMessage class.
 *
 * This custom message is used when preparing an assault.
 *
 * @author Andre Cruz <andremiguelcruz@ua.pt>
 */
public class PrepareAssaultMessage extends Message
{
    protected int roomId;
    protected int teamId;

    /**
     * Class constructor.
     *
     * @param action the action that was performed
     * @param teamId the id of the team that will assault the room
     * @param roomId the id of the room that will be robbed
     */
    public PrepareAssaultMessage(int action, int teamId, int roomId)
    {
        super(action);

        this.teamId = teamId;
        this.roomId = roomId;
    }

    /**
     * Get the room id.
     *
     * @return the id of the room
     */
    public int getRoomId() {
        return this.roomId;
    }

    /**
     * Get the team id.
     *
     * @return the id of the team
     */
    public int getTeamId() {
        return this.teamId;
    }
}
