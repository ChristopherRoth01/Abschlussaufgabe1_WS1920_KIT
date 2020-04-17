package userinterface;

import edu.kit.informatik.Terminal;
import logic.ModelRailWay;
import userinterface.commands.Command;
import userinterface.commands.CommandCenter;

/**
 * Class for a Session. A Session combines the UserInterface with the
 * ModelRailWay. Handles the Terminal input and executes the fitting Command,
 * which is returned by the CommandCenter.
 * 
 * @author Christopher Roth
 * @version 1.0
 *
 */
public class Session {
    /**
     * Boolean for the while(true) loop in the run() method. Is set to false if exit
     * is typed in the Terminal.
     */
    private boolean finished = false;
    /**
     * A ModelRailWay.
     */
    private ModelRailWay modelRailWay;

    /**
     * Constructor, which initiates a ModelRailWay.
     */
    public Session() {
        modelRailWay = new ModelRailWay();
    }

    /**
     * The method which is executed in the main-Class. Handles the Terminal input
     * and executes the fitting Command, which is returned by the CommandCenter.
     *
     */
    public void run() {
        CommandCenter center = new CommandCenter(this);

        while (!finished) {
            try {
                Command command = center.getCommand(Terminal.readLine());
                command.execute();
            } catch (InputException i) {
                Terminal.printLine(i.getMessage());
            }
        }
    }

    /**
     * The method which sets finished to true, which leads to a termination of the
     * program.
     */
    public void quit() {
        finished = true;
    }

    /**
     * Getter for the ModelRailWay.
     * 
     * @return the ModelRailWay of the Session.
     */
    public ModelRailWay getModelRailWay() {
        return this.modelRailWay;
    }
}
