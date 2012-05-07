package museumassault.common;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

/**
 * Configuration class.
 * This class contains all the project settings.
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
    protected String logFileName = "log.txt";
    protected String shutdownPassword = "12345";

    protected ArrayList<Integer> thiefIds;
    protected ArrayList<Integer> chiefIds;
    protected ArrayList<Integer> roomIds;
    protected ArrayList<Integer> corridorIds;
    protected ArrayList<Integer> teamIds;

    protected String sharedSiteThievesConnectionString = "192.168.8.171:20000";  // The server address must be equal to the line bellow
    protected String sharedSiteChiefsConnectionString = "192.168.8.171:20001";   // The server address must be equal to the line above
    protected String loggerConnectionString = "192.168.8.180:20000";

    protected HashMap<Integer, String> roomConnections;
    protected HashMap<Integer, String> corridorConnections;
    protected HashMap<Integer, Integer> roomCorridorAssociations;

    /**
     * Constructor.
     */
    public Configuration()
    {
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

        this.roomConnections = new HashMap<Integer, String>();
        this.roomConnections.put(1, "192.168.8.175:20000");
        this.roomConnections.put(2, "192.168.8.175:20001");
        this.roomConnections.put(3, "192.168.8.176:20000");
        this.roomConnections.put(4, "192.168.8.177:20000");
        this.roomConnections.put(5, "192.168.8.178:20000");

        this.corridorConnections = new HashMap<Integer, String>();
        this.corridorConnections.put(1, "192.168.8.175:20002");
        this.corridorConnections.put(2, "192.168.8.175:20003");
        this.corridorConnections.put(3, "192.168.8.176:20001");
        this.corridorConnections.put(4, "192.168.8.177:20001");
        this.corridorConnections.put(5, "192.168.8.178:20001");

        this.roomCorridorAssociations = new HashMap<Integer, Integer>();
        this.roomCorridorAssociations.put(1, 1);
        this.roomCorridorAssociations.put(2, 2);
        this.roomCorridorAssociations.put(3, 3);
        this.roomCorridorAssociations.put(4, 4);
        this.roomCorridorAssociations.put(5, 5);
    }

    /**
     * Get the maximum number of canvas in a room.
     *
     * @return
     */
    public int getMaxCanvasInRoom()
    {
        return this.maxCanvasInRoom;
    }

    /**
     * Get the maximum distance between the room and the exterior.
     *
     * @return
     */
    public int getMaxDistanceBetweenRoomAndOutside()
    {
        return this.maxDistanceBetweenRoomAndOutside;
    }

    /**
     * Get the maximum distance between two thieves in a corridor.
     *
     * @return
     */
    public int getMaxDistanceBetweenThieves()
    {
        return this.maxDistanceBetweenThieves;
    }

    /**
     * Get the maximum thief power.
     * @return
     */
    public int getMaxPowerPerThief()
    {
        return this.maxPowerPerThief;
    }

    /**
     * Get the number of chiefs.
     *
     * @return
     */
    public int getNrChiefs()
    {
        return this.nrChiefs;
    }

    /**
     * Get the chief ids.
     *
     * @return
     */
    public List<Integer> getChiefIds()
    {
        return this.chiefIds;
    }

    /**
     * Get the number of thieves.
     *
     * @return
     */
    public int getNrThieves()
    {
        return nrThieves;
    }

    /**
     * Get the thief ids.
     *
     * @return
     */
    public List<Integer> getThiefIds()
    {
        return this.thiefIds;
    }

    /**
     * Ge the number of rooms.
     *
     * @return
     */
    public int getNrRooms()
    {
        return this.nrRooms;
    }

    /**
     * Get the room ids.
     *
     * @return
     */
    public List<Integer> getRoomIds()
    {
        return this.roomIds;
    }

    /**
     * Get the corridor ids.
     *
     * @return
     */
    public List<Integer> getCorridorIds()
    {
        return this.corridorIds;
    }

    /**
     * Get the number of teams.
     *
     * @return
     */
    public int getNrTeams()
    {
        return this.nrTeams;
    }

    /**
     * Get the team ids.
     *
     * @return
     */
    public List<Integer> getTeamIds()
    {
        return this.teamIds;
    }

    /**
     * Get the number of thieves per team.
     *
     * @return
     */
    public int getNrThievesPerTeam()
    {
        return this.nrThievesPerTeam;
    }

    /**
     * Get the connection string of the shared site for chiefs.
     *
     * @return
     */
    public String getSharedChiefsSiteConnectionString()
    {
        return this.sharedSiteChiefsConnectionString;
    }

    /**
     * Get the connection string of the shared site for thieves.
     *
     * @return
     */
    public String getSharedThievesSiteConnectionString()
    {
        return this.sharedSiteThievesConnectionString;
    }

    /**
     * Get the port number of the shared site for chiefs.
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
     * Get the port number of the shared site for thieves.
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
     * Gets the connection string of a room.
     *
     * @param roomId the room id
     *
     * @return
     */
    @Override
    public String getRoomConnectionString(int roomId)
    {
        return this.roomConnections.get(roomId);
    }

    /**
     * Gets the port number of a room.
     *
     * @param roomId the room id
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
     * Gets the connection string of a corridor.
     *
     * @param corridorId the corridor id
     *
     * @return
     */
    @Override
    public String getCorridorConnectionString(int corridorId)
    {
        return this.corridorConnections.get(corridorId);
    }

    /**
     * Gets the port number of a corridor.
     *
     * @param corridorId the corridor id
     *
     * @return
     */
    public Integer getCorridorPort(int corridorId)
    {
        String connectionString = this.corridorConnections.get(corridorId);
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
     * Gets the corridor id that is associated with a room
     *
     * @param roomId the room id
     *
     * @return
     */
    @Override
    public Integer getRoomCorridorId(int roomId)
    {
        return (Integer) this.roomCorridorAssociations.get(roomId);
    }

    /**
     * Gets the logger connection string.
     *
     * @return
     */
    public String getLoggerConnectionString()
    {
        return this.loggerConnectionString;
    }

    /**
     * Gets the logger port number.
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
     * Gets the password that allows the shutdown of the various servers.
     *
     * @return
     */
    public String getShutdownPassword()
    {
        return this.shutdownPassword;
    }

    /**
     * Gets the log file name.
     *
     * @return
     */
    public String getLogFileName() {
        return this.logFileName;
    }
}
