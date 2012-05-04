package museumassault.room.server;

import museumassault.common.Message;
import museumassault.common.ServerCom;
import museumassault.room.IRoomMessageConstants;

/**
 *
 * @author Hugo Oliveira <hugo.oliveira@ua.pt>
 */
public class RequestHandler extends Thread implements IRoomMessageConstants
{
    protected ServerCom con;
    protected Room room;

    /**
     * Constructor
     *
     * @param con
 param room
     */
    public RequestHandler(ServerCom con, Room room)
    {
        this.con = con;
        this.room = room;
    }

    /**
     * Handles the request.
     */
    @Override
    public void run()
    {
        Message fromClient = this.con.readMessage();
        Message toClient;

        switch (fromClient.getType()) {
            case ROLL_A_CANVAS_TYPE:
                System.out.println("Handling message of type ROLL_A_CANVAS_TYPE..");
                Boolean rolledCanvas = this.room.rollACanvas(fromClient.getOriginId());
                toClient = new Message(CANVAS_ROLLED_TYPE, rolledCanvas);
                break;
            default:
                System.out.println("Handling message of type UNKNOWN_TYPE..");
                toClient = new Message(UNKNOWN_TYPE);
        }

        // Send the message and close the connection
        this.con.writeMessage(toClient);
        this.con.close();

        System.out.println("Response sent!");
    }
}
