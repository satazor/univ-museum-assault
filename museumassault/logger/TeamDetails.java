package museumassault.logger;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

/**
 * TeamDetails class.
 * This class has the details of a team in order to be saved into the log.
 *
 * @author Andre Cruz <andremiguelcruz@ua.pt>
 */
public class TeamDetails implements Serializable
{
    private static final long serialVersionUID = 1000L;

    protected int teamId;
    protected int capacity;
    protected Integer assignedRoomId;
    protected List<Integer> thiefIds;

    /**
     * Constructor.
     *
     * @param teamId
     * @param capacity
     */
    public TeamDetails(int teamId, int capacity)
    {
        this.teamId = teamId;
        this.capacity = capacity;
        this.assignedRoomId = null;
        this.thiefIds = new ArrayList<Integer>();
    }

    /**
     * Constructor.
     *
     * @param teamId
     * @param capacity
     * @param assignedRoomId
     * @param thiefIds
     */
    public TeamDetails(int teamId, int capacity, int assignedRoomId, List<Integer> thiefIds)
    {
        this.teamId = teamId;
        this.capacity = capacity;
        this.assignedRoomId = assignedRoomId;
        this.thiefIds = thiefIds;
    }

    /**
     * Get the team id.
     *
     * @return
     */
    public int getTeamId()
    {
        return this.teamId;
    }

    /**
     * Get the team capacity.
     *
     * @return
     */
    public int getCapacity()
    {
        return this.capacity;
    }

    /**
     * Get the current assigned room.
     *
     * @return
     */
    public Integer getAssignedRoomId()
    {
        return this.assignedRoomId;
    }

    /**
     * Get the thief ids currently in the team.
     *
     * @return
     */
    public List<Integer> getThiefIds()
    {
        return this.thiefIds;
    }
}
