package logic;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

/**
 * This class contains all trains and handles the Composition of RollMaterial.
 * 
 * @author Christopher Roth
 * @version 1.0
 *
 */
public class RollMaterialComposition {

    /**
     * Contains all build trains.
     */
    private List<Train> trains;

    private RollMaterialStock rollMaterialStock;

    /**
     * Constructor.
     */
    public RollMaterialComposition() {
        trains = new ArrayList<Train>();
        rollMaterialStock = new RollMaterialStock();
    }

    /**
     * Adds a train. Therefore it checks if a train with the given trainId exists,
     * if not a new Train is initiated. Also checks if RollMaterial with the give
     * rollMaterialId exists. Then checks if Train and RollMaterial is compatible.
     * If so the RollMaterial is attached to the train.
     * 
     * @param trainId        the Id of the train
     * @param rollMaterialId the Id of the RollMaterial to add
     * @return a String which documents the operation.
     * @throws LogicException if RollMaterial with rollMaterialId does not exist, if
     *                        Train and RollMaterial are incompatible, if
     *                        RollMaterial is already used in train.
     */
    public String addTrain(int trainId, String rollMaterialId) throws LogicException {

        RollMaterial stock = rollMaterialStock.findRollMaterial(rollMaterialId);
        
        if (this.trainWithIdExists(trainId) == null) {
            rollMaterialStock.checkIfRollMaterialisInTrain(stock);
            Train newTrain = new Train(trainId);            
            trains.add(newTrain);
            newTrain.addPart(stock);
            newTrain.generateLength();
            stock.setTrain(newTrain);
            rollMaterialStock.setStockUsed(rollMaterialId);
            if (stock.getType().getType().contains("coach")) {
                return stock.getType().getType().toString() + " W" + stock.getId() + " added to train "
                        + newTrain.getId();
            } else {
                return stock.getType().getType().toString() + " " + stock.getId() + " added to train "
                        + newTrain.getId();
            }
        } else {
            
            Train train = this.trainWithIdExists(trainId);
            this.checkIfTrainIsSet(train);
            this.checkIfCompatible(train, stock);
            train.addPart(stock);
            rollMaterialStock.checkIfRollMaterialisInTrain(stock);
            stock.setTrain(train);
            rollMaterialStock.setStockUsed(rollMaterialId);
            if (stock.getType().getType().equals("special coach") || stock.getType().getType().equals("freight coach")
                    || stock.getType().getType().equals("passenger coach")) {
                return stock.getType().getType().toString() + " W" + stock.getId() + " added to train " + train.getId();
            } else {
                return stock.getType().getType().toString() + " " + stock.getId() + " added to train " + train.getId();
            }
        }

    }

    private void checkIfTrainIsSet(Train train) throws LogicException {
        if (train.getPointsOfTrain().size() != 0) {
            throw new LogicException(ErrorMessages.TRAIN_ALREADY_STANDING.getMessage());
        }

    }

    /**
     * Checks whether a train is compatible with a stock. To be compatible they both
     * need the same coupling attribute.
     * 
     * @param newTrain the train to check
     * @param stock    the stock to check
     * @return true if compatible
     * @throws LogicException if not compatible
     */
    private boolean checkIfCompatible(Train newTrain, RollMaterial stock) throws LogicException {
        if ((!newTrain.checkIfTrainIsTrainSet() && stock.getType().getType().equals("train-set"))) {
            throw new LogicException(ErrorMessages.STOCK_NOT_COMPATIBLE.getMessage().toString());
        }
        if (newTrain.checkIfTrainIsTrainSet() && (!stock.getType().getType().equals("train-set"))) {
            throw new LogicException(ErrorMessages.STOCK_NOT_COMPATIBLE.getMessage().toString());
        }
        if (newTrain.hasNoTrainParts()) {
            return true;
        } else if ((stock.getType().getType().equals("train-set")) && newTrain.getLast().getCouplingBack()
                && stock.getCouplingFront()) {

            newTrain.checkIfTrainIsTrainSet();
            newTrain.checkIfBuildSeriesIsTheSame(stock);
            return true;

        } else if (newTrain.getLast().getCouplingBack() && stock.getCouplingFront()) {
            return true;
        } else {
            throw new LogicException(ErrorMessages.STOCK_NOT_COMPATIBLE.getMessage().toString());
        }
    }

    /**
     * Finds a train with a given id.
     * 
     * @param trainId the id of the train to find.
     * @return the train, or null
     */
    public Train trainWithIdExists(int trainId) {
        for (Train train : trains) {

            if (train.getId() == trainId) {
                return train;
            }
        }
        return null;
    }

    /**
     * Returns all present trains as String in the Form <trainId> <members>
     * 
     * @return a list of all trains as String.
     */
    public String returnTrains() {
        Collections.sort(trains);
        String toReturn = "";
        if (trains.isEmpty()) {
            toReturn += "No train exists";
        }
        for (Train train : this.trains) {
            toReturn += train.toString() + "\n";
        }
        return toReturn.trim();
    }

    /**
     * Deletes a Train. Therefore all Elements of the train are reset and the id is
     * added to the id collection.
     * 
     * @param trainId id of the Train.
     * @return "OK" if everything went alright.
     * @throws LogicException if train does not exist.
     */
    String deleteTrain(String trainId) throws LogicException {
        Train trainToDelete = this.findTrain(trainId);
        trainToDelete.setElementsWithoutTrain();
        trainToDelete.removeId();
        trains.remove(trainToDelete);
        return "OK";

    }

    /**
     * Finds a train with a given iD. If the train is not existing a LogicException
     * is thrown.
     * 
     * @param trainId the id of the train which should be found.
     * @return the found train
     * @throws LogicException if train with given id does not exist.
     */
    private Train findTrain(String trainId) throws LogicException {
        int trainNumber = Integer.parseInt(trainId);
        for (Train train : trains) {
            if (train.getId() == trainNumber) {
                return train;
            }
        }
        throw new LogicException(ErrorMessages.TRAIN_NOT_EXISTING.getMessage().toString());
    }

    /**
     * Getter for the RollMaterialStock.
     * 
     * @return the rollMaterialStock.
     */
    public RollMaterialStock getRollMaterialStock() {
        return this.rollMaterialStock;
    }

}
