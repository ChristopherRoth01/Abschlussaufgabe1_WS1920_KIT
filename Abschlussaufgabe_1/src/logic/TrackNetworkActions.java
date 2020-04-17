package logic;

/**
 * Class which is responsible for the adding of Tracks and Switcbes to the TrackNetwork.
 * @author Christopher Roth
 * @version 1.0
 *
 */
public class TrackNetworkActions {
    
    private TrackNetwork netWork;
    
    /**
     * 
     * Constructor.
     * @param netWork the netWork the tracks should be added to.
     */
    TrackNetworkActions(TrackNetwork netWork) {
        this.netWork = netWork;
    }
    

    /**
     * Adds a switch to the TrackNet. The Switch is not set.
     * 
     * @param start startPoint
     * @param end1  first endPoint
     * @param end2  second endPoint
     * @return the id of the switch if everything went alright.
     * @throws LogicException if the switch is not attached to any other track.
     */
    public String addSwitch(String start, String end1, String end2) throws LogicException {
        TrackPoint trackStartPoint = netWork.createTrackPoint(start.split(","));
        TrackPoint trackEnd1Point = netWork.createTrackPoint(end1.split(","));
        TrackPoint trackEnd2Point = netWork.createTrackPoint(end2.split(","));
        netWork.checkIfTrackExists(trackStartPoint, trackEnd1Point);
        netWork.checkIfTrackExists(trackStartPoint, trackEnd2Point);
        netWork.checkGeometryOfPointsNormal(trackStartPoint, trackEnd1Point);
        netWork.checkGeometryOfPointsNormal(trackStartPoint, trackEnd2Point);
        TrackSwitch trackSwitch = null;
        if (netWork.isFirstTrack()) {
            netWork.setFirstTrack(false);
            trackSwitch = new TrackSwitch(trackStartPoint, trackEnd1Point, trackEnd2Point);
            trackSwitch.setActive();
            trackSwitch.linkNormalDirection();
            netWork.addPoints(trackStartPoint, trackEnd1Point, trackEnd2Point);
            netWork.addTrackPoints(trackSwitch.getInheritedPoints());
            trackSwitch.setPointsInActive();
            netWork.add(trackStartPoint);
            netWork.add(trackEnd1Point);
            netWork.add(trackEnd2Point);
            netWork.addNormalTrackToNetwork(trackSwitch);
            netWork.addSwitchToNetwork(trackSwitch);
            return "" + trackSwitch.getTrackID();
        } else {
            if ((netWork.findTrackPointNull(trackStartPoint) == null
                    && netWork.findTrackPointNull(trackEnd1Point) == null
                    && netWork.findTrackPointNull(trackEnd2Point) == null)) {
                throw new LogicException(ErrorMessages.TRACK_NOT_CONNECTED.getMessage().toString());
            }
            if (netWork.numberOfConnectedTracks(trackStartPoint) == 2 
                    || netWork.numberOfConnectedTracks(trackEnd1Point) == 2
                    || netWork.numberOfConnectedTracks(trackEnd2Point) == 2) {
                throw new LogicException(ErrorMessages.POINT_HAS_TWO_CONNECTED.getMessage());
            }
            TrackPoint newTrackStart = netWork.findTrackPoint(trackStartPoint);
            TrackPoint newTrackEnd1 = netWork.findTrackPoint(trackEnd1Point);
            TrackPoint newTrackEnd2 = netWork.findTrackPoint(trackEnd2Point);
            trackSwitch = new TrackSwitch(newTrackStart, newTrackEnd1, newTrackEnd2);
            trackSwitch.setActive();
            netWork.addPoints(newTrackStart, newTrackEnd1, newTrackEnd2);
            netWork.addNormalTrackToNetwork(trackSwitch);
            netWork.addSwitchToNetwork(trackSwitch);
            if (trackSwitch.getStart().getPrevious() != null
                    && (trackSwitch.getEnd().getNext() == null || trackSwitch.getEnd2().getNext() == null)
                    || trackSwitch.getStart().getPrevious() == null
                            && (trackSwitch.getEnd().getNext() != null || trackSwitch.getEnd2().getNext() != null)) {
                trackSwitch.linkNormalDirection();
            } else if (trackSwitch.getStart().getNext() == null
                    && (trackSwitch.getEnd().getPrevious() != null || trackSwitch.getEnd2().getPrevious() != null)
                    || trackSwitch.getStart().getNext() != null && (trackSwitch.getEnd().getPrevious() == null
                            || trackSwitch.getEnd2().getPrevious() == null)) {

                trackSwitch.linkPointsOppositeDirection();
            }
            if (netWork.checkIfInherited(newTrackStart) == null) {
                netWork.add(newTrackStart);
            }
            if (netWork.checkIfInherited(newTrackEnd1) == null) {
                netWork.add(newTrackEnd1);
            }
            if (netWork.checkIfInherited(newTrackEnd2) == null) {
                netWork.add(newTrackEnd2);
            }
            netWork.addTrackPoints(trackSwitch.getInheritedPoints());
            trackSwitch.setPointsInActive();
            return "" + trackSwitch.getTrackID();
        }

    }

