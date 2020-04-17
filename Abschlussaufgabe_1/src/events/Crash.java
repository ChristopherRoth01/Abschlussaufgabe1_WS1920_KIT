package events;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import logic.Train;

/**
 * A Crash is a Event that is created when trains Crash.
 * 
 * @author Christopher Roth
 * @version 1.0
 *
 */
public class Crash extends Event {

    /**
     * List of crashed Trains.
     */
    private List<Train> crashedTrains;

    /**
     * Constructor.
     * 
     * @param crashedTrains The list of trains that are involved in the crash.
     * @param id            the id of the crash.
     */
    public Crash(List<Train> crashedTrains, int id) {
        this.crashedTrains = crashedTrains;
        Collections.sort(this.crashedTrains);
        this.id = id;
        this.type = "crash";
    }

    @Override
    public int hashCode() {

        return this.id;
    }

    @Override
    public String toString() {
        String toReturn = "Crash of train ";
        for (int i = 0; i < crashedTrains.size(); i++) {
            if (i < crashedTrains.size() - 1) {
                toReturn += crashedTrains.get(i).getId() + ",";
            } else {
                toReturn += crashedTrains.get(i).getId();
            }
        }
        return toReturn;
    }

    @Override
    public String getType() {
        return this.type;
    }

    @Override
    public List<Train> getTrains() {
        return this.clonedTrains();
    }

    /**
     * Clones the list of trains.
     * 
     * @return the cloned version of Trains.
     */
    private List<Train> clonedTrains() {
        List<Train> clone = new ArrayList<Train>();
        for (Train train : this.crashedTrains) {
            clone.add(train);
        }
        return clone;
    }

}
