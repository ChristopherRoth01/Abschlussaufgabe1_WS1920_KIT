package logic;

/**
 * Enum for the different RollMaterialTypes. Every Object contains a short
 * representation, a long representation and the fitting String Representation
 * for the ShowTrain Command.
 * 
 * @author Christopher Roth
 * @version 1.0
 */
enum RollMaterialType {
    /**
     * Represents a train-set.
     */
    TRAIN_SET("train-set", "train-set", TrainSet.getRepresentation()),
    /**
     * Represents a Steam-Engine.
     */
    STEAM("s", "steam engine", Locomotive.getRepresentationSteam()),
    /**
     * Represents a Diesel-Engine.
     */
    DIESEL("d", "diesel engine", Locomotive.getRepresentationDiesel()),
    /**
     * Represents a Electrical-Engine.
     */
    ELECTRICAL("e", "electrical engine", Locomotive.getRepresentationElectrical()),
    /**
     * Represents a PassengerCar.
     */
    PassengerCoach("p", "passenger coach", Coach.getPassengerCoach()),
    /**
     * Represens a FreightCar.
     */
    FreightCoach("f", "freight coach", Coach.getFreightCoach()),
    /**
     * Represents a SpecialCar.
     */
    SpecialCoach("s", "special coach", Coach.getSpecialCoach());

    /**
     * The Long Type
     */
    private final String type;
    /**
     * The short Type.
     */
    private final String shortType;
    /**
     * Representation for the ShowTrain Command.
     */
    private final String[] representation;

    /**
     * Constructor.
     * 
     * @param shortType      the short String representation.
     * @param type           the long String representation.
     * @param representation the String representation for the ShowTrain Command.
     */
    private RollMaterialType(String shortType, String type, String[] representation) {
        this.type = type;
        this.shortType = shortType;
        this.representation = representation;
    }

    /**
     * Getter for the ArrayRepresentation.
     * 
     * @return the Array-representation.
     */
    public String[] getRepresentation() {
        return this.representation;
    }

    /**
     * Getter for the Short Type.
     * 
     * @return the short-type.
     */
    public String getShortType() {
        return this.shortType;
    }

    /**
     * Getter for the Long Type.
     * 
     * @return the long-type.
     */
    public String getType() {
        return this.type;
    }

}
