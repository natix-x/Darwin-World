package agh.ics.poproject.model.map;

import agh.ics.poproject.model.Vector2d;
import agh.ics.poproject.model.elements.Plant;

import java.util.Set;

public interface PlantGrowingMethod extends PlantGrowthValidator {
    /**
     * Generate positions at which plants can grow.
     *
     */
    public Set<Vector2d> generatePlantPositions(int numberOfPlants);

}
