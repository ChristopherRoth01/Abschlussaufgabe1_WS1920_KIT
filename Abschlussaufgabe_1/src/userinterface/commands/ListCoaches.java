package userinterface.commands;

import java.util.regex.Pattern;

import edu.kit.informatik.Terminal;

/**
 * Class for the ListCoaches Command.
 * 
 * @author Christopher Roth
 * @version 1.0
 */
class ListCoaches extends Command {
    /**
     * Pattern
     */
    private final Pattern listCoachesPattern = Pattern.compile("^list coaches$");

    @Override
    public void execute() {

        Terminal.printLine(modelRailWay.getRollMaterialComposition().getRollMaterialStock().returnCoaches());

    }

    @Override
    public Pattern getPattern() {
        return this.listCoachesPattern;
    }

}
