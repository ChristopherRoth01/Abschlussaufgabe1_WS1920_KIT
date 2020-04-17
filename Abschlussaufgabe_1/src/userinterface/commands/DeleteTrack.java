package userinterface.commands;

import java.util.regex.Matcher;

import java.util.regex.Pattern;

import edu.kit.informatik.Terminal;
import logic.ErrorMessages;
import logic.LogicException;
import userinterface.InputException;
import userinterface.Strings;

/**
 * Class for the Delete Track Command.
 * 
 * @author Christopher Roth
 * @version 1.0
 *
 */
class DeleteTrack extends Command {
    /**
     * Pattern.
     */
    private final Pattern deleteTrackPattern = Pattern
            .compile("^delete track (" + Strings.DIGIT_POSITIVE.getMessage() + ")$");
    /**
     * The argument for the Command.
     */
    private String argument;

    @Override
    public void execute() {
        try {
            tryParse();
            Terminal.printLine(this.modelRailWay.deleteTrack(Integer.parseInt(argument)));
        } catch (LogicException l) {
            Terminal.printLine(l.getMessage());
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
        Matcher matcher = deleteTrackPattern.matcher(argument);
        if (matcher.matches()) {
            this.argument = matcher.group(1);
        }

    }

    @Override
    public Pattern getPattern() {
        return this.deleteTrackPattern;
    }

}
