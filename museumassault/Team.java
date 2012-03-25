package museumassault;

import museumassault.monitor.Room;

/**
 *
 * @author Andre Cruz <andremiguelcruz@ua.pt>
 */
public class Team {

    protected boolean prepared = false;
    protected boolean busy = false;
    protected int id;
    protected int nrThieves;
    protected int nrBusyThieves = 0;
    protected Room room;

    /**
     * Constructor of a Team
     * @param id - the id of the team
     * @param nrThieves - the number of thieves in the team 
     */
    public Team(int id, int nrThieves)
    {
        this.id = id;
        this.nrThieves = nrThieves;
    }

    /**
     * Method that returns the id of this team
     * @return Integer - the id of the team
     */
    public int getId()
    {
        return this.id;
    }

    /**
     * Method that returns the number of thieves in this team
     * @return Integer - the number of thieves in the team
     */
    public int getNrThieves()
    {
        return this.nrThieves;
    }

    /**
     * Method that returns the number of thieves robbing
     * @return Integer - the number of busy thieves
     */
    public int getNrBusyThieves()
    {
        return this.nrBusyThieves;
    }

    /**
     * Method that increments the number of busy thieves
     */
    public void incrementNrBusyThieves()
    {
        if (this.nrBusyThieves == 0) {
            this.isBusy(true);
            this.nrBusyThieves = 1;
        } else {
            this.nrBusyThieves++;
        }
    }

    /**
     * Method that decrements the number of busy thieves
     */
    public void decrementNrBusyThieves()
    {
        if (this.nrBusyThieves == 1) {
            this.isBusy(false);
            this.nrBusyThieves = 0;
        } else if (this.nrBusyThieves > 1) {
            this.nrBusyThieves--;
        }
    }

    /**
     * Method that returns the room to be robbed by this team
     * @return Room
     */
    public Room getAssignedRoom()
    {
        return this.room;
    }

    /**
     * Method that assigns a room to be robbed to this team
     * @param room - the room to be assigned to this team
     */
    public void setAssignedRoom(Room room)
    {
        this.room = room;
    }
    /**
     * Method that checks if the team is prepared to rob the assigned room
     * @param prepared - true if we want to check if the team is ready to rob
     * @return boolean - Returns true if the team is ready to rob
     */
    public boolean isPrepared(boolean prepared)
    {
        return this.prepared = prepared;
    }

    /**
     * Method that checks if the team is prepared to rob the assigned room
     * @return boolean - Returns true if the team is ready to rob
     */
    public boolean isPrepared()
    {
        return this.prepared;
    }

    /**
     * Method that checks if the team is robbing
     * @param busy - true if we want to check if the team is robbing
     * @return boolean - Returns true if the team is robbing
     */
    public boolean isBusy(boolean busy)
    {
        this.prepared = busy;

        if (!busy) this.nrBusyThieves = 0;

        if (this.room != null) {
            this.room.isBeingRobed(busy);
        }

        return this.busy = busy;
    }

    /**
     * Method that checks if the team is robbing
     * @return boolean - Returns true if the team is robbing
     */
    public boolean isBusy()
    {
        return this.busy;
    }
}
