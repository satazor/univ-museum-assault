package museumassault.shared_site.server;

/**
 * SharedSiteThievesAdapter class.
 *
 * This class servers as facade to the SharedSite, decoupling it from the
 * network protocol.
 *
 * @author Andre Cruz <andremiguelcruz@ua.pt>
 */
public class SharedSiteThievesAdapter implements IThievesConcentrationSite
{
    protected SharedSite site;

    /**
     * Constructor.
     *
     * @param site The shared site
     */
    public SharedSiteThievesAdapter(SharedSite site)
    {
        this.site = site;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Integer amINeeded(int thiefId)
    {
        System.err.println("Thief #" + thiefId + " called amINeeded(" + thiefId + ")");

        return this.site.amINeeded(thiefId);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Integer prepareExcursion(int thiefId, int teamId)
    {
        System.err.println("Thief #" + thiefId + " called prepareExcursion(" + thiefId + ", " + teamId + ")");

        return this.site.prepareExcursion(thiefId, teamId);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void handACanvas(int thiefId, int teamId, boolean rolledCanvas)
    {
        System.err.println("Thief #" + thiefId + " called handACanvas(" + thiefId + ", " + teamId + ", " + rolledCanvas + ")");

        this.site.handACanvas(thiefId, teamId, rolledCanvas);
    }

}
