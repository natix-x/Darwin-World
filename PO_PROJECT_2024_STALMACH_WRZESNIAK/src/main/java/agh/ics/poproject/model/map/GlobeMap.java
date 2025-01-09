package agh.ics.poproject.model.map;


import agh.ics.poproject.inheritance.Genome;
import agh.ics.poproject.model.Vector2d;
import agh.ics.poproject.model.elements.Animal;
import agh.ics.poproject.model.elements.Plant;
import agh.ics.poproject.model.elements.WorldElement;
import agh.ics.poproject.simulation.Simulation;
import agh.ics.poproject.util.Configuration;

import java.util.HashSet;
import java.util.Objects;
import java.util.Random;
import java.util.Set;

public class GlobeMap extends AbstractWorldMap {
    private static final Vector2d LOWER_BOUND = new Vector2d(0, 0);
    private static final int LEFT_EDGE = LOWER_BOUND.x();
    private static final int BOTTOM_EDGE = LOWER_BOUND.y();
    private final Vector2d upperBound;
    private final int rightEdge;
    private final int topEdge;

    public GlobeMap(int width, int height) {
        this.upperBound = new Vector2d(width, height);
        this.rightEdge = upperBound.x();
        this.topEdge = upperBound.y();
    }

    @Override
    public boolean canMoveTo(Vector2d position) {
        return !isBeyondTopOrBottomEdge(position) && (position.follows(LOWER_BOUND) && position.precedes(upperBound));
    }

    /**
     Moves the specified animal to a new position on the map.
     Throws an exception if the animal is not found at its current position.
     */
    @Override
    public void move(Animal animal) {
        Vector2d currentPosition = animal.getPosition();
        if (Objects.equals(objectAt(currentPosition), animal)) {
            animals.remove(currentPosition);
            animal.rotateAndMove(animal.getGenome().getActiveGene(), this);
            Vector2d animalNewPosition = animal.getPosition();
            if (isBeyondLeftEdge(animalNewPosition)) {
                animalNewPosition = new Vector2d(rightEdge, animalNewPosition.y());
            } else if (isBeyondRightEdge(animalNewPosition)) {
                animalNewPosition = new Vector2d(LEFT_EDGE, animalNewPosition.y());
            }
            animal.setPosition(animalNewPosition);
            animals.put(animal.getPosition(), animal);
            super.mapChanged("Animal moved to the position " + animal.getPosition());
        } else {
            throw new IllegalArgumentException("Animal not found at position " + currentPosition);
        }
    }

    @Override
    public Boundary getCurrentBounds() {
        return new Boundary(LOWER_BOUND, upperBound);
    }


    private boolean isBeyondTopOrBottomEdge(Vector2d position) {
        return position.y() > topEdge || position.y() < BOTTOM_EDGE;
    }

    private boolean isBeyondRightEdge(Vector2d position) {
        return position.x() > rightEdge;
    }

    private boolean isBeyondLeftEdge(Vector2d position) {
        return position.x() < LEFT_EDGE;
    }

    @Override
    public void placeWorldElement(WorldElement element) throws IncorrectPositionException {
        Vector2d position = element.getPosition();
        if (element instanceof Animal) {
            placeAnimal(position, (Animal) element);
        }
        else if (element instanceof Plant) {
            placePlant(position, (Plant) element);
        }
    }

    private void placeAnimal(Vector2d position, Animal animal) throws IncorrectPositionException {
        if (canMoveTo(position)) {
            animals.put(position, animal);
            mapChanged("Animal placed at position " + position);
        }
        else
            throw new IncorrectPositionException(position);
    }


    private void placePlant(Vector2d position, Plant plant) throws IncorrectPositionException {
        if (canPlantGrow(position)) {
            plants.put(position, plant);
            mapChanged("Plant placed at position " + position);
        }
        else
            throw new IncorrectPositionException(position);
    }

    /**
     Checks if plant can grow on selected position.
     Plant can grow on every position except position occupied by another plant.
     */
    private boolean canPlantGrow(Vector2d position) {
        return plants.get(position) == null;
    }

    /**
     Removes element from world map, first checking if its type is animal or plant
     */
    @Override
    public void removeElement(WorldElement element, Vector2d position) {
        if (element instanceof Animal) {
            animals.remove(position);
        }
        else if (element instanceof Plant) {
            plants.remove(position);
        }
    }

    public void generateAnimals(Simulation simulation) throws IncorrectPositionException {
        Configuration config = simulation.getConfig();

        int numberOfAnimalsToGenerate = config.initialAnimals();
        // zakładamy, że zwierzaki nie mogą się pojawiać na tym samym polu, można o to dopytać
        Set<Vector2d> generatedAnimalsRandomPositions = getRandomPositions(numberOfAnimalsToGenerate, simulation);
        for (Vector2d position : generatedAnimalsRandomPositions) {
            Genome genome = new Genome(config.genomeLength());
            Animal animal = new Animal(position, genome, config.initialEnergy());
            simulation.addAnimal(animal);
            simulation.getWorldMap().placeWorldElement(animal);  // pokazanie na mapie
        }
    }

    public void generatePlants(Simulation simulation) throws IncorrectPositionException {
        Configuration config = simulation.getConfig();
        int numberOfPlantsToGenerate = config.initialPlants();
        Set<Vector2d> generatedPlantsRandomPositions = getRandomPositions(numberOfPlantsToGenerate, simulation);
        for (Vector2d position : generatedPlantsRandomPositions) {
            Plant plant = new Plant(position);
            simulation.addPlant(plant);
            simulation.getWorldMap().placeWorldElement(plant);
        }

    }

    /**
     * Generate random position of elements knowing that only one element from one category can be on each position.
     * @param numberOfElementsToGenerate is the number of map elements that are going be placed on the map
     */
    private Set<Vector2d> getRandomPositions (int numberOfElementsToGenerate, Simulation simulation) {
        Configuration config = simulation.getConfig();
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
