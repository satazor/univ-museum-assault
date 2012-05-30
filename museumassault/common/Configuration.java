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
        this.roomConnections.put(1, "localhost:20000");
        this.roomConnections.put(2, "localhost:20001");
        this.roomConnections.put(3, "localhost:20002");
        this.roomConnections.put(4, "localhost:20003");
        this.roomConnections.put(5, "localhost:20004");

        this.corridorConnections = new HashMap<Integer, String>();
        this.corridorConnections.put(1, "localhost:21000");
        this.corridorConnections.put(2, "localhost:21001");
        this.corridorConnections.put(3, "localhost:21002");
        this.corridorConnections.put(4, "localhost:21003");
        this.corridorConnections.put(5, "localhost:21004");

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
     * Get the host of the shared site for chiefs.
     *
     * @return
     */
    public String getSharedChiefsSiteHost()
    {
        return this.extractHost(this.sharedSiteChiefsConnectionString);
    }

    /**
     * Get the host of the shared site for thieves.
     *
     * @return
     */
    public String getSharedThievesSiteHost()
    {
        return this.extractHost(this.sharedSiteThievesConnectionString);
    }

    /**
     * Get the port number of the shared site for chiefs.
     *
     * @return
     */
    public int getSharedChiefsSitePort()
    {
        return this.extractPort(this.sharedSiteChiefsConnectionString);
    }

    /**
     * Get the port number of the shared site for thieves.
     *
     * @return
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
     * @return
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
     * @return
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
     * @return
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
     * @return
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
     * @return
     */
    @Override
    public Integer getRoomCorridorId(int roomId)
    {
        return (Integer) this.roomCorridorAssociations.get(roomId);
    }

    /**
     * Gets the logger host.
     *
     * @return
     */
    public String getLoggerHost()
    {
        return this.extractHost(this.loggerConnectionString);
    }

    /**
     * Gets the logger port number.
     *
     * @return
     */
    public int getLoggerPort()
    {
        return this.extractPort(this.loggerConnectionString);
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
