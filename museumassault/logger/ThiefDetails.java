package museumassault.logger;

import java.io.Serializable;

/**
 * ThiefDetails class.
 * This class has the details of a team in order to be saved into the log.
 *
 * @author Andre Cruz <andremiguelcruz@ua.pt>
 */
public class ThiefDetails implements Serializable
{
    private static final long serialVersionUID = 1000L;

    protected int thiefId;
    protected Integer power;

    /**
     * Constructor.
     *
     * @param thiefId
     * @param power
     */
    public ThiefDetails(int thiefId, Integer power)
    {
        this.thiefId = thiefId;
        this.power = power;
    }

    /**
     * Get the thief id.
     *
     * @return the thief id
     */
    public int getThiefId()
    {
        return thiefId;
    }

    /**
     * Get the thief power.
     *
     * @return the thief power
     */
    public Integer getPower()
    {
        return power;
    }
}
