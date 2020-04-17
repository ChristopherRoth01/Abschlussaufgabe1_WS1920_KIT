package logic;

import java.util.ArrayList;

import java.util.List;

/**
 * This Class represents the TrackNetwork.
 * 
 * @author Christopher Roth
 * @version 1.0
 *
 */
public class TrackNetwork {

    private TrackNetworkActions trackNetworkActions;
    private boolean firstTrack = true;
    private List<NormalTrack> trackList;
    private List<TrackSwitch> switchList;
    private List<TrackPoint> trackConnectionPointList;
    private List<TrackPoint> allTrackPoints;

    /**
     * Constructor. Initialises all Lists.
     */
    public TrackNetwork() {
        trackList = new ArrayList<NormalTrack>();
        trackConnectionPointList = new ArrayList<TrackPoint>();
        switchList = new ArrayList<TrackSwitch>();
        allTrackPoints = new ArrayList<TrackPoint>();
        trackNetworkActions = new TrackNetworkActions(this);
    }

    /**
     * Getter.
     * 
     * @return trackNetworkActions
     */
    public TrackNetworkActions getTrackNetworkActions() {
        return trackNetworkActions;
    }

    /**
     * Returns the number of connected Tracks from a Point.
     * 
     * @param trackStartPoint the point the number of connected Tracks should be
     *                        calculated from.
     * @return the number of connected Tracks.
     */
    int numberOfConnectedTracks(TrackPoint trackStartPoint) {
        int number = 0;
        for (NormalTrack nt : this.trackList) {
            if (nt.getStart().equals(trackStartPoint) || nt.getEnd().equals(trackStartPoint)) {
                number = number + 1;
            }
        }
        return number;

    }

    /**
     * Checks if the geometry of the Points is valid. Also checks if the points are
     * identical.
     * 
     * @param start the StartPoint of a Track
     * @param end   the EndPoint of a Track
     * @return true if everything is valid
     * @throws LogicException if anything is not valid.
     */
    boolean checkGeometryOfPointsNormal(TrackPoint start, TrackPoint end) throws LogicException {
        if (start.equals(end)) {
            throw new LogicException(ErrorMessages.POINTS_ARE_THE_SAME.getMessage());
        }
        if (start.getCoordX() != end.getCoordX() && start.getCoordY() != end.getCoordY()) {
            throw new LogicException(ErrorMessages.POINTS_NOT_CORRECT.getMessage());
        }
        return true;
    }

    /**
     * Checks if a Track with the given startPoint and endPoint already exists.
     * 
     * @param start the startPoint of a Track.
     * @param end   the endPoint of a Track.
     * @return false if the track is not existing.
     * @throws LogicException if the track is existing.
     */
    boolean checkIfTrackExists(TrackPoint start, TrackPoint end) throws LogicException {
        for (NormalTrack tr : this.trackList) {
            if (tr.getStart().equals(start) && tr.getEnd().equals(end)
                    || tr.getStart().equals(end) && tr.getEnd().equals(start)) {
                throw new LogicException(ErrorMessages.TRACK_ALREADY_EXISTS.getMessage());
            }
        }
        return false;
    }

    /**
     * Finds a switch in the TrackNet with a given id.
     * 
     * @param id the id of the switch that should be found.
     * @return the fitting switch.
     */
    TrackSwitch findSwitch(String id) {
        for (TrackSwitch ts : switchList) {
            if (ts.getTrackID() == Integer.parseInt(id)) {
                return ts;
            }
        }
        return null;
    }

    /**
     * Returns the Track that inherits the given point.
     * 
     * @param point the point of which the track should be
     * @return the Track that inherits the given Point.
     */
    NormalTrack getTrackWithPoint(TrackPoint point) {
        for (NormalTrack nt : this.trackList) {
            if (nt.getInheritedPoints().contains(point)) {
                if (!this.trackConnectionPointList.contains(point)) {
                    return nt;
                }
            }
        }
        return null;
    }

    /**
     * Adds TrackPoints to the trackConnectionPointList.
     * 
     * @param trackStartPoint the first point to be added.
     * @param trackEnd1Point  the second point to be added.
     * @param trackEnd2Point  the third point to be added.
     */
    void addPoints(TrackPoint trackStartPoint, TrackPoint trackEnd1Point, TrackPoint trackEnd2Point) {
        if (this.findTrackPointNull(trackStartPoint) == null) {
            trackConnectionPointList.add(trackStartPoint);
        }
        if (this.findTrackPointNull(trackEnd1Point) == null) {
            trackConnectionPointList.add(trackEnd1Point);
        }
        if (this.findTrackPointNull(trackEnd2Point) == null) {
            trackConnectionPointList.add(trackEnd2Point);
        }
    }

