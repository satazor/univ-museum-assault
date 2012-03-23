package museumassault;

/**
 * Message class.
 *
 * @author André
 */
public class Message
{
    protected int action;

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
    public int getAction()
    {
        return this.action;
    }
}
