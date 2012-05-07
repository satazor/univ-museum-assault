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
     *
     */
    public ComException() {
        super();
    }

    /**
     *
     * @param string
     */
    public ComException(String string) {
        super(string);
    }
}
