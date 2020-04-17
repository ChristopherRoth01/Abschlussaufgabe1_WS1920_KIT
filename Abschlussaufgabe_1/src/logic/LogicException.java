package logic;

/**
 * A LogicException which is thrown whenever a Problem in the Logic of the
 * Program occurs.
 * 
 * @author Christopher Roth
 * @version 1.0
 *
 */
public class LogicException extends Exception {
    /**
     * An Id so Java is happy.
     */
    private static final long serialVersionUID = 1505050000L;

    /**
     * Constructs a new LogicException, which is thrown whenever a Logic error
     * occurs.
     * 
     * @param errorMessage the errorMessage which is thrown with the Exception.
     */
    public LogicException(String errorMessage) {
        super(errorMessage);
    }
}
