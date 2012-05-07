package museumassault.common.exception;

/**
 * ComException class.
 * This class represent a communication exception.
 *
 * @author Andre Cruz <andremiguelcruz@ua.pt>
 */
public class ComException extends Exception
{
    /**
     * Constructor.
     */
    public ComException() {
        super();
    }

    /**
     * Constructor.
     *
     * @param string the error message
     */
    public ComException(String string) {
        super(string);
    }
}
