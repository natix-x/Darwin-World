package agh.ics.poproject.model;


import java.util.Collection;
import java.util.UUID;

/**
 * The interface responsible for interacting with the map of the world.
 * Assumes that Vector2d and MoveDirection classes are defined.
 *
 * @author apohllo, idzik
 */
public interface WorldMap extends MoveValidator {

    void subscribe(MapChangeListener listener);

    /**
     * Place an animal on the map.
     *
     * @param animal The animal to place on the map.
     */
    void place(Animal animal) throws IncorrectPositionException;

    /**
     * Moves an animal (if it is present on the map) according to specified direction.
     * If the move is not possible, this method has no effect.
     */
    void move(Animal animal, MoveDirection direction);

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
    Collection<WorldElement> getElements();

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

}
