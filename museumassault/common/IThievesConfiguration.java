
package museumassault.common;

/**
 *
 * @author Andre Cruz <andremiguelcruz@ua.pt>
 */
public interface IThievesConfiguration
{
    /**
     *
     * @param roomId
     *
     * @return
     */
    public String getRoomConnectionString(int roomId);

    /**
     *
     * @param roomId
     *
     * @return
     */
    public String getCorridorConnectionString(int roomId);

    /**
     *
     * @param roomId
     *
     * @return
     */
    public Integer getRoomCorridorId(int roomId);
}
