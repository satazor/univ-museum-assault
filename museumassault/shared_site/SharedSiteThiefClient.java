package museumassault.shared_site;

import java.util.Random;
import museumassault.common.ClientCom;
import museumassault.common.Message;
import museumassault.room.RoomClient;

/**
 *
 * @author Andre Cruz <andremiguelcruz@ua.pt>
 */
public class SharedSiteThiefClient implements IThiefMessageConstants
{
    protected ClientCom con;
    protected Random random = new Random();

    /**
     * Constructor.
     */
    public SharedSiteThiefClient(String connectionString)
    {
        this.con = new ClientCom(connectionString);
    }

    /**
     * Waits until the thief is needed.
     *
     * @param thiefId the thief id
     *
     * @return the id of the team in which this thief is needed
     */
    public Integer amINeeded(int thiefId)
    {
        while (true) {

            while (!this.con.open()) {                           // Try until the server responds
                try {
                    Thread.sleep(this.random.nextInt(500) + 500);
                } catch (InterruptedException e) {}
            }

            this.con.writeMessage(new Message(AM_I_NEEDED_TYPE, thiefId));

            Message response = this.con.readMessage();

            if (response.getType() == YOUR_NEEDED_TYPE) {
                return (Integer) response.getExtra();
            } else if (response.getType() == YOUR_NOT_NEEDED_TYPE) {
                try {
                    Thread.sleep(this.random.nextInt(90) + 10);  // Ask again after 10-100 ms
                } catch (InterruptedException e) {}
            }
        }
    }

    /**
     * Prepares the thief for the excursion.
     * This method should signal the master that the thief is ready and only depart
     * when notified.
     *
     * @param thiefId the thief id
     * @param teamId  the team in which the thief belongs
     *
     * @return the room assigned to the team
     */
    public RoomClient prepareExcursion(int thiefId, int teamId)
    {
        return null;
    }

    /**
     * Hands a canvas to the chief.
     *
     * @param thiefId      the thief id
     * @param teamId       the team where that thief belongs
     * @param rolledCanvas true if a canvas was stolen, false otherwise
     */
    public void handACanvas(int thiefId, int teamId, boolean rolledCanvas)
    {

    }
}
