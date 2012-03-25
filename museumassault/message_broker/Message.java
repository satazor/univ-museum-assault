package museumassault.message_broker;

/**
 * Message class.
 *
 * @author Andre Cruz <andremiguelcruz@ua.pt>
 */
public class Message
{
    protected int action;
    protected int originId;

    /**
     * Constructor of a Message based on the action to be performed and on the id of the sender
     * @param action - the action to be performed
     * @param originId - the id of the sender
     */
    public Message(int action, int originId)
    {
        this.action = action;
        this.originId = originId;
    }

    /**
     * Constructor of a Message based on the action to be performed
     * @param action - the action to be performed
     */
    public Message(int action)
    {
        this.action = action;
    }

    /**
     * Method that returns the id of the sender of the message
     * @return Integer - Returns the id of the sender
     */
    public int getOriginId()
    {
        return this.originId;
    }

    /**
     * Method that returns the action to be performed 
     * @return Integer - Returns the action to be performed
     */
    public int getAction()
    {
        return this.action;
    }
}
