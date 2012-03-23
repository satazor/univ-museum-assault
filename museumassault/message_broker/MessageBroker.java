package museumassault.message_broker;

/**
 *
 * @author André
 */
public interface MessageBroker
{
    /**
     *
     * @param action
     * @return
     */
    public Message readMessage(int action);

    /**
     * @param action
     * @param id
     * @return
     */
    public Message readMessage(int action, int id);

    /**
     *
     * @param message
     */
    public void writeMessage(Message message);
}
