package museumassault.shared_site;

import museumassault.common.Message;
import museumassault.common.ServerCom;

/**
 *
 * @author Andre Cruz <andremiguelcruz@ua.pt>
 */
public class RequestHandler extends Thread
{
    protected ServerCom con;
    protected SharedSite site;

    /**
     * Constructor
     *
     * @param con
     * @param site
     */
    public RequestHandler(ServerCom con, SharedSite site)
    {
        this.con = con;
        this.site = site;
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
            case IThiefMessageConstants.AM_I_NEEDED_TYPE:
                System.out.println("Handling message of type AM_I_NEEDED_TYPE..");
                Integer teamId = this.site.amINeeded(fromClient.getOriginId());
                toClient = new Message(IThiefMessageConstants.YOUR_NEEDED_TYPE, teamId);
                break;
            default:
                System.out.println("Handling message of type UNKNOWN_TYPE..");
                toClient = new Message(IThiefMessageConstants.UNKNOWN_TYPE);
        }

        System.out.println("Sending response...");
        
        // Send the message and close the connection
        this.con.writeMessage(toClient);
        this.con.close();

        System.out.println("Response sent!");
    }
}
