package museumassault.corridor.server;

import museumassault.common.Message;
import museumassault.common.ServerCom;
import museumassault.common.exception.ComException;
import museumassault.corridor.ICorridorMessageConstants;

/**
 *
 * @author Andre Cruz <andremiguelcruz@ua.pt>
 */
public class RequestHandler extends Thread implements ICorridorMessageConstants
{
    protected ServerCom con;
    protected Corridor corridor;
    protected String shutdownPassword;

    /**
     * Constructor
     *
     * @param con
     * @param corridor
     */
    public RequestHandler(ServerCom con, Corridor corridor, String shutdownPassword)
    {
        this.con = con;
        this.corridor = corridor;
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

            Boolean ret;

            switch (fromClient.getType()) {
                case CRAWL_IN_TYPE:
                    System.out.println("Handling message of type CRAWL_IN_TYPE..");
                    ret = this.corridor.crawlIn(fromClient.getOriginId(), (Integer) fromClient.getExtra());
                    toClient = new Message(CRAWLED_IN_TYPE, ret);
                    break;
                case CRAWL_OUT_TYPE:
                    System.out.println("Handling message of type CRAWL_OUT_TYPE..");
                    ret = this.corridor.crawlOut(fromClient.getOriginId(), (Integer) fromClient.getExtra());
                    toClient = new Message(CRAWLED_OUT_TYPE, ret);
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
        } catch (ComException e) {
            System.err.println(e.getMessage());
        }
    }
}
