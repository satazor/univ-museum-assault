package museumassault.shared_site;

/**
 * IThievesMessageConstants interface.
 *
 * This interfaces exposes all the message type constants related to the content
 * negotiation between the shared site and thieves.
 *
 * @author Hugo Oliveira <hugo.oliveira@ua.pt>
 */
public interface IThiefMessageConstants
{
    public static final int UNKNOWN_TYPE = 0;
    public static final int AM_I_NEEDED_TYPE = 1;
    public static final int YOUR_NEEDED_TYPE = 2;
    public static final int YOUR_NOT_NEEDED_TYPE = 3;
    public static final int PREPARE_EXCURSION_TYPE = 4;
    public static final int EXCURSION_PREPARED_TYPE = 5;
    public static final int HAND_A_CANVAS_TYPE = 6;
    public static final int GOT_CANVAS_TYPE = 7;
    
    
}
