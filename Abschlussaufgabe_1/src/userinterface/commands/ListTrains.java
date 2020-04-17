package userinterface.commands;

import java.util.regex.Pattern;

import edu.kit.informatik.Terminal;

/**
 * Class for the ListTrains Command.
 * 
 * @author Christopher Roth
 * @version 1.0
 */
class ListTrains extends Command {
    /**
     * Pattern
     */
    private final Pattern listTrainsPattern = Pattern.compile("^list trains$");

    @Override
    public void execute() {
        Terminal.printLine(modelRailWay.getRollMaterialComposition().returnTrains());

    }

    @Override
    public Pattern getPattern() {
        return this.listTrainsPattern;
    }

}
