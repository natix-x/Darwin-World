package agh.ics.poproject.model.map;

import agh.ics.poproject.model.Vector2d;
import agh.ics.poproject.model.elements.Plant;
import agh.ics.poproject.util.Configuration;

import java.util.*;

public class ZyciodajneTruchla extends AbstractPlantGrowingMethod {

    private WorldMap worldMap;

    @Override
    public Set<Vector2d> generatePlantPositions(int numberOfPlants) {
        Set<Vector2d> generatedPlantsRandomPositions = getRandomPositionsWithParetoPrinciple(numberOfPlants);
        for (Vector2d position : generatedPlantsRandomPositions) {
            Plant plant = new Plant(position);
        }
        return generatedPlantsRandomPositions;
    }


    private Set<Vector2d> getRandomPositionsWithParetoPrinciple (int numberOfElementsToGenerate) {
        Random random = new Random();
        Set<Vector2d> uniquePositions = new HashSet<>();
        while (uniquePositions.size() < numberOfElementsToGenerate) {
            int x = random.nextInt(config.mapWidth());
            int y = random.nextInt(config.mapHeight());
            Vector2d position = new Vector2d(x, y);
            uniquePositions.add(position);
        }
        return uniquePositions;
    }
}
