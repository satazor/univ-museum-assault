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
    public boolean crallIn(int thiefId, int increment);

    /**
     *
     */
    public boolean crallOut(int thiefId, int increment);
}
