package agh.ics.poproject.model.map;

import agh.ics.poproject.model.Vector2d;

import java.util.*;

public class ZyciodajneTruchla extends AbstractPlantGrowingMethod {

    public ZyciodajneTruchla(WorldMap worldMap) {
        super(worldMap);
    }

    @Override
    public Set<Vector2d> generatePlantPositions(int numberOfPlants) {
        Set<Vector2d> generatedPlantsRandomPositions = getRandomPositionsWithParetoPrinciple(numberOfPlants);
        //for (Vector2d position : generatedPlantsRandomPositions) {
        //    Plant plant = new Plant(position);
        //}  -> ta część pójdzie do worldmapy -> do metody generate plants
        // i do dnia -> generateInitial oraz genereteplants (na koniec każdego dnia)
        return generatedPlantsRandomPositions;
    }


    public Set<Vector2d> getRandomPositionsWithParetoPrinciple(int numberOfElementsToGenerate) {
        return Set.of();
    }
}
