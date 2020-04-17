package logic;


/**
 * Represents a TrackPoint. Every TrackPoint has a x and a y-Coordinat. Also
 * every TrackPoint knows the next and the previous TrackPoint.
 * 
 * @author Christopher Roth
 * @version 1.0
 *
 */
class TrackPoint {
    /**
     * Represents the Y-coordinate of a TrackPoint.
     */
    private final int coordY;
    /**
     * Represents the X-coordinate of a TrackPoint.
     */
    private final int coordX;

    /**
     * The Track which inherits the Point.
     */
    private NormalTrack currentTrack;
    /**
     * The train which is standing on the TrackPoint.
     */
    private Train currentTrain;
    /**
     * The previous TrackPoint.
     */
    private TrackPoint previous;
    /**
     * The next TrackPoint.
     */
    private TrackPoint next;
    /**
     * boolean which determines if the TrackPoint is active.
     */
    private boolean active;

    /**
     * Constructor for a TrackPoint.
     * 
     * @param coordX the x-Coordinate of the TrackPoint
     * @param coordY the y-Coordinate of the TrackPoint
     */
    TrackPoint(int coordX, int coordY) {
        this.coordX = coordX;
        this.coordY = coordY;

    }

    /**
     * Getter for the current track.
     * 
     * @return the currentTrack
     */
    public NormalTrack getCurrentTrack() {
        return currentTrack;
    }

    /**
     * Setter for the current track.
     * 
     * @param normalTrack the new Track which inehrits the Point.
     */
    public void setCurrentTrack(NormalTrack normalTrack) {
        this.currentTrack = normalTrack;
    }

    /**
     * Getter for the previous TrackPoint.
     * 
     * @return the previous TrackPoint.
     */
    public TrackPoint getPrevious() {
        return previous;
    }
        
    /**
     * Setter for the previous TrackPoint.
     * 
     * @param previous the new previous TrackPoint.
     */
    public void setPrevious(TrackPoint previous) {
        this.previous = previous;
    }


    @Override
    public int hashCode() {

        return super.hashCode();
    }

    /**
     * Getter for the next TrackPoint.
     * 
     * @return the next TrackPoint.
     */
    public TrackPoint getNext() {
        return next;
    }

    /**
     * Setter for the next TrackPoint.
     * 
     * @param next the next TrackPoint.
     */
    public void setNext(TrackPoint next) {
        this.next = next;
    }

    /**
     * Getter for the active boolean.
     * 
     * @return active
     */
    public boolean isActive() {
        return this.active;
    }

    /**
     * Setter for the active boolean.
     * 
     * @param active new active boolean.
     */
    public void setActive(boolean active) {
        this.active = active;
    }

    /**
     * Getter for the currentTrain.
     * 
     * @return the currentTrain
     */
    public Train getCurrentTrain() {
        return currentTrain;
    }

    /**
     * Setter for the currentTrain.
     * 
     * @param currentTrain the new currentTrain.
     */
    public void setCurrentTrain(Train currentTrain) {
        this.currentTrain = currentTrain;
    }

    /**
     * Getter for the y-coordinate.
     * 
     * @return the y-coordinate.
     */
    public int getCoordY() {
        return coordY;
    }

    /**
     * Getter for the x-coordinate.
     * 
     * @return the x-coordinate.
     */
    public int getCoordX() {
        return coordX;
    }

    @Override
    public String toString() {
        return "(" + this.coordX + "," + this.coordY + ")";
    }

    /**
     * Checks if a Point is occupied by a train.
     * 
     * @return true if occupied, false if not.
     */
    public boolean isOccupied() {
        if (this.currentTrain != null) {
            return true;
        }
        return false;
    }


    @Override
    public boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        if (obj == null) {
            return false;
        }
        TrackPoint tp = (TrackPoint) obj;
        return this.coordX == tp.coordX && this.coordY == tp.coordY;

    }

}
