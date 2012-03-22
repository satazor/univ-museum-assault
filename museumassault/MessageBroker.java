package museumassault;

/**
 *
 * @author André
 */
public interface MessageBroker
{
    /**
     *
     * @param originType
     * @param action
     * @return
     */
    public Message readMessage(int action);

    /**
     *
     * @param originType
     * @param message
     */
    public void writeMessage(Message message);
}
