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

    protected String sharedSiteThievesConnectionString = "192.168.8.171:22210";  // The server address must be equal to the line bellow
    protected String sharedSiteChiefsConnectionString = "192.168.8.171:22211";   // The server address must be equal to the line above
    protected String loggerConnectionString = "192.168.8.180:22210";

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
        this.roomConnections.put(1, "192.168.8.175:22210");
        this.roomConnections.put(2, "192.168.8.175:22211");
        this.roomConnections.put(3, "192.168.8.176:22210");
        this.roomConnections.put(4, "192.168.8.177:22210");
        this.roomConnections.put(5, "192.168.8.178:22210");

        this.corridorConnections = new HashMap<Integer, String>();
        this.corridorConnections.put(1, "192.168.8.175:22212");
        this.corridorConnections.put(2, "192.168.8.175:22213");
        this.corridorConnections.put(3, "192.168.8.176:22211");
        this.corridorConnections.put(4, "192.168.8.177:22211");
        this.corridorConnections.put(5, "192.168.8.178:22211");

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
     * @return the maxium number of canvas
     */
    public int getMaxCanvasInRoom()
    {
        return this.maxCanvasInRoom;
    }

    /**
     * Get the maximum distance between the room and the exterior.
     *
     * @return the max distance
     */
    public int getMaxDistanceBetweenRoomAndOutside()
    {
        return this.maxDistanceBetweenRoomAndOutside;
    }

    /**
     * Get the maximum distance between two thieves in a corridor.
     *
     * @return the max distance
     */
    public int getMaxDistanceBetweenThieves()
    {
        return this.maxDistanceBetweenThieves;
    }

    /**
     * Get the maximum thief power.
     *
     * @return the max power
     */
    public int getMaxPowerPerThief()
    {
        return this.maxPowerPerThief;
    }

    /**
     * Get the number of chiefs.
     *
     * @return the number of chiefs
     */
    public int getNrChiefs()
    {
        return this.nrChiefs;
    }

    /**
     * Get the chief ids.
     *
     * @return the chief ids
     */
    public List<Integer> getChiefIds()
    {
        return this.chiefIds;
    }

    /**
     * Get the number of thieves.
     *
     * @return the number of thieves
     */
    public int getNrThieves()
    {
        return nrThieves;
    }

    /**
     * Get the thief ids.
     *
     * @return the thieves ids
     */
    public List<Integer> getThiefIds()
    {
        return this.thiefIds;
    }

    /**
     * Ge the number of rooms.
     *
     * @return the number of rooms
     */
    public int getNrRooms()
    {
        return this.nrRooms;
    }

    /**
     * Get the room ids.
     *
     * @return the room ids
     */
    public List<Integer> getRoomIds()
    {
        return this.roomIds;
    }

    /**
     * Get the corridor ids.
     *
     * @return the corridor ids
     */
    public List<Integer> getCorridorIds()
    {
        return this.corridorIds;
    }

    /**
     * Get the number of teams.
     *
     * @return the number of teams
     */
    public int getNrTeams()
    {
        return this.nrTeams;
    }

    /**
     * Get the team ids.
     *
     * @return the team ids
     */
    public List<Integer> getTeamIds()
    {
        return this.teamIds;
    }

    /**
     * Get the number of thieves per team.
     *
     * @return the total thieves per team
     */
    public int getNrThievesPerTeam()
    {
        return this.nrThievesPerTeam;
    }

    /**
     * Get the host of the shared site for chiefs.
     *
     * @return the host of the shared site (chief)
     */
    public String getSharedChiefsSiteHost()
    {
        return this.extractHost(this.sharedSiteChiefsConnectionString);
    }

    /**
     * Get the host of the shared site for thieves.
     *
     * @return the host of the shared site (thief)
     */
    public String getSharedThievesSiteHost()
    {
        return this.extractHost(this.sharedSiteThievesConnectionString);
    }

    /**
     * Get the port number of the shared site for chiefs.
     *
     * @return the port of the shared site (chief)
     */
    public int getSharedChiefsSitePort()
    {
        return this.extractPort(this.sharedSiteChiefsConnectionString);
    }

    /**
     * Get the port number of the shared site for thieves.
     *
     * @return the port of the shared site (thief)
     */
    public int getSharedThievesSitePort()
    {
        return this.extractPort(this.sharedSiteThievesConnectionString);
    }

    /**
     * Gets the host of a given room.
     *
     * @param roomId the room id
     *
     * @return the room host
     */
    @Override
    public String getRoomHost(int roomId)
    {
       String connectionString = this.roomConnections.get(roomId);
        if (connectionString == null) {
            return null;
        }

        return this.extractHost(connectionString);
    }

    /**
     * Gets the port number of a room.
     *
     * @param roomId the room id
     *
     * @return the room port
     */
    @Override
    public Integer getRoomPort(int roomId)
    {
       String connectionString = this.roomConnections.get(roomId);
        if (connectionString == null) {
            return null;
        }

        return this.extractPort(connectionString);
    }

    /**
     *
     * Gets the host of a given corridor.
     *
     * @param corridorId the corridor id
     *
     * @return the corridor host
     */
    @Override
    public String getCorridorHost(int corridorId)
    {
        String connectionString = this.corridorConnections.get(corridorId);
        if (connectionString == null) {
            return null;
        }

        return this.extractHost(connectionString);
    }

    /**
     * Gets the port of a given corridor.
     *
     * @param corridorId the corridor id
     *
     * @return the corridor port
     */
    @Override
    public Integer getCorridorPort(int corridorId)
    {
        String connectionString = this.corridorConnections.get(corridorId);
        if (connectionString == null) {
            return null;
        }

        return this.extractPort(connectionString);
    }

    /**
     * Gets the corridor id that is associated with a room
     *
     * @param roomId the room id
     *
     * @return the corridor id of the room
     */
    @Override
    public Integer getRoomCorridorId(int roomId)
    {
        return (Integer) this.roomCorridorAssociations.get(roomId);
    }

    /**
     * Gets the logger host.
     *
     * @return the logger host
     */
    public String getLoggerHost()
    {
        return this.extractHost(this.loggerConnectionString);
    }

    /**
     * Gets the logger port number.
     *
     * @return the logger port
     */
    public int getLoggerPort()
    {
        return this.extractPort(this.loggerConnectionString);
    }


    /**
     * Gets the password that allows the shutdown of the various servers.
     *
     * @return the shutdown password
     */
    public String getShutdownPassword()
    {
        return this.shutdownPassword;
    }

    /**
     * Gets the log file name.
     *
     * @return the path to the log file
     */
    public String getLogFileName() {
        return this.logFileName;
    }

    /**
     * Extracts the host of a given connection string.
     *
     * @param connectionString the connection string
     *
     * @return the host
     */
    protected String extractHost(String connectionString)
    {
        String[] split = connectionString.split(":");
        if (split.length < 1) {
            throw new RuntimeException("Could not extract host from the connection string.");
        }

        return split[0];
    }

    /**
     * Extracts the port of a given connection string.
     *
     * @param connectionString the connection string
     *
     * @return the port
     */
    protected int extractPort(String connectionString)
    {
        String[] split = connectionString.split(":");
        if (split.length < 2) {
            throw new RuntimeException("Could not extract port from the connection string.");
        }

        return Integer.parseInt(split[1]);
    }
}
