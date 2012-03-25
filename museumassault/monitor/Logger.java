package museumassault.monitor;

import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;
import java.util.HashMap;
import museumassault.Chief;
import museumassault.Team;
import museumassault.Thief;

/**
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
        AT_A_ROOM,
        CRAWLING_OUTWARDS
    };

    protected HashMap chiefsStatus = new HashMap();
    protected HashMap thievesStatus = new HashMap();
    protected Chief[] chiefs;
    protected Thief[] thieves;
    protected Team[] teams;

    FileWriter fileWriter;
    BufferedWriter writeBuff;
    String fileName;
    protected boolean configured = false;

    /**
     * Constructor of a Logger
     * @param fileName - the name of the file to be written
     */
    public Logger(String fileName)
    {
        this.fileName = fileName;
        try {
            this.fileWriter = new FileWriter(fileName);
            this.writeBuff = new BufferedWriter(fileWriter);
        } catch (IOException e) {
            System.err.println("Unable to write to log file " + fileName);
        }
    }

    /**
     * Method that initializes the status of the chiefs and thieves
     * @param chiefs - the different chiefs that control the assault
     * @param thieves - the different thieves that rob the paintings
     */
    public void configure(Chief[] chiefs, Thief[] thieves, Team[] teams)
    {
        this.thieves = thieves;
        this.chiefs = chiefs;
        this.teams = teams;
        this.configured = true;

        int length = chiefs.length;
        for (int x = 0; x < length; x++) {
            this.chiefsStatus.put(chiefs[x].getChiefId(), this.statusToStr(CHIEF_STATUS.PLANNING_THE_HEIST));
        }

        length = thieves.length;
        for (int x = 0; x < length; x++) {
            this.thievesStatus.put(thieves[x].getThiefId(), this.statusToStr(THIEF_STATUS.OUTSIDE));
        }

        this.printHeader();
        this.writeToLog();
    }

    /**
     * Method that changes the chief's status
     * @param status - the new status of the given chief
     * @param chiefId - the id of the chief changing status
     */
    public synchronized void setChiefStatus(int chiefId, CHIEF_STATUS status)
    {
        assert(this.configured);
        this.chiefsStatus.put(chiefId, this.statusToStr(status));

        this.writeToLog();
    }

    /**
     * Method that changes the thief's status
     * @param status - the new status of the given thief
     * @param thiefId - the id of the thief changing status
     */
    public synchronized void setThiefStatus(int thiefId, THIEF_STATUS status)
    {
        assert(this.configured);
        this.thievesStatus.put(thiefId, this.statusToStr(status));

        this.writeToLog();
    }

    /**
     * Method that prints the header of the log
     */
    protected synchronized void printHeader()
    {
        if (this.writeBuff != null) {
            try {
                this.writeBuff.write("Assault to the museum");
                this.writeBuff.newLine();
                this.writeBuff.newLine();

                int length = this.chiefs.length;
                for (int x = 0; x < length; x++) {
                    this.writeBuff.write(String.format("%-12s", "CHIEF_" + x));
                }
                length = this.thieves.length;
                for (int x = 0; x < length; x++) {
                    this.writeBuff.write(String.format("%-12s", "THIEF_" + x));
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
                    int nrColumns = 12 + this.teams[x].getCapacity()*6;
                    this.writeBuff.write(String.format("%-" + nrColumns + "s", "PARTY_" + x));
                }

                this.writeBuff.newLine();

                for (int x = 0; x < length; x++) {
                    this.writeBuff.write(String.format("%-4s", "RId"));
                    this.writeBuff.write(String.format("%-6s", "RTPos"));
                    int nrMembers = this.teams[x].getCapacity();
                    for (int y = 0; y < nrMembers; y++) {
                        this.writeBuff.write(String.format("%-6s", "Mem_" + y));
                    }
                    this.writeBuff.write("  ");
                }

                this.writeBuff.newLine();
            } catch (IOException e) {
                System.err.println("Unable to write to log file " + this.fileName);
            }
        }
    }

    /**
     * Method that writes on the file the contents of the buffer
     */
    protected synchronized void writeToLog()
    {
        if (this.writeBuff != null) {
            try {
                int length = this.chiefs.length;
                for (int x = 0; x < length; x++) {
                    this.writeBuff.write(String.format("%-12s", this.chiefsStatus.get(this.chiefs[x].getChiefId())));
                }

                length = this.thieves.length;
                for (int x = 0; x < length; x++) {
                    this.writeBuff.write(String.format("%-6s", this.thievesStatus.get(this.thieves[x].getThiefId())));
                    this.writeBuff.write(String.format("%-6s", this.thieves[x].getPower()));
                }

                this.writeBuff.newLine();

                length = this.teams.length;
                for (int x = 0; x < length; x++) {
                    Room room = this.teams[x].getAssignedRoom();
                    this.writeBuff.write(String.format("%-4s", room != null ? this.teams[x].getAssignedRoom().getId() : "-"));
                    this.writeBuff.write(String.format("%-6s", room != null ? room.getCorridor().getTotalPositions() : "-"));
                    int[] thievesIds = this.teams[x].getThiefs();
                    int nrMembers = thievesIds.length;
                    int capacity = this.teams[x].getCapacity();
                    for (int y = 0; y < capacity; y++) {
                        if (y >= nrMembers) {
                            this.writeBuff.write(String.format("%-3s", "-"));
                            this.writeBuff.write(String.format("%-3s", "-"));
                        } else {
                            this.writeBuff.write(String.format("%-3s", thievesIds[y]));
                            Integer position = room != null ? room.getCorridor().getThiefPosition(y) : null;
                            this.writeBuff.write(String.format("%-3s", position != null ? position : "-" ));
                        }
                    }
                    this.writeBuff.write("  ");
                }

                this.writeBuff.newLine();
                this.writeBuff.flush();
            } catch (IOException e) {
                System.err.println("Unable to write to log file " + this.fileName);
            }
        }
    }

    /**
     * Method that returns the tag associated with the given chief's status
     * @param status - the chief's status
     * @return String - Returns the tag associated with the status
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
     * Method that returns the tag associated with the given thief's status
     * @param status - the thief's status
     * @return String - Returns the tag associated with the status
     */
    protected String statusToStr(THIEF_STATUS status) {

        switch (status) {
            case OUTSIDE:
                return "OUTS";
            case CRAWLING_OUTWARDS:
                return "COUT";
            case AT_A_ROOM:
                return "ATAR";
            case CRAWLING_INWARDS:
                return "CRIN";
            default:
                return "";
        }
    }
}
