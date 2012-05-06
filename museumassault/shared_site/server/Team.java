package museumassault.shared_site.server;

import java.util.LinkedList;

/**
 * Team class.
 *
 * This class represents a team of thieves or a party.
 *
 * @author Andre Cruz <andremiguelcruz@ua.pt>
 */
public class Team
{
    protected boolean beingPrepared = false;
    protected boolean busy = false;
    protected int id;
    protected int nrThieves;
    protected int nrBusyThieves = 0;
    protected int roomId;
    protected LinkedList<Integer> thiefIds = new LinkedList<>();

    /**
     * Class constructor.
     *
     * @param id        the id of the team
     * @param nrThieves the number of thieves in the team
     */
    public Team(int id, int nrThieves)
    {
        this.id = id;
        this.nrThieves = nrThieves;
    }

    /**
     * Get the team id.
     *
     * @return Integer the id of the team
     */
    public int getId()
    {
        return this.id;
    }

    /**
     * Get the team capacity (total of thieves that it can hold).
     *
     * @return the team capacity
     */
    public int getCapacity()
    {
        return this.nrThieves;
    }

    /**
     * Get the number of thieves that are present in the team.
     *
     * @return the number of thieves currently in the team
     */
    public int getNrThieves()
    {
        return this.nrBusyThieves;
    }

    /**
     * Adds a thief to the team.
     * The team status will be set to busy automatically upon addition.
     *
     * @param thiefId the id of the thief to be added
     */
    public void addThief(int thiefId)
    {
        if (!this.thiefIds.contains(thiefId)) {
            this.thiefIds.push(thiefId);

            if (this.nrBusyThieves == 0) {
                this.isBusy(true);
                this.nrBusyThieves = 1;
            } else {
                this.nrBusyThieves++;
            }
        }
    }

    /**
     * Removes a thief from the team.
     * The team status will be set to non-busy if the team has no thieves currently in the team.
     *
     * @param thiefId the id of the thief to be removed
     */
    public void removeThief(int thiefId)
    {
        int index = this.thiefIds.indexOf(thiefId);
        if (index != -1) {

            this.thiefIds.remove(index);

            if (this.nrBusyThieves == 1) {
                this.isBusy(false);
                this.nrBusyThieves = 0;
            } else if (this.nrBusyThieves > 1) {
                this.nrBusyThieves--;
            }
        }
    }

    /**
     * Get the thieves currently in the team.
     *
     * @return the array of ids of the thieves currently present
     */
    public int[] getThiefs()
    {
        int length = this.thiefIds.size();
        int[] newArray = new int[length];
        for (int x = 0; x < length; x++) {
            newArray[x] = thiefIds.get(x);
        }

        return newArray;
    }

    /**
     * Get the room that was assigned for the team.
     *
     * @return the assigned room
     */
    public int getAssignedRoomId()
    {
        return this.roomId;
    }

    /**
     * Set the assigned room for the team.
     *
     * @param room the room to be assigned
     */
    public void setAssignedRoomId(int id)
    {
        this.roomId = id;
    }

    /**
     * Set if the team is being prepared (assembled).
     *
     * @param prepared true if the team is being prepared, false otherwise
     */
    public void isBeingPrepared(boolean prepared)
    {
        this.beingPrepared = prepared;
    }

    /**
     * Check if the team is being prepared (assembled).
     *
     * @return true if the team is being prepared, false otherwise
     */
    public boolean isBeingPrepared()
    {
        return this.beingPrepared;
    }

    /**
     * Sets the busy state of the team.
     *
     * A team is busy if it has thieves that are robbing a room.
     *
     * @param busy true to set the team as busy, false otherwise
     */
    protected void isBusy(boolean busy)
    {
        if (!busy) this.nrBusyThieves = 0;

        // TODO: check this
        /*if (this.room != null) {
            this.room.isBeingRobed(busy);
        }*/

        this.beingPrepared = busy;
        this.busy = busy;
    }

    /**
     * Checks if the team is busy.
     *
     * A team is busy if it has thieves that are robbing a room.
     *
     * @return true to set the team as busy, false otherwise
     */
    public boolean isBusy()
    {
        return this.busy;
    }
}
