package logic;

/**
 * Interface for all RollMaterialTypes.
 * 
 * @author Christopher Roth
 * @version 1.0
 *
 */
interface RollMaterial {
    /**
     * Getter for the id of the RollMaterial
     * 
     * @return the id.
     */
    String getId();

    /**
     * Getter for the Train the RollMaterial is currently used in.
     * 
     * @return the train.
     */
    Train getTrain();

    /**
     * Getter for the Type of The RollMaterial.
     * 
     * @return the RollMaterialType
     */
    RollMaterialType getType();

    /**
     * Getter for the CouplingFront-Attribute.
     * 
     * @return the couplingFront
     */
    boolean getCouplingFront();

    /**
     * Getter for the CouplingBack-Attribute.
     * 
     * @return the couplingBack
     */
    boolean getCouplingBack();

    /**
     * Sets the train of a RollMaterial.
     * 
     * @param newTrain the train which should be set.
     */
    void setTrain(Train newTrain);

    /**
     * Getter for the BuildSeries.
     * 
     * @return the buildSeries.
     */
    String getBuildSeries();

    /**
     * Getter for the Length of the RollMaterial.
     * 
     * @return the length.
     */
    int getLength();

    /**
     * resets Id and adds it back to the pool of possible iDs.
     */
    void resetId();

}
