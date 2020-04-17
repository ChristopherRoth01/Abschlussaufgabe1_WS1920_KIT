package userinterface.commands;

import java.util.HashMap;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import edu.kit.informatik.Terminal;
import logic.ErrorMessages;
import logic.LogicException;

import userinterface.InputException;
import userinterface.Strings;

/**
 * Represents the add switch command.
 * 
 * @author Christopher Roth
 * @version 1.0
 */
class AddSwitch extends Command {

    /**
     * Regex Pattern for the addSwitch Command.
     */
    private final Pattern addSwitchPattern = Pattern.compile("^add switch (" + Strings.TRACKPOINT.getMessage() + ")"
            + Strings.ARROW.getMessage() + "(" + Strings.TRACKPOINT.getMessage() + ")" + Strings.COMMA.getMessage()
            + "(" + Strings.TRACKPOINT.getMessage() + ")$");
    /**
     * The Map which contains the arguments for the addSwitch command. Which are
     * three TrackPoints.
     */
    private Map<Integer, String> trackPoints = new HashMap<Integer, String>();

    /**
     * Getter for the addSwitch Pattern.
     * 
     * @return the Pattern of the command.
     */
    public Pattern getPattern() {
        return this.addSwitchPattern;
    }

    private void tryParse() throws LogicException {
        try {
            for (int i = 0; i < trackPoints.size(); i++) {
                this.modelRailWay.getTrackNet().createTrackPoint(trackPoints.get(i).split(","));
            }
        } catch (NumberFormatException e) {
            throw new LogicException(ErrorMessages.NUMBER_TOO_BIG.getMessage());

        }

    }

    @Override
    public void execute() {
        try {
            tryParse();
            Terminal.printLine(modelRailWay.getTrackNet().getTrackNetworkActions().addSwitch(trackPoints.get(0),
                    trackPoints.get(1), trackPoints.get(2)));

        } catch (LogicException l) {
            Terminal.printLine(l.getMessage().toString());

        }

    }

    @Override
    void setArguments(String argument) throws InputException {
        Matcher matcher = addSwitchPattern.matcher(argument);
        if (matcher.matches()) {
            trackPoints.put(0, matcher.group(1));
            trackPoints.put(1, matcher.group(2));
            trackPoints.put(2, matcher.group(3));
        }
    }

}
