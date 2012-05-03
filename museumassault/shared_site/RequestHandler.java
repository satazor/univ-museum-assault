package museumassault.shared_site;

import java.util.HashMap;
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

        Integer roomId;
        Integer teamId;

        switch (fromClient.getType()) {
            // Thieves types
            case IThiefMessageConstants.AM_I_NEEDED_TYPE:
                System.out.println("Handling message of type AM_I_NEEDED_TYPE..");
                teamId = this.site.amINeeded(fromClient.getOriginId());
                toClient = new Message(IThiefMessageConstants.YOUR_NEEDED_TYPE, teamId);
                break;
            case IThiefMessageConstants.PREPARE_EXCURSION_TYPE:
                System.out.println("Handling message of type PREPARE_EXCURSION_TYPE..");
                roomId = this.site.prepareExcursion(fromClient.getOriginId(), (Integer) fromClient.getExtra());
                toClient = new Message(IThiefMessageConstants.EXCURSION_PREPARED_TYPE, roomId);
                break;
            case IThiefMessageConstants.HAND_A_CANVAS_TYPE:
                System.out.println("Handling message of type HAND_A_CANVAS_TYPE..");
                HashMap<String, Object> extra = (HashMap<String, Object>) fromClient.getExtra();
                this.site.handACanvas(fromClient.getOriginId(), (Integer) extra.get("teamId"), (boolean) extra.get("rolledCanvas"));
                toClient = new Message(IThiefMessageConstants.GOT_CANVAS_TYPE);
                break;
            // Chiefs types
            case IChiefMessageConstants.APPRAISE_SIT_TYPE:
                System.out.println("Handling message of type APPRAISE_SIT_TYPE..");
                roomId = this.site.appraiseSit(fromClient.getOriginId());
                toClient = new Message(IChiefMessageConstants.APPRAISED_SIT_TYPE, roomId);
                break;
            case IChiefMessageConstants.PREPARE_ASSAULT_PARTY_TYPE:
                System.out.println("Handling message of type PREPARE_ASSAULT_PARTY_TYPE..");
                teamId = this.site.prepareAssaultParty(fromClient.getOriginId(), (Integer) fromClient.getExtra());
                toClient = new Message(IChiefMessageConstants.ASSAULT_PARTY_PREPARED_TYPE, teamId);
                break;
            case IChiefMessageConstants.SEND_ASSAULT_PARTY_TYPE:
                System.out.println("Handling message of type SEND_ASSAULT_PARTY_TYPE..");
                this.site.sendAssaultParty(fromClient.getOriginId(), (Integer) fromClient.getExtra());
                toClient = new Message(IChiefMessageConstants.ASSAULT_PARTY_SENT_TYPE);
                break;
            case IChiefMessageConstants.TAKE_A_REST_TYPE:
                System.out.println("Handling message of type TAKE_A_REST_TYPE..");
                Integer thiefId = this.site.takeARest(fromClient.getOriginId());
                toClient = new Message(IChiefMessageConstants.TOOK_A_REST_TYPE, thiefId);
                break;
            case IChiefMessageConstants.COLLECT_CANVAS_TYPE:
                System.out.println("Handling message of type COLLECT_CANVAS_TYPE..");
                this.site.collectCanvas(fromClient.getOriginId(), (Integer) fromClient.getExtra());
                toClient = new Message(IChiefMessageConstants.COLLECTED_CANVAS_TYPE);
                break;
            case IChiefMessageConstants.SUM_UP_RESULTS:
                System.out.println("Handling message of type SUM_UP_RESULTS..");
                Integer nrCanvas = this.site.sumUpResults(fromClient.getOriginId());
                toClient = new Message(IChiefMessageConstants.SUMMED_UP_RESULTS, nrCanvas);
                break;
            default:
                System.out.println("Handling message of type UNKNOWN_TYPE..");
                toClient = new Message(IThiefMessageConstants.UNKNOWN_TYPE);
        }

        // Send the message and close the connection
        this.con.writeMessage(toClient);
        this.con.close();

        System.out.println("Response sent!");
    }
}
