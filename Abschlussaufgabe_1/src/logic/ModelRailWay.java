package logic;

import java.util.ArrayList;
import java.util.Collections;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

import events.Crash;
import events.Event;
import events.Success;

/**
 * Class which represents a ModelRailWay. A ModelRailWay combines a TrackNet
 * with trains.
 * 
 * @author Christopher Roth
 * @version 1.0
 *
 */
public class ModelRailWay {

    private ModelRailWayMovement movement;
    private final TrackNetwork trackNet;
    private final RollMaterialComposition rollMaterialComposition;
    private final List<Train> trainsDriving;

    /**
     * Constructor for a ModelRailWay.
     */
    public ModelRailWay() {
        this.trackNet = new TrackNetwork();
        this.rollMaterialComposition = new RollMaterialComposition();
        this.trainsDriving = new ArrayList<Train>();
        movement = new ModelRailWayMovement(this);
    }

    /**
     * Getter for the TrackNet.
     * 
     * @return the tracknet.
     */
    public TrackNetwork getTrackNet() {
        return trackNet;
    }

    /**
     * Finds a train in the trainsDriving List.
     * 
     * @param trainId the id of the train to be found.
     * @return the train or null.
     */
    private Train findTrain(String trainId) {
        if (!trainsDriving.isEmpty()) {
            for (Train train : trainsDriving) {
                if (train.getId() == Integer.parseInt(trainId)) {
                    return train;
                }
            }
        }
        return null;
    }

    /**
     * Removes a Train from a Track.
     * 
     * @param trainId the trainId of the train that should be removed.
     * @return "OK" if everything went alright
     * @throws LogicException if train does not exist.
     */
    public String deleteTrainFromTrack(String trainId) throws LogicException {
        Train trainToDelete;
        if (this.findTrain(trainId) != null) {
            trainToDelete = this.findTrain(trainId);
            trainsDriving.remove(trainToDelete);
            this.rollMaterialComposition.deleteTrain(trainId);
            return "OK";
        } else {
            this.rollMaterialComposition.deleteTrain(trainId);
            return "OK";
        }
    }

    /**
     * Puts a train on a point in the TrackNet.
     * 
     * @param trainId the iD of the Train that should be placed.
     * @param point   the point on which the train should be set.
     * @param vectorX the vector in X-Dimension of the train.
     * @param vectorY the vector in Y-Dimension of the train.
     * @return "OK" if everything went alright
     * @throws LogicException    if train with id is not existing, if point is not
     *                           existing, if unsets switches are in the track,
     * @throws MovementException
     */
    public String putTrain(String trainId, String point, int vectorX, int vectorY) throws LogicException {
        Train trainToSet = this.rollMaterialComposition.trainWithIdExists(Integer.parseInt(trainId));
        TrackPoint pointToSetOn = this.trackNet.createTrackPoint(point.split(","));
        TrackPoint newPoint;
        if (trainToSet == null) {
            throw new LogicException(ErrorMessages.TRAIN_NOT_EXISTING.getMessage().toString());
        }
        if (trackNet.switchUnset()) {
            throw new LogicException(ErrorMessages.UNSET_SWITCHES_IN_TRACK.getMessage());
        }
        if (trackNet.getTotalLength() < trainToSet.getLength()) {
            throw new LogicException(ErrorMessages.TRAIN_LONGER_THAN_TRACK.getMessage());
        }
        trainToSet.checkIfValid();
        newPoint = this.trackNet.checkIfInherited(pointToSetOn);
        if (!trainToSet.getPointsOfTrain().isEmpty()) {
            throw new LogicException(ErrorMessages.TRAIN_ALREADY_PUT.getMessage());
        }
        if (newPoint == null) {
            throw new LogicException(ErrorMessages.POINT_NOT_EXISTING.getMessage().toString());
        }
        if (newPoint.isActive() == false) {
            throw new LogicException(ErrorMessages.SWITCH_IS_UNSET.getMessage().toString());
        }
        if (this.trainStanding(newPoint)) {
            throw new LogicException(ErrorMessages.TRAIN_ALREADY_STANDING.getMessage());
        }
        TrackVector newVector = new TrackVector(vectorX, vectorY);
        trainToSet.setVector(newVector);
        trainToSet.setHeadPoint(newPoint);
        movement.determineDirection(trainToSet);
        this.checkDirections(trainToSet, newPoint);
        try {
            movement.generatePointsOfTrain(trainToSet);
        } catch (MovementException e) {
            throw new LogicException(e.getMessage());
        }
        this.generateTracksOfTrain(trainToSet);
        this.trainsDriving.add(trainToSet);
        return "OK";
    }

    /**
     * Checks if a Train is standing on the given Point, or on the Track of the
     * point.
     * 
     * @param newPoint the point to check.
     * @return true if a train is standing false if not.
     */
    private boolean trainStanding(TrackPoint newPoint) {
        if (newPoint.getCurrentTrack() != null) {
            for (Train train : this.trainsDriving) {
                for (NormalTrack nt : train.getCurrentTracks()) {
                    if (nt.equals(newPoint.getCurrentTrack())) {
                        return true;
                    }
                }
            }
        } else {
            for (Train train : this.trainsDriving) {
                for (TrackPoint tp : train.getPointsOfTrain()) {
                    if (tp.equals(newPoint)) {
                        return true;
                    }
                }
            }
        }
        return false;
    }

    /**
     * Generates the inherited Tracks of a train out of the points of the Train.
     * 
     * @param trainToSet the train of which the Tracks should be created.
     */
    void generateTracksOfTrain(Train trainToSet) {
        trainToSet.deleteCurrentTracks();
        for (TrackPoint tp : trainToSet.getPointsOfTrain()) {
            if (this.trackNet.getTrackWithPoint(tp) != null) {
                trainToSet.addTrack(this.trackNet.getTrackWithPoint(tp));
            }
        }
    }

    /**
     * Checks if the directions of a train and the point to set on are the same.
     * 
     * @param trainToSet the train to set
     * @param point      the point to check
     * @return true if directions are the same
     * @throws LogicException if directions are not the same.
     */
    private boolean checkDirections(Train trainToSet, TrackPoint point) throws LogicException {
        if (this.trackNet.findTrackPointNull(point) != null) {
            return true;
        }
        if (point.getCurrentTrack() != null && (!point.getCurrentTrack().getVector().equals(trainToSet.getVector()))) {
            throw new LogicException(ErrorMessages.DIRECTIONS_NOT_SAME.getMessage().toString());
        }
        return true;
    }

    /**
     * Helper Method that links the Points of switches in the opposite Direction.
     * 
     * @param switchToSet the switch of which the points should be linked.
     * @param destination the active switchEnd
     */
    private void setSwitchUtilityOppositeDirectionEnd(TrackSwitch switchToSet, TrackPoint destination) {
        if (switchToSet.getLength() == 1) {
            switchToSet.getStart().setPrevious(destination);
            destination.setNext(switchToSet.getStart());
        } else {
            switchToSet.generateInheritedPoints(destination, switchToSet.getStart());
            switchToSet.getStart().setPrevious(switchToSet.getLastInherited());
            destination.setNext(switchToSet.getFirstInherited());
            switchToSet.getFirstInherited().setPrevious(destination);
            switchToSet.getLastInherited().setNext(switchToSet.getStart());
        }
    }

    /**
     * Helper Method that links the Points of switches.
     * 
     * @param switchToSet the switch of which the points should be linked.
     * @param destination the active switchEnd
     */
    private void setSwitchUtilityEnd(TrackSwitch switchToSet, TrackPoint destination) {
        if (switchToSet.getLength() == 1) {
            switchToSet.getStart().setNext(destination);
            destination.setPrevious(switchToSet.getStart());
        } else {
            switchToSet.generateInheritedPoints(switchToSet.getStart(), destination);
            switchToSet.getStart().setNext(switchToSet.getFirstInherited());
            destination.setPrevious(switchToSet.getLastInherited());
            switchToSet.getFirstInherited().setPrevious(switchToSet.getStart());
            switchToSet.getLastInherited().setNext(destination);
        }
    }

    /**
     * Sets a switch with the given id. Sets the switch to the point which is given.
     * 
     * @param trackId the id of the switch
     * @param tp      the point the switch should be set on.
     * @return "OK" if everything went alright.
     * @throws LogicException if switch is not existing, if trackpoint is not
     *                        existing,
     */
    public String setSwitch(String trackId, String tp) throws LogicException {
        TrackSwitch switchToSet = this.trackNet.findSwitch(trackId);
        if (switchToSet == null) {
            throw new LogicException(ErrorMessages.SWITCH_NOT_EXISTING.getMessage().toString());
        }
        TrackPoint trackPointToSet = this.trackNet.createTrackPoint(tp.split(","));
        this.trackNet.removeTrackPoints(switchToSet);
        if (switchToSet.getEnd().equals(trackPointToSet)) {
            switchToSet.setActiveSwitchEnd(switchToSet.getEnd());
            switchToSet.deleteInheritedPoints();
            if (switchToSet.getStart().getPrevious() != null && switchToSet.getEnd().getNext() == null) {
                this.setSwitchUtilityEnd(switchToSet, switchToSet.getEnd());
            } else if (switchToSet.getStart().getPrevious() == null && switchToSet.getEnd().getNext() != null) {
                this.setSwitchUtilityEnd(switchToSet, switchToSet.getEnd());
            } else if (switchToSet.getStart().getNext() == null && switchToSet.getEnd().getPrevious() != null) {
                this.setSwitchUtilityOppositeDirectionEnd(switchToSet, switchToSet.getEnd());
            } else if (switchToSet.getStart().getNext() != null && switchToSet.getEnd().getPrevious() == null) {
                this.setSwitchUtilityOppositeDirectionEnd(switchToSet, switchToSet.getEnd());
            } else if (switchToSet.getStart().getPrevious() != null && switchToSet.getEnd().getNext() != null
                    && switchToSet.getStart().getPrevious().getCurrentTrack() != switchToSet) {
                this.setSwitchUtilityEnd(switchToSet, switchToSet.getEnd());
            } else if (switchToSet.getStart().getNext() != null && switchToSet.getEnd().getPrevious() != null) {
                this.setSwitchUtilityOppositeDirectionEnd(switchToSet, switchToSet.getEnd());
            }
            this.checkForCrashSwitch(switchToSet);
            this.trackNet.addTrackPoints(switchToSet.inheritedPoints);
            return "OK";
        } else if (switchToSet.getEnd2().equals(trackPointToSet)) {
            switchToSet.setActiveSwitchEnd(switchToSet.getEnd2());
            switchToSet.deleteInheritedPoints();
            if (switchToSet.getStart().getPrevious() != null && switchToSet.getEnd2().getNext() == null) {
                this.setSwitchUtilityEnd(switchToSet, switchToSet.getEnd2());
            } else if (switchToSet.getStart().getPrevious() == null && switchToSet.getEnd2().getNext() != null) {
                this.setSwitchUtilityEnd(switchToSet, switchToSet.getEnd2());
            } else if (switchToSet.getStart().getPrevious() != null && switchToSet.getEnd2().getNext() != null) {
                this.setSwitchUtilityEnd(switchToSet, switchToSet.getEnd2());
            } else if (switchToSet.getStart().getNext() == null && switchToSet.getEnd2().getPrevious() != null) {
                this.setSwitchUtilityOppositeDirectionEnd(switchToSet, switchToSet.getEnd2());
            } else if (switchToSet.getStart().getNext() != null && switchToSet.getEnd2().getPrevious() == null) {
                this.setSwitchUtilityOppositeDirectionEnd(switchToSet, switchToSet.getEnd2());
            } else if (switchToSet.getStart().getNext() != null && switchToSet.getEnd2().getPrevious() != null) {
                this.setSwitchUtilityOppositeDirectionEnd(switchToSet, switchToSet.getEnd2());
            }
            this.trackNet.addTrackPoints(switchToSet.inheritedPoints);
            this.checkForCrashSwitch(switchToSet);
            return "OK";
        }
        throw new LogicException(ErrorMessages.INVALID_SWITCH_END.getMessage().toString());
    }

    /**
     * Checks if a Train crashes when a Track is set.
     * 
     * @param switchToSet the switch to set.
     */
    private void checkForCrashSwitch(TrackSwitch switchToSet) {
        List<Train> crashedTrains = new ArrayList<Train>();
        for (Train train : this.trainsDriving) {
            for (NormalTrack nt : train.getCurrentTracks()) {
                if (nt.equals(switchToSet)) {
                    crashedTrains.add(train);
                }
            }
            if (train.checkIfTrainIsOnPoint(switchToSet.getEnd()) != null
                    || train.checkIfTrainIsOnPoint(switchToSet.getEnd2()) != null) {
                crashedTrains.add(train);
            }
        }
        this.removeTrainsFromTrack(crashedTrains);
    }

    /**
     * Step command. Moves every train in the railnet by <speed> points.
     * Differintiates between negative and positive speed.
     * 
     * @param speed how often the trains should be moved.
     * @return "OK" if everything went alright.
     * @throws LogicException if switches are unset.
     */
    public String step(short speed) throws LogicException {
        Collections.sort(this.trainsDriving);
        List<Event> events = new ArrayList<Event>();
        if (this.trackNet.switchUnset()) {
            throw new LogicException(ErrorMessages.SWITCH_IS_UNSET.getMessage());
        } else if (this.trainsDriving.isEmpty()) {
            return "OK";
        }
        if (speed == 0) {
            for (Train train : trainsDriving) {
                Event success = new Success(train, train.getId());
                events.add(success);
            }
        } else if (speed > 0) {
            for (int i = 0; i < speed; i++) {
                for (int j = 0; j < this.trainsDriving.size(); j++) {
                    Train trainToMove = this.trainsDriving.get(j);
                    try {
                        movement.move(trainToMove);
                    } catch (MovementException e) {
                        events.add(e.getCrash());
                        movement.handleCrash(events);
                    }
                }
                events.addAll(movement.handleCrash(this.checkForCrash()));
            }
            events.addAll(movement.handleSuccess(this.checkForCrash()));
        } else if (speed < 0) {
            for (int i = 0; i < Math.abs(speed); i++) {
                for (int j = 0; j < trainsDriving.size(); j++) {
                    Train trainToMove = this.trainsDriving.get(j);
                    try {
                        movement.moveBackWards(trainToMove);
                    } catch (MovementException e) {
                        events.add(e.getCrash());
                        movement.handleCrash(events);
                    }
                }
                events.addAll(movement.handleCrash(this.checkForCrash()));
            }
            events.addAll(movement.handleSuccess(this.checkForCrash()));
        }
        String string = "";
        List<Event> eventsList = events.stream().distinct().collect(Collectors.toList());
        Collections.sort(eventsList);
        for (Event event : eventsList) {
            if (event.getType().equals("crash")) {
                if (event.getTrains().size() == 1) {
                    event.getTrains().get(0).removeTracks();
                    event.getTrains().get(0).removePoints();
                } else {
                    for (Train train : event.getTrains()) {
                        train.removePoints();
                        train.removeTracks();
                    }
                }
            }
        }
        for (Event event : eventsList) {
            string += event.toString() + "\n";
        }
        return string.trim();
    }

    /**
     * Method that checks for crash on railnet.
     * 
     * @return list of Events.
     */
    private List<Event> checkForCrash() {
        List<Event> events = new ArrayList<Event>();
        List<Crash> crashes = new ArrayList<Crash>();
        List<Train> crashedTrains = new ArrayList<Train>();
        Collections.sort(trainsDriving);
        for (Train train : this.trainsDriving) {
            for (int i = 0; i < trainsDriving.size(); i++) {
                if (!train.equals(trainsDriving.get(i))) {
                    List<NormalTrack> tracksToCheck = train.getCurrentTracks();
                    tracksToCheck.retainAll(trainsDriving.get(i).getCurrentTracks());
                    if (!tracksToCheck.isEmpty()) {
                        if (!crashedTrains.contains(train)) {
                            crashedTrains.add(train);
                            crashedTrains.add(trainsDriving.get(i));
                        }
                    }
                }
            }
        }
        if (crashedTrains.isEmpty()) {
            for (Train train : trainsDriving) {
                if (!crashedTrains.contains(train)) {
                    Event success = new Success(train, train.getId());
                    events.add(success);
                }
            }
        } else {
            Set<Train> set = new LinkedHashSet<Train>(crashedTrains);
            crashedTrains = new ArrayList<Train>(set);
            Collections.sort(crashedTrains);
            Event crash = new Crash(crashedTrains, crashedTrains.get(0).getId());
            crashes.add((Crash) crash);
            events.add(crash);
        }
        return events;
    }

    /**
     * Removes a List of Trains from the Track.
     * 
     * @param crashedTrains the List of Trains to be removed.
     */
    void removeTrainsFromTrack(List<Train> crashedTrains) {
        for (Train train : crashedTrains) {
            this.trainsDriving.remove(train);
            train.removePoints();
            train.removeTracks();
        }
    }

    /**
     * Getter for the RollMaterialComposition.
     * 
     * @return the rollMaterialComposition
     */
    public RollMaterialComposition getRollMaterialComposition() {
        return this.rollMaterialComposition;
    }

    /**
     * Deletes a Track out of the TrackNet. Checks if logically everything is
     * alright, then deletes the track.
     * 
     * @param trackId the id of the track to be deleted.
     * @return "OK" if everything went alright
     * @throws LogicException if a train is standing on the track, if the track
     *                        cannot be deleted.
     */
    public String deleteTrack(int trackId) throws LogicException {
        NormalTrack trackToDelete = this.trackNet.findTrack(trackId);
        for (TrackPoint tp : trackToDelete.getInheritedPoints()) {
            if (this.trainStanding(tp)) {
                throw new LogicException(ErrorMessages.TRAIN_ALREADY_STANDING.getMessage());
            }
        }
        if (this.trainStanding(trackToDelete.getStart()) || this.trainStanding(trackToDelete.getEnd())) {
            throw new LogicException(ErrorMessages.TRAIN_ALREADY_STANDING.getMessage());
        }
        if (this.trackNet.getTrackList().size() == 1) {
            this.trackNet.remove(trackToDelete);
            trackToDelete.resetId();
            this.trackNet.setFirstTrackTrue();
            return "OK";
        } else if (this.trackNet.getTrackList().size() <= 2
                || this.trackNet.numberOfConnectedTracks(trackToDelete) <= 1) {
            trackNet.remove(trackToDelete);
            trackToDelete.resetId();
            return "OK";
        }
        for (TrackPoint tp : trackToDelete.getInheritedPoints()) {
            if (this.trainStanding(tp)) {
                throw new LogicException(ErrorMessages.TRAIN_ALREADY_STANDING.getMessage());
            }
        }
        if (this.trackNet.existingWayWithout(trackToDelete, trackToDelete.getStart(), trackToDelete.getEnd())) {
            trackNet.remove(trackToDelete);
            trackToDelete.resetId();
            return "OK";
        } else {
            throw new LogicException(ErrorMessages.TRACK_CANNOT_DELETED.getMessage());
        }
    }
}
