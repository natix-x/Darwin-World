package agh.ics.poproject.model;

import java.util.*;

public class GrassField extends AbstractWorldMap {

    private final Map<Vector2d, Grass> grasses = new HashMap<>();

    public GrassField(int grassNumber) {
        randomGrassPlacer(grassNumber);
    }

    private void randomGrassPlacer(int grassNumber) {
        Random random = new Random();
        int maxCoordinate = (int) Math.sqrt(grassNumber *10);
        Set <Vector2d> uniquePositions = new HashSet<>();
        while (uniquePositions.size() < grassNumber) {
            int x = random.nextInt(maxCoordinate + 1);
            int y = random.nextInt(maxCoordinate + 1);
            Vector2d position = new Vector2d(x, y);
            if (uniquePositions.add(position)) {
                grasses.put(position, new Grass(position));
            }
        }
    }

    @Override
    public WorldElement objectAt(Vector2d position) {
        WorldElement animal = super.objectAt(position);
        if (animal == null) {
            return grasses.get(position);
        }
        return animal;
    }

    @Override
    public boolean canMoveTo(Vector2d position) {
        return !(objectAt(position) instanceof Animal);
    }

    @Override
    public List<WorldElement> getElements() {
        List<WorldElement> elements = super.getElements();
        for (Map.Entry<Vector2d, Grass> entry : grasses.entrySet()) {
            elements.add(entry.getValue());}
        return elements;
    }

    @Override
    public Boundary getCurrentBounds() {
        Vector2d lowerBound = new Vector2d(Integer.MAX_VALUE, Integer.MAX_VALUE);
        Vector2d upperBound = new Vector2d(Integer.MIN_VALUE, Integer.MIN_VALUE);
        for(WorldElement element: getElements()) {
            Vector2d position = element.getPosition();
            lowerBound = lowerBound.lowerLeft(position);
            upperBound = upperBound.upperRight(position);
        }
        return new Boundary(lowerBound, upperBound);
    }
}
