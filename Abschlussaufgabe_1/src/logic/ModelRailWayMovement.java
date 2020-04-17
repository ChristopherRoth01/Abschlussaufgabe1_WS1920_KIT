package logic;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import events.Crash;
import events.Event;

/**
 * The Class which moves Trains.
 * 
 * @author Christopher Roth
 * @version 1.0
 *
 */
public class ModelRailWayMovement {

    private ModelRailWay railWay;

    /**
     * Constructor.
     * 
     * @param modelRailWay the modelRailWay on which trains shall be moved.
     */
    public ModelRailWayMovement(ModelRailWay modelRailWay) {
        this.railWay = modelRailWay;
    }

    /**
     * Moves a train one point backwards.
     * 
     * @param train the train to be moved.
     * @throws MovementException if train derails.
     */
    void moveBackWards(Train train) throws MovementException {
        TrackPoint headPoint = train.getHeadPoint();
        if (train.isDirection() == true) {

            if (headPoint.getNext() == null) {
                List<Train> crashed = new ArrayList<Train>();
                crashed.add(train);
                throw new MovementException(new Crash(crashed, crashed.get(0).getId()));
            }
            train.setHeadPoint(headPoint.getNext());
        } else if (train.isDirection() == false) {

            if (headPoint.getPrevious() == null) {
                List<Train> crashed = new ArrayList<Train>();
                crashed.add(train);
                throw new MovementException(new Crash(crashed, crashed.get(0).getId()));
            }
            train.setHeadPoint(headPoint.getPrevious());
        }
        try {
            this.generatePointsOfTrain(train);
        } catch (LogicException e) {
            List<Train> crashed = new ArrayList<Train>();
            crashed.add(train);
            throw new MovementException(new Crash(crashed, crashed.get(0).getId()));
        }
        railWay.generateTracksOfTrain(train);
    }

    /**
     * Moves a train one point forward.
     * 
     * @param train the train to be moved.
     * @throws MovementException if train derails.
     */
    void move(Train train) throws MovementException {
        TrackPoint headPoint = train.getHeadPoint();
        if (train.isDirection() == true) {
            if (headPoint.getPrevious() == null) {
                List<Train> crashed = new ArrayList<Train>();
                crashed.add(train);
                throw new MovementException(new Crash(crashed, crashed.get(0).getId()));
            }
            train.setHeadPoint(headPoint.getPrevious());
        } else if (train.isDirection() == false) {
            if (headPoint.getNext() == null) {
                List<Train> crashed = new ArrayList<Train>();
                crashed.add(train);
                throw new MovementException(new Crash(crashed, crashed.get(0).getId()));
            }
            train.setHeadPoint(headPoint.getNext());
        }
        try {
            this.generatePointsOfTrain(train);
        } catch (LogicException e) {
            List<Train> crashed = new ArrayList<Train>();
            crashed.add(train);
            throw new MovementException(new Crash(crashed, crashed.get(0).getId()));
        }
        railWay.generateTracksOfTrain(train);
    }

    /**
     * Generates the Points of a Train, depending from the start Point.
     * 
     * @param trainToSet the train of which the points should be generated
     * @throws LogicException    if the train cant be set at the current position.
     * @throws MovementException if the train is derailed.
     */
    void generatePointsOfTrain(Train trainToSet) throws LogicException, MovementException {
        List<TrackPoint> points = new ArrayList<TrackPoint>();
        trainToSet.removePoints();
        TrackPoint point = trainToSet.getHeadPoint();

        if (point == null) {
            List<Train> crashed = new ArrayList<Train>();
            crashed.add(trainToSet);
            railWay.removeTrainsFromTrack(crashed);
            throw new MovementException(new Crash(crashed, crashed.get(0).getId()));
        }

        if (trainToSet.isDirection() == true) {
            for (int i = 0; i <= trainToSet.getLength(); i++) {
                if (point == null || point.isActive() == false) {
                    throw new LogicException(ErrorMessages.TRACK_NOT_EXISTING.getMessage());
                }
                points.add(point);
                point = point.getNext();

            }

        } else if (trainToSet.isDirection() == false) {
            for (int i = 0; i <= trainToSet.getLength(); i++) {
                if (point == null || point.isActive() == false) {
                    throw new LogicException(ErrorMessages.TRACK_NOT_EXISTING.getMessage());
                }
                points.add(point);
                point = point.getPrevious();
            }
            trainToSet.setDirection(false);
        }
        trainToSet.addPoints(points);

    }

    /**
     * Determines the direction of a train depending from his Vector.
     * 
     * @param trainToSet the train that should be set and of which the direction is
     *                   determined.
     */
    public void determineDirection(Train trainToSet) {
        TrackPoint point = trainToSet.getHeadPoint();

        TrackPoint newPoint;
        newPoint = new TrackPoint(point.getCoordX() - trainToSet.getVector().getVectorX(),
                point.getCoordY() - trainToSet.getVector().getVectorY());

        if (newPoint.equals(point.getNext())) {
            trainToSet.setDirection(true);
        } else if (newPoint.equals(point.getPrevious())) {
            trainToSet.setDirection(false);
        } else {
            newPoint = new TrackPoint(point.getCoordX() + trainToSet.getVector().getVectorX(),
                    point.getCoordY() + trainToSet.getVector().getVectorY());
            if (newPoint.equals(point.getNext())) {
                trainToSet.setDirection(false);
            } else if (newPoint.equals(point.getPrevious())) {
                trainToSet.setDirection(true);
            }
        }

    }

    /**
     * Handles Success Events.
     * 
     * @param checkForCrash the List of Events that should be filtered.
     * @return a List of success.
     */
    public Collection<? extends Event> handleSuccess(List<Event> checkForCrash) {
        List<Event> success = new ArrayList<Event>();
        for (Event event : checkForCrash) {
            if (event.getType().equals("success")) {
                success.add(event);
            }
        }
        return success;
    }

    /**
     * Handles Crash Events.
     * 
     * @param events the List of Events that should be handled.
     * @return list of crashes.
     */
    public Collection<? extends Event> handleCrash(List<Event> events) {
        List<Crash> crashes = new ArrayList<Crash>();
        for (Event event : events) {
            if (event.getType().equals("crash")) {
                crashes.add((Crash) event);
                railWay.removeTrainsFromTrack(event.getTrains());
            }
        }
        return crashes;
    }

}
