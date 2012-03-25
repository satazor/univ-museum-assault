package museumassault.monitor;

/**
 *
 * @author André
 */
public interface TargetCorridor
{
    /**
     *
     */
    public boolean crawlIn(int thiefId, int increment);

    /**
     *
     */
    public boolean crawlOut(int thiefId, int increment);
}
