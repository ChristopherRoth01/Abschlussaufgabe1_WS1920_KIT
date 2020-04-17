package events;

import java.util.List;

import logic.Train;

/**
 * A Success is a Event that occurs if a train moved successfully.
 * 
 * @author Christopher Roth
 * @version 1.0
 */
public class Success extends Event {

    private Train train1;

    /**
     * Constructor.
     * 
     * @param train1 The train that moved successfully.
     * @param id     the id of the success.
     */
    public Success(Train train1, int id) {
        this.train1 = train1;
        this.id = id;
        this.type = "success";
    }

    @Override
    public int hashCode() {

        return this.id;
    }

    @Override
    public String toString() {
        String toReturn = "Train " + train1.getId() + " at " + train1.getHeadPoint();
        return toReturn;
    }

    @Override
    public String getType() {
        return this.type;
    }

    @Override
    public List<Train> getTrains() {
        return null;
    }
}
