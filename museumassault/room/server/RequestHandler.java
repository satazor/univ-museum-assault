package museumassault.room.server;

import museumassault.common.Message;
import museumassault.common.ServerCom;
import museumassault.common.exception.ComException;
import museumassault.room.IRoomMessageConstants;

/**
 * Room server request handler class.
 * Every request is handled by this class.
 * It interprets a message and responds with an appropriate message.
 *
 * @author Hugo Oliveira <hugo.oliveira@ua.pt>
 * @author Andre Cruz <andremiguelcruz@ua.pt>
 */
public class RequestHandler extends Thread implements IRoomMessageConstants
{
    protected ServerCom con;
    protected Room room;
    protected String shutdownPassword;

    /**
     * Constructor.
     *
     * @param con              The server con
     * @param room             The room monitor
     * @param shutdownPassword The shutdown password
     */
    public RequestHandler(ServerCom con, Room room, String shutdownPassword)
    {
        this.con = con;
        this.room = room;
        this.shutdownPassword = shutdownPassword;
    }

    /**
     * Handles the request.
     */
    @Override
    public void run()
    {
        try {
            Message fromClient = this.con.readMessage();
            Message toClient;

            switch (fromClient.getType()) {
                case ROLL_A_CANVAS_TYPE:
                    System.out.println("Handling message of type ROLL_A_CANVAS_TYPE..");
                    Boolean rolledCanvas = this.room.rollACanvas(fromClient.getOriginId());
                    toClient = new Message(CANVAS_ROLLED_TYPE, rolledCanvas);
                    System.out.println("Number of canvas in room: " + room.getNrCanvas());
                    break;
                case SHUTDOWN_TYPE:
                    System.out.println("Handling message of type SHUTDOWN_TYPE..");

                    if (((String) fromClient.getExtra()).equals(this.shutdownPassword)) {
                        toClient = new Message(SHUTDOWN_COMPLETED_TYPE);
                    } else {
                        toClient = new Message(WRONG_SHUTDOWN_PASSWORD_TYPE);
                    }
                    break;
                default:
                    System.out.println("Handling message of type UNKNOWN_TYPE..");
                    toClient = new Message(UNKNOWN_TYPE);
            }

            // Send the message and close the connection
            this.con.writeMessage(toClient);
            this.con.close();

            System.out.println("Response sent!");

            if (fromClient.getType() == SHUTDOWN_TYPE && toClient.getType() != WRONG_SHUTDOWN_PASSWORD_TYPE) {
                System.out.println("Shutting down..");
                this.con.end();
            }
        } catch (ComException ex) {
            System.err.println(ex.getMessage());
        }
    }
}
