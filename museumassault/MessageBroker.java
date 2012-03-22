package museumassault;

/**
 *
 * @author André
 */
public interface MessageBroker {

    /**
     *
     * @param originType
     * @return
     */
    public Message readMessage(int originType);

    /**
     *
     * @param originType
     * @param action
     * @return
     */
    public Message readMessage(int originType, int action);

    /**
     * 
     * @param originType
     * @param message
     */
    public void writeMessage(int originType, Message message);
}
