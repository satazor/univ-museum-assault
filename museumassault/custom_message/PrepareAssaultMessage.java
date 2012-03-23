package museumassault.custom_message;

import museumassault.message_broker.Message;

/**
 * Message class.
 *
 * @author André
 */
public class PrepareAssaultMessage extends Message
{
    protected int roomId;
    protected int teamId;
    /**
     *
     * @param action
     */
    public PrepareAssaultMessage(int action, int teamId, int roomId)
    {
        super(action);

        this.teamId = teamId;
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
    public int getTeamId() {
        return this.teamId;
    }
}
