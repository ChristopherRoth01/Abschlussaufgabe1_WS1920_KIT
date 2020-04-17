package userinterface.commands;

import java.util.HashMap;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import edu.kit.informatik.Terminal;
import logic.ErrorMessages;
import logic.LogicException;

import userinterface.InputException;
import userinterface.Strings;

/**
 * Represents the AddTrain Command.
 * 
 * @author Christopher Roth
 * @version 1.0
 */
class AddTrain extends Command {
    /**
     * Regex Pattern for the addTrain Command.
     */
    private final Pattern addTrainPattern = Pattern
            .compile("^add train " + "(" + Strings.DIGIT_POSITIVE.getMessage().toString() + ")" + " " + "("
                    + Strings.DIGIT_OR_LETTER_SEPERATED.getMessage().toString() + ")");
    /**
     * The Map that contains the arguments for the command. It contains the Train-Id
     * and the id of the RollMaterial that should be added.
     */
    private HashMap<Integer, String> arguments = new HashMap<Integer, String>();

    @Override
    public void execute() {

        try {
            tryParse();
            Terminal.printLine(modelRailWay.getRollMaterialComposition().addTrain(Integer.parseInt(arguments.get(0)),
                    arguments.get(1)));

        } catch (LogicException e) {

            Terminal.printLine(e.getMessage().toString());
        }

    }

    private void tryParse() throws LogicException {
        try {
            Integer.parseInt(arguments.get(0));
        } catch (NumberFormatException e) {
            throw new LogicException(ErrorMessages.NUMBER_TOO_BIG.getMessage());
        }

    }

    @Override
    void setArguments(String argument) throws InputException {
        Matcher matcher = addTrainPattern.matcher(argument);
        if (matcher.matches()) {
            arguments.put(0, matcher.group(1));
            arguments.put(1, matcher.group(2));
        }

    }

    /**
     * Getter for the addTrain-Pattern
     * 
     * @return the addTrain Pattern
     */
    @Override
    public Pattern getPattern() {
        return this.addTrainPattern;
    }

}
