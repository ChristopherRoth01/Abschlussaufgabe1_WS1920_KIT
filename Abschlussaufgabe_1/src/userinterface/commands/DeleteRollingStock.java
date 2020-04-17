package userinterface.commands;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

import edu.kit.informatik.Terminal;
import logic.ErrorMessages;
import logic.LogicException;
import userinterface.InputException;
import userinterface.Strings;

/**
 * Class for the DeleteRollingStock Command.
 * 
 * @author Christopher Roth
 * @version 1.0
 */
class DeleteRollingStock extends Command {
    /**
     * Pattern.
     */
    private final Pattern deleteRollingStockPattern = Pattern
            .compile("^delete rolling stock " + "(" + Strings.DIGIT_OR_LETTER_SEPERATED.getMessage().toString() + ")$");
    /**
     * Contains the argument.
     */
    private String argument;

    @Override
    public void execute() {
        try {
            tryParse();
            Terminal.printLine(
                    modelRailWay.getRollMaterialComposition().getRollMaterialStock().deleteRollingStock(argument));
        } catch (LogicException l) {
            Terminal.printLine(l.getMessage().toString());
        }

    }

    private void tryParse() throws LogicException {
        if (argument.contains("W")) {

            try {
                Integer.parseInt(argument.replace("W", ""));
            } catch (NumberFormatException n) {
                throw new LogicException(ErrorMessages.NUMBER_TOO_BIG.getMessage());
            }
        }
    }

    @Override
    void setArguments(String input) throws InputException {
        Matcher matcher = deleteRollingStockPattern.matcher(input);
        if (matcher.matches()) {
            this.argument = matcher.group(1);
        }

    }

    @Override
    public Pattern getPattern() {
        return this.deleteRollingStockPattern;
    }

}
