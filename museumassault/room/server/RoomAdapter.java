package museumassault.room.server;

import museumassault.common.IShutdownHandler;

/**
 * RoomAdapter class.
 *
 * This class servers as facade to the Room, decoupling it from the
 * network protocol.
 *
 * @author Andre Cruz <andremiguelcruz@ua.pt>
 */
public class RoomAdapter implements IRoom
{
    protected Room room;
    protected IShutdownHandler shutdownHandler;
    protected String shutdownPassword;

    /**
     * Constructor.
     *
     * @param room             the room
     * @param shutdownPassword the shutdown password
     * @param shutdownHandler  the shutdown handler
     */
    public RoomAdapter(Room room, String shutdownPassword, IShutdownHandler shutdownHandler)
    {
        this.room = room;
        this.shutdownPassword = shutdownPassword;
        this.shutdownHandler = shutdownHandler;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public boolean rollACanvas(int thiefId)
    {
        System.out.println("Thief #" + thiefId + " rollACanvas(" + thiefId + ")");
        boolean rolled = this.room.rollACanvas(thiefId);
        System.out.println("Canvas remaining: " + this.room.getNrCanvas());

        return rolled;
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
