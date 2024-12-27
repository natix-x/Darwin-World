package agh.ics.poproject.model;

/**
 * The interface responsible for interacting with elements of the map.
 * Assumes that Vector2d class is defined.
 */

public interface WorldElement {

    /**
     * Get the position of the world element.
     *
     * @return Position of the world element.
     */
    Vector2d getPosition();

    /**
     * Get the position of the world element.
     *
     * @param position Selected position.
     * @return True if on selected position is world element or False if there is not.
     */
    default boolean isAt(Vector2d position) {
        return getPosition().equals(position);
    }
}
