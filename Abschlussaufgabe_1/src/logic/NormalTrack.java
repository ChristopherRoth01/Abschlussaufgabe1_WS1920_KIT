package logic;

import java.util.ArrayList;
import java.util.Collections;
import java.util.LinkedList;
import java.util.List;

/**
 * Class for a NormalTrack with a Startpoint and an Endpoint. A Track has a
 * length aswell as a direction. Also a Track can be active or inactive.
 * 
 * @author Christopher Roth
 * @version 1.0
 *
 */
class NormalTrack {

    /**
     * A static counter for the distribution of Ids
     */
    private static int counter = 0;
    /**
     * List holding Ids for tracks and switches.
     */
    private static List<Integer> trackIds = new ArrayList<Integer>();
    /**
     * The id of a track
     */
    protected final int trackID;

    /**
     * StartPoint of a Track.
     */
    protected final TrackPoint start;
    /**
     * EndPoint of a Track
     */
    protected final TrackPoint end;

    /**
     * The Vector of a Track.
     * 
     */
    protected TrackVector trackVector;

    /**
     * boolean which determines if a track is active
     */
    protected boolean isActive;
    /**
     * The list of inherited Points of the track.
     */
    protected List<TrackPoint> inheritedPoints;
    /**
     * length of a Track
     */
    protected long length;

    /**
     * List of connected tracks.
     */
    private List<NormalTrack> connectedTracks;
    /**
     * vector in x dimension
     */
    private int vectorX;
    /**
     * Vector in y dimension
     */
    private int vectorY;

    /**
     * Constructor for a normal Track
     * 
     * @param start startPoint of the Track
     * @param end   endPoint of the Track
     */
    NormalTrack(TrackPoint start, TrackPoint end) {

        this.isActive = true;
        this.start = start;
        this.end = end;
        this.start.setActive(true);
        this.end.setActive(true);
        this.length = this.calcLength();
        this.trackID = this.returnNextFreeId();
        this.inheritedPoints = new LinkedList<TrackPoint>();
        this.generateInheritedPoints(start, end);
        this.vectorX = this.generateVectorX(start, end);
        this.vectorY = this.generateVectorY(start, end);
        this.trackVector = new TrackVector(this.vectorX, this.vectorY);
        this.connectedTracks = new ArrayList<NormalTrack>();

    }

    /**
     * Getter for the x-vector of a Track.
     * 
     * @return vectorX
     */
    public int getVectorX() {
        return this.vectorX;

    }

    /**
     * Getter for the y-vector of a Track.
     * 
     * @return vectorY
     */
    public int getVectorY() {
        return this.vectorY;
    }

    /**
     * Generates the x-vector in dependance of start and endPoint. Vector can bei
     * either 0,1,-1.
     * 
     * @return the matching vector.
     */
    private int generateVectorX(TrackPoint start, TrackPoint end) {
        if (start.getCoordX() == end.getCoordX()) {
            return 0;
        } else if (start.getCoordX() > end.getCoordX()) {
            return -1;
        } else {
            return 1;
        }
    }

    /**
     * Generates the y-vector in dependance of start and endPoint. Vector can bei
     * either 0,1,-1.
     * 
     * @return the matching vector.
     */
    private int generateVectorY(TrackPoint start, TrackPoint end) {
        if (start.getCoordY() == end.getCoordY()) {
            return 0;
        } else if (start.getCoordY() > end.getCoordY()) {
            return -1;
        } else {
            return 1;
        }
    }

    /**
     * Help method for the Generation of Inherited Points of a Track. Sets the point
     * in a active state. Links the point to the previous and next point.
     * 
     * @param newPoint the point to be linked.
     */
    private void generateInheritedPointsUtility(TrackPoint newPoint) {
        if (this.inheritedPoints.size() >= 1) {
            newPoint.setPrevious(inheritedPoints.get(inheritedPoints.size() - 1));
            inheritedPoints.get(inheritedPoints.size() - 1).setNext(newPoint);
        }
        newPoint.setCurrentTrack(this);
        newPoint.setActive(true);

        this.inheritedPoints.add(newPoint);

    }

    /**
     * Generates a List of TrackPoints the Track covers.
     * 
     * @param start startPoint of the Track
     * @param end   endPoint of the Track
     */
    void generateInheritedPoints(TrackPoint start, TrackPoint end) {
        TrackPoint newPoint;
        if (this.length != 1) {
            if (start.getCoordX() < end.getCoordX()) {

                for (int i = start.getCoordX() + 1; i < end.getCoordX(); i++) {
                    newPoint = new TrackPoint(i, start.getCoordY());
                    this.generateInheritedPointsUtility(newPoint);

                }

            } else if (start.getCoordX() > end.getCoordX()) {
                for (int i = start.getCoordX() - 1; i > end.getCoordX(); i--) {
                    newPoint = new TrackPoint(i, start.getCoordY());
                    this.generateInheritedPointsUtility(newPoint);
                }

            } else if (start.getCoordY() < end.getCoordY()) {
                for (int i = start.getCoordY() + 1; i < end.getCoordY(); i++) {
                    newPoint = new TrackPoint(start.getCoordX(), i);
                    this.generateInheritedPointsUtility(newPoint);
                }

            } else if (start.getCoordY() > end.getCoordY()) {
                for (int i = start.getCoordY() - 1; i > end.getCoordY(); i--) {
                    newPoint = new TrackPoint(start.getCoordX(), i);
                    this.generateInheritedPointsUtility(newPoint);

                }
            }
        }
    }

