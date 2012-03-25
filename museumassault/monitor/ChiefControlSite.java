package museumassault.monitor;

/**
 *
 * @author Andre Cruz <andremiguelcruz@ua.pt>
 */
public interface ChiefControlSite
{
    /**
     *
     */
    public Integer appraiseSit(int chiefId);

    /**
     *
     */
    public Integer prepareAssaultParty(int chiefId, int roomId);

    /**
     *
     */
    public void sendAssaultParty(int chiefId, int teamId);

    /**
     *
     * @return
     */
    public Integer takeARest(int chiefId);

    /**
     *
     */
    public void collectCanvas(int chiefId, int thiefId);

    /**
     *
     */
    public int sumUpResults(int chiefId);
}
