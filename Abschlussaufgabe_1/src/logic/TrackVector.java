package logic;

/**
 * This class represents the vector of a Track.
 * 
 * @author Christopher Roth
 * @version 1.0
 *
 */
class TrackVector {
    /**
     * x-dimension of the Vector.
     */
    private int vectorX;
    /**
     * y-dimension of the Vector.
     */
    private int vectorY;

    /**
     * Constructor.
     * 
     * @param vectorX the vector in X dimension.
     * @param vectorY the vector in Y dimension.
     */
    TrackVector(int vectorX, int vectorY) {
        this.vectorX = vectorX;
        this.vectorY = vectorY;
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj) {
            return true;
        } 
        if (obj == null) {
            return false;
        }
        TrackVector vector = (TrackVector) obj;
        if (this.vectorX == vector.vectorX && this.vectorY == vector.vectorY) {
            return true;
        } else if (this.vectorX == -vector.vectorX && this.vectorY == vector.vectorY) {
            return true;
        } else if (-this.vectorX == vector.vectorX && this.vectorY == vector.vectorY) {
            return true;
        } else if (this.vectorX == vector.vectorX && -this.vectorY == vector.vectorY) {
            return true;
        } else if (this.vectorX == vector.vectorX && this.vectorY == -vector.vectorY) {
            return true;
        }
        return false;
    }

    @Override
    public int hashCode() {
        return super.hashCode();
    }

    /**
     * Getter for the x-dimension of the Vector.
     * 
     * @return the x-vector
     */
    public int getVectorX() {
        return this.vectorX;
    }

    /**
     * Getter for the y-dimension of the Vector.
     * 
     * @return the y-vector
     */
    public int getVectorY() {
        return this.vectorY;
    }

}
