package museumassault.monitor;

import museumassault.Room;

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
    public Room prepareExcursion(int teamId);

    /**
     *
     */
    public void handACanvas(int thiefId, int teamId, boolean rolledCanvas);
}
