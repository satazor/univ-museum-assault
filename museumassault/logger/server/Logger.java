package museumassault.logger.server;

import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;
import java.util.HashMap;
import java.util.List;
import museumassault.logger.*;

/**
 * Logger class.
 *
 * This class is responsible for producing the log file. There is a internal
 * buffer in which the contents are written. When the buffer reaches a certain
 * threshold or when terminateLog() is called, the contents are really written
 * to the file, avoiding heavy I/O.
 *
 * @author Andre Cruz <andremiguelcruz@ua.pt>
 */
public class Logger implements ILoggerStatusConstants
{
    protected HashMap<Integer, CHIEF_STATUS> chiefsStatus = new HashMap<>();
    protected HashMap<Integer, THIEF_STATUS> thievesStatus = new HashMap<>();
    protected HashMap<Integer, ThiefDetails> thievesDetails = new HashMap<>();
    protected HashMap<Integer, RoomDetails> roomsDetails = new HashMap<>();
    protected HashMap<Integer, CorridorDetails> corridorsDetails = new HashMap<>();
    protected HashMap<Integer, TeamDetails> teamsDetails = new HashMap<>();

    protected List<Integer> chiefIds;
    protected List<Integer> thiefIds;
    protected List<Integer> corridorIds;
    protected List<Integer> roomIds;
    protected List<Integer> teamIds;
    protected FileWriter fileWriter;

    protected BufferedWriter writeBuff;
    protected String fileName;

    /**
     * Class constructor
     *
     * @param chiefIds the chiefs ids
     * @param thiefIds the thieves ids
     * @param teamIds the teams ids
     * @param roomIds the rooms ids
     * @param corridorIds the corridor ids
     */
    public Logger(String fileName, List<Integer> chiefIds, List<Integer> thiefIds,
            List<Integer> teamIds, List<Integer> roomIds, List<Integer> corridorIds, int teamsMaxCapacity)
    {
        this.fileName = fileName;
        this.thiefIds = thiefIds;
        this.chiefIds = chiefIds;
        this.teamIds = teamIds;
        this.roomIds = roomIds;
        this.corridorIds = corridorIds;

        int length = chiefIds.size();
        for (int x = 0; x < length; x++) {
            this.chiefsStatus.put(chiefIds.get(x), CHIEF_STATUS.PLANNING_THE_HEIST);
        }

        length = thiefIds.size();
        for (int x = 0; x < length; x++) {
            this.thievesStatus.put(thiefIds.get(x), THIEF_STATUS.OUTSIDE);
            this.thievesDetails.put(thiefIds.get(x), new ThiefDetails(thiefIds.get(x), null));
        }

        length = teamIds.size();
        for (int x = 0; x < length; x++) {
            this.teamsDetails.put(teamIds.get(x), new TeamDetails(teamIds.get(x), teamsMaxCapacity));
        }

        length = roomIds.size();
        for (int x = 0; x < length; x++) {
            this.roomsDetails.put(roomIds.get(x), new RoomDetails(roomIds.get(x), 5, null));
        }

        length = corridorIds.size();
        for (int x = 0; x < length; x++) {
            this.corridorsDetails.put(roomIds.get(x), new CorridorDetails(corridorIds.get(x)));
        }

        try {
            this.fileWriter = new FileWriter(fileName);
            this.writeBuff = new BufferedWriter(fileWriter, 51200);   // Write to log each 50kb
        } catch (IOException e) {
            System.err.println("Unable to write to log file " + fileName);
        }

        this.writeHeader();
        this.writeToLog();
    }

    /**
     * Logs a change of a chief status.
     *
     * @param chiefId the id of the chief
     * @param status the status to be logged
     */
    public synchronized void setChiefStatus(int chiefId, CHIEF_STATUS status)
    {
        if (!this.chiefsStatus.containsKey(chiefId)) {
            throw new IllegalArgumentException("Unknown chief id: " + chiefId);
        }

        this.chiefsStatus.put(chiefId, status);

        this.writeToLog();
    }

    /**
     * Logs a change of a thief status.
     *
     * @param details the thief details
     * @param status the status to be logged
     */
    public synchronized void setThiefStatus(int thiefId, THIEF_STATUS status)
    {
        if (!this.thievesStatus.containsKey(thiefId)) {
            throw new IllegalArgumentException("Unknown thief id: " + thiefId);
        }

        this.thievesStatus.put(thiefId, status);

        this.writeToLog();
    }

