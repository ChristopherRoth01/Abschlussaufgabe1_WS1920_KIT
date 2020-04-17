package logic;

/**
 * Enum that contains all ErrorMessages which can be thrown with
 * a @LogicException
 * 
 * @author Christopher Roth
 * @version 1.0
 *
 */
public enum ErrorMessages {
    /**
     * Error message, when a new Track should be placed, but is not connected to
     * another Track.
     */
    TRACK_NOT_CONNECTED("Error, The Track must be connected to another Track!"),
    /**
     * Error message which occurs when no matching command is found.
     */
    NOT_MATCHING("Error, there is no matching Command!"),
    /**
     * Error message when user trys to add a Track, that already exists.
     */
    TRACK_ALREADY_EXISTS("Error, the Track already exists."),
    /**
     * Error message when user trys to add a Locomotive with a Type that is not
     * allowed.
     */
    LOCTYPE_NOT_EXISTING("Error, the LocType you tried to add does not exist"),
    /**
     * Error message when user trys to add a Locomotive which contains a <W> in the
     * Buildseries.
     */
    BUILDSERIES_CONTAINS_W("Error, buildSeries contains unallowed Char <W>"),
    /**
     * Error message when user trys to add a Locomotive, of which the Id is already
     * existing.
     */
    LOCOMOTIVE_EXISTING("Error, TrainSet or Locomotive already existing!"),
    /**
     * Error message when user trys to add a Track with TrackPoints that have not
     * the same X or Y Coordinate.
     */
    POINTS_NOT_CORRECT("Error, the Track needs to be aligned either vertical or horizontal!"),
    /**
     * Error message when user trys to add a coach with a Type that is not allowed.
     */
    WAGONTYPE_NOT_EXISTING("Error, the WagonType you tried to add does not exist!"),
    /**
     * Error message when user trys to delete a Coach with a id, which does not
     * exist.
     */
    WAGON_NOT_FOUND("Error, the Coach you tried to delete does not exist!"),
    /**
     * Error message when user trys to delete rollmaterial which is already set in a
     * train.
     */
    ROLLMATERIAL_IS_IN_USE("Error, this rolling stock is in use!"),
    /**
     * Error message when a RollMaterial already exists.
     */
    ROLLING_STOCK_NOT_EXIST("Error, the rolling stock with this id does not exist or is in use!"),
    /**
     * Error message when a Stock is not compatible.
     */
    STOCK_NOT_COMPATIBLE("Error, this stock can not be composed!"),
    /**
     * Error message when a TrainSet is not compatible.
     */
    TRAINSET_NOT_COMPATIBLE("Error, the train set is not compatible with the train!"),
    /**
     * Error message if a train is not existing.
     */
    TRAIN_NOT_EXISTING("Error, this train is not existing!"),
    /**
     * Error message if a point is not existing.
     */
    POINT_NOT_EXISTING("Error, this point is not existing!"),
    /**
     * Error message if a Switch is unset.
     */
    SWITCH_IS_UNSET("Error, this switch needs to be set first!"),
    /**
     * Error messaage when another Train is already on a track.
     */
    TRAIN_ALREADY_STANDING("Error, already a train on this track!"),
    /**
     * Error message when directions are not the same.
     */
    DIRECTIONS_NOT_SAME("Error, the direction is not the same as the one of the Track."),
    /**
     * Error message if a train is not valid, when tried to be set.
     */
    TRAIN_NOT_VALID("Error, the train you tried to set is not valid!"),
    /**
     * Error message when a switch should be set on a not existing switch end.
     */
    INVALID_SWITCH_END("Error, this switch state is invalid!"),
    /**
     * Error message when a switch with a given id is not existing.
     */
    SWITCH_NOT_EXISTING("Error, this switch is not existing!"),
    /**
     * Error message when a track with a given id is not existing.
     */
    TRACK_NOT_EXISTING("Error, there is no Track!"),
    /**
     * Error message when user trys to put a train, but the track contains unset
     * switches.
     */
    UNSET_SWITCHES_IN_TRACK("Error, there are unset Switches in the Track!"),
    /**
     * Error message when a user trys to add a train and the id is not matching the
     * next free id.
     */
    NOT_NEXT_FREE_ID("Error, the id does not match the next free id!"),

    /**
     * Error message when the track cannot be deleted.
     */
    TRACK_CANNOT_DELETED("Error, the track cannot be deleted!"),
    /**
     * Error message when user trys to add track with identical points
     */
    POINTS_ARE_THE_SAME("Error, the points are identical!"),
    /**
     * Error message when user trys to add RollMaterial with both couplings set to
     * false.
     */
    AT_LEAST_ONE_COUPLING("Error, at least one coupling is needed!"),
    /**
     * Error message when input number is too big.
     */
    NUMBER_TOO_BIG("Error, Not a valid number!"),
    /**
     * Error message, when a point already has two attached tracks.
     */
    POINT_HAS_TWO_CONNECTED("Error, already two tracks connected!"),
    /**
     * Error message, when user trys to add RollMaterial with the length zero.
     */
    LENGTH_IS_ZERO("Error, the length can´t be 0!"),
    /**
     * Error message for the case a Train is longer than the trackNet.
     */
    TRAIN_LONGER_THAN_TRACK("Error, the train is longer than the TrackNet!"),
    /**
     * Error message when user trys to put a train that is already put.
     */
    TRAIN_ALREADY_PUT("Error, this train is alread put!"), 
    /**
     * Error message when user trys to put a train with an invalid Vector.
     */
    VECTOR_INVALID("Error, this vector is invalid!");

    /**
     * The errorMessage which a Enum-object contains.
     */

    private String errorMessage;

    /**
     * Constructor
     * 
     * @param errorMessage the errorMessage of this Enum-Object.
     */
    ErrorMessages(String errorMessage) {
        this.errorMessage = errorMessage;
    }

    /**
     * Getter for the message.
     * 
     * @return the errorMessage.
     */
    public String getMessage() {
        return this.errorMessage;
    }
}
