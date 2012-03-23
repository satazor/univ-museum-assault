package museumassault;

/**
 *
 * @author André
 */
public class Team {

    protected boolean prepared = false;
    protected boolean busy = false;
    protected int id;
    protected int nrthieves;
    protected int nrBusythieves = 0;
    protected Room room;

    /**
     *
     * @param id
     */
    public Team(int id, int nrthieves)
    {
        this.id = id;
        this.nrthieves = nrthieves;
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
    public int getNrthieves()
    {
        return this.nrthieves;
    }

    /**
     *
     */
    public int getNrBusythieves()
    {
        return this.nrBusythieves;
    }

    /**
     *
     */
    public void incrementNrBusythieves()
    {
        if (this.nrBusythieves == 0) {
            this.isBusy(true);
            this.nrBusythieves = 1;
        } else {
            this.nrBusythieves++;
        }
    }

    /**
     *
     */
    public void decrementNrBusythieves()
    {
        if (this.nrBusythieves == 1) {
            this.isBusy(false);
            this.nrBusythieves = 0;
        } else if (this.nrBusythieves > 1) {
            this.nrBusythieves--;
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

        if (!busy) this.nrBusythieves = 0;

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
