package museumassault.logger;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

/**
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
     *
     * @param teamId
     * @param capacity
     */
    public TeamDetails(int teamId, int capacity)
    {
        this.teamId = teamId;
        this.capacity = capacity;
        this.assignedRoomId = null;
        this.thiefIds = new ArrayList<>();
    }

    /**
     *
     * @param teamId
     * @param capacity
     * @param assignedRoomId
     */
    public TeamDetails(int teamId, int capacity, int assignedRoomId, List<Integer> thiefIds)
    {
        this.teamId = teamId;
        this.capacity = capacity;
        this.assignedRoomId = assignedRoomId;
        this.thiefIds = thiefIds;
    }

    /**
     *
     * @return
     */
    public int getTeamId()
    {
        return this.teamId;
    }

    /**
     *
     * @return
     */
    public int getCapacity()
    {
        return this.capacity;
    }

    /**
     *
     * @return
     */
    public Integer getAssignedRoomId()
    {
        return this.assignedRoomId;
    }

    /**
     *
     * @return
     */
    public List<Integer> getThiefIds()
    {
        return this.thiefIds;
    }
}
