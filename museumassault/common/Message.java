package museumassault.common;

/**
 * Message class.
 *
 * This is the base message that the message broker is aware of.
 *
 * @author Andre Cruz <andremiguelcruz@ua.pt>
 */
public class Message
{
    protected int type;
    protected int originId;
    protected Object extra;

    /**
     * Class constructor.
     *
     * @param action   the action to be performed
     * @param originId the id of the sender
     * @param extra    an object with aditional information
     */
    public Message(int type, int originId, Object extra)
    {
        this.type = type;
        this.originId = originId;
        this.extra = extra;
    }

    /**
     * Class constructor.
     *
     * @param action   the action to be performed
     * @param extra    an object with aditional information
     */
    public Message(int type, Object extra)
    {
        this.type = type;
        this.extra = extra;
    }

    /**
     * Class constructor.
     *
     * @param action   the action to be performed
     * @param originId the id of the sender
     */
    public Message(int type, int originId)
    {
        this.type = type;
        this.originId = originId;
    }

    /**
     * Class constructor.
     *
     * @param action the action to be performed
     */
    public Message(int type)
    {
        this.type = type;
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
    public int getType()
    {
        return this.type;
    }

    /**
     * Get the extra object associated with this message.
     * This can be anything that must be passed associated with the type of the message.
     *
     * @return the object
     */
    public Object getExtra()
    {
        return this.extra;
    }
}
