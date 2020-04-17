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
 * Class for the CreateCoach Command.
 * 
 * @author Christopher Roth
 * @version 1.0
 */
class CreateCoach extends Command {
    /**
     * Pattern.
     */
    private final Pattern createCoachPattern = Pattern
            .compile("^create coach " + "(" + Strings.COACH_TYPE.getMessage().toString() + ")" + " " + "("
                    + Strings.DIGIT_POSITIVE.getMessage().toString() + ")" + " " + "("
                    + Strings.TRUE_FALSE.getMessage().toString() + ")" + " " + "("
                    + Strings.TRUE_FALSE.getMessage().toString() + ")");
    /**
     * Contains all arguments needed.
     */
    private HashMap<Integer, String> arguments = new HashMap<Integer, String>();

    @Override
    public void execute() {
        try {
            tryParse();
            Terminal.printLine(modelRailWay.getRollMaterialComposition().getRollMaterialStock()
                    .addCoachToCollection(arguments.get(0), arguments.get(1), arguments.get(2), arguments.get(3)));
        } catch (LogicException l) {
            Terminal.printLine(l.getMessage().toString());
        }
    }

    private void tryParse() throws LogicException {
        try {
            Integer.parseInt(arguments.get(1));
        } catch (NumberFormatException n) {
            throw new LogicException(ErrorMessages.NUMBER_TOO_BIG.getMessage());
        }

    }

    @Override
    void setArguments(String argument) throws InputException {
        Matcher matcher = createCoachPattern.matcher(argument);
        if (matcher.matches()) {
            arguments.put(0, matcher.group(1));
            arguments.put(1, matcher.group(2));
            arguments.put(2, matcher.group(3));
            arguments.put(3, matcher.group(4));
        }
    }

    @Override
    public Pattern getPattern() {
        return this.createCoachPattern;
    }

}
