package museumassault.logger.server;

import java.util.HashMap;
import museumassault.common.Message;
import museumassault.common.ServerCom;
import museumassault.common.exception.ComException;
import museumassault.logger.ILoggerMessageConstants;

/**
 *
 * @author Andre Cruz <andremiguelcruz@ua.pt>
 */
public class RequestHandler extends Thread implements ILoggerMessageConstants
{
    protected ServerCom con;
    protected Logger logger;
    protected String shutdownPassword;

    /**
     * Constructor
     *
     * @param con
     * @param logger
     * @param shutdownPassword
     */
    public RequestHandler(ServerCom con, Logger logger, String shutdownPassword)
    {
        this.con = con;
        this.logger = logger;
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
                case SET_CHIEF_STATUS_TYPE:
                    System.out.println("Handling message of type SET_CHIEF_STATUS_TYPE..");
                    this.logger.setChiefStatus(fromClient.getOriginId(), (Logger.CHIEF_STATUS) fromClient.getExtra());
                    toClient = new Message(STATUS_UPDATED_TYPE);
                    break;
                case SET_THIEF_STATUS_TYPE:
                    System.out.println("Handling message of type SET_THIEF_STATUS_TYPE..");
                    this.logger.setThiefStatus(fromClient.getOriginId(), (Logger.THIEF_STATUS) fromClient.getExtra());
                    toClient = new Message(STATUS_UPDATED_TYPE);
                    break;
                case SHUTDOWN_TYPE:
                    System.out.println("Handling message of type SHUTDOWN_TYPE..");
                    HashMap<String, Object> extra = (HashMap<String, Object>) fromClient.getExtra();

                    if (((String) extra.get("password")).equals(this.shutdownPassword)) {
                        toClient = new Message(SHUTDOWN_COMPLETED_TYPE);
                        this.logger.terminateLog((Integer) extra.get("total_canvas"));
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
