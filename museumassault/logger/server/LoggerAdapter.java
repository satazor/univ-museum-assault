package museumassault.logger.server;

import museumassault.common.IShutdownHandler;
import museumassault.logger.CorridorDetails;
import museumassault.logger.RoomDetails;
import museumassault.logger.TeamDetails;
import museumassault.logger.ThiefDetails;

/**
 * LoggerAdapter class.
 *
 * This class servers as facade to the Logger, decoupling it from the
 * network protocol.
 *
 * @author Andre Cruz <andremiguelcruz@ua.pt>
 */
public class LoggerAdapter implements ILogger
{
    protected Logger logger;
    protected IShutdownHandler shutdownHandler;
    protected String shutdownPassword;

    /**
     * Constructor.
     *
     * @param logger           the logger
     * @param shutdownPassword the shutdown password
     * @param shutdownHandler  the shutdown handler
     */
    public LoggerAdapter(Logger logger, String shutdownPassword, IShutdownHandler shutdownHandler)
    {
        this.logger = logger;
        this.shutdownPassword = shutdownPassword;
        this.shutdownHandler = shutdownHandler;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public boolean shutdown(String shutdownPassword, int totalCanvas)
    {
        System.err.println("Shutdown called.");

        this.logger.terminateLog(totalCanvas);
        
        if (this.shutdownPassword.equals(shutdownPassword)) {
            this.shutdownHandler.onShutdown();

            return true;
        }

        return false;
    }

     /**
     * {@inheritDoc}
     */
    @Override
    public void setChiefStatus(int chiefId, CHIEF_STATUS status)
    {
        System.out.println("Chief #" + chiefId + " setChiefStatus(" + chiefId + ", " + status + ")");

        this.logger.setChiefStatus(chiefId, status);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void setThiefStatus(int thiefId, THIEF_STATUS status)
    {
        System.out.println("Thief #" + thiefId + " setThiefStatus(" + thiefId + ", " + status + ")");

        this.logger.setThiefStatus(thiefId, status);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void setThiefDetails(ThiefDetails details)
    {
        System.out.println("setThiefDetails(details)");

        this.logger.setThiefDetails(details);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void setRoomDetails(RoomDetails details)
    {
        System.out.println("setRoomDetails(details)");

        this.logger.setRoomDetails(details);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void setCorridorDetails(CorridorDetails details)
    {
        System.out.println("setCorridorDetails(details)");

        this.logger.setCorridorDetails(details);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void setTeamDetails(TeamDetails details)
    {
        System.out.println("setTeamDetails(details)");

        this.logger.setTeamDetails(details);
    }
}
