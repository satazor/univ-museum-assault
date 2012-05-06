package museumassault.shared_site.server;

import java.util.HashMap;
import museumassault.common.Message;
import museumassault.common.ServerCom;
import museumassault.common.exception.ComException;
import museumassault.shared_site.IChiefMessageConstants;
import museumassault.shared_site.IThiefMessageConstants;

/**
 *
 * @author Andre Cruz <andremiguelcruz@ua.pt>
 */
public class RequestHandler extends Thread implements IChiefMessageConstants, IThiefMessageConstants
{
    protected ServerCom con;
    protected SharedSite site;
    protected final REQUEST_TYPE type;
    protected String shutdownPassword;

    public static enum REQUEST_TYPE {
        CHIEF,
        THIEF
    };

    /**
     * Constructor
     *
     * @param con
     * @param type
     * @param site
     * @param shutdownPassword
     */
    public RequestHandler(ServerCom con, REQUEST_TYPE type, SharedSite site, String shutdownPassword)
    {
        this.con = con;
        this.site = site;
        this.type = type;
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

            Integer roomId;
            Integer teamId;

            if (this.type == REQUEST_TYPE.THIEF) {
                // Thieves messages
                switch (fromClient.getType()) {
                    case AM_I_NEEDED_TYPE:
                        System.out.println("Handling message of type AM_I_NEEDED_TYPE..");
                        teamId = this.site.amINeeded(fromClient.getOriginId());
                        toClient = new Message(YOUR_NEEDED_TYPE, teamId);
                        break;
                    case PREPARE_EXCURSION_TYPE:
                        System.out.println("Handling message of type PREPARE_EXCURSION_TYPE..");
                        roomId = this.site.prepareExcursion(fromClient.getOriginId(), (Integer) fromClient.getExtra());
                        toClient = new Message(EXCURSION_PREPARED_TYPE, roomId);
                        break;
                    case HAND_A_CANVAS_TYPE:
                        System.out.println("Handling message of type HAND_A_CANVAS_TYPE..");
                        HashMap<String, Object> extra = (HashMap<String, Object>) fromClient.getExtra();
                        this.site.handACanvas(fromClient.getOriginId(), (Integer) extra.get("teamId"), (boolean) extra.get("rolledCanvas"));
                        toClient = new Message(GOT_CANVAS_TYPE);
                        break;
                    default:
                        System.out.println("Handling message of type UNKNOWN_TYPE..");
                        toClient = new Message(IThiefMessageConstants.UNKNOWN_TYPE);
                }
            } else {
                // Chief messages
                switch (fromClient.getType()) {
                    case APPRAISE_SIT_TYPE:
                        System.out.println("Handling message of type APPRAISE_SIT_TYPE..");
                        roomId = this.site.appraiseSit(fromClient.getOriginId());
                        toClient = new Message(APPRAISED_SIT_TYPE, roomId);
                        break;
                    case PREPARE_ASSAULT_PARTY_TYPE:
                        System.out.println("Handling message of type PREPARE_ASSAULT_PARTY_TYPE..");
                        teamId = this.site.prepareAssaultParty(fromClient.getOriginId(), (Integer) fromClient.getExtra());
                        toClient = new Message(ASSAULT_PARTY_PREPARED_TYPE, teamId);
                        break;
                    case SEND_ASSAULT_PARTY_TYPE:
                        System.out.println("Handling message of type SEND_ASSAULT_PARTY_TYPE..");
                        this.site.sendAssaultParty(fromClient.getOriginId(), (Integer) fromClient.getExtra());
                        toClient = new Message(ASSAULT_PARTY_SENT_TYPE);
                        break;
                    case TAKE_A_REST_TYPE:
                        System.out.println("Handling message of type TAKE_A_REST_TYPE..");
                        Integer thiefId = this.site.takeARest(fromClient.getOriginId());
                        toClient = new Message(TOOK_A_REST_TYPE, thiefId);
                        break;
                    case COLLECT_CANVAS_TYPE:
                        System.out.println("Handling message of type COLLECT_CANVAS_TYPE..");
                        this.site.collectCanvas(fromClient.getOriginId(), (Integer) fromClient.getExtra());
                        toClient = new Message(COLLECTED_CANVAS_TYPE);
                        break;
                    case SUM_UP_RESULTS:
                        System.out.println("Handling message of type SUM_UP_RESULTS..");
                        Integer nrCanvas = this.site.sumUpResults(fromClient.getOriginId());
                        toClient = new Message(SUMMED_UP_RESULTS, nrCanvas);
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
                        toClient = new Message(IChiefMessageConstants.UNKNOWN_TYPE);
                }
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
