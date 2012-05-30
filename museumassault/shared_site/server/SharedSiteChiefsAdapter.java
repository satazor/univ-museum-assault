package museumassault.shared_site.server;

/**
 * SharedSiteChiefsAdapter class.
 *
 * This class servers as facade to the SharedSite, decoupling SharedSite from the
 * network protocol.
 *
 * @author Andre Cruz <andremiguelcruz@ua.pt>
 */
public class SharedSiteChiefsAdapter implements IChiefsControlSite
{
    protected SharedSite site;
    protected ShutdownHandler shutdownHandler;
    protected String shutdownPassword;

    /**
     * Constructor.
     *
     * @param site The shared site
     */
    public SharedSiteChiefsAdapter(SharedSite site, String shutdownPassword, ShutdownHandler shutdownHandler)
    {
        this.site = site;
        this.shutdownPassword = shutdownPassword;
        this.shutdownHandler = shutdownHandler;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Integer appraiseSit(int chiefId)
    {
        System.err.println("Chief #" + chiefId + " called appraiseSit(" + chiefId + ")");

        return this.site.appraiseSit(chiefId);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Integer prepareAssaultParty(int chiefId, int roomId)
    {
        System.err.println("Chief #" + chiefId + " called prepareAssaultParty(" + chiefId + ", " + roomId + ")");

        return this.site.prepareAssaultParty(chiefId, roomId);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void sendAssaultParty(int chiefId, int teamId)
    {
        System.err.println("Chief #" + chiefId + " called sendAssaultParty(" + chiefId + ", " + teamId + ")");

        this.site.sendAssaultParty(chiefId, teamId);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void collectCanvas(int chiefId, int thiefId)
    {
        System.err.println("Chief #" + chiefId + " called collectCanvas(" + chiefId + ", " + thiefId + ")");

        this.site.collectCanvas(chiefId, thiefId);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Integer sumUpResults(int chiefId)
    {
        System.err.println("Chief #" + chiefId + " called sumUpResults(" + chiefId + ")");

        return this.site.sumUpResults(chiefId);
    }

    /**
     * Takes a rest, waiting for a thief arrival.
     *
     * @param chiefId the id of the chief
     *
     * @return the id of the thief that arrived or null if there is no remaining thieves
     */
    @Override
    public Integer takeARest(int chiefId)
    {
        System.err.println("Chief #" + chiefId + " called takeARest(" + chiefId + ")");

        return this.site.takeARest(chiefId);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public boolean shutdown(String shutdownPassword)
    {
        System.err.println("Shutdown called.");

        if (this.shutdownPassword.equals(shutdownPassword)) {
            this.shutdownHandler.onShutdown();

            return true;
        }

        return false;
    }

    /**
     * Interface that exposes the shutdown handler.
     */
    public interface ShutdownHandler
    {
        /**
         * Executes the following function on shutdown.
         */
        public void onShutdown();
    }
}
