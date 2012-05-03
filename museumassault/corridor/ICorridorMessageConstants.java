package museumassault.corridor;

/**
 * IThievesMessageConstants interface.
 *
 * This interfaces exposes all the message type constants related to the content
 * negotiation between a corridor and a thief.
 *
 * @author Andre Cruz <andremiguelcruz@ua.pt>
 */
public interface ICorridorMessageConstants
{
    public static final int UNKNOWN_TYPE = 0;
    public static final int CRAWL_IN_TYPE = 1;
    public static final int CRAWLED_IN_TYPE = 2;
    public static final int CRAWL_OUT_TYPE = 3;
    public static final int CRAWLED_OUT_TYPE = 4;
}
