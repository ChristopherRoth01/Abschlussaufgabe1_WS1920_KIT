package logic;

/**
 * Represents a this. Extends a NormalTrack, and has additional to the
 * NormalTrack, another End, and a switchState.
 * 
 * @author Christopher Roth
 * @version 1.0
 *
 */
class TrackSwitch extends NormalTrack {
    /**
     * Second endPoint of the switch.
     */
    private final TrackPoint end2;
    /**
     * Determines which TrackPoint is the one, the switch is connected to.
     */
    private TrackPoint switchState;

    /**
     * Constructor for a TrackSwitch.
     * 
     * @param start the StartPoint of the TrackSwitch
     * @param end1  the first EndPoint of the TrackSwitch
     * @param end2  the second EndPoint of the TrackSwitch
     */
    TrackSwitch(TrackPoint start, TrackPoint end1, TrackPoint end2) {

        super(start, end1);

        this.isActive = false;
        this.length = this.calcLength();
        this.end2 = end2;
        this.generateAllInheritedPoints();

    }

    /**
     * Sets all TrackPoints of the switch active.
     */
    void setPointsInActive() {
        for (TrackPoint tp : this.inheritedPoints) {
            tp.setActive(false);
        }
    }

    /**
     * Generates all Inherited Points of a Switch.
     */
    private void generateAllInheritedPoints() {
        this.generateInheritedPoints(start, end);
        this.generateInheritedPoints(start, end2);
        for (TrackPoint point : this.inheritedPoints) {
            point.setActive(false);
        }
    }

    @Override
    public int calcLength() {
        if (this.switchState == null) {
            return 0;
        } else {
            if (start.getCoordX() == switchState.getCoordX()) {
                return Math.abs(start.getCoordY() - switchState.getCoordY());
            } else {
                return Math.abs(start.getCoordX() - switchState.getCoordX());
            }
        }
    }

    /**
     * Returns the active Switchend.
     * 
     * @return the active SwitchEnd
     */
    public TrackPoint getActiveSwitchEnd() {
        return this.switchState;
    }

    /**
     * Setter for the active SwitchEnd. Therefore the vectors are recalculated
     * aswell as the length.
     * 
     * @param tp the tp that should be set as the active switchState.
     */
    public void setActiveSwitchEnd(TrackPoint tp) {
        this.switchState = tp;
        this.trackVector = new TrackVector(this.generateVectorX(), this.generateVectorY());

        this.inheritedPoints.clear();

        this.length = this.calcLength();

        this.start.setActive(true);
        this.isActive = true;

    }

    @Override
    public String toString() {

        if (this.length != 0) {
            return "s " + this.trackID + " " + this.start.toString() + " -> " + this.end.toString() + ","
                    + this.end2.toString() + " " + this.length;
        } else {
            return "s " + this.trackID + " " + this.start.toString() + " -> " + this.end.toString() + ","
                    + this.end2.toString();
        }
    }

    @Override
    public int getTrackID() {
        return this.trackID;
    }

    /**
     * Getter for the second End.
     * 
     * @return the second End.
     */
    public TrackPoint getEnd2() {
        return this.end2;
    }

    /**
     * Generates the vectorX depending on the start and active Switch End.
     * 
     * @return the new VectorX
     */
    private int generateVectorX() {
        if (switchState == null) {
            return 0;
        }
        if (start.getCoordX() == switchState.getCoordX()) {
            return 0;
        } else if (start.getCoordX() > switchState.getCoordX()) {
            return -1;
        } else {
            return 1;
        }
    }

    /**
     * Generates the vectorY depending on the start and active Switch End.
     * 
     * @return the new VectorY
     */
    private int generateVectorY() {
        if (switchState == null) {
            return 0;
        }
        if (start.getCoordY() == switchState.getCoordY()) {
            return 0;
        } else if (start.getCoordY() > switchState.getCoordY()) {
            return -1;
        } else {
            return 1;
        }
    }

    /**
     * Sets all points to active.
     */
    public void setActive() {
        this.start.setActive(true);
        this.end.setActive(true);
        this.end2.setActive(true);
    }

    @Override
    public void linkPointsOppositeDirection() {
        if (this.onePossibleLengthIsOne()) {
            this.start.setPrevious(this.end);
            this.end.setNext(this.start);
        } else {
            this.generateInheritedPoints(this.getEnd(), this.getStart());
            this.getStart().setPrevious(this.inheritedPoints.get(this.inheritedPoints.size() - 1));
            this.getEnd().setNext(this.inheritedPoints.get(0));
            this.getEnd2().setNext(this.inheritedPoints.get(0));
            this.inheritedPoints.get(0).setPrevious(this.getEnd());
            this.inheritedPoints.get(this.inheritedPoints.size() - 1).setNext(this.getStart());
        }
    }

    /**
     * Checks if a possible length of the track could be 1.
     * @return true if so, false if not
     */
    private boolean onePossibleLengthIsOne() {
        if (Math.abs(start.getCoordY() - end.getCoordY()) == 1) {
            return true;
        } else if (Math.abs(start.getCoordY() - end2.getCoordY()) == 1) {
            return true;
        } else {
            return false;
        }
    }

    @Override
    public void linkNormalDirection() {
        if (this.onePossibleLengthIsOne()) {
            this.start.setNext(this.end);
            this.end.setPrevious(this.start);
        } else {
            this.generateInheritedPoints(this.start, this.getEnd());
            this.start.setNext(this.inheritedPoints.get(0));
            this.getEnd().setPrevious(this.inheritedPoints.get(this.inheritedPoints.size() - 1));
            this.getEnd2().setPrevious(this.inheritedPoints.get(this.inheritedPoints.size() - 1));
            this.inheritedPoints.get(0).setPrevious(this.start);
            this.inheritedPoints.get(this.inheritedPoints.size() - 1).setNext(this.getEnd());
        }
    }

    /**
     * Getter for the length of a switch.
     * @return the length of the switch.
     */
    public long getLength() {
        return this.calcLength();
    }
}