    /**
     * Adds TrackPoints to the trackConnectionPointList.
     * 
     * @param trackStartPoint the first point to be added.
     * @param trackEndPoint   the second point to be added.
     */
    void addPoints(TrackPoint trackStartPoint, TrackPoint trackEndPoint) {
        if (this.findTrackPointNull(trackStartPoint) == null) {
            trackConnectionPointList.add(trackStartPoint);
        }
        if (this.findTrackPointNull(trackEndPoint) == null) {
            trackConnectionPointList.add(trackEndPoint);
        }
    }

    /**
     * Checks if the TrackPointList contains a TrackPoint. If not it returns the
     * given TrackPoint. This method helps in the add track and add switch method.
     * 
     * @param trackPoint the trackPoint that should be found.
     * @return the given TrackPoint, or the aquivalent point in the list.
     */
    public TrackPoint findTrackPoint(TrackPoint trackPoint) {
        return this.trackConnectionPointList.stream().filter(s -> s.equals(trackPoint)).findFirst().orElse(trackPoint);
    }

    /**
     * /** Finds a TrackPoint in the trackConnectionPointList.
     * 
     *
     * @param trackPoint the TrackPoint that should be searched.
     * @return the TrackPoint that equals or null.
     */
    TrackPoint findTrackPointNull(TrackPoint trackPoint) {
        return this.trackConnectionPointList.stream().filter(s -> s.equals(trackPoint)).findFirst().orElse(null);
    }

    /**
     * Creates a TrackPoint out of a String Array.
     * 
     * @param input the Array that should be converted into a TrackPoint.
     * @return the TrackPoint that is created out of the String Array.
     */
    public TrackPoint createTrackPoint(String[] input) {
        TrackPoint newPoint = new TrackPoint(Integer.parseInt(input[0].replace("(", "")),
                Integer.parseInt(input[1].replace(")", "")));
        return newPoint;
    }

    /**
     * Returns the Stringrepresentation of the Tracks in the TrackNetwork.
     * 
     * @return the trackList as String.
     */
    public String trackListToString() {
        String toReturn = "";
        for (NormalTrack track : this.trackList) {
            toReturn += track.toString() + "\n";
        }
        return toReturn.trim();
    }

    /**
     * Checks if there are already tracks present in the TrackNetwork
     * 
     * @return true if trackList is empty, false if else.
     */
    public boolean isEmpty() {
        if (this.trackList.isEmpty()) {
            return true;
        } else {
            return false;
        }
    }

    /**
     * Checks if a point is inherited.
     * 
     * @param point the point to find.
     * @return the point that equals or null
     */
    TrackPoint checkIfInherited(TrackPoint point) {
        return this.allTrackPoints.stream().filter(s -> s.equals(point)).findAny().orElse(null);
    }

    /**
     * Adds a List of TrackPoints to the allTrackPointList. Only adds if
     * allTrackPoints does not contain the points.
     * 
     * @param points the list of points to be added.
     */
    void addTrackPoints(List<TrackPoint> points) {
        for (TrackPoint point : points) {
            if (!allTrackPoints.contains(point)) {
                allTrackPoints.add(point);
            }
        }
    }

    /**
     * Checks if unset switches are present in the trackNet.
     * 
     * @return true if unset switches are present, false if not
     */
    boolean switchUnset() {
        for (TrackSwitch ts : this.switchList) {
            if (ts.getActiveSwitchEnd() == null) {
                return true;
            }
        }
        return false;
    }

    /**
     * Removes all points of a given switch form the allTrackPointsList.
     * 
     * @param switchToSet the switch of which the Points should be removed.
     */
    void removeTrackPoints(TrackSwitch switchToSet) {
        this.allTrackPoints.removeAll(switchToSet.inheritedPoints);
    }

    /**
     * Finds a track with a given id in the railNet.
     * 
     * @param trackId the id of the track that should be returned.
     * @return the track with the given id
     * @throws LogicException if track with id does not exist.
     */
    NormalTrack findTrack(int trackId) throws LogicException {
        for (NormalTrack nt : this.trackList) {
            if (nt.getTrackID() == trackId) {
                return nt;
            }
        }
        throw new LogicException(ErrorMessages.TRACK_NOT_EXISTING.getMessage());
    }

    /**
     * Returns the number of connected Tracks from a Track.
     * 
     * @param trackToDelete the track the number of connected Tracks should be
     *                      calculated for.
     * @return the number of connected Tracks.
     */
    int numberOfConnectedTracks(NormalTrack trackToDelete) {
        int number = 0;
        TrackPoint start = trackToDelete.getStart();
        for (NormalTrack nt : trackList) {
            if (!nt.equals(trackToDelete) && nt.getStart().equals(start) || nt.getEnd().equals(start)) {
                number = number + 1;
            }
        }
        TrackPoint end = trackToDelete.getEnd();
        for (NormalTrack nt : trackList) {
            if (!nt.equals(trackToDelete) && (nt.getStart().equals(end) || nt.getEnd().equals(end))) {
                number = number + 1;
            }
        }
        return number;
    }

