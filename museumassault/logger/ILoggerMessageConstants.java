package museumassault.logger;


/**
 * ILoggerMessageConstants interface.
 *
 * This interfaces exposes all the message type constants related to the content negotiation.
 *
 * @author Andre Cruz <andremiguelcruz@ua.pt>
 */
public interface ILoggerMessageConstants
{
    public static final int UNKNOWN_TYPE = 0;
    public static final int SET_CHIEF_STATUS_TYPE = 1;
    public static final int SET_THIEF_STATUS_TYPE = 2;
    public static final int SET_THIEF_DETAILS_TYPE = 10;
    public static final int SET_TEAM_DETAILS_TYPE = 11;
    public static final int SET_ROOM_DETAILS_TYPE = 12;
    public static final int SET_CORRIDOR_DETAILS_TYPE = 13;
    public static final int STATUS_UPDATED_TYPE = 20;
    public static final int DETAILS_UPDATED_TYPE = 21;
    public static final int SHUTDOWN_TYPE = 100;
    public static final int SHUTDOWN_COMPLETED_TYPE = 101;
    public static final int WRONG_SHUTDOWN_PASSWORD_TYPE = 102;
}
