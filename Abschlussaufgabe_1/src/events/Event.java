package events;

import java.util.List;

import logic.Train;

/**
 * The Event Class is the upper class of Success and Crash. It is needed for the
 * step command and the crash detection. Whenever a Crash is detected a crash
 * object is created.
 * 
 * @author Christopher Roth
 * @version 1.0
 *
 */
public abstract class Event implements Comparable<Event> {
    /**
     * The id of a event. Is distributed so the events can be sorted.
     */
    protected int id;
    /**
     * The type. Can be either crash or success.
     */
    protected String type;

    /**
     * Getter for the id.
     * 
     * @return the id.
     */
    public int getId() {
        return this.id;
    }

    @Override
    public int compareTo(Event o) {
        return this.getId() - o.getId();
    }

    /**
     * Getter for the type.
     * 
     * @return the type.
     */
    public abstract String getType();

    /**
     * Getter for the trains involved.
     * 
     * @return a cloned version of the trains.
     */
    public abstract List<Train> getTrains();

    @Override
    public boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        if (obj == null) {
            return false;
        }
        Event event = (Event) obj;
        return this.getId() == event.getId();
    }

}
