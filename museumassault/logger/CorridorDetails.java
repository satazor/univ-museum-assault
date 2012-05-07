package museumassault.logger;

import java.io.Serializable;
import java.util.HashMap;

/**
 * CorridorDetails class.
 * This class has the details of a corridor in order to be saved into the log.
 *
 * @author Andre Cruz <andremiguelcruz@ua.pt>
 */
public class CorridorDetails implements Serializable
{
    private static final long serialVersionUID = 1000L;

    protected int corridorId;
    protected HashMap<Integer, Integer> positions;
    protected Integer totalPositions;

    /**
     * Constructor.
     *
     * @param corridorId
     * @param totalPositions
     * @param positions
     */
    public CorridorDetails(int corridorId, int totalPositions, HashMap<Integer, Integer> positions)
    {
        this.corridorId = corridorId;
        this.positions = positions;
        this.totalPositions = totalPositions;
    }

    /**
     * Constructor.
     *
     * @param corridorId
     */
    public CorridorDetails(int corridorId)
    {
        this.corridorId = corridorId;
        this.positions = new HashMap<Integer, Integer>();
        this.totalPositions = null;
    }

    /**
     * Get the corridor id.
     *
     * @return
     */
    public int getCorridorId()
    {
        return this.corridorId;
    }

    /**
     * Get a thief position.
     *
     * @param thiefId the thief id
     *
     * @return
     */
    public Integer getThiefPosition(int thiefId)
    {
        Integer pos = this.positions.get(thiefId);
        if (pos == null || pos == -1 || pos > this.getTotalPositions()) {
            return null;
        }

        return pos;
    }

    /**
     * Get the total number of positions.
     * 
     * @return
     */
    public Integer getTotalPositions()
    {
        return this.totalPositions;
    }
}
