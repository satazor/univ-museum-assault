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
     * Method that returns and removes from the list of messages the message to be read
     * @param action - the action to be performed
     * @param originId - the id of the sender of the message
     * @return Message - Returns the message to be read
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
     * Method that returns the message at the head of the message list
     * @param action - the action to be performed
     * @return Message - Returns the message to be read
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
     * Method that puts a new Message in the message list
     * @param message - the message to be put in the message list
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
