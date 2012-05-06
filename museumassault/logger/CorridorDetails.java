package museumassault.logger;

import java.io.Serializable;
import java.util.HashMap;

/**
 *
 * @author Andre Cruz <andremiguelcruz@ua.pt>
 */
public class CorridorDetails implements Serializable
{
    private static final long serialVersionUID = 1000L;

    protected int corridorId;
    protected HashMap<Integer, Integer> positions;
    private int totalPositions;

    /**
     *
     * @param corridorId
     */
    public CorridorDetails(int corridorId, int totalPositions, HashMap<Integer, Integer> positions)
    {
        this.corridorId = corridorId;
        this.positions = positions;
        this.totalPositions = totalPositions;
    }

    /**
     *
     * @param corridorId
     */
    public CorridorDetails(int corridorId)
    {
        this.corridorId = corridorId;
        this.positions = new HashMap<>();
    }

    /**
     *
     * @return
     */
    public int getCorridorId()
    {
        return this.corridorId;
    }

    /**
     *
     * @param thiefId
     *
     * @return
     */
    public Integer getThiefPosition(int thiefId)
    {
        return this.positions.get(thiefId);
    }

    /**
     *
     * @return
     */
    public int getTotalPositions()
    {
        return this.totalPositions;
    }
}
