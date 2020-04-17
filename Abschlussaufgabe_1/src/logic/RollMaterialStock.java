package logic;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

/**
 * The Class which represents a RollMaterialStock. This Stock contains all Parts
 * which a train can contain.
 * 
 * @author Christopher Roth
 * @version 1.0
 */
public class RollMaterialStock {
    /**
     * This List contains all Types of rollMaterial that can be added to a Train.
     * Elements are removed if the RollMaterial is deleted, or is used in a Train.
     */
    private List<RollMaterial> rollMaterialCollection;
    /**
     * This List contains all Locomotives which are added to the Stock. It doesn´t
     * matter if the RollMaterial is used in a Train or not.
     */
    private List<Locomotive> locCollection;
    /**
     * This List contains all Coaches which are added to the Stock. It doesn´t
     * matter if the RollMaterial is used in a Train or not.
     */
    private List<Coach> coachCollection;
    /**
     * This List contains all Coaches which are added to the Stock. It doesn´t
     * matter if the RollMaterial is used in a Train or not.
     */
    private List<TrainSet> trainSetCollection;

    /**
     * Constrcutor for a Stock. Initiates all different Lists.
     */
    public RollMaterialStock() {
        rollMaterialCollection = new ArrayList<RollMaterial>();
        locCollection = new ArrayList<Locomotive>();
        coachCollection = new ArrayList<Coach>();
        trainSetCollection = new ArrayList<TrainSet>();
    }

    /**
     * Deletes a rolling Stock with the fitting Id. A rolling Stock is only deleted
     * if it´s not used in a Train.
     * 
     * @param id the id of the RollMaterial that should be deleted.
     * @return "OK" if everything went alright
     * @throws LogicException if the RollMaterial is used in a train.
     */
    public String deleteRollingStock(String id) throws LogicException {
        RollMaterial coach = this.findRollMaterial(id);
        this.checkIfRollMaterialisInTrain(coach);
        coach.resetId();
        coachCollection.remove(coach);
        trainSetCollection.remove(coach);
        locCollection.remove(coach);
        rollMaterialCollection.remove(coach);
        return "OK";
    }

    /**
     * Checks if a RollMaterial is already in use in a train.
     * 
     * @param rollMaterial the RollMaterial that should be deleted.
     * @return false if RollMaterial is not in use.
     * @throws LogicException if the RollMaterial is in use.
     */
    boolean checkIfRollMaterialisInTrain(RollMaterial rollMaterial) throws LogicException {
        if (rollMaterial.getTrain() != null) {
            throw new LogicException(ErrorMessages.ROLLMATERIAL_IS_IN_USE.getMessage().toString());
        }
        return false;

    }

    /**
     * Finds a RollMaterial with the given id. Therefore the method searches in all
     * Lists.
     * 
     * @param id the id of the RollMaterial that should be found.
     * @return the RollMaterial with the given id.
     * @throws LogicException if no fitting RollMaterial exists.
     */
    RollMaterial findRollMaterial(String id) throws LogicException {

        if ((!id.contains("-")) && id.contains("W")) {
            for (RollMaterial coach : coachCollection) {
                if (coach.getId().equals(id.replace("W", ""))) {

                    return coach;
                }
            }
            throw new LogicException(ErrorMessages.WAGON_NOT_FOUND.getMessage().toString());

        } else {
            for (RollMaterial trainSet : trainSetCollection) {
                if (trainSet.getId().equals(id)) {

                    return trainSet;
                }
            }
            for (RollMaterial loc : locCollection) {
                if (loc.getId().equals(id)) {

                    return loc;
                }
            }
        }
        throw new LogicException(ErrorMessages.ROLLING_STOCK_NOT_EXIST.getMessage().toString());
    }

    /**
     * Adds Coaches to the rollMaterialCollection and the coachCollection. Coaches
     * are then ready to be used in a train.
     * 
     * @param coachType     can be either passenger|freight|special
     * @param length        the length of the coach
     * @param couplingFront determines if coach has coupling in the front
     * @param couplingBack  determines if coach has coupling in the back
     * @return the id of the newCoach as String
     * @throws LogicException if the coachType is not fitting.
     */
    public String addCoachToCollection(String coachType, String length, String couplingFront, String couplingBack)
            throws LogicException {

        RollMaterial newCoach = new Coach(coachType, length, couplingFront, couplingBack);
        rollMaterialCollection.add(newCoach);
        coachCollection.add((Coach) newCoach);
        return newCoach.getId();
    }

    /**
     * Adds Locomotives to the rollMaterialCollection and the locCollection. 
     * Locomotives are then ready to be used in a train.
     * @param locType the type of the Locomotive
     * @param buildSeries the buildSeries of the Loc
     * @param name the name of the Loc
     * @param length the length of the Loc
     * @param couplingFront determines if coach has coupling in the front
     * @param couplingBack determines if coach has coupling in the back
     * @return the id of the newLoc as String
     * @throws LogicException if the locType is not fitting or a loc with the id already exists.
     */
    public String addLocomotiveToCollection(String locType, String buildSeries, String name, String length,
            String couplingFront, String couplingBack) throws LogicException {

        RollMaterial newLoc = new Locomotive(locType, buildSeries, length, name, couplingFront, couplingBack);
        if (!this.containsLocomotiveTrainSetWithID(newLoc.getId())) {
            rollMaterialCollection.add(newLoc);
            locCollection.add((Locomotive) newLoc);
            return newLoc.getId();
        } else {
            throw new LogicException(ErrorMessages.LOCOMOTIVE_EXISTING.getMessage().toString());
        }
    }

    /**
     * 
     * Adds TrainSets to the rollMaterialCollection and the trainSetCollection.
     * 
     *
     * @param buildSeries   the buildSeries to be given
     * @param name          the name to be given
     * @param length        the length of the trainSet
     * @param couplingFront determines if trainSet has coupling in the front
     * @param couplingBack  determines if trainSet has coupling in the back
     * @return the id of the new TrainSet as String.
     * @throws LogicException if the Id is already existing.
     */
    public String addTrainSetToCollection(String buildSeries, String name, String length, String couplingFront,
            String couplingBack) throws LogicException {
        
        RollMaterial newTrainSet = new TrainSet(buildSeries, length, name, couplingFront, couplingBack);
        if (!this.containsLocomotiveTrainSetWithID(newTrainSet.getId())) {
            rollMaterialCollection.add(newTrainSet);
            trainSetCollection.add((TrainSet) newTrainSet);
            return newTrainSet.getId();
        } else {
            throw new LogicException(ErrorMessages.LOCOMOTIVE_EXISTING.getMessage().toString());
        }
    }

    /**
     * Returns all Engines as String in lines.
     * 
     * @return a String containing all Engines.
     */
    public String returnEngines() {
        String toReturn = "";
        Collections.sort(locCollection);
        if (locCollection.isEmpty()) {
            toReturn = "No engine exists";
            return toReturn;
        }
        for (RollMaterial engine : locCollection) {
            toReturn += engine.toString() + "\n";
        }
        return toReturn.trim();
    }

    /**
     * Returns all Coaches as String in lines.
     * 
     * @return a String containing all Coaches.
     */
    public String returnCoaches() {
        Collections.sort(coachCollection);
        String toReturn = "";
        if (coachCollection.isEmpty()) {
            toReturn = "No coach exists";
            return toReturn;
        }
        for (RollMaterial coach : coachCollection) {
            toReturn += coach.toString() + "\n";
        }
        return toReturn.trim();
    }

    /**
     * Returns all TrainSets as String in lines.
     * 
     * @return a String containing all TrainSets.
     */
    public String returnTrainSets() {
        String toReturn = "";
        Collections.sort(trainSetCollection);
        if (trainSetCollection.isEmpty()) {
            toReturn = "No train-set exists";
            return toReturn;
        }
        for (RollMaterial trainSet : trainSetCollection) {
            toReturn += trainSet.toString() + "\n";
        }
        return toReturn.trim();
    }

    /**
     * Help method, which checks if a Locomotive or TrainSet with the given id
     * already exists.
     * 
     * @param id the id which should be checked.
     * @return true if Loc or TrainSet exists, false if not.
     */
    private boolean containsLocomotiveTrainSetWithID(String id) {
        for (Locomotive rollMaterial : locCollection) {
            if (rollMaterial.getId().equals(id)) {
                return true;
            }
        }
        for (TrainSet rollMaterial : trainSetCollection) {
            if (rollMaterial.getId().equals(id)) {
                return true;
            }
        }
        return false;
    }

    /**
     * Deletes the RollMaterial with the given id from the rollMaterialCollection.
     * 
     * @param rollMaterialId the id of the RollMaterial.
     * @throws LogicException if RollMaterial with the id doesn´t exist.
     */
    public void setStockUsed(String rollMaterialId) throws LogicException {
        this.rollMaterialCollection.remove(this.findRollMaterial(rollMaterialId));

    }

}
