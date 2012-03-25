package museumassault.message_broker;

import java.util.HashMap;
import java.util.LinkedList;

/**
 * MessageBroker.
 *
 * A message broker is a repository of messages, indexed by action.
 * It decouples a recipient from the sender with an indirect communication (with messages)
 * All the messages are indexed by action and stored in a first in first out policy.
 *
 * @author Andre Cruz <andremiguelcruz@ua.pt>
 */
public class MessageBroker
{
    HashMap messages = new HashMap();

    /**
     * Reads a message from the broker.
     * If muliple messages with the same action exists, the first is returned.
     *
     * @param action the action of the message that is expected to be read
     *
     * @return The message or null if none found
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
     * Reads a message from the broker.
     * Searchs for a specific sender within the list of messages of a given action.
     *
     * @param action   the action of the message that is expected to be read
     * @param originId the expected of the sender
     *
     * @return The message or null if none found
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
     * Writes a message in the broker.
     *
     * @param message the message to be put in the broker
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
