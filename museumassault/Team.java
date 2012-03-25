package museumassault;

import museumassault.monitor.Room;

/**
 *
 * @author André
 */
public class Team {

    protected boolean prepared = false;
    protected boolean busy = false;
    protected int id;
    protected int nrThieves;
    protected int nrBusyThieves = 0;
    protected Room room;

    /**
     *
     * @param id
     */
    public Team(int id, int nrThieves)
    {
        this.id = id;
        this.nrThieves = nrThieves;
    }

    /**
     *
     */
    public int getId()
    {
        return this.id;
    }

    /**
     *
     */
    public int getNrThieves()
    {
        return this.nrThieves;
    }

    /**
     *
     */
    public int getNrBusyThieves()
    {
        return this.nrBusyThieves;
    }

    /**
     *
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
     *
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
     *
     */
    public Room getAssignedRoom()
    {
        return this.room;
    }

    /**
     *
     */
    public void setAssignedRoom(Room room)
    {
        this.room = room;
    }
    /**
     *
     * @return
     */
    public boolean isPrepared(boolean prepared)
    {
        return this.prepared = prepared;
    }

    /**
     *
     * @return
     */
    public boolean isPrepared()
    {
        return this.prepared;
    }

    /**
     *
     * @return
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
     *
     * @return
     */
    public boolean isBusy()
    {
        return this.busy;
    }
}