    /**
     * Calculates the Length of a track in dependency of the start and
     * end-Coordinates.
     * 
     * @return the amount of the lenght.
     */
    public int calcLength() {
        if (start.getCoordX() == end.getCoordX()) {
            return Math.abs(start.getCoordY() - end.getCoordY());
        } else {
            return Math.abs(start.getCoordX() - end.getCoordX());
        }
    }

    /**
     * Getter for startPoint
     * 
     * @return startPoint
     */
    public TrackPoint getStart() {
        return start;
    }

    /**
     * Getter for endPoint
     * 
     * @return endPoint
     */
    public TrackPoint getEnd() {
        return end;
    }

    /**
     * Returns the next free id.
     * 
     * @return the next free id.
     */
    private int returnNextFreeId() {
        if (trackIds.isEmpty()) {
            counter++;
            trackIds.add(counter);
            Collections.sort(trackIds);

            return trackIds.remove(0);
        } else {
            Collections.sort(trackIds);
            Collections.reverse(trackIds);
            return trackIds.remove(trackIds.size() - 1);
        }
    }

    @Override
    public String toString() {
        return "t " + this.trackID + " " + this.start.toString() + " -> " + this.end.toString() + " " + this.length;
    }

    /**
     * Getter for trackId.
     * 
     * @return the trackId.
     */
    public int getTrackID() {
        return this.trackID;
    }

    /**
     * Checks if Track inherits a given point.
     * 
     * @param point the Point to check
     * @return the Point that equals to the point to check or null.
     */
    TrackPoint gotPoint(TrackPoint point) {
        for (TrackPoint tp : this.inheritedPoints) {
            if (tp.equals(point)) {
                return tp;
            }
        }
        return null;
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        if (obj == null) {
            return false;
        }
        NormalTrack track = (NormalTrack) obj;
        return (this.start.equals(track.start) && this.end.equals(track.end))
                || (this.start.equals(track.end) && this.end.equals(track.start)) || this.trackID == track.trackID;
    }

    /**
     * Clears the list of inheritedPoints.
     */
    void deleteInheritedPoints() {
        this.inheritedPoints.clear();

    }

    /**
     * 
     * Getter for the first point of the inheritedPoints List.
     * 
     * @return the first point.
     */
    public TrackPoint getFirstInherited() {
        if (this.inheritedPoints.size() >= 1) {
            return this.inheritedPoints.get(0);
        }
        return null;
    }

    /**
     * Getter for the last point of the inheritedPoints List.
     * 
     * @return the last point.
     */
    public TrackPoint getLastInherited() {
        if (this.inheritedPoints.size() == 1) {
            return this.getFirstInherited();
        } else if (this.inheritedPoints.size() > 1) {
            return this.inheritedPoints.get(this.inheritedPoints.size() - 1);
        }
        return null;
    }

    /**
     * Getter for the list of inherited Points. Returns a cloned version.
     * 
     * @return a cloned list of inherited Points.
     */
    public ArrayList<TrackPoint> getInheritedPoints() {
        return this.cloneInheritedPoints();
    }

    /**
     * Clones the list of inherited Points.
     * 
     * @return the cloned List.
     */
    private ArrayList<TrackPoint> cloneInheritedPoints() {
        ArrayList<TrackPoint> cloned = new ArrayList<TrackPoint>();
        for (TrackPoint tp : this.inheritedPoints) {
            cloned.add(tp);
        }
        return cloned;
    }

    /**
     * Links the first and last inherited point to end and start in opposite
     * direction.
     */
    public void linkPointsOppositeDirection() {
        if (this.calcLength() == 1) {
            this.start.setPrevious(this.end);
            this.end.setNext(this.start);
        } else {
            this.deleteInheritedPoints();
            this.generateInheritedPoints(this.getEnd(), this.getStart());
            this.getStart().setPrevious(this.inheritedPoints.get(this.inheritedPoints.size() - 1));
            this.getEnd().setNext(this.inheritedPoints.get(0));
            this.inheritedPoints.get(0).setPrevious(this.getEnd());
            this.inheritedPoints.get(this.inheritedPoints.size() - 1).setNext(this.getStart());
        }
    }

    /**
     * Links the first and the last inherited point to start and end in normal
     * direction.
     */
    public void linkNormalDirection() {
        if (this.calcLength() == 1) {
            this.start.setNext(this.end);
            this.end.setPrevious(this.start);
        } else {
            this.deleteInheritedPoints();
            this.generateInheritedPoints(this.getStart(), this.getEnd());
            this.getEnd().setPrevious(this.inheritedPoints.get(this.inheritedPoints.size() - 1));

            this.getStart().setNext(this.inheritedPoints.get(0));
            this.inheritedPoints.get(0).setPrevious(this.getStart());
            this.inheritedPoints.get(this.inheritedPoints.size() - 1).setNext(this.getEnd());
        }
    }

    /**
     * Sets all connection points active.
     */
    public void setActive() {
        this.getStart().setActive(true);
        this.getEnd().setActive(true);
    }

    /**
     * Removes a id to the list of possible ids for a Track.
     */
    void resetId() {
        trackIds.add(this.trackID);
    }

    @Override
    public int hashCode() {
        return this.trackID;
    }

    /**
     * Getter for the vector.
     * 
     * @return the Vector of the Track.
     */
    public TrackVector getVector() {
        return this.trackVector;
    }

    /**
     * 
     * Getter for the calculated length of the Track.
     * 
     * @return the length of the Track
     */
    public long getLength() {
        return this.length;
    }

}
