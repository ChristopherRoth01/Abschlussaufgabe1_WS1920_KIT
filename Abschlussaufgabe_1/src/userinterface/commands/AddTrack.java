package userinterface.commands;

import java.util.HashMap;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import edu.kit.informatik.Terminal;
import userinterface.InputException;

import userinterface.Strings;
import logic.ErrorMessages;
import logic.LogicException;

/**
 * Represents the addTrack Command.
 * 
 * @author Christopher Roth
 * @version 1.0
 */
class AddTrack extends Command {

    /**
     * The regex Pattern of the addTrack Command.
     */
    private final Pattern addTrackPattern = Pattern.compile("^add track (" + Strings.TRACKPOINT.getMessage() + ")"
            + Strings.ARROW.getMessage() + "(" + Strings.TRACKPOINT.getMessage() + ")$");
    /**
     * The Map which contains the arguments for the addTrack command. Which are two
     * trackPoints.
     */
    private Map<Integer, String> trackPoints = new HashMap<Integer, String>();

    /**
     * Getter for the addTrack Pattern
     * 
     * @return the addTrack Pattern
     */
    public Pattern getPattern() {
        return this.addTrackPattern;
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
            Terminal.printLine(modelRailWay.getTrackNet().getTrackNetworkActions().addNormalTrack(trackPoints.get(0),
                    trackPoints.get(1)));

        } catch (LogicException l) {
            Terminal.printLine(l.getMessage());
        }
    }

    @Override
    public void setArguments(String argument) throws InputException {
        Matcher matcher = addTrackPattern.matcher(argument);
        if (matcher.matches()) {
            trackPoints.put(0, matcher.group(1));
            trackPoints.put(1, matcher.group(2));
        }

    }

}
