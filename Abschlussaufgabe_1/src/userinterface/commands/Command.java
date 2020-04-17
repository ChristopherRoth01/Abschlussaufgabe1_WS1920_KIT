package userinterface.commands;

import java.util.regex.Pattern;

import logic.ModelRailWay;
import userinterface.InputException;
import userinterface.Session;

/**
 * Abstract Class which is a blueprint for all Commands.
 * 
 * @author Christopher Roth
 * @version 1.0
 */
public abstract class Command {
    /**
     * The ModelRailWay Object all Commands are executed on.
     */
    protected ModelRailWay modelRailWay;

    /**
     * Executes the fitting command, depending on the fitting regex Pattern. Takes
     * the arguments which are set in the setArguments() method.
     */
    public abstract void execute();

    /**
     * The method which sets the arguments, depending on the fitting regex Pattern.
     * If no arguments need to be set, the method is empty. If more than one
     * argument needs to be set the arguments are either saved in a String or a Map.
     * 
     * @param argument the Terminal-Input
     * @throws InputException if the Terminal-Input does not fit a regex-Pattern of
     *                        one of the Commands.
     */
    void setArguments(String argument) throws InputException {
    }

    /**
     * Sets the session of a command, so no NullPointerException occurs, when a
     * Command Class is executed.
     * 
     * @param session the session to be set.
     */
    void setSession(Session session) {
        this.modelRailWay = session.getModelRailWay();
    }

    /**
     * Getter for the Pattern of the commands.
     * 
     * @return the regex-Pattern
     */
    abstract Pattern getPattern();
}
