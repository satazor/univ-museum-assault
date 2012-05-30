package museumassault.corridor.server;

import museumassault.common.IShutdownHandler;

/**
 * SharedSiteChiefsAdapter class.
 *
 * This class servers as facade to the Corridor, decoupling it from the
 * network protocol.
 *
 * @author Andre Cruz <andremiguelcruz@ua.pt>
 */
public class CorridorAdapter implements ICorridor
{
    protected Corridor corridor;
    protected IShutdownHandler shutdownHandler;
    protected String shutdownPassword;

    /**
     * Constructor.
     *
     * @param corridor         the corridor
     * @param shutdownPassword the shutdown password
     * @param shutdownHandler  the shutdown handler
     */
    public CorridorAdapter(Corridor corridor, String shutdownPassword, IShutdownHandler shutdownHandler)
    {
        this.corridor = corridor;
        this.shutdownPassword = shutdownPassword;
        this.shutdownHandler = shutdownHandler;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public boolean crawlOut(int thiefId, int increment)
    {
        System.out.println("Thief #" + thiefId + " crawlOut(" + thiefId + ", " + increment + ")");

        return this.corridor.crawlOut(thiefId, increment);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public boolean crawlIn(int thiefId, int increment)
    {
        System.out.println("Thief #" + thiefId + " crawlIn(" + thiefId + ", " + increment + ")");

        return this.corridor.crawlIn(thiefId, increment);
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
}
