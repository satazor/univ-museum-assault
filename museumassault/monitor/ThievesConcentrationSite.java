package museumassault.monitor;

/**
 *
 * @author André
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
    public TargetRoom prepareExcursion(int teamId);

    /**
     *
     */
    public void handACanvas(int thiefId, int teamId, boolean rolledCanvas);
}
