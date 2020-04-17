package userinterface.commands;

import java.util.regex.Pattern;

import userinterface.InputException;
import userinterface.Session;

/**
 * Class for the Exit Pattern.
 * 
 * @author Christopher Roth
 * @version 1.0
 */
class Exit extends Command {
    /**
     * Pattern.
     */
    private final Pattern exitPattern = Pattern.compile("^exit$");

    /**
     * This command gets a session in contrast to the other commands, because this
     * is the only Command which is executed on the session.
     */
    private Session session;

    /**
     * Getter for the Pattern.
     * 
     * @return the Pattern of this Command.
     */
    public Pattern getPattern() {
        return this.exitPattern;
    }

    @Override
    public void execute() {

        session.quit();
    }

    @Override
    public void setSession(Session session) {
        this.session = session;
    }

    @Override
    void setArguments(String argument) throws InputException {
        // no arguments are needed for this Command.

    }

}
