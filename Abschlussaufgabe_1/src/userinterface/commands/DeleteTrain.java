package userinterface.commands;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

import edu.kit.informatik.Terminal;
import logic.ErrorMessages;
import logic.LogicException;
import userinterface.InputException;
import userinterface.Strings;

/**
 * Class for the DeleteTrain Command.
 * 
 * @author Christopher Roth
 * @version 1.0
 */
class DeleteTrain extends Command {
    /**
     * Pattern.
     */
    private final Pattern deleteTrainPattern = Pattern
            .compile("^delete train " + "(" + Strings.DIGIT_POSITIVE.getMessage().toString() + ")$");
    /**
     * Contains the argument.
     */
    private String argument;

    @Override
    public void execute() {
        try {
            tryParse();
            Terminal.printLine(modelRailWay.deleteTrainFromTrack(argument));
        } catch (LogicException l) {
            Terminal.printLine(l.getMessage().toString());
        }
    }

    private void tryParse() throws LogicException {
        try {
            Integer.parseInt(argument);
        } catch (NumberFormatException n) {
            throw new LogicException(ErrorMessages.NUMBER_TOO_BIG.getMessage());
        }

    }

    @Override
    void setArguments(String argument) throws InputException {
        Matcher matcher = deleteTrainPattern.matcher(argument);
        if (matcher.matches()) {
            this.argument = matcher.group(1);
        }

    }

    @Override
    public Pattern getPattern() {
        return this.deleteTrainPattern;
    }

}
