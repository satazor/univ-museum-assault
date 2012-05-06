package museumassault.common;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

/**
 *
 * @author Andre Cruz <andremiguelcruz@ua.pt>
 */
public class Configuration implements IThievesConfiguration
{
    protected int nrChiefs = 1;
    protected int nrThieves = 7;
    protected int nrTeams = 5;
    protected int nrThievesPerTeam = 3;
    protected int nrRooms = 5;
    protected int maxDistanceBetweenThieves = 1;
    protected int maxDistanceBetweenRoomAndOutside = 10;
    protected int maxPowerPerThief = 5;
    protected int maxCanvasInRoom = 20;
    protected String logFileName = "../log.txt";
    protected String shutdownPassword = "12345";

    protected ArrayList<Integer> thiefIds;
    protected ArrayList<Integer> chiefIds;
    protected ArrayList<Integer> roomIds;
    protected ArrayList<Integer> corridorIds;
    protected ArrayList<Integer> teamIds;

    protected String sharedSiteThievesConnectionString = "localhost:11000";  // The server address must be equal to the line bellow
    protected String sharedSiteChiefsConnectionString = "localhost:11001";   // The server address must be equal to the line above
    protected String loggerConnectionString = "localhost:15000";

    protected HashMap<Integer, String> roomConnections;
    protected HashMap<Integer, String> corridorConnections;
    protected HashMap<Integer, Integer> roomCorridorAssociations;

    /**
     * Constructor.
     */
    public Configuration()
    {
        this.roomConnections = new HashMap<>();

        this.roomIds = new ArrayList(this.nrRooms);
        for (int x = 0; x < this.nrRooms; x++) {
            this.roomIds.add(x + 1);
        }

        this.chiefIds = new ArrayList(this.nrChiefs);
        for (int x = 0; x < this.nrChiefs; x++) {
            this.chiefIds.add(x + 1);
        }

        this.thiefIds = new ArrayList(this.nrThieves);
        for (int x = 0; x < this.nrThieves; x++) {
            this.thiefIds.add(x + 1);
        }

        this.roomIds = new ArrayList(this.nrRooms);
        for (int x = 0; x < this.nrRooms; x++) {
            this.roomIds.add(x + 1);
        }

        this.corridorIds = new ArrayList(this.nrRooms);
        for (int x = 0; x < this.nrRooms; x++) {
            this.corridorIds.add(x + 1);
        }

        this.teamIds = new ArrayList(this.nrTeams);
        for (int x = 0; x < this.nrTeams; x++) {
            this.teamIds.add(x + 1);
        }

        this.roomConnections.put(1, "localhost:20000");
        this.roomConnections.put(2, "localhost:20001");
        this.roomConnections.put(3, "localhost:20002");
        this.roomConnections.put(4, "localhost:20003");
        this.roomConnections.put(5, "localhost:20004");

        this.corridorConnections = new HashMap<>();
        this.corridorConnections.put(1, "localhost:21000");
        this.corridorConnections.put(2, "localhost:21001");
        this.corridorConnections.put(3, "localhost:21002");
        this.corridorConnections.put(4, "localhost:21003");
        this.corridorConnections.put(5, "localhost:21004");

        this.roomCorridorAssociations = new HashMap<>();
        this.roomCorridorAssociations.put(1, 1);
        this.roomCorridorAssociations.put(2, 2);
        this.roomCorridorAssociations.put(3, 3);
        this.roomCorridorAssociations.put(4, 4);
        this.roomCorridorAssociations.put(5, 5);
    }

    /**
     *
     * @return
     */
    public int getMaxCanvasInRoom()
    {
        return this.maxCanvasInRoom;
    }

    /**
     *
     * @return
     */
    public int getMaxDistanceBetweenRoomAndOutside()
    {
        return this.maxDistanceBetweenRoomAndOutside;
    }

    /**
     *
     * @return
     */
    public int getMaxDistanceBetweenThieves()
    {
        return this.maxDistanceBetweenThieves;
    }

    /**
     *
     * @return
     */
    public int getMaxPowerPerThief()
    {
        return this.maxPowerPerThief;
    }

    /**
     *
     * @return
     */
    public int getNrChiefs()
    {
        return this.nrChiefs;
    }

    /**
     *
     * @return
     */
    public List<Integer> getChiefIds()
    {
        return this.chiefIds;
    }

    /**
     *
     * @return
     */
    public List<Integer> getThiefIds()
    {
        return this.thiefIds;
    }

    /**
     *
     * @return
     */
    public int getNrThieves()
    {
        return nrThieves;
    }

    /**
     *
     * @return
     */
    public int getNrRooms()
    {
        return this.nrRooms;
    }

    /**
     *
     * @return
     */
    public List<Integer> getRoomIds()
    {
        return this.roomIds;
    }

    /**
     *
     * @return
     */
    public List<Integer> getCorridorIds()
    {
        return this.corridorIds;
    }

    /**
     *
     * @return
     */
    public int getNrTeams()
    {
        return this.nrTeams;
    }

    /**
     *
     * @return
     */
    public List<Integer> getTeamIds()
    {
        return this.teamIds;
    }

    /**
     *
     * @return
     */
    public int getNrThievesPerTeam()
    {
        return this.nrThievesPerTeam;
    }

    /**
     *
     * @return
     */
    public String getSharedChiefsSiteConnectionString()
    {
        return this.sharedSiteChiefsConnectionString;
    }

    /**
     *
     * @return
     */
    public String getSharedThievesSiteConnectionString()
    {
        return this.sharedSiteThievesConnectionString;
    }

    /**
     *
     * @return
     */
    public int getSharedThievesSitePort()
    {
        String[] split = this.sharedSiteThievesConnectionString.split(":");
        if (split.length != 2) {
            throw new RuntimeException("Could not extract port from the connection string.");
        }

        return Integer.parseInt(split[1]);
    }

    /**
     *
     * @return
     */
    public int getSharedChiefsSitePort()
    {
        String[] split = this.sharedSiteChiefsConnectionString.split(":");
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
    @Override
    public String getRoomConnectionString(int roomId)
    {
        return this.roomConnections.get(roomId);
    }

    /**
     *
     * @param roomId
     *
     * @return
     */
    public Integer getRoomPort(int roomId)
    {
        String connectionString = this.roomConnections.get(roomId);
        if (connectionString == null) {
            return null;
        }

        String[] split = connectionString.split(":");
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
    @Override
    public String getCorridorConnectionString(int corridorId)
    {
        return this.corridorConnections.get(corridorId);
    }

    /**
     *
     * @param roomId
     *
     * @return
     */
    public Integer getCorridorPort(int roomId)
    {
        String connectionString = this.corridorConnections.get(roomId);
        if (connectionString == null) {
            return null;
        }

        String[] split = connectionString.split(":");
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
    @Override
    public Integer getRoomCorridorId(int roomId)
    {
        return (Integer) this.roomCorridorAssociations.get(roomId);
    }

    /**
     *
     * @return
     */
    public String getLoggerConnectionString()
    {
        return this.loggerConnectionString;
    }

    /**
     *
     * @return
     */
    public int getLoggerPort()
    {
        String[] split = this.loggerConnectionString.split(":");
        if (split.length != 2) {
            throw new RuntimeException("Could not extract port from the connection string.");
        }

        return Integer.parseInt(split[1]);
    }


    /**
     *
     * @return
     */
    public String getShutdownPassword()
    {
        return this.shutdownPassword;
    }

    /**
     *
     * @return
     */
    public String getLogFileName() {
        return this.logFileName;
    }
}
