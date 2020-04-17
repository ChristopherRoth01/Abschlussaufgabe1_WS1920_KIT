package userinterface.commands;

import java.util.regex.Pattern;

import edu.kit.informatik.Terminal;

/**
 * Class for the ListEngines Command.
 * 
 * @author Christopher Roth
 * @version 1.0
 */
class ListEngines extends Command {
    /**
     * Pattern.
     */
    private final Pattern listEnginesPattern = Pattern.compile("^list engines$");

    @Override
    public void execute() {
        Terminal.printLine(modelRailWay.getRollMaterialComposition().getRollMaterialStock().returnEngines());

    }

    @Override
    public Pattern getPattern() {
        return this.listEnginesPattern;
    }

}
