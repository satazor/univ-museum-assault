package museumassault.monitor;

/**
 *
 * @author André
 */
public interface TargetRoom
{
    /**
     *
     * @return
     */
    public TargetCorridor getTargetCorridor();
    /**
     *
     * @return
     */
    public boolean rollACanvas(int thiefId);
}
