
package museumassault.common;

/**
 * IThievesConfiguration interface.
 * This interface is just to ensure that thieves do not get access to sensitive information.
 *
 * @author Andre Cruz <andremiguelcruz@ua.pt>
 */
public interface IThievesConfiguration
{
    /**
     * Gets the connection string of a room.
     *
     * @param roomId the room id
     *
     * @return
     */
    public String getRoomConnectionString(int roomId);

    /**
     * Gets the connection string of a corridor.
     *
     * @param corridorId the corridor id
     *
     * @return
     */
    public String getCorridorConnectionString(int corridorId);

    /**
     * Gets the corridor id that is associated with a room
     *
     * @param roomId the room id
     *
     * @return
     */
    public Integer getRoomCorridorId(int roomId);
}
