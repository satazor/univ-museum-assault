package museumassault.shared_site;

/**
 * IThievesMessageConstants interface.
 *
 * This interfaces exposes all the message type constants related to the content
 * negotiation between the shared site and thieves.
 *
 * @author Andre Cruz <andremiguelcruz@ua.pt>
 */
public interface IThiefMessageConstants
{
    public static final int UNKNOWN_TYPE = 0;
    public static final int AM_I_NEEDED_TYPE = 1;
    public static final int YOUR_NEEDED_TYPE = 2;
    public static final int YOUR_NOT_NEEDED_TYPE = 3;
}
