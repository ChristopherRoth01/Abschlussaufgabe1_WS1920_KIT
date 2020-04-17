package logic;

/**
 * Class of a TrainSet. This Class implements the RollMaterial-Interface, aswell
 * as the Comparable Interface so trainSets can be sorted.
 * 
 * @author Christopher Roth
 * @version 1.0
 */
class TrainSet implements RollMaterial, Comparable<TrainSet> {
    /**
     * The String Representation of a TrainSet which is used in the Show Train
     * Command.
     */
    private static final String[] REPRESENTATION = {
        "   (O)        (O)   ", 
        "|__________________|",
        "|__________________|", 
        "|  |_| |_| |_| |_| |", 
        "|  ___ ___ ___ ___ |", 
        "_________||_________",
        "         ||         ", 
        "         ++         " };
    /**
     * The length of a trainSet which is initiated when a trainSet is constructed.
     */
    private final int length;
    /**
     * A boolean which determines if a TrainSet has a coupling in the Front.
     */
    private final boolean couplingFrontBool;
    /**
     * A boolean which determines if a TrainSet has a coupling in the Back.
     */
    private final boolean couplingBackBool;
    /**
     * The buildSeries of a TrainSet.
     */
    private final String buildSeries;
    /**
     * The iD of a TrainSet, which is composed out of the buildSeries and the name.
     * <buildSeries>-<name>
     */
    private final String id;
    /**
     * The name of a TrainSet.
     */
    private final String name;
    /**
     * The RollMaterialType of a TrainSet.
     */
    private final RollMaterialType type = RollMaterialType.TRAIN_SET;
    /**
     * The train the TrainSet could be attached to.
     */
    private Train train;
    /**
     * Constructor.
     * @param buildSeries the buildSeries of the trainSet
     * @param length the length of the TrainSet
     * @param name the name of the TrainSet
     * @param couplingFrontBool determines if TrainSet has a coupling in the front
     * @param couplingBackBool determines if TrainSet has a coupling in the back
     * @throws LogicException if user trys to add a TrainSet with a "W" in the buildSeries.
     */
    
    TrainSet(String buildSeries, String length, String name, String couplingFrontBool, String couplingBackBool)
            throws LogicException {
        this.name = name;
        if (this.convertToBoolean(couplingBackBool) == false && this.convertToBoolean(couplingFrontBool) == false) {
            throw new LogicException(ErrorMessages.AT_LEAST_ONE_COUPLING.getMessage());
        }
        if (!buildSeries.equals("W")) {
            this.buildSeries = buildSeries;
            this.id = buildSeries + "-" + name;
        } else {
            throw new LogicException(ErrorMessages.BUILDSERIES_CONTAINS_W.getMessage().toString());
        }

        this.couplingFrontBool = this.convertToBoolean(couplingFrontBool);
        this.couplingBackBool = this.convertToBoolean(couplingBackBool);

        if (Integer.parseInt(length) != 0) {
            this.length = Integer.parseInt(length);
        } else {
            throw new LogicException(ErrorMessages.LENGTH_IS_ZERO.getMessage());
        }
        this.train = null;

    }

    /**
     * Getter for the String representation.
     * 
     * @return the representation
     */
    static String[] getRepresentation() {
        return REPRESENTATION;
    }

    /**
     * Converts a String input into the fitting boolean.
     * 
     * @param input String which should be converted.
     * @return true when input String is true, false everytime else.
     */
    private boolean convertToBoolean(String input) {
        if (input.equals("true")) {
            return true;

        } else {
            return false;
        }
    }

    /**
     * Getter for the length.
     * 
     * @return the length of a TrainSet.
     * 
     */
    public int getLength() {
        return length;
    }

    @Override
    public String getId() {
        return this.id;
    }

    @Override
    public Train getTrain() {
        return this.train;
    }

    @Override
    public String toString() {
        if (this.train == null) {
            return "none " + this.buildSeries + " " + this.name + " " + this.length + " " + this.couplingFrontBool + " "
                    + this.couplingBackBool;
        } else {
            return this.train.getId() + " " + this.buildSeries + " " + this.name + " " + this.length + " "
                    + this.couplingFrontBool + " " + this.couplingBackBool;
        }
    }

    @Override
    public RollMaterialType getType() {
        return this.type;
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
        return this.buildSeries;
    }

    @Override
    public int compareTo(TrainSet o) {
        return this.getId().compareTo(o.getId());
    }

    @Override
    public void resetId() {
        // does nothing because iDs are distributed individually.

    }

}
