package museumassault.common;

/**
 * Interface that exposes the shutdown handler.
 *
 * @author Andre Cruz <andremiguelcruz@ua.pt>
 */
public interface IShutdownHandler
{
    /**
     * Executes the following function on shutdown.
     */
    public void onShutdown();
}