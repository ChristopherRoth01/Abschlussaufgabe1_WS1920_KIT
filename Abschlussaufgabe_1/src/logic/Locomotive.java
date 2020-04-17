package logic;

/**
 * This Class represents a Locomotive. This Class implements the
 * RollMaterial-Interface, aswell as the Comparable Interface so locs can be
 * sorted.
 * 
 * @author Christopher Roth
 * @version 1.0
 *
 */
public class Locomotive implements RollMaterial, Comparable<Locomotive> {
    /**
     * The string representation for a electrical engine.
     */
    private static String[] representationElectrical = {
        "  (O)(O)      (O)(O)  ", 
        " \\__________________/ ",
        "\\                    /", 
        "/   |____________|   \\", 
        " /_| ____________ |_\\ ", 
        "  _______________/__  ",
        "                 \\    ", 
        "               ___    ", };
    /**
     * The string representation for a diesel engine.
     */
    private static String[] representationDiesel = {
        "  (O)(O)      (O)(O)  ", 
        " \\__________________/ ",
        "\\                    /", 
        "/   |____________|   \\", 
        " /_| ____________ |_\\ ", 
        "  _____________|____  " };
    /**
     * The string representation for a steam engine.
     */
    private static String[] representationSteam = {
        "//// \\_/      \\_/   ", 
        " _|--/~\\------/~\\-+ ",
        "  + ========  +-+ | ", 
        "   /---------|| | | ", 
        "     ||      |+-+ | ", 
        "     ++      +------" };
    /**
     * The length of a locomotive.
     */
    private final int length;
    /**
     * The train the Locomotive is attached to.
     */
    private Train train;
    /**
     * The RollMaterialType of the Locomotive.
     */
    private RollMaterialType locType;
    /**
     * The BuildSeries of a Locomotive.
     */
    private String buildSeries;
    /**
     * The iD of a Locomotive, which is composed out of the buildSeries and the
     * name. <buildSeries>-<name>
     */
    private String id;
    /**
     * A boolean which determines if a TrainSet has a coupling in the Front.
     */
    private final boolean couplingFrontBool;
    /**
     * A boolean which determines if a TrainSet has a coupling in the Back.
     */
    private final boolean couplingBackBool;
    /**
     * The name of a Locomotive.
     */
    private String name;

    /**
     * Constructor.
     * 
     * @param locType           The RollMaterialType of the Locomotive.
     * @param buildSeries       The BuildSeries of a Locomotive.
     * @param length            The length of a locomotive.
     * @param name              The name of a Locomotive.
     * @param couplingFrontBool determines if a TrainSet has a coupling in the
     *                          Front.
     * @param couplingBackBool  determines if a TrainSet has a coupling in the Back.
     * @throws LogicException if buildSeries contains a W, which should not be
     *                        allowed. Anyway this error should never be thrown, its
     *                        just there for finding bugs.
     */
    Locomotive(String locType, String buildSeries, String length, String name, String couplingFrontBool,
            String couplingBackBool) throws LogicException {
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
        this.locType = this.convertLocType(locType);
        if (Integer.parseInt(length) != 0) {
            this.length = Integer.parseInt(length);
        } else {
            throw new LogicException(ErrorMessages.LENGTH_IS_ZERO.getMessage());
        }
        
        this.train = null;

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
     * Converts a String into the fitting RollMaterialType.
     * 
     * @param locType the String that should be converted.
     * @return the fitting RollMaterialType.
     * @throws LogicException if the types are not fitting.
     */
    private RollMaterialType convertLocType(String locType) throws LogicException {
        if (locType.equals("diesel")) {
            return RollMaterialType.DIESEL;
        } else if (locType.equals("steam")) {
            return RollMaterialType.STEAM;
        } else if (locType.equals("electrical")) {
            return RollMaterialType.ELECTRICAL;
        } else {
            throw new LogicException(ErrorMessages.LOCTYPE_NOT_EXISTING.getMessage().toString());
        }
    }

    @Override
    public String getId() {
        return this.id;
    }

    @Override
    public String toString() {
        if (this.train == null) {
            return "none " + this.locType.getShortType().toString() + " " + this.buildSeries + " " + this.name + " "
                    + this.length + " " + this.couplingFrontBool + " " + this.couplingBackBool;
        } else {
            return this.train.getId() + " " + this.locType.getShortType().toString() + " " + this.buildSeries + " "
                    + this.name + " " + this.length + " " + this.couplingFrontBool + " " + this.couplingBackBool;
        }
    }

    /**
     * Getter for the representation of a Steam engine.
     * 
     * @return representationSteam
     */
    public static String[] getRepresentationSteam() {
        return representationSteam;
    }

    @Override
    public Train getTrain() {
        return this.train;
    }

    @Override
    public RollMaterialType getType() {
        return this.locType;
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

    /**
     * Getter for the representation of a diesel engine.
     * 
     * @return representationDiesel
     */
    public static String[] getRepresentationDiesel() {
        return representationDiesel;
    }

    /**
     * Getter for the representation of a electrical engine.
     * 
     * @return representationElectrical
     */
    public static String[] getRepresentationElectrical() {
        return representationElectrical;
    }

    @Override
    public String getBuildSeries() {
        return this.buildSeries;
    }

    /**
     * Getter for the length of a Locomotive.
     * @return the length of the Locomotive.
     */
    public int getLength() {
        return length;
    }

    @Override
    public int compareTo(Locomotive o) {
        return this.getId().compareTo(o.getId());
    }

    @Override
    public void resetId() {
        // does nothing because iDs are distributed individually.

    }
}