    /**
     * Checks if a way exists from the startPoint to the endPoint, without the track
     * that should be deleted.
     * 
     * @param trackToDelete the track to delete
     * @param start         the startPoint
     * @param end           the endPoint
     * @return true if way exists, false if not.
     */
    boolean existingWayWithout(NormalTrack trackToDelete, TrackPoint start, TrackPoint end) {
        boolean wayWithOut = false;
        TrackPoint startPoint = start;
        if (start == null) {
            return false;
        }
        if (start.equals(end)) {
            return true;
        }
        List<TrackPoint> pointsWithOutTrack;
        if (trackToDelete.gotPoint(startPoint.getNext()) != null) {
            pointsWithOutTrack = this.getAllPreviousPoints(startPoint);
            for (int i = 0; i < pointsWithOutTrack.size(); i++) {
                if (startPoint.equals(end)) {
                    return true;
                }
                startPoint = startPoint.getPrevious();
                wayWithOut = false;
            }
        } else if (trackToDelete.gotPoint(trackToDelete.start.getPrevious()) != null) {
            pointsWithOutTrack = this.getAllNextPoints(startPoint);
            for (int i = 0; i < pointsWithOutTrack.size(); i++) {
                if (startPoint.equals(end)) {
                    return false;
                }
                startPoint = startPoint.getNext();
                wayWithOut = true;
            }
        }
        return wayWithOut;
    }

    /**
     * Gets all Points from a startPoint in the next-direction.
     * 
     * @param start the startPoint the connected points should be returned from
     * @return the List of next TrackPoints.
     */
    private List<TrackPoint> getAllNextPoints(TrackPoint start) {
        List<TrackPoint> nextPoints = new ArrayList<TrackPoint>();
        TrackPoint next = start.getNext();
        for (int i = 0; i < allTrackPoints.size(); i++) {
            if (next == null) {
                break;
            }
            nextPoints.add(next);
            next = next.getNext();
        }
        return nextPoints;
    }

    /**
     * Gets all Points from a startPoint in the previous-direction.
     * 
     * @param start the startPoint the connected points should be returned from
     * @return the List of previous TrackPoints.
     */
    private List<TrackPoint> getAllPreviousPoints(TrackPoint start) {
        List<TrackPoint> previousPoints = new ArrayList<TrackPoint>();
        TrackPoint next = start.getPrevious();
        for (int i = 0; i < allTrackPoints.size(); i++) {
            if (next == null) {
                break;
            }
            previousPoints.add(next);
            next = next.getPrevious();
        }
        return previousPoints;
    }

    /**
     * Removes a Track from the TrackNet.
     * 
     * @param trackToDelete the track to be deleted.
     */
    void remove(NormalTrack trackToDelete) {
        this.trackList.remove(trackToDelete);
        this.switchList.remove(trackToDelete);
        this.allTrackPoints.removeAll(trackToDelete.getInheritedPoints());

    }

    /**
     * Getter for the TrackList. Returns a cloned Version.
     * 
     * @return the cloned tracklist
     */
    public List<NormalTrack> getTrackList() {
        return this.clonedTrackList();
    }

    private List<NormalTrack> clonedTrackList() {
        List<NormalTrack> clonedVersion = new ArrayList<NormalTrack>();
        for (NormalTrack nt : this.trackList) {
            clonedVersion.add(nt);
        }
        return clonedVersion;
    }

    /**
     * Sets the boolean first Track true.
     */
    void setFirstTrackTrue() {
        this.firstTrack = true;
    }

    /**
     * Calculates the total length of the TrackNetwork.
     * 
     * @return the total length.
     */
    public int getTotalLength() {
        int totalLength = 0;
        for (NormalTrack nt : this.trackList) {
            totalLength += nt.getLength();
        }
        return totalLength;
    }

    /**
     * Setter for the firstTrack-Attribute.
     * 
     * @param firstTrack the new firstTrack value
     */
    public void setFirstTrack(boolean firstTrack) {
        this.firstTrack = firstTrack;
    }

    /**
     * Getter for the firstTrack-Attribute.
     * 
     * @return the firstTrack-Attribute.
     */
    public boolean isFirstTrack() {
        return this.firstTrack;
    }

    /**
     * Adds a TrackPoint to the allTrackPointList.
     * 
     * @param trackPoint the point to be added.
     */
    public void add(TrackPoint trackPoint) {
        this.allTrackPoints.add(trackPoint);

    }

    /**
     * Adds a normal Track to the network.
     * 
     * @param nt the track to be added.
     */
    public void addNormalTrackToNetwork(NormalTrack nt) {
        this.trackList.add(nt);

    }

    /**
     * Adds a switch to the network.
     * 
     * @param trackSwitch the switch to be added.
     */
    public void addSwitchToNetwork(TrackSwitch trackSwitch) {
        this.switchList.add(trackSwitch);

    }
}