package museumassault.monitor;

/**
 *
 * @author Andre Cruz <andremiguelcruz@ua.pt>
 */
public interface TargetCorridor
{
    /**
     *
     */
    public boolean crawlOut(int thiefId, int increment);

    /**
     *
     */
    public boolean crawlIn(int thiefId, int increment);
}
