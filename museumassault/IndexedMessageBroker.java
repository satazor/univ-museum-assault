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
    public synchronized Message readMessage(int action)
    {
        LinkedList messagesList = (LinkedList) this.messages.get(action);

        if (messagesList != null) {
            if (messagesList.size() > 0) {
                return (Message) messagesList.pop();
            }
        }

        return null;
    }

    /**
     *
     */
    public synchronized void writeMessage(Message message)
    {
        LinkedList messagesList = (LinkedList) this.messages.get(message.getAction());

        if (messagesList == null) {
            messagesList = new LinkedList();
            this.messages.put(message.getAction(), messagesList);
        }

        messagesList.addLast(message);
    }
}
