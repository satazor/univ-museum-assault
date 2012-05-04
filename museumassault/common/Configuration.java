package museumassault.common;

import java.util.ArrayList;
import java.util.HashMap;
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

    protected static String sharedSiteThievesConnectionString = "localhost:11000";  // The server address must be equal to the line bellow
    protected static String sharedSiteChiefsConnectionString = "localhost:11001";   // The server address must be equal to the line above

    protected static HashMap<Integer, String> roomConnections;
    protected static HashMap<Integer, String> corridorConnections;
    /**
     * Constructor.
     */
    public Configuration()
    {
        roomConnections = new HashMap<>();
        roomConnections.put(1, "ip:port");
        roomConnections.put(2, "ip:port");
        roomConnections.put(3, "ip:port");
        roomConnections.put(4, "ip:port");
        roomConnections.put(5, "ip:port");
        
        corridorConnections = new HashMap<>();
        corridorConnections.put(1, "ip:port");
        corridorConnections.put(2, "ip:port");
        corridorConnections.put(3, "ip:port");
        corridorConnections.put(4, "ip:port");
        corridorConnections.put(5, "ip:port");    
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
    public static String getSharedChiefsSiteConnectionString()
    {
        return sharedSiteChiefsConnectionString;
    }

    /**
     *
     * @return
     */
    public static String getSharedThievesSiteConnectionString()
    {
        return sharedSiteThievesConnectionString;
    }

    /**
     *
     * @return
     */
    public static int getSharedThievesSitePort()
    {
        String[] split = sharedSiteThievesConnectionString.split(":");
        if (split.length != 2) {
            throw new RuntimeException("Could not extract port from the connection string.");
        }

        return Integer.parseInt(split[1]);
    }

    /**
     *
     * @return
     */
    public static int getSharedChiefsSitePort()
    {
        String[] split = sharedSiteChiefsConnectionString.split(":");
        if (split.length != 2) {
            throw new RuntimeException("Could not extract port from the connection string.");
        }

        return Integer.parseInt(split[1]);
    }
    
    /**
     * 
     * @param roomId
     * 
     * @return 
     */
    public static String getRoomConnectionString(int roomId)
    {
        return roomConnections.get(roomId);
    }
    
    /**
     * 
     * @param roomId
     * 
     * @return 
     */
    public static int getRoomPort(int roomId)
    {
        String[] split = roomConnections.get(roomId).split(":");
        if (split.length != 2) {
            throw new RuntimeException("Could not extract port from the connection string.");
        }

        return Integer.parseInt(split[1]);
    }
    
    /**
     * 
     * @param roomId
     * 
     * @return 
     */
    public static String getCorridorConnectionString(int roomId)
    {
        return corridorConnections.get(roomId);
    }
    
    /**
     * 
     * @param roomId
     * 
     * @return 
     */
    public static int getCorridorPort(int roomId)
    {
        String[] split = corridorConnections.get(roomId).split(":");
        if (split.length != 2) {
            throw new RuntimeException("Could not extract port from the connection string.");
        }

        return Integer.parseInt(split[1]);
    }
}
