
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
     * Gets the host of a given room.
     *
     * @param roomId the room id
     *
     * @return the room host
     */
    public String getRoomHost(int roomId);

    /**
     * Gets the host of a given corridor.
     *
     * @param corridorId the corridor id
     *
     * @return the corridor host
     */
    public String getCorridorHost(int corridorId);

    /**
     * Gets the port of a given room.
     *
     * @param roomId the room id
     *
     * @return the room port
     */
    public Integer getRoomPort(int roomId);

    /**
     * Gets the port of a given corridor.
     *
     * @param corridorId the corridor id
     *
     * @return the corridor port
     */
    public Integer getCorridorPort(int corridorId);

    /**
     * Gets the corridor id that is associated with a room
     *
     * @param roomId the room id
     *
     * @return the corridor id of the room
     */
    public Integer getRoomCorridorId(int roomId);
}
