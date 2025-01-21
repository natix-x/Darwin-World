package agh.ics.poproject.simulation;

import agh.ics.poproject.model.Vector2d;
import agh.ics.poproject.model.map.ZyciodajneTruchla;

import java.util.*;

public class Carcasses {

    private final Map<Vector2d, Integer> carcasses = new HashMap<>();  // positionOfCarcass
    private final List<ZyciodajneTruchla> listeners = new ArrayList<>();
    private final int numberOfDaysAfterCarcassIsRemoved;

    public Carcasses(int numberOfDaysAfterCarcassIsRemoved) {
        this.numberOfDaysAfterCarcassIsRemoved = numberOfDaysAfterCarcassIsRemoved;
    }

    /**
     * Adds a carcass at a given position with a priority of 1 (new carcass).
     * @param position the position of the carcass.
     */
    public void addCarcass(Vector2d position) {
        carcasses.put(position, 1);
        notifyListenersAboutCarcassesUpdate();
    }

    public void subscribe(ZyciodajneTruchla listener) {

        listeners.add(listener);
    }

    public void unsubscribe(ZyciodajneTruchla listener) {
        listeners.remove(listener);
    }

    private void notifyListenersAboutCarcassesUpdate() {
        listeners.forEach(listener -> listener.updateCarcasses(this));
    }

    /**
     * To prioritise recently deceased carcasses. Those older than 8 days old are removed from
     * the priority list.
     *
     */
    public void changeCarcassPriority() {
        Iterator<Map.Entry<Vector2d, Integer>> iterator = carcasses.entrySet().iterator();
        while (iterator.hasNext()) {
            Map.Entry<Vector2d, Integer> entry = iterator.next();
            if (entry.getValue() >= numberOfDaysAfterCarcassIsRemoved) {
                iterator.remove();
            } else {
                carcasses.put(entry.getKey(), entry.getValue() + 1);
            }
        }
        notifyListenersAboutCarcassesUpdate();
    }

    /**
     * Returns all the carcasses.
     * @return the map of carcasses.
     */
    public Map<Vector2d, Integer> getCarcasses() {
        return carcasses;
    }
}

