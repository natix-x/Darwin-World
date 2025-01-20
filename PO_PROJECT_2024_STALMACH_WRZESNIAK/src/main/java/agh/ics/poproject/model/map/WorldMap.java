package agh.ics.poproject.model.map;


import agh.ics.poproject.model.MapChangeListener;
import agh.ics.poproject.model.Vector2d;
import agh.ics.poproject.model.elements.Animal;
import agh.ics.poproject.model.elements.WorldElement;

import java.util.*;

/**
 * The interface responsible for interacting with the map of the world.
 * Assumes that Vector2d and MoveDirection classes are defined.
 *
 * @author apohllo, idzik
 */
public interface WorldMap extends MoveValidator {

    Map<Vector2d, Integer> carcasses = new HashMap<>();

    Map<Vector2d, Integer> getCarcasses();

    void subscribe(MapChangeListener listener);

    /**
     * Place an animal on the map.
     *
     * @param worldElement The worldElement to place on map.
     */
    void placeWorldElement(WorldElement worldElement) throws IncorrectPositionException;

    /**
     * Moves an animal (if it is present on the map) according to specified direction.
     * If the move is not possible, this method has no effect.
     */
    void move(Animal animal);

    /**
     * Return true if given position on the map is occupied. Should not be
     * confused with canMove since there might be empty positions where the animal
     * cannot move.
     *
     * @param position Position to check.
     * @return True if the position is occupied.
     */
    default boolean isOccupied(Vector2d position) {
        return objectAt(position) != null;
    }


    /**
     * Return an element of world at a given position.
     *
     * @param position The position of the animal.
     * @return World element or null if the position is not occupied.
     */
    WorldElement objectAt(Vector2d position);

    /**
     * Return all elements placed on the map.
     *
     * @return Collection of elements placed on the map.
     */
    List<WorldElement> getElements();

    /**
     * Return Boundary Object storing map's current bounds.
     *
     * @return Boundary Object storing leftBound and rightBound of the map.
     */
    Boundary getCurrentBounds();

    /**
     * Returns unique map identifier.
     *
     * @return UUID Map identifier.
     */
    UUID getId();

    /**
     * Removes a map element on a given position
     */
    void removeElement(WorldElement worldElement, Vector2d position);

    /**
     * Calculates surface area.
     * @return Calculated surface of the map.
     */
    default int calculateCurrentSurface() {
        Vector2d upperBound = getCurrentBounds().UpperBound();
        Vector2d lowerBound = getCurrentBounds().LowerBound();
        int width = upperBound.x() - lowerBound.x() + 1;
        int height = upperBound.y() - lowerBound.y() + 1;
        return width * height;
    }

}
