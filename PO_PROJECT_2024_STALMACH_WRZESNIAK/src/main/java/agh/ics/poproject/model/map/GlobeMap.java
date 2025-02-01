package agh.ics.poproject.model.map;


import agh.ics.poproject.model.MapChangeListener;
import agh.ics.poproject.model.Vector2d;
import agh.ics.poproject.model.elements.Animal;
import agh.ics.poproject.model.elements.Plant;
import agh.ics.poproject.model.elements.WorldElement;
import agh.ics.poproject.model.util.MapVisualizer;

import java.util.*;

// TODO: refaktoryzacja zgodna ze wzoracami projektowymi - niesprawdzanie istance of itp
public class GlobeMap implements MoveValidator {
    private static final Vector2d LOWER_BOUND = new Vector2d(0, 0);
    private static final int LEFT_EDGE = LOWER_BOUND.x();
    private static final int BOTTOM_EDGE = LOWER_BOUND.y();
    private final Vector2d upperBound;
    private final int rightEdge;
    private final int topEdge;
    private final Map<Vector2d, List<Animal>> animals = new HashMap<>();

    private final Map<Vector2d, Plant> plants = new HashMap<>();
    private final List<MapChangeListener> mapChangeListeners = new ArrayList<>();
    private final UUID mapID = UUID.randomUUID();

    public GlobeMap(int width, int height) {
        this.upperBound = new Vector2d(width, height);
        this.rightEdge = upperBound.x();
        this.topEdge = upperBound.y();
    }


    public List<WorldElement> getElements() {
        List<WorldElement> elements = new ArrayList<>();
        animals.values().forEach(elements::addAll);
        elements.addAll(plants.values());
        return elements;
    }

    public Map<Vector2d, List<Animal>> getAnimals() {
        return animals;
    }

    public Map<Vector2d, Plant> getPlants() {
        return plants;
    }


    public UUID getId() {
        return mapID;
    }

    public void subscribe(MapChangeListener listener) {
        mapChangeListeners.add(listener);
    }


    public String toString() {
        MapVisualizer map = new MapVisualizer(this);
        Boundary currentBounds = getCurrentBounds();
        return map.draw(currentBounds.LowerBound(), currentBounds.UpperBound());
    }

    public void mapChanged(String message){
        mapChangeListeners.forEach(listener -> listener.mapChange(this, message));
    }

    public boolean canMoveTo(Vector2d position) {
        return !isBeyondTopOrBottomEdge(position);
    }

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

    /**
     * Assigns position of an animal to move beyond left and right edge
     * @param newPosition
     * @return
     */
    private Vector2d assignNewXPosition(Vector2d newPosition) {
        if (isBeyondLeftEdge(newPosition)) {
            newPosition = new Vector2d(rightEdge, newPosition.y());
        } else if (isBeyondRightEdge(newPosition)) {
            newPosition = new Vector2d(LEFT_EDGE, newPosition.y());
        };
        return newPosition;
    }

    /**
     Moves the specified animal to a new position on the map ensuring it stays within map bounds
     Throws an exception if the animal is not found at its current position.
     @param animal animal that will be moved
     */

    public void move(Animal animal) {
        Vector2d currentPosition = animal.getPosition();
        List<Animal> animalsAtSelectedPosition = animals.get(currentPosition);

        if (animalsAtSelectedPosition != null && animalsAtSelectedPosition.contains(animal)) {
            animalsAtSelectedPosition.remove(animal);
            if (animalsAtSelectedPosition.isEmpty()) {
                animals.remove(currentPosition);
            }

            animal.rotateAndMove(animal.getGenome().getActiveGene(), this);
            Vector2d animalNewPosition = animal.getPosition();

            animalNewPosition = assignNewXPosition(animalNewPosition);

            animal.setPosition(animalNewPosition);

            animals.computeIfAbsent(animalNewPosition, k -> new ArrayList<>()).add(animal);
        } else {
            throw new IllegalArgumentException("Animal not found at position " + currentPosition);
        }
    }

    public void placeAnimal(Animal animal) throws IncorrectPositionException {
        Vector2d position = animal.getPosition();
        if (canMoveTo(position)) {
            animals.computeIfAbsent(position, k -> new ArrayList<>()).add(animal);
        }
        else
            throw new IncorrectPositionException(position);
    }


    public void placePlant(Plant plant) throws IncorrectPositionException {
        Vector2d position = plant.getPosition();
        if (canPlantGrow(position)) {
            plants.put(position, plant);
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

    // TODO: zastosować wzorzec projektowy
    /**
     Removes element from world map, first checking if its type is animal or plant
     */

    public void removeElement(WorldElement element, Vector2d position) {
        if (element instanceof Animal) {
            List<Animal> animalsAtPosition = animals.get(element.getPosition());
                animalsAtPosition.remove(element);
                if (animalsAtPosition.isEmpty()) {
                    animals.remove(element.getPosition());
                }
        }
        else if (element instanceof Plant) {
            plants.remove(position);
        }
    }

    public Optional <WorldElement> objectAt(Vector2d position) {
        if (animals.containsKey(position)) {
            return Optional.of(animals.get(position).getFirst());
        }
        else if (plants.containsKey(position)) {
            return Optional.of(plants.get(position));
        }
        return Optional.empty();
    }

    public boolean isOccupied(Vector2d position) {
        return objectAt(position).isPresent();
    }

    public int calculateCurrentSurface() {
        Vector2d upperBound = getCurrentBounds().UpperBound();
        Vector2d lowerBound = getCurrentBounds().LowerBound();
        int width = upperBound.x() - lowerBound.x() + 1;
        int height = upperBound.y() - lowerBound.y() + 1;
        return width * height;
    }
};