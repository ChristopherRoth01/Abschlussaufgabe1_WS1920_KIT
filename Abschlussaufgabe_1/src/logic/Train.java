package logic;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

/**
 * This Class represents a Train which can drive on rails. A train has a length
 * which is calculated by the length of all members. A train also has a
 * direction which says in which way the train is driving.
 * 
 * @author Christopher Roth.
 * @version 1.0
 *
 */
public class Train implements Comparable<Train> {
    /**
     * A static counter.
     */
    private static int counter = 0;
    /**
     * A list containing all ids.
     */
    private static List<Integer> iDs = new ArrayList<Integer>();
    /**
     * true = direction getNext() false = direction getPrevious()
     */
    private boolean direction;
    /**
     * The length of a train.
     */
    private int length;
    /** 
     * The vector of a train.
     */
    private TrackVector vector;
    /**
     * The headpoint of a train.
     */
    private TrackPoint headPoint;
    /**
     * The id of a train.
     */
    private final int id;
    /**
     * Trainparts.
     */
    private final List<RollMaterial> trainParts;
    /**
     * The points the train inherits.
     */
    private final List<TrackPoint> pointsOfTrain;
    /**
     * The tracks the train inherits.
     */
    private final List<NormalTrack> currentTracks;

    /**
     * Constructor.
     * 
     * @param trainId the trainId the train should have.
     * @throws LogicException is thrown if the id does not match the next free id.
     */
    Train(int trainId) throws LogicException {
        this.pointsOfTrain = new ArrayList<TrackPoint>();
        trainParts = new ArrayList<RollMaterial>();
        currentTracks = new ArrayList<NormalTrack>();

        this.generateLength();
        if (trainId == this.returnNextFreeId()) {
            this.id = trainId;
            iDs.remove(0);

        } else {

            throw new LogicException(ErrorMessages.NOT_NEXT_FREE_ID.getMessage());
        }

    }

    /**
     * Adds the length of all parts together and sets the length of the train to the
     * new Length.
     */
    void generateLength() {
        int newLength = 0;
        for (RollMaterial part : this.trainParts) {
            newLength += part.getLength();
        }
        this.length = newLength;
    }

    /**
     * Getter for direction.
     * 
     * @return the direction
     */
    public boolean isDirection() {
        return direction;
    }

    /**
     * Setter for the direction.
     * 
     * @param direction the new direction
     */
    public void setDirection(boolean direction) {
        this.direction = direction;
    }

    /**
     * Getter for the headPoint of the Train.
     * 
     * @return the headPoint of the train
     */
    public TrackPoint getHeadPoint() {
        return headPoint;
    }

    /**
     * Setter for the headPoint of the Train.
     * 
     * @param headPoint new HeadPoint.
     */
    public void setHeadPoint(TrackPoint headPoint) {
        this.headPoint = headPoint;
    }

    /**
     * Getter for the id of a Train.
     * 
     * @return the id of a train.
     */
    public int getId() {
        return id;
    }

    /**
     * Method which works like a isEmpty() method for a train.
     * 
     * @return true if train has no parts, false if not.
     */
    boolean hasNoTrainParts() {
        if (this.trainParts.isEmpty()) {
            return true;
        } else {
            return false;
        }
    }

    /**
     * Method which returns the next free id. If a train is deleted the id is added
     * to the iDs-List and then returned.
     * 
     * @return the next free id.
     */
    private int returnNextFreeId() {
        if (iDs.isEmpty()) {
            counter++;

            iDs.add(counter);

            return iDs.get(0);

        } else {
            Collections.sort(iDs);
            return iDs.get(0);
        }
    }

    /**
     * Adds a Part to the Train.
     * 
     * @param stock the RollMaterial that should be added.
     */
    void addPart(RollMaterial stock) {
        this.trainParts.add(stock);

    }

    /**
     * Checks if a train consists of train-sets.
     * 
     * @return false if no trainSet, true if trainSet
     * 
     */
    boolean checkIfTrainIsTrainSet() {
        for (RollMaterial trainPart : this.trainParts) {
            if (!trainPart.getType().getType().toString().equals("train-set")) {
                return false;
            }
        }
        return true;

    }

    @Override
    public String toString() {
        String toReturn = this.getId() + " ";
        for (int i = 0; i < this.trainParts.size(); i++) {
            if (trainParts.get(i).getId().contains("-")) {
                toReturn += trainParts.get(i).getId() + " ";
            } else {
                toReturn += "W" + trainParts.get(i).getId() + " ";
            }
        }
        return toReturn.trim();
    }

    /**
     * Gets the max Height of the Train. This height corresponds to the height of
     * the highest RollMaterial in the Train.
     * 
     * @return the maxHeight
     */
    public int getMaxHeight() {
        int maxHeight = 0;
        for (int i = 0; i < this.trainParts.size(); i++) {
            if (this.trainParts.get(i).getType().getRepresentation().length > maxHeight) {
                maxHeight = this.trainParts.get(i).getType().getRepresentation().length;
            }
        }
        return maxHeight;

    }

    /**
     * Returns a Train as a String. Therefore it checks the maxHeight of the train
     * and initiates a new String[] array. Then the method composes all
     * Representations seperated by a whitespace.
     * 
     * @return an String array containing the composed RollMaterials.
     */
    public String[] returnTrainToString() {

        String[] trainAsString = new String[this.getMaxHeight()];

        for (int l = 0; l < trainAsString.length; l++) {
            trainAsString[l] = "";
        }
        for (RollMaterial stock : this.trainParts) {
            for (int i = 0; i < trainAsString.length; i++) {
                if (stock.getType().getRepresentation().length > i) {

                    trainAsString[i] += stock.getType().getRepresentation()[i] + " ";
                } else {
                    for (int k = 0; k < stock.getType().getRepresentation()[0].length(); k++) {
                        trainAsString[i] += " ";
                    }
                    trainAsString[i] += " ";
                }
            }
        }
        for (int i = 0; i < trainAsString.length; i++) {
            trainAsString[i] = trainAsString[i].substring(0, trainAsString[i].length() - 1);
        }
        return trainAsString;

    }

    @Override
    public int hashCode() {
        return this.id;
    }

    /**
     * Checks if a train is valid. To be valid it either needs to be a set of
     * trainsets or a Locomotive in the front or at the end.
     * 
     * @return true if valid
     * @throws LogicException if train is not valid.
     */
    boolean checkIfValid() throws LogicException {

        if (this.trainParts.get(0).getType().getType().equals("steam engine")
                || this.trainParts.get(0).getType().getType().equals("diesel engine")
                || this.trainParts.get(0).getType().getType().equals("electrical engine")) {
            return true;
        } else if (this.trainParts.get(trainParts.size() - 1).getType().getType().equals("steam engine")
                || this.trainParts.get(trainParts.size() - 1).getType().getType().equals("diesel engine")
                || this.trainParts.get(trainParts.size() - 1).getType().getType().equals("electrical engine")) {
            return true;
        } else if (this.checkIfTrainIsTrainSet()) {
            return true;
        } else {
            throw new LogicException(ErrorMessages.TRAIN_NOT_VALID.getMessage().toString());
        }
    }

    /**
     * Sets the Train of all Elements in the train to null.
     */
    void setElementsWithoutTrain() {
        for (int i = 0; i < this.trainParts.size(); i++) {
            this.trainParts.get(i).setTrain(null);
        }

    }

    /**
     * Getter for the length attribute.
     * 
     * @return the length of the train.
     */
    public int getLength() {
        this.generateLength();
        return this.length;
    }

    /**
     * Gets the last part of the train.
     * 
     * @return the last part of the train.
     */
    public RollMaterial getLast() {
        return this.trainParts.get(this.trainParts.size() - 1);
    }

    /**
     * Adds a track to the list of currentTracks of the train.
     * 
     * @param trackWithPoint the track that should be added.
     */
    void addTrack(NormalTrack trackWithPoint) {
        if (!this.currentTracks.contains(trackWithPoint)) {
            this.currentTracks.add(trackWithPoint);
        }
    }

    /**
     * Clears the list of currentTracks.
     */
    void deleteCurrentTracks() {
        this.currentTracks.clear();

    }

    /**
     * Clears the list of currentPoints.
     */
    void removePoints() {
        this.pointsOfTrain.clear();

    }

    /**
     * Adds a list of points to the train. Before adding, the list is cleared.
     * 
     * @param points the list of points that should be added.
     */
    void addPoints(List<TrackPoint> points) {
        this.removePoints();
        this.pointsOfTrain.addAll(points);

    }

    /**
     * Checks if buildSeries of Train and stock is the same. Method is only needed
     * if train is a train-set.
     * 
     * @param stock the stock of which the buildSeries should be checked.
     * @return true if the same
     * @throws LogicException if trainSet is not compatible
     */
    boolean checkIfBuildSeriesIsTheSame(RollMaterial stock) throws LogicException {
        if (this.checkIfTrainIsTrainSet()) {
            for (RollMaterial trainPart : this.trainParts) {
                if (!trainPart.getBuildSeries().equals(stock.getBuildSeries())) {
                    throw new LogicException(ErrorMessages.TRAINSET_NOT_COMPATIBLE.getMessage().toString());
                }
            }

        }
        return true;
    }

    /**
     * Adds the id of a train to the list of iDs.
     */
    void removeId() {
        iDs.add(this.getId());

    }

    /**
     * Clones the list pointsOfTrain
     * 
     * @return the cloned list
     */
    private List<TrackPoint> cloneTrackPoints() {
        ArrayList<TrackPoint> clonedPointsOfTrain = new ArrayList<TrackPoint>();
        for (TrackPoint tp : this.pointsOfTrain) {
            clonedPointsOfTrain.add(tp);
        }
        return clonedPointsOfTrain;
    }

    /**
     * Clones the list currentTracks
     * 
     * @return the cloned list
     */
    private List<NormalTrack> cloneCurrentTracks() {
        ArrayList<NormalTrack> clonedCurrentTracks = new ArrayList<NormalTrack>();
        for (NormalTrack nt : this.currentTracks) {
            clonedCurrentTracks.add(nt);
        }
        return clonedCurrentTracks;
    }

    /**
     * Getter for the trackPoints the Train is standing on.
     * 
     * @return a cloned version of pointsOfTrain
     */
    public List<TrackPoint> getPointsOfTrain() {
        return this.cloneTrackPoints();
    }

    /**
     * Getter for the list of currentTracks.
     * 
     * @return a cloned version of currentTracks.
     */
    public List<NormalTrack> getCurrentTracks() {
        return this.cloneCurrentTracks();
    }

    /**
     * Clears the list of current Tracks.
     */
    void removeTracks() {
        this.currentTracks.clear();

    }

    @Override
    public int compareTo(Train train) {
        return this.getId() - train.getId();
    }

    /**
     * Getter for the Vector of a Track.
     * 
     * @return the vector of the track.
     */
    public TrackVector getVector() {
        return this.vector;
    }

    /**
     * Setter for the Vector of a Track.
     * 
     * @param newVector the new Vector that should be set for the Track.
     */
    public void setVector(TrackVector newVector) {
        this.vector = newVector;

    }

    /**
     * Checks if a Train inherits a given Point.
     * 
     * @param point the point to check.
     * @return null if not the point if true.
     */
    TrackPoint checkIfTrainIsOnPoint(TrackPoint point) {
        return this.pointsOfTrain.stream().filter(s -> s.equals(point)).findAny().orElse(null);
    }
}
