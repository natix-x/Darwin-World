package agh.ics.poproject.model.map;

import agh.ics.poproject.model.Vector2d;

import java.util.Set;

public abstract class AbstractPlantGrowingMethod implements PlantGrowingMethod {

    @Override
    public Set<Vector2d> generatePlantPositions(int numberOfPlants) {
        return Set.of();
    }

    @Override
    public boolean canGrowAt(Vector2d position) {
        return false;
    }
}
