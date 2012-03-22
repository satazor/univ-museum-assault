package museumassault;

import java.util.LinkedList;
import java.util.concurrent.locks.ReentrantLock;

/**
 *
 * @author André
 */
public class Exterior
{

    LinkedList messages = new LinkedList();
    ReentrantLock lock = new ReentrantLock();


    public Exterior()
    {

    }

    /**
     *
     */
    public synchronized Message readMessage(int id)
    {
        Message message = (Message) messages.getFirst();
        
        return message.getDestinationId() == id ? message : null;
    }

    /**
     *
     */
    public synchronized void writeMessage(Message message)
    {
        messages.push(message);
    }
}
