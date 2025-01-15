package agh.ics.poproject.model.map;

import agh.ics.poproject.model.Vector2d;
import agh.ics.poproject.model.elements.Plant;

import java.util.*;

public abstract class AbstractPlantGrowthMethod implements PlantGrowthMethod {
    protected WorldMap worldMap;
    protected Random random = new Random();

    AbstractPlantGrowthMethod(WorldMap worldMap) {
        this.worldMap = worldMap;
    }

    @Override
    public Set<Vector2d> generatePlantPositions(int numberOfPlantsToGenerate) {
        if (allPositionsOccupiedByPlants()) {
            return Collections.emptySet();
        }

        Set<Vector2d> uniquePositions = new HashSet<>();
        int PositionsUnoccupiedByPlants = worldMap.calculateCurrentSurface() - numberOfPositionOccupiedByPlants();

        if (PositionsUnoccupiedByPlants < numberOfPlantsToGenerate) {
            numberOfPlantsToGenerate = PositionsUnoccupiedByPlants;
        }

        while (uniquePositions.size() < numberOfPlantsToGenerate) {


            Vector2d position = calculatePositionWithParetoPrinciple();
            if (! canGrowAt(position)) {
                continue;
            }
            uniquePositions.add(position);
        }
        return uniquePositions;
    }

    public abstract Vector2d calculatePositionWithParetoPrinciple();

    @Override
    public boolean canGrowAt(Vector2d position) {
        return worldMap.getElements().stream()
                .filter(element -> element instanceof Plant)
                .noneMatch(element -> element.getPosition().equals(position));
    }

    private int numberOfPositionOccupiedByPlants() {
        return (int) worldMap.getElements().stream()
                .filter(element -> element instanceof Plant)
                .count();
    }

    private boolean allPositionsOccupiedByPlants() {
        return numberOfPositionOccupiedByPlants() >= worldMap.calculateCurrentSurface();
    }
}
