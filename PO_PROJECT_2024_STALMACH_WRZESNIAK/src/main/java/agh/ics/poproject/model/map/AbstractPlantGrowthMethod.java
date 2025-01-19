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

    /**
     Generates random plant positions according to Pareto Principle.
     @param numberOfPlantsToGenerate passed and set via map configuration
     */
    @Override
    public Set<Vector2d> generatePlantPositions(int numberOfPlantsToGenerate) {
        if (areAllPositionsOccupiedByPlants()) {
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

    /**
     Defines which positions are more attractive (80% of likelihood) and which are less.
     */
    public abstract Vector2d calculatePositionWithParetoPrinciple();

    @Override
    public boolean canGrowAt(Vector2d position) {
        return worldMap.getElements().stream()
                .filter(element -> element instanceof Plant)
                .noneMatch(element -> element.getPosition().equals(position));
    }

    /**
     Counts the number of positions from the map occupied by plants.
     */
    private int numberOfPositionOccupiedByPlants() {
        return (int) worldMap.getElements().stream()
                .filter(element -> element instanceof Plant)
                .count();
    }

    /**
     Removes element from world map, first checking if its type is animal or plant
     */
    private boolean areAllPositionsOccupiedByPlants() {
        return numberOfPositionOccupiedByPlants() >= worldMap.calculateCurrentSurface();
    }
}
