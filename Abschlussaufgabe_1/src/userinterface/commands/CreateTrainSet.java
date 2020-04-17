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
 * Class for the Create TrainSet Command.
 * 
 * @author Christopher Roth
 * @version 1.0
 */
class CreateTrainSet extends Command {
    /**
     * Pattern
     */
    private final Pattern createTrainSetPattern = Pattern
            .compile("^create train-set " + "(" + Strings.DIGIT_OR_LETTER.getMessage().toString() + ")" + " " + "("
                    + Strings.DIGIT_OR_LETTER.getMessage().toString() + ")" + " " + "("
                    + Strings.DIGIT_POSITIVE.getMessage().toString() + ")" + " " + "("
                    + Strings.TRUE_FALSE.getMessage().toString() + ")" + " " + "("
                    + Strings.TRUE_FALSE.getMessage().toString() + ")$");
    /**
     * Contains all arguments.
     */
    private HashMap<Integer, String> arguments = new HashMap<Integer, String>();

    @Override
    public void execute() {
        try {
            tryParse();
            Terminal.printLine(modelRailWay.getRollMaterialComposition().getRollMaterialStock().addTrainSetToCollection(
                    arguments.get(0), arguments.get(1), arguments.get(2), arguments.get(3), arguments.get(4)));
        } catch (LogicException l) {
            Terminal.printLine(l.getMessage().toString());
        }

    }

    private void tryParse() throws LogicException {
        try {
            Integer.parseInt(arguments.get(2));
        } catch (NumberFormatException n) {
            throw new LogicException(ErrorMessages.NUMBER_TOO_BIG.getMessage());
        }

    }

    @Override
    void setArguments(String argument) throws InputException {
        Matcher matcher = createTrainSetPattern.matcher(argument);
        if (matcher.matches()) {
            arguments.put(0, matcher.group(1));
            arguments.put(1, matcher.group(2));
            arguments.put(2, matcher.group(3));
            arguments.put(3, matcher.group(4));
            arguments.put(4, matcher.group(5));

        }

    }

    /**
     * Getter for the Pattern
     * 
     * @return the Pattern of this command.
     */
    public Pattern getPattern() {
        return this.createTrainSetPattern;
    }

}
