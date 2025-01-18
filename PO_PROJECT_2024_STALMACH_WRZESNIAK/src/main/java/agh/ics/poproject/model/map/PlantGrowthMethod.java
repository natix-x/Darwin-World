package agh.ics.poproject.model.map;

import agh.ics.poproject.model.Vector2d;

import java.util.Set;

public interface PlantGrowthMethod extends PlantGrowthValidator {
    /**
     * Generate positions at which plants can grow.
     *
     */
    public Set<Vector2d> generatePlantPositions(int numberOfPlants);

}
