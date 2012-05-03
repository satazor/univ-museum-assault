package museumassault.corridor.server;

import museumassault.common.Message;
import museumassault.common.ServerCom;
import museumassault.corridor.ICorridorMessageConstants;

/**
 *
 * @author Andre Cruz <andremiguelcruz@ua.pt>
 */
public class RequestHandler extends Thread implements ICorridorMessageConstants
{
    protected ServerCom con;
    protected Corridor corridor;

    /**
     * Constructor
     *
     * @param con
     * @param corridor
     */
    public RequestHandler(ServerCom con, Corridor corridor)
    {
        this.con = con;
        this.corridor = corridor;
    }

    /**
     * Handles the request.
     */
    @Override
    public void run()
    {
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
                toClient = new Message(CRAWLED_IN_TYPE, ret);
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
