package userinterface;

/**
 * This Enum contains the most needed Strings for Regex-Patterns.
 * 
 * @author Christopher Roth
 * @version 1.0
 *
 */
public enum Strings {

    /**
     * String for a TrackPoint.
     */
    TRACKPOINT("[(]-?\\d+,-?\\d+[)]"),
    /**
     * String for an arrow.
     */
    ARROW(" -> "),
    /**
     * String for a comma.
     */
    COMMA(","),
    /**
     * String which accepts one of the three different engine types.
     */
    ENGINE_TYPE("diesel|steam|electrical"),
    /**
     * String which accepts a digit or letter.
     */
    DIGIT_OR_LETTER("[\\p{L}0-9]*"),
    /**
     * String which accepts a digit or letter, with a seperation in between.
     */
    DIGIT_OR_LETTER_SEPERATED("[\\p{L}0-9]*[-][\\p{L}0-9]*|[\\p{L}0-9]*"),
    /**
     * String for a positive digit
     */
    DIGIT_POSITIVE("[/+]?\\d+"),
    /**
     * String for true or false.
     */
    TRUE_FALSE("true|false"),
    /**
     * String which accepts one of the three different coach types.
     */
    COACH_TYPE("passenger|freight|special"),
    /**
     * String which accepts a positive or negative digit.
     */
    DIGIT_POSITIVE_NEGATIVE("[+-]?\\d+");

    /**
     * The String the enum-Object should contain.
     */
    private final String message;

    /**
     * Constructor which initiates the message-attribute.
     * 
     * @param message the message of the enum-Object
     */
    Strings(final String message) {
        this.message = message;
    }

    /**
     * Getter for the String in the enum-object
     * 
     * @return the message of the enum-object
     */
    public String getMessage() {
        return this.message;
    }

}
