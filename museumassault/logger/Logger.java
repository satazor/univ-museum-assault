package museumassault.logger;

import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;
import java.util.HashMap;
import museumassault.chief.Chief;
import museumassault.common.Team;
import museumassault.room.Room;
import museumassault.thief.Thief;

/**
 * Logger class.
 *
 * This class is responsible for producing the log file.
 * There is a internal buffer in which the contents are written.
 * When the buffer reaches a certain threshold or when terminateLog() is called,
 * the contents are really written to the file, avoiding heavy I/O.
 *
 * @author Andre Cruz <andremiguelcruz@ua.pt>
 */
public class Logger
{
    public static enum CHIEF_STATUS {
        PLANNING_THE_HEIST,
        DECIDING_WHAT_TO_DO,
        ASSEMBLING_A_GROUP,
        WAITING_FOR_ARRIVAL,
        PRESENTING_THE_REPORT
    };

    public static enum THIEF_STATUS {
        OUTSIDE,
        CRAWLING_INWARDS,
        AT_ROOM_ENTRANCE,
        AT_A_ROOM,
        AT_ROOM_EXIT,
        CRAWLING_OUTWARDS
    };

    protected HashMap<Integer, CHIEF_STATUS> chiefsStatus = new HashMap<>();
    protected HashMap<Integer, THIEF_STATUS> thievesStatus = new HashMap<>();
    protected Chief[] chiefs;
    protected Thief[] thieves;
    protected Team[] teams;
    protected Room[] rooms;

    protected FileWriter fileWriter;
    protected BufferedWriter writeBuff;
    protected String fileName;
    protected boolean initialized = false;

    /**
     * Class constructor
     *
     * @param fileName the path of the file in which the log will be saved
     */
    public Logger(String fileName)
    {
        this.fileName = fileName;

        try {
            this.fileWriter = new FileWriter(fileName);
            this.writeBuff = new BufferedWriter(fileWriter, 51200);   // Write to log each 50kb
        } catch (IOException e) {
            System.err.println("Unable to write to log file " + fileName);
        }
    }

    /**
     * Initializes the logger with some necessary dependencies.
     *
     * @param chiefs  the chiefs instances
     * @param thieves the thieves instances
     * @param teams   the teams instances
     * @param rooms   the rooms instances
     */
    public void initialize(Chief[] chiefs, Thief[] thieves, Team[] teams, Room[] rooms)
    {
        this.thieves = thieves;
        this.chiefs = chiefs;
        this.teams = teams;
        this.rooms = rooms;
        this.initialized = true;

        int length = chiefs.length;
        for (int x = 0; x < length; x++) {
            this.chiefsStatus.put(chiefs[x].getChiefId(), CHIEF_STATUS.PLANNING_THE_HEIST);
        }

        length = thieves.length;
        for (int x = 0; x < length; x++) {
            this.thievesStatus.put(thieves[x].getThiefId(), THIEF_STATUS.OUTSIDE);
        }

        this.writeHeader();
        this.writeToLog();
    }

    /**
     * Logs a change of a chief status.
     *
     * @param chiefId the id of the chief
     * @param status  the status to be logged
     */
    public synchronized void setChiefStatus(int chiefId, CHIEF_STATUS status)
    {
        if (!this.initialized) {
            throw new IllegalStateException("Please initialize the logger first.");
        }

        this.chiefsStatus.put(chiefId, status);

        this.writeToLog();
    }

    /**
     * Logs a change of a thief status.
     *
     * @param thiefId the id of the chief
     * @param status  the status to be logged
     */
    public synchronized void setThiefStatus(int thiefId, THIEF_STATUS status)
    {
        if (!this.initialized) {
            throw new IllegalStateException("Please initialize the logger first.");
        }

        this.thievesStatus.put(thiefId, status);

        this.writeToLog();
    }

