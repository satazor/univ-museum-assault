package museumassault.room;

/**
 * IRoomMessageConstants interface.
 *
 * This interfaces exposes all the message type constants related to the content
 * negotiation between a room and a thief & chief.
 *
 * @author Hugo Oliveira <hugo.oliveira@ua.pt>
 * @author Andre Cruz <andremiguelcruz@ua.pt>
 */
public interface IRoomMessageConstants
{
    public static final int UNKNOWN_TYPE = 0;
    public static final int ROLL_A_CANVAS_TYPE = 1;
    public static final int CANVAS_ROLLED_TYPE = 2;
    public static final int SHUTDOWN_TYPE = 100;
    public static final int SHUTDOWN_COMPLETED_TYPE = 101;
    public static final int WRONG_SHUTDOWN_PASSWORD_TYPE = 102;
}