    /**
     *
     * @param details
     */
    public synchronized void setThiefDetails(ThiefDetails details)
    {
        if (!this.thievesStatus.containsKey(details.getThiefId())) {
            throw new IllegalArgumentException("Unknown thief id: " + details.getThiefId());
        }

        this.thievesDetails.put(details.getThiefId(), details);
    }

    /**
     *
     * @param details
     */
    public synchronized void setRoomDetails(RoomDetails details)
    {
        if (!this.roomsDetails.containsKey(details.getRoomId())) {
            throw new IllegalArgumentException("Unknown thief id: " + details.getRoomId());
        }

        this.roomsDetails.put(details.getRoomId(), details);
    }

    /**
     *
     * @param details
     */
    public synchronized void setCorridorDetails(CorridorDetails details)
    {
        if (!this.corridorsDetails.containsKey(details.getCorridorId())) {
            throw new IllegalArgumentException("Unknown thief id: " + details.getCorridorId());
        }

        this.corridorsDetails.put(details.getCorridorId(), details);
    }

    /**
     *
     * @param details
     */
    public synchronized void setTeamDetails(TeamDetails details)
    {
        if (!this.teamsDetails.containsKey(details.getTeamId())) {
            throw new IllegalArgumentException("Unknown thief id: " + details.getTeamId());
        }

        this.teamsDetails.put(details.getTeamId(), details);
    }

    /**
     * Terminates the log file, flushing all the pending logs and writing the
     * final result.
     *
     * @param totalCanvas The total number of robed canvas
     */
    public synchronized void terminateLog(int totalCanvas)
    {
        if (this.writeBuff != null) {
            try {
                this.writeBuff.newLine();
                this.writeBuff.write("My friends we are rich because we gathered a total of " + totalCanvas + " canvasses!");
                this.writeBuff.close();
                this.writeBuff = null;
            } catch (IOException e) {
                System.err.println("Unable to write to log file " + this.fileName);
            }
        }
    }

    /**
     * Write the log header to the buffer.
     */
    protected final synchronized void writeHeader()
    {
        if (this.writeBuff != null) {
            try {
                this.writeBuff.write("Assault to the museum");
                this.writeBuff.newLine();
                this.writeBuff.newLine();

                int length = this.chiefIds.size();
                for (int x = 0; x < length; x++) {
                    this.writeBuff.write(String.format("%-12s", "CHIEF_" + this.chiefIds.get(x)));
                }
                length = this.thiefIds.size();
                for (int x = 0; x < length; x++) {
                    this.writeBuff.write(String.format("%-12s", "THIEF_" + this.thiefIds.get(x)));
                }

                this.writeBuff.newLine();

                length = this.chiefIds.size();
                for (int x = 0; x < length; x++) {
                    this.writeBuff.write(String.format("%-12s", "Stat"));
                }
                length = this.thiefIds.size();
                for (int x = 0; x < length; x++) {
                    this.writeBuff.write(String.format("%-6s", "Stat"));
                    this.writeBuff.write(String.format("%-6s", "Str"));
                }

                this.writeBuff.newLine();

                length = this.teamIds.size();
                for (int x = 0; x < length; x++) {
                    int nrColumns = 6 + this.teamsDetails.get(this.teamIds.get(x)).getCapacity() * 6;
                    this.writeBuff.write(String.format("%-" + nrColumns + "s", "PARTY_" + this.teamIds.get(x)));
                }

                length = this.roomIds.size();
                for (int x = 0; x < length; x++) {
                    this.writeBuff.write(String.format("%-14s", "ROOM_" + this.roomIds.get(x)));
                }

                this.writeBuff.newLine();

                length = this.teamIds.size();
                for (int x = 0; x < length; x++) {
                    this.writeBuff.write(String.format("%-4s", "RId"));
                    int nrMembers = this.teamsDetails.get(this.teamIds.get(x)).getCapacity();
                    for (int y = 0; y < nrMembers; y++) {
                        this.writeBuff.write(String.format("%-6s", "Mem_" + y));
                    }
                    this.writeBuff.write("  ");
                }

                length = this.roomIds.size();
                for (int x = 0; x < length; x++) {
                    this.writeBuff.write(String.format("%-4s", "RId"));
                    this.writeBuff.write(String.format("%-5s", "Dist"));
                    this.writeBuff.write(String.format("%-5s", "NC"));
                }

                this.writeBuff.newLine();
            } catch (IOException e) {
                System.err.println("Unable to write to log file " + this.fileName);
            }
        }
    }

