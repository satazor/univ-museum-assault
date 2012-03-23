/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package museumassault;

/**
 *
 * @author André
 */
class Team {

    protected boolean prepared = false;
    protected boolean busy = false;
    protected int id;
    protected int nrThiefs;
    protected int nrBusyThiefs = 0;
    protected Room room;

    /**
     *
     * @param id
     */
    public Team(int id, int nrThiefs)
    {
        this.id = id;
        this.nrThiefs = nrThiefs;
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
    public int getNrThiefs()
    {
        return this.nrThiefs;
    }

    /**
     *
     */
    public int getNrBusyThiefs()
    {
        return this.nrBusyThiefs;
    }

    /**
     *
     */
    public void incrementNrBusyThiefs()
    {
        if (this.nrBusyThiefs == 0) {
            this.isBusy(true);
            this.nrBusyThiefs = 1;
        } else {
            this.nrBusyThiefs++;
        }
    }

    /**
     *
     */
    public void decrementNrBusyThiefs()
    {
        if (this.nrBusyThiefs == 1) {
            this.isBusy(false);
            this.nrBusyThiefs = 0;
        } else if (this.nrBusyThiefs > 1) {
            this.nrBusyThiefs--;
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

        if (!busy) this.nrBusyThiefs = 0;

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