    /**
     * Terminates the log file, flushing all the pending logs and writing the final result.
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
    protected synchronized void writeHeader()
    {
        if (this.writeBuff != null) {
            try {
                this.writeBuff.write("Assault to the museum");
                this.writeBuff.newLine();
                this.writeBuff.newLine();

                int length = this.chiefs.length;
                for (int x = 0; x < length; x++) {
                    this.writeBuff.write(String.format("%-12s", "CHIEF_" + this.chiefs[x].getChiefId()));
                }
                length = this.thieves.length;
                for (int x = 0; x < length; x++) {
                    this.writeBuff.write(String.format("%-12s", "THIEF_" + this.thieves[x].getThiefId()));
                }

                this.writeBuff.newLine();

                length = this.chiefs.length;
                for (int x = 0; x < length; x++) {
                    this.writeBuff.write(String.format("%-12s", "Stat"));
                }
                length = this.thieves.length;
                for (int x = 0; x < length; x++) {
                    this.writeBuff.write(String.format("%-6s", "Stat"));
                    this.writeBuff.write(String.format("%-6s", "Str"));
                }

                this.writeBuff.newLine();

                length = this.teams.length;
                for (int x = 0; x < length; x++) {
                    int nrColumns = 6 + this.teams[x].getCapacity()*6;
                    this.writeBuff.write(String.format("%-" + nrColumns + "s", "PARTY_" + this.teams[x].getId()));
                }

                length = this.rooms.length;
                for (int x = 0; x < length; x++) {
                    this.writeBuff.write(String.format("%-14s", "ROOM_" + this.rooms[x].getId()));
                }

                this.writeBuff.newLine();

                length = this.teams.length;
                for (int x = 0; x < length; x++) {
                    this.writeBuff.write(String.format("%-4s", "RId"));
                    int nrMembers = this.teams[x].getCapacity();
                    for (int y = 0; y < nrMembers; y++) {
                        this.writeBuff.write(String.format("%-6s", "Mem_" + y));
                    }
                    this.writeBuff.write("  ");
                }

                length = this.rooms.length;
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
    protected synchronized void writeToLog()
    {
        if (this.writeBuff != null) {
            try {
                int length = this.chiefs.length;
                for (int x = 0; x < length; x++) {
                    this.writeBuff.write(String.format("%-12s", this.statusToStr(this.chiefsStatus.get(this.chiefs[x].getChiefId()))));
                }

                length = this.thieves.length;
                for (int x = 0; x < length; x++) {
                    this.writeBuff.write(String.format("%-6s", this.statusToStr(this.thievesStatus.get(this.thieves[x].getThiefId()))));
                    this.writeBuff.write(String.format("%-6s", this.thieves[x].getPower()));
                }

                this.writeBuff.newLine();

                length = this.teams.length;
                for (int x = 0; x < length; x++) {
                    Room room = this.teams[x].getAssignedRoom();
                    this.writeBuff.write(String.format("%-4s", room != null ? this.teams[x].getAssignedRoom().getId() : "-"));
                    int[] thievesIds = this.teams[x].getThiefs();
                    int nrMembers = thievesIds.length;
                    int capacity = this.teams[x].getCapacity();
                    for (int y = 0; y < capacity; y++) {
                        if (y >= nrMembers) {
                            this.writeBuff.write(String.format("%-3s", "-"));
                            this.writeBuff.write(String.format("%-3s", "-"));
                        } else {
                            this.writeBuff.write(String.format("%-3s", thievesIds[y]));
                            THIEF_STATUS status = this.thievesStatus.get(thievesIds[y]);
                            Integer position = room != null ? (status == THIEF_STATUS.AT_ROOM_ENTRANCE || status == THIEF_STATUS.AT_ROOM_EXIT ? null : room.getCorridor().getThiefPosition(thievesIds[y])) : null;
                            this.writeBuff.write(String.format("%-3s", position != null ? position : "-" ));
                        }
                    }
                    this.writeBuff.write("  ");
                }

                length = this.rooms.length;
                for (int x = 0; x < length; x++) {
                    this.writeBuff.write(String.format("%-4s", this.rooms[x].getId()));
                    this.writeBuff.write(String.format("%-5s", this.rooms[x].getCorridor().getTotalPositions() / 2));
                    this.writeBuff.write(String.format("%-5s", this.rooms[x].getNrCanvas()));
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
    protected String statusToStr(CHIEF_STATUS status) {

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
    protected String statusToStr(THIEF_STATUS status) {

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
