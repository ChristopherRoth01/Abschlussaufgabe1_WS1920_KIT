package userinterface.commands;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

import edu.kit.informatik.Terminal;
import logic.ErrorMessages;
import logic.LogicException;
import userinterface.InputException;
import userinterface.Strings;

/**
 * Class for the ShowTrain Command.
 * 
 * @author Christopher Roth
 * @version 1.0
 *
 */
class ShowTrain extends Command {
    /**
     * Pattern.
     */
    private final Pattern showTrainPattern = Pattern
            .compile("^show train " + "(" + Strings.DIGIT_POSITIVE.getMessage().toString() + ")$");
    /**
     * Contains the argument.
     */
    private String argument;

    @Override
    public void execute() {

        try {
            tryParse();
            if (modelRailWay.getRollMaterialComposition().trainWithIdExists(Integer.parseInt(argument)) != null) {
                String[] train = modelRailWay.getRollMaterialComposition().trainWithIdExists(Integer.parseInt(argument))
                        .returnTrainToString();
                for (int i = train.length - 1; i >= 0; i--) {
                    Terminal.printLine(train[i]);
                }
            } else {
                Terminal.printError("This train is not existing!");
            }

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
        Matcher matcher = showTrainPattern.matcher(argument);
        if (matcher.matches()) {
            this.argument = matcher.group(1);
        }

    }

    @Override
    public Pattern getPattern() {
        return this.showTrainPattern;
    }

}
