package logic;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

/**
 * Class which represents a Coach.
 * 
 * @author Christopher Roth
 * @version 1.0
 *
 */
class Coach implements RollMaterial, Comparable<Coach> { 
    /**
     * String Representation of a Passenger Coach.
     */
    private static final String[] PASSENGER_COACH = {
        "   (O)        (O)   ", 
        "|__________________|",
        "|__________________|",
        "|  |_| |_| |_| |_| |", 
        "|  ___ ___ ___ ___ |", 
        "____________________" };
    /**
     * String Representation of a Freight Coach.
     */
    private static final String[] FREIGTH_COACH = {
        "   (O)        (O)   ",
        "|__________________|",
        "|                  |", 
        "|                  |", 
        "|                  |" };
    /**
     * String Representation of a Special Coach.
     */
    private static final String[] SPECIAL_COACH = {
        "   (O)       (O)   ", 
        "|_________________|", 
        " _|_|__________|  |",
        "  | |          |  |", 
        "\\--------------|  |",
        "/--------------|  |", 
        "               ____" };
    /**
     * A static counter which counts one up everytime a Coach is created.
     */
    private static int counter = 0;
    /**
     * List which contains all the possible iDs for a Coach.
     */
    private static List<Integer> iDs = new ArrayList<Integer>();
    /**
     * The iD of a Coach which is set by the returnNextFreeId() method.
     */
    private final int coachId;
    /**
     * The train the Coach is atteched to.
     */
    private Train train;
    /**
     * The type of the coach.
     */
    private RollMaterialType wagonType;
    /**
     * The length of a coach which is set when a Coach is constructed.
     */
    private final int length;

    /**
     * A boolean which determines if a Coach has a coupling in the Front.
     */
    private final boolean couplingFrontBool;
    /**
     * A boolean which determines if a Coach has a coupling in the Back.
     */
    private final boolean couplingBackBool;

    /**
     * Constructor.
     * 
     * @param wagonType         can be either passenger|freight|special
     * @param length            the length with which the coach should be
     *                          constructed.
     * @param couplingFrontBool determines if a Coach has a coupling in the Front.
     * @param couplingBackBool  determines if a Coach has a coupling in the Back.
     * @throws LogicException thrown if any of the parameters is not fitting.
     */
    Coach(String wagonType, String length, String couplingFrontBool, String couplingBackBool)
            throws LogicException {
        if (this.convertToBoolean(couplingBackBool) == false && this.convertToBoolean(couplingFrontBool) == false) {
            throw new LogicException(ErrorMessages.AT_LEAST_ONE_COUPLING.getMessage());
        }
        this.couplingBackBool = this.convertToBoolean(couplingBackBool);
        this.couplingFrontBool = this.convertToBoolean(couplingFrontBool);

        if (Integer.parseInt(length) != 0) {
            this.length = Integer.parseInt(length);
        } else {
            throw new LogicException(ErrorMessages.LENGTH_IS_ZERO.getMessage());
        }
        this.wagonType = this.convertToWagonType(wagonType);
        this.coachId = this.returnNextFreeId();
        this.train = null;
    }

    /**
     * Returns the next free id.
     * 
     * @return the next free id.
     */
    private int returnNextFreeId() {
        if (iDs.isEmpty()) {
            counter++;
            iDs.add(counter);

            return iDs.remove(0);
        } else {
            Collections.sort(iDs);
            Collections.reverse(iDs);
            return iDs.remove(iDs.size() - 1);
        }
    }

    /**
     * Getter for the coachId.
     * 
     * @return the coachId.
     */
    public int getCoachId() {
        return coachId;
    }

    /**
     * Getter for the String representation of a freight coach.
     * 
     * @return the String representation
     */
    static String[] getFreightCoach() {
        return FREIGTH_COACH;
    }

    /**
     * Getter for the String representation of a special coach.
     * 
     * @return the String representation
     */
    static String[] getSpecialCoach() {
        return SPECIAL_COACH;
    }

    /**
     * Getter for the String representation of a passenger coach.
     * 
     * @return the String representation
     */
    static String[] getPassengerCoach() {
        return PASSENGER_COACH;
    }

    /**
     * Getter for the length of a Coach.
     * @return the length of the Coach
     */
    public int getLength() {
        return this.length;
    }

    /**
     * Converts a String input into the fitting boolean.
     * 
     * @param input the String which should be converted.
     * @return true when input is "true", false everytime else.
     */
    private boolean convertToBoolean(String input) {
        if (input.equals("true")) {
            return true;

        } else {
            return false;
        }
    }

    /**
     * Converts a String to the fitting WagonType
     * 
     * @param locType the String which should be converted.
     * @return the fitting WagonType.
     * @throws LogicException if the CoachType is not Existing.
     */
    private RollMaterialType convertToWagonType(String coachType) throws LogicException {
        if (coachType.equals("passenger")) {
            return RollMaterialType.PassengerCoach;
        } else if (coachType.equals("freight")) {
            return RollMaterialType.FreightCoach;
        } else if (coachType.equals("special")) {
            return RollMaterialType.SpecialCoach;
        } else {
            throw new LogicException(ErrorMessages.WAGONTYPE_NOT_EXISTING.getMessage().toString());
        }
    }

    /**
     * Getter for the id of a coach as String.
     * @return the id of the coach as String.
     */
    public String getId() {
        return "" + this.getCoachId();
    }

    /**
     * Getter for the current Train the Coach is attached to.
     * @return the train the Coach is composed to.
     */
    public Train getTrain() {
        return this.train;
    }

    @Override
    public String toString() {
        if (this.train == null) {
            return this.coachId + " none " + this.wagonType.getShortType().toString() + " " + this.length + " "
                    + this.couplingFrontBool + " " + this.couplingBackBool;
        } else {
            return this.coachId + " " + this.train.getId() + " " + this.wagonType.getShortType().toString() + " "
                    + this.length + " " + this.couplingFrontBool + " " + this.couplingBackBool;
        }
    }

    @Override
    public RollMaterialType getType() {
        return this.wagonType;
    }

    @Override
    public boolean getCouplingFront() {
        return this.couplingFrontBool;
    }

    @Override
    public boolean getCouplingBack() {
        return this.couplingBackBool;
    }

    @Override
    public void setTrain(Train train) {
        this.train = train;

    }

    @Override
    public String getBuildSeries() {

        // does nothing, is just imported so Locomotives and TrainSets can use the
        // method.
        return null;
    }

    @Override
    public void resetId() {
        iDs.add(Integer.parseInt(this.getId()));

    }

    @Override
    public int compareTo(Coach o) {
        return this.getCoachId() - o.getCoachId();
    }
}
