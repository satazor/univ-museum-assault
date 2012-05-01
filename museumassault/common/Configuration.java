package museumassault.common;

import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author Andre Cruz <andremiguelcruz@ua.pt>
 */
public class Configuration
{
    protected static int nrChiefs = 1;
    protected static int nrThieves = 7;
    protected static int nrTeams = 5;
    protected static int nrThievesPerTeam = 3;
    protected static int nrRooms = 5;
    protected static int maxDistanceBetweenThieves = 1;
    protected static int maxDistanceBetweenRoomAndOutside = 10;
    protected static int maxPowerPerThief = 5;
    protected static int maxCanvasInRoom = 20;

    protected static String sharedSiteConnectionString = "localhost:11000";


    /**
     * Constructor.
     */
    public Configuration()
    {
        throw new UnsupportedOperationException("This class cannot be instantiated.");
    }

    /**
     *
     * @return
     */
    public static int getMaxCanvasInRoom()
    {
        return maxCanvasInRoom;
    }

    /**
     *
     * @return
     */
    public static int getMaxDistanceBetweenRoomAndOutside()
    {
        return maxDistanceBetweenRoomAndOutside;
    }

    /**
     *
     * @return
     */
    public static int getMaxDistanceBetweenThieves()
    {
        return maxDistanceBetweenThieves;
    }

    /**
     *
     * @return
     */
    public static int getMaxPowerPerThief()
    {
        return maxPowerPerThief;
    }

    /**
     *
     * @return
     */
    public static int getNrChiefs()
    {
        return nrChiefs;
    }

    /**
     *
     * @return
     */
    public static int getNrThieves()
    {
        return nrThieves;
    }

    /**
     *
     * @return
     */
    public static int getNrRooms()
    {
        return nrRooms;
    }

    /**
     *
     * @return
     */
    public static List<Integer> getRoomIds()
    {
        ArrayList<Integer> ids = new ArrayList(nrRooms);

        for (int x = 0; x < nrRooms; x++) {
            ids.add(x + 1);
        }

        return ids;
    }

    /**
     *
     * @return
     */
    public static int getNrTeams()
    {
        return nrTeams;
    }

    /**
     *
     * @return
     */
    public static int getNrThievesPerTeam()
    {
        return nrThievesPerTeam;
    }

    /**
     *
     * @return
     */
    public static String getSharedSiteConnectionString()
    {
        return sharedSiteConnectionString;
    }

    /**
     *
     * @return
     */
    public static int getSharedSitePort()
    {
        String[] split = sharedSiteConnectionString.split(":");
        if (split.length != 2) {
            throw new RuntimeException("Could not extract port from the connection string.");
        }

        return Integer.parseInt(split[1]);
    }
}
