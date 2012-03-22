package museumassault;

/**
 * Message class.
 *
 * @author André
 */
public class Message {

    protected int action;
    protected int originId;
    protected int destinationId;
    public Thief thief;

    public Message(int action, int originId, int destinationId)
    {
        this.originId = originId;
        this.destinationId = destinationId;
        this.thief = thief;
    }

    public int getDestinationId()
    {
        return this.destinationId;
    }
    
    public int getAction() {
        return this.action;
    }
}
