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
 * Class for the SetSwitch Command.
 * 
 * @author Christopher Roth
 * @version 1.0
 *
 */
class SetSwitch extends Command {
    /**
     * Pattern.
     */
    private final Pattern setSwitchPattern = Pattern
            .compile("^set switch " + "(" + Strings.DIGIT_POSITIVE.getMessage().toString() + ")" + " position " + "("
                    + Strings.TRACKPOINT.getMessage().toString() + ")");
    /**
     * Contains all arguments.
     */
    private HashMap<Integer, String> arguments = new HashMap<Integer, String>();

    @Override
    public void execute() {
        try {
            tryParse();
            Terminal.printLine(modelRailWay.setSwitch(arguments.get(0), arguments.get(1)));
        } catch (LogicException l) {
            Terminal.printLine(l.getMessage().toString());
        }

    }

    private void tryParse() throws LogicException {
        try {
            Integer.parseInt(arguments.get(0));
            this.modelRailWay.getTrackNet().createTrackPoint(arguments.get(1).split(","));

        } catch (NumberFormatException n) {
            throw new LogicException(ErrorMessages.NUMBER_TOO_BIG.getMessage());
        }

    }

    @Override
    void setArguments(String argument) throws InputException {
        Matcher matcher = setSwitchPattern.matcher(argument);
        if (matcher.matches()) {
            arguments.put(0, matcher.group(1));
            arguments.put(1, matcher.group(2));

        }

    }

    @Override
    public Pattern getPattern() {
        return this.setSwitchPattern;
    }

}