    /**
     * Writes the current state to the buffer.
     */
    protected final synchronized void writeToLog()
    {
        if (this.writeBuff != null) {
            try {
                int length = this.chiefIds.size();
                for (int x = 0; x < length; x++) {
                    this.writeBuff.write(String.format("%-12s", this.statusToStr(this.chiefsStatus.get(this.chiefIds.get(x)))));
                }

                length = this.thiefIds.size();
                for (int x = 0; x < length; x++) {
                    this.writeBuff.write(String.format("%-6s", this.statusToStr(this.thievesStatus.get(this.thiefIds.get(x)))));
                    Integer power = this.thievesDetails.get(this.thiefIds.get(x)).getPower();
                    this.writeBuff.write(String.format("%-6s", power != null ? power : "-"));
                }

                this.writeBuff.newLine();

                RoomDetails roomDetails;
                length = this.teamIds.size();

                for (int x = 0; x < length; x++) {
                    TeamDetails teamDetails = this.teamsDetails.get(this.teamIds.get(x));

                    roomDetails = teamDetails.getAssignedRoomId() != null ? this.roomsDetails.get(teamDetails.getAssignedRoomId()) : null;
                    this.writeBuff.write(String.format("%-4s", roomDetails != null ? roomDetails.getRoomId() : "-"));

                    List<Integer> thievesIds = teamDetails.getThiefIds();
                    int nrMembers = thievesIds.size();
                    int capacity = teamDetails.getCapacity();
                    for (int y = 0; y < capacity; y++) {
                        if (y >= nrMembers) {
                            this.writeBuff.write(String.format("%-3s", "-"));
                            this.writeBuff.write(String.format("%-3s", "-"));
                        } else {
                            this.writeBuff.write(String.format("%-3s", thievesIds.get(y)));
                            THIEF_STATUS status = this.thievesStatus.get(thievesIds.get(y));
                            if ((status != THIEF_STATUS.CRAWLING_INWARDS && status != THIEF_STATUS.CRAWLING_OUTWARDS)) {
                                this.writeBuff.write(String.format("%-3s", "-"));
                            } else if (roomDetails != null) {
                                CorridorDetails corridorDetails = this.corridorsDetails.get(roomDetails.getCorridorId());
                                Integer position = corridorDetails.getThiefPosition(thievesIds.get(y));
                                this.writeBuff.write(String.format("%-3s", position != null? position : "-"));
                            }
                        }
                    }
                    this.writeBuff.write("  ");
                }

                length = this.roomIds.size();
                for (int x = 0; x < length; x++) {
                    roomDetails = this.roomsDetails.get(this.roomIds.get(x));
                    Integer totalPositions = this.corridorsDetails.get(roomDetails.getCorridorId()).getTotalPositions();
                    this.writeBuff.write(String.format("%-4s", roomDetails.getRoomId()));
                    this.writeBuff.write(String.format("%-5s", totalPositions != null ? totalPositions / 2 : "-"));
                    this.writeBuff.write(String.format("%-5s", roomDetails.getNrCanvas() == null ? "-" : roomDetails.getNrCanvas()));
                }

                this.writeBuff.newLine();
            } catch (IOException e) {
                System.err.println("Unable to write to log file " + this.fileName);
            }
        }
    }

    /**
     * Converts the status from the chief enum type to string.
     *
     * @param status the thief's status
     *
     * @return String the converted status
     */
    protected String statusToStr(CHIEF_STATUS status)
    {
        switch (status) {
            case PLANNING_THE_HEIST:
                return "PLTH";
            case ASSEMBLING_A_GROUP:
                return "ASAG";
            case DECIDING_WHAT_TO_DO:
                return "DWTD";
            case WAITING_FOR_ARRIVAL:
                return "WTFA";
            case PRESENTING_THE_REPORT:
                return "PRTP";
            default:
                return "";
        }
    }

    /**
     * Converts the status from the thief enum type to string.
     *
     * @param status the thief's status
     *
     * @return String the converted status
     */
    protected String statusToStr(THIEF_STATUS status)
    {

        switch (status) {
            case OUTSIDE:
                return "OUTS";
            case CRAWLING_OUTWARDS:
                return "COUT";
            case AT_ROOM_ENTRANCE:
                return "RENT";
            case AT_A_ROOM:
                return "ATAR";
            case AT_ROOM_EXIT:
                return "REXIT";
            case CRAWLING_INWARDS:
                return "CRIN";
            default:
                return "";
        }
    }
}
