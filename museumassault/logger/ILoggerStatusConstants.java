package museumassault.logger;

/**
 * ILoggerStatusConstants interface.
 * This interface has all the chief/thief statuses.
 * 
 * @author Andre Cruz <andremiguelcruz@ua.pt>
 */
public interface ILoggerStatusConstants
{
    public static enum CHIEF_STATUS
    {
        PLANNING_THE_HEIST,
        DECIDING_WHAT_TO_DO,
        ASSEMBLING_A_GROUP,
        WAITING_FOR_ARRIVAL,
        PRESENTING_THE_REPORT
    };

    public static enum THIEF_STATUS
    {
        OUTSIDE,
        CRAWLING_INWARDS,
        AT_ROOM_ENTRANCE,
        AT_A_ROOM,
        AT_ROOM_EXIT,
        CRAWLING_OUTWARDS
    };
}
