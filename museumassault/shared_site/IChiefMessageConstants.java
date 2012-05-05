package museumassault.shared_site;

/**
 * IThievesMessageConstants interface.
 *
 * This interfaces exposes all the message type constants related to the content
 * negotiation between the shared site and the chiefs.
 *
 * @author Andre Cruz <andremiguelcruz@ua.pt>
 */
public interface IChiefMessageConstants
{
    public static final int UNKNOWN_TYPE = 0;
    public static final int APPRAISE_SIT_TYPE = 50;
    public static final int APPRAISED_SIT_TYPE = 51;
    public static final int PREPARE_ASSAULT_PARTY_TYPE = 52;
    public static final int ASSAULT_PARTY_PREPARED_TYPE = 53;
    public static final int SEND_ASSAULT_PARTY_TYPE = 54;
    public static final int ASSAULT_PARTY_SENT_TYPE = 55;
    public static final int TAKE_A_REST_TYPE = 56;
    public static final int TOOK_A_REST_TYPE = 57;
    public static final int COLLECT_CANVAS_TYPE = 58;
    public static final int COLLECTED_CANVAS_TYPE = 59;
    public static final int SUM_UP_RESULTS = 60;
    public static final int SUMMED_UP_RESULTS = 61;
    public static final int SHUTDOWN_TYPE = 100;
    public static final int SHUTDOWN_COMPLETED_TYPE = 101;
    public static final int WRONG_SHUTDOWN_PASSWORD_TYPE = 102;
}
