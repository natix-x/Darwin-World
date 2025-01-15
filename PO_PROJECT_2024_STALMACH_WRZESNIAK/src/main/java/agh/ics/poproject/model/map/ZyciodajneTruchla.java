package agh.ics.poproject.model.map;

import agh.ics.poproject.model.Vector2d;
import agh.ics.poproject.model.elements.Animal;

import java.util.*;

public class ZyciodajneTruchla extends AbstractPlantGrowthMethod {
    private List<Animal> animals;

    public ZyciodajneTruchla(WorldMap worldMap) {
        super(worldMap);
    }


    @Override
    public Vector2d calculatePositionWithParetoPrinciple() {
        return null;
    }


    public Set<Vector2d> getRandomPositionsWithParetoPrinciple(int numberOfElementsToGenerate) {
        return Set.of();
    }
}
