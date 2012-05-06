package museumassault.logger;

import java.io.Serializable;

/**
 *
 * @author Andre Cruz <andremiguelcruz@ua.pt>
 */
public class ThiefDetails implements Serializable
{
    private static final long serialVersionUID = 1000L;

    protected int thiefId;
    protected Integer power;

    /**
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
     *
     * @return
     */
    public int getThiefId()
    {
        return thiefId;
    }

    /**
     *
     * @return
     */
    public Integer getPower()
    {
        return power;
    }
}