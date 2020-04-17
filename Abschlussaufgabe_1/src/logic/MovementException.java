package logic;

import events.Crash;
import events.Event;

/**
 * This exception is a exception that is thrown in the case of a Train that
 * derails.
 * 
 * @author Christopher Roth
 * @version 1.0
 */
class MovementException extends Exception {

    /**
     * Just an id so Java is happy.
     */
    private static final long serialVersionUID = 165115615616516L;
    /**
     * A Crash Event that could be initialised by a Movementexception.
     */
    private Crash crash;

    /**
     * Constructor with a Crash.
     * 
     * @param crash the Crash that occured.
     */
    MovementException(Crash crash) {
        this.crash = crash;
    }

    /**
     * Getter for the crash event of the Exception.
     * 
     * @return the crash Event of the Exception
     */
    public Event getCrash() {
        return this.crash;
    }
}
