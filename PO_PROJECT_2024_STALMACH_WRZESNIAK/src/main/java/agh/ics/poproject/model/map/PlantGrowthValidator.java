package agh.ics.poproject.model.map;

import agh.ics.poproject.model.Vector2d;

public interface PlantGrowthValidator {
    /**
     * Indicate if plant can grow at the given position.
     *
     * @param position
     *            The position checked for the plant growth possibility.
     * @return True if plant can grow at that position.
     */
    public boolean canGrowAt(Vector2d position);
}
