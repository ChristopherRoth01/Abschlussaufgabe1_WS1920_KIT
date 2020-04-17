package userinterface.commands;

import java.util.regex.Pattern;

import edu.kit.informatik.Terminal;

/**
 * Class for the ListTrainSets Command.
 * 
 * @author Christopher Roth
 * @version 1.0
 */
class ListTrainSets extends Command {
    /**
     * Pattern.
     */
    private final Pattern listTrainSetsPattern = Pattern.compile("^list train-sets$");

    @Override
    public void execute() {
        Terminal.printLine(modelRailWay.getRollMaterialComposition().getRollMaterialStock().returnTrainSets());

    }

    @Override
    public Pattern getPattern() {
        return this.listTrainSetsPattern;
    }
}
