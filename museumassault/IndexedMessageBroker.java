package museumassault;

import java.util.HashMap;
import java.util.LinkedList;

/**
 *
 * @author André
 * @TODO: Make a clean function that clears empty linked lists
 */
public class IndexedMessageBroker implements MessageBroker
{
    HashMap messages = new HashMap();

    /**
     *
     */
    public synchronized Message readMessage(int originType)
    {
        LinkedList messagesList = (LinkedList) this.messages.get(originType);

        if (messagesList == null) {
            return null;
        }

        return (Message) messagesList.pop();
    }

    /**
     *
     */
    public synchronized Message readMessage(int originType, int action)
    {
        LinkedList messagesList = (LinkedList) this.messages.get(originType);

        if (messagesList != null) {

            int length = messagesList.size();

            for (int x = 0; x < length; x++) {
                Message message = (Message) messagesList.get(x);
                if (message.getAction() == action) {
                    messagesList.remove(x);
                    return message;
                }
            }
        }

        return null;
    }

    /**
     *
     */
    public synchronized void writeMessage(int originType, Message message)
    {
        LinkedList messagesList = (LinkedList) this.messages.get(originType);

        if (messagesList == null) {
            messagesList = new LinkedList();
            this.messages.put(originType, messagesList);
        }

        messagesList.addLast(message);
    }
}