    /**
     * Adds a normal Track to the TrackNetwork. Therefore it checks if the start and
     * endpoint already exist and if all logic conditions are given.
     * 
     * @param start startPoint of track
     * @param end   endPoint of track
     * @return the trackId if everything went alright
     * @throws LogicException if track is not aligned correctly, if track has no
     *                        connection points
     */
    public String addNormalTrack(String start, String end) throws LogicException {
        String[] startpoint = start.split(",");
        String[] endpoint = end.split(",");
        TrackPoint trackStartPoint = netWork.createTrackPoint(startpoint);
        TrackPoint trackEndPoint = netWork.createTrackPoint(endpoint);
        NormalTrack newTrack;
        netWork.checkIfTrackExists(trackStartPoint, trackEndPoint);

        netWork.checkGeometryOfPointsNormal(trackStartPoint, trackEndPoint);
        if (netWork.isFirstTrack()) {
            if (netWork.numberOfConnectedTracks(trackStartPoint) == 2
                    || netWork.numberOfConnectedTracks(trackEndPoint) == 2) {
                throw new LogicException(ErrorMessages.POINT_HAS_TWO_CONNECTED.getMessage());
            }
            netWork.setFirstTrack(false);
            netWork.addPoints(trackStartPoint, trackEndPoint);
            newTrack = new NormalTrack(trackStartPoint, trackEndPoint);
            newTrack.setActive();
            newTrack.linkNormalDirection();
            netWork.addNormalTrackToNetwork(newTrack);
            netWork.add(trackStartPoint);
            netWork.addTrackPoints(newTrack.getInheritedPoints());
            netWork.add(trackEndPoint);
            return "" + newTrack.getTrackID();
        } else {

            if ((netWork.findTrackPointNull(trackStartPoint) == null
                    && netWork.findTrackPointNull(trackEndPoint) == null)) {
                throw new LogicException(ErrorMessages.TRACK_NOT_CONNECTED.getMessage().toString());
            }
            TrackPoint newTrackStart = netWork.findTrackPoint(trackStartPoint);
            TrackPoint newTrackEnd = netWork.findTrackPoint(trackEndPoint);
            if (netWork.numberOfConnectedTracks(newTrackStart) == 2 
                    || netWork.numberOfConnectedTracks(newTrackEnd) == 2) {
                throw new LogicException(ErrorMessages.POINT_HAS_TWO_CONNECTED.getMessage());
            }
            newTrack = new NormalTrack(newTrackStart, newTrackEnd);
            newTrack.setActive();
            if (netWork.findTrackPointNull(trackEndPoint) == null) {
                if (newTrackStart.getNext() != null) {
                    newTrack.linkPointsOppositeDirection();
                } else {
                    newTrack.linkNormalDirection();
                }
            } else if (netWork.findTrackPointNull(trackStartPoint) == null) {
                if (newTrackEnd.getPrevious() != null) {
                    newTrack.linkPointsOppositeDirection();
                } else {
                    newTrack.linkPointsOppositeDirection();
                }
            } else {
                if (newTrackStart.getPrevious() != null) {
                    newTrack.linkNormalDirection();
                } else if (newTrackStart.getNext() != null) {
                    newTrack.linkPointsOppositeDirection();
                }
            }
            if (netWork.findTrackPointNull(trackStartPoint) == null) {
                netWork.add(newTrackStart);
            }
            netWork.addTrackPoints(newTrack.getInheritedPoints());
            if (netWork.findTrackPointNull(trackEndPoint) == null) {
                netWork.add(newTrackEnd);
            }
            netWork.addPoints(newTrackStart, newTrackEnd);
            netWork.addNormalTrackToNetwork(newTrack);
            return "" + newTrack.getTrackID();
        }
    }


}
