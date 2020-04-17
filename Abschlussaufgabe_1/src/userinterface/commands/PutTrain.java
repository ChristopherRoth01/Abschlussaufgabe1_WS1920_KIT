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
 * Class for the PutTrain Command.
 * 
 * @author Christopher Roth
 * @version 1.0
 */
class PutTrain extends Command {
    /**
     * Pattern.
     */
    private final Pattern putTrainPattern = Pattern
            .compile("^put train " + "(" + Strings.DIGIT_POSITIVE.getMessage().toString() + ")" + " at " + "("
                    + Strings.TRACKPOINT.getMessage().toString() + ")" + " in direction " + "("
                    + Strings.DIGIT_POSITIVE_NEGATIVE.getMessage().toString() + ")" + "," + "("
                    + Strings.DIGIT_POSITIVE_NEGATIVE.getMessage().toString() + ")$");
    /*
     * Contains all arguments.
     */
    private HashMap<Integer, String> arguments = new HashMap<Integer, String>();

    @Override
    public void execute() {
        try {
            tryParse();
            Terminal.printLine(modelRailWay.putTrain(arguments.get(0), arguments.get(1),
                    Integer.parseInt(arguments.get(2)), Integer.parseInt(arguments.get(3))));

        } catch (LogicException l) {
            Terminal.printLine(l.getMessage().toString());
        }
    }

    private void tryParse() throws LogicException {
        try {
            Integer.parseInt(arguments.get(0));
            this.modelRailWay.getTrackNet().createTrackPoint(arguments.get(1).split(","));
            if (Integer.parseInt(arguments.get(2)) == 1 && Integer.parseInt(arguments.get(3)) == 1) {
                throw new LogicException(ErrorMessages.VECTOR_INVALID.getMessage());
            }

        } catch (NumberFormatException n) {
            throw new LogicException(ErrorMessages.NUMBER_TOO_BIG.getMessage());
        }

    }

    @Override
    void setArguments(String argument) throws InputException {
        Matcher matcher = putTrainPattern.matcher(argument);
        if (matcher.matches()) {
            arguments.put(0, matcher.group(1));
            arguments.put(1, matcher.group(2));
            if (Integer.parseInt(matcher.group(3)) > 0) {
                arguments.put(2, "1");
            } else if (Integer.parseInt(matcher.group(3)) < 0) {
                arguments.put(2, "-1");
            } else {
                arguments.put(2, "0");
            }
            if (Integer.parseInt(matcher.group(4)) > 0) {
                arguments.put(3, "1");
            } else if (Integer.parseInt(matcher.group(4)) < 0) {
                arguments.put(3, "-1");
            } else {
                arguments.put(3, "0");
            }
        }

    }

    @Override
    public Pattern getPattern() {
        return this.putTrainPattern;
    }
}
