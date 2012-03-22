package museumassault;

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
    public Message(int action)
    {
        this.action = action;
    }

    /**
     *
     * @param action
     */
    public Message(int originId, int action)
    {
        this.action = action;
        this.originId = originId;
    }

    /**
     *
     * @return
     */
    public int getAction()
    {
        return this.action;
    }

    /**
     *
     * @return
     */
    public int getOriginId()
    {
        return this.originId;
    }
}
