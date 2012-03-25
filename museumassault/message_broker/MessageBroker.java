package museumassault.message_broker;

import java.util.HashMap;
import java.util.LinkedList;

/**
 *
 * @author Andre Cruz <andremiguelcruz@ua.pt>
 */
public class MessageBroker
{
    HashMap messages = new HashMap();

    /**
     *
     * @param action
     * @param id
     * @return
     */
    public Message readMessage(int action, int originId)
    {
        LinkedList messagesList = (LinkedList) this.messages.get(action);

        if (messagesList != null) {
            int length = messagesList.size();

            for (int x = 0; x < length; x++) {
                Message message = (Message) messagesList.get(x);
                if (message.getOriginId() == originId) {
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
