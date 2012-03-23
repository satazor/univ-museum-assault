package museumassault.message_broker;

/**
 * Message class.
 *
 * @author André
 */
public class Message
{
    protected int action;
    protected int originId;

    /**
     *
     * @param action
     */
    public Message(int action, int originId)
    {
        this.action = action;
        this.originId = originId;
    }

    /**
     *
     * @param action
     */
    public Message(int action)
    {
        this.action = action;
    }

    /**
     *
     * @return
     */
    public int getOriginId()
    {
        return this.originId;
    }

    /**
     *
     * @return
     */
    public int getAction()
    {
        return this.action;
    }
}
