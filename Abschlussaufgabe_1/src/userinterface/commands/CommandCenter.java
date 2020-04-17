package userinterface.commands;

import java.util.ArrayList;
import java.util.List;
import logic.ErrorMessages;
import userinterface.InputException;
import userinterface.Session;

/**
 * Works as the Invoker Class of the Java Command Pattern. The CommandCenter
 * handles all Commands. Matches input Strings to the fitting Patterns. Then
 * creates a new Instance of the fitting Command class and executes it.
 * 
 * @author Christopher Roth
 * @version 1.0
 */
public class CommandCenter {

    private final List<Command> commandPackage = new ArrayList<Command>();

    private final Session session;

    /**
     * Constructor.
     * 
     * @param session the Session to be initiated.
     */
    public CommandCenter(final Session session) {
        commandPackage.add(new AddSwitch());
        commandPackage.add(new AddTrack());
        commandPackage.add(new AddTrain());
        commandPackage.add(new CreateCoach());
        commandPackage.add(new CreateEngine());
        commandPackage.add(new CreateTrainSet());
        commandPackage.add(new DeleteRollingStock());
        commandPackage.add(new DeleteTrack());
        commandPackage.add(new DeleteTrain());
        commandPackage.add(new Exit());
        commandPackage.add(new ListCoaches());
        commandPackage.add(new ListEngines());
        commandPackage.add(new ListTrack());
        commandPackage.add(new ListTrains());
        commandPackage.add(new ListTrainSets());
        commandPackage.add(new PutTrain());
        commandPackage.add(new SetSwitch());
        commandPackage.add(new ShowTrain());
        commandPackage.add(new Step());

        this.session = session;
    }

    /**
     * Method that prepares the command to be executed.
     * 
     * @param input the String input to be matched against a Pattern.
     * @return the fitting Command Class.
     * @throws InputException if the String does not match.
     */
    public Command getCommand(final String input) throws InputException {

        Command matchingCommand = commandPackage.stream().filter(s -> s.getPattern().matcher(input).matches())
                .findFirst().orElse(null);
        if (matchingCommand == null) {
            throw new InputException(ErrorMessages.NOT_MATCHING.getMessage().toString());
        }

        matchingCommand.setArguments(input);
        matchingCommand.setSession(session);

        return matchingCommand;
    }
}
