package museumassault.monitor;

/**
 *
 * @author André
 */
public interface ChiefControlSite
{
    /**
     *
     */
    public Integer appraiseSit();

    /**
     *
     */
    public Integer prepareAssaultParty(int roomId);

    /**
     *
     */
    public void sendAssaultParty(int teamId);

    /**
     *
     * @return
     */
    public Integer takeARest();

    /**
     *
     */
    public void collectCanvas(int thiefId);

    /**
     *
     */
    public int sumUpResults();
}
