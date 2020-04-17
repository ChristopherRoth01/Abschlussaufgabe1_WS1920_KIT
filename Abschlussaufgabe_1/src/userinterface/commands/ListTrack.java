package userinterface.commands;

import java.util.regex.Pattern;

import edu.kit.informatik.Terminal;

/**
 * Class for the ListTrack Command.
 * 
 * @author Christopher Roth.
 * @version 1.0
 */
class ListTrack extends Command {
    /**
     * Pattern.
     */
    private final Pattern listTrackPattern = Pattern.compile("^list tracks$");

    @Override
    public void execute() {

        if (!modelRailWay.getTrackNet().isEmpty()) {
            Terminal.printLine(modelRailWay.getTrackNet().trackListToString());
        } else {
            Terminal.printLine("No track exists");
        }

    }

    @Override
    public Pattern getPattern() {
        return this.listTrackPattern;
    }
}
