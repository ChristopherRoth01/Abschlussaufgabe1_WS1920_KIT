package userinterface;

/**
 * The InputException is thrown if the Terminal Input does not match any Regex
 * Patterns.
 * 
 * @author Christopher Roth
 * @version 1.0
 */
public class InputException extends Exception {
    /**
     * Just a random Id so Java is happy.
     */
    private static final long serialVersionUID = 12222262626L;

    /**
     * Constructor of a InputException.
     * 
     * @param errorMessage the Message which is printed when a InputException is
     *                     thrown.
     */
    public InputException(String errorMessage) {
        super(errorMessage);
    }
}
