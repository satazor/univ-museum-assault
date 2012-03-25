package museumassault.monitor;

/**
 *
 * @author Andre Cruz <andremiguelcruz@ua.pt>
 */
public interface ThievesConcentrationSite
{
    /**
     *
     */
    public Integer amINeeded(int thiefId);

    /**
     *
     */
    public TargetRoom prepareExcursion(int thiefId, int teamId);

    /**
     *
     */
    public void handACanvas(int thiefId, int teamId, boolean rolledCanvas);
}
