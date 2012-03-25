package museumassault.message_broker;

/**
 * Message class.
 *
 * This is the base message that the message broker is aware of.
 *
 * @author Andre Cruz <andremiguelcruz@ua.pt>
 */
public class Message
{
    protected int action;
    protected int originId;

    /**
     * Class constructor.
     *
     * @param action   the action to be performed
     * @param originId the id of the sender
     */
    public Message(int action, int originId)
    {
        this.action = action;
        this.originId = originId;
    }

    /**
     * Class constructor.
     *
     * @param action the action to be performed
     */
    public Message(int action)
    {
        this.action = action;
    }

    /**
     * Get the id of the sender.
     *
     * @return returns the id of the sender
     */
    public int getOriginId()
    {
        return this.originId;
    }

    /**
     * Get the action that was performed.
     *
     * @return the action that was performed
     */
    public int getAction()
    {
        return this.action;
    }
}
