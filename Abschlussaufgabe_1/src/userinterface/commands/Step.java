package userinterface.commands;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

import edu.kit.informatik.Terminal;
import logic.ErrorMessages;
import logic.LogicException;
import userinterface.InputException;
import userinterface.Strings;

/**
 * Class for the Step command.
 * 
 * @author Christopher Roth
 * @version 1.0
 * 
 *
 */
class Step extends Command {
    /**
     * Pattern.
     */
    private final Pattern stepPattern = Pattern
            .compile("^step " + "(" + Strings.DIGIT_POSITIVE_NEGATIVE.getMessage() + ")$");
    /**
     * Contains the argument.
     */
    private String argument;

    @Override
    public void execute() {

        try {
            tryParse(argument);
            Terminal.printLine(modelRailWay.step(Short.parseShort(argument)));
        } catch (LogicException l) {
            Terminal.printLine(l.getMessage());
        }

    }

    private void tryParse(String argument2) throws LogicException {
        try {
            Short.parseShort(argument2);
        } catch (NumberFormatException e) {
            throw new LogicException(ErrorMessages.NUMBER_TOO_BIG.getMessage());
        }

    }

    @Override
    void setArguments(String argument) throws InputException {
        Matcher matcher = stepPattern.matcher(argument);
        if (matcher.matches()) {
            this.argument = matcher.group(1);
        }

    }

    @Override
    public Pattern getPattern() {
        return this.stepPattern;
    }
}
