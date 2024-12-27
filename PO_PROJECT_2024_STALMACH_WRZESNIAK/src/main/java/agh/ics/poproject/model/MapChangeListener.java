package agh.ics.poproject.model;

/**
 * The interface responsible for handling operations connected with listeners for changes in Map.
 * Assumes that worldMap interface is defined.
 *
 */
public interface MapChangeListener {
    /**
     *
     *
     * @param worldMap map of the world.
     * @param message message about change on the map.
     */
    void mapChange(WorldMap worldMap, String message);
}
