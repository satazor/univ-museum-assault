package museumassault.monitor;

/**
 *
 * @author Andre Cruz <andremiguelcruz@ua.pt>
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
