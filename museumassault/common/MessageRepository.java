package museumassault.common;

import java.util.HashMap;
import java.util.LinkedList;

/**
 * MessageRepository.
 *
 * This class is a repository of messages, indexed by type.
 * It decouples a recipient from the sender with an indirect communication (with messages)
 * All the messages are indexed by type and stored in a first in first out policy.
 *
 * @see Message
 * @author Andre Cruz <andremiguelcruz@ua.pt>
 */
public class MessageRepository
{
    HashMap<Integer, LinkedList<Message>> messages = new HashMap<>();

    /**
     * Reads a message from the broker.
     * If multiple messages with the same type exists, the first is returned.
     *
     * @param type the type of the message that is expected to be read
     *
     * @return The message or null if none found
     */
    public synchronized Message readMessage(int type)
    {
        LinkedList<Message> messagesList = this.messages.get(type);

        if (messagesList != null) {
            if (messagesList.size() > 0) {
                return (Message) messagesList.pop();
            }
        }

        return null;
    }

    /**
     * Reads a message from the broker.
     * Search for a specific sender within the list of messages of a given type.
     *
     * @param type     the type of the message that is expected to be read
     * @param originId the expected of the sender
     *
     * @return The message or null if none found
     */
    public Message readMessage(int type, int originId)
    {
        LinkedList<Message> messagesList = this.messages.get(type);

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
        LinkedList<Message> messagesList = this.messages.get(message.getType());

        if (messagesList == null) {
            messagesList = new LinkedList<>();
            this.messages.put(message.getType(), messagesList);
        }

        messagesList.addLast(message);
    }
}
