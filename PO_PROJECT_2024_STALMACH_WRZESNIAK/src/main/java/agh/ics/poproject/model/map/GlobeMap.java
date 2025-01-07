package agh.ics.poproject.model.map;


import agh.ics.poproject.model.Vector2d;
import agh.ics.poproject.model.elements.Animal;
import agh.ics.poproject.model.elements.Plant;
import agh.ics.poproject.model.elements.WorldElement;

import java.util.Objects;

public class GlobeMap extends AbstractWorldMap {
    private static final Vector2d LOWER_BOUND = new Vector2d(0, 0);
    private static final int LEFT_EDGE = LOWER_BOUND.getX();
    private static final int BOTTOM_EDGE = LOWER_BOUND.getY();
    private final Vector2d upperBound;
    private final int rightEdge;
    private final int topEdge;

    public GlobeMap(int width, int height) {
        this.upperBound = new Vector2d(width, height);
        this.rightEdge = upperBound.getX();
        this.topEdge = upperBound.getY();
    }

    @Override
    public boolean canMoveTo(Vector2d position) {
        return !isBeyondTopOrBottomEdge(position);
    }

    /**
     Moves the specified animal to a new position on the map.
     Checks if the animal is present at its current position and removes it from there.
     The animal's movement and rotation are determined by its active gene.
     If the animal moves beyond the left or right edge of the map, its position wraps around to the opposite edge.
     The animal is added to its new position on the map, and a notification is sent about the movement.
     Throws an exception if the animal is not found at its current position.
     */
    @Override
    public void move(Animal animal) {
        Vector2d currentPosition = animal.getPosition();
        if (Objects.equals(objectAt(currentPosition), animal)) {
            animals.remove(currentPosition);
            animal.rotateAndMove(animal.getGenome().getActivateGene(), this);
            Vector2d animalNewPosition = animal.getPosition();
            if (isBeyondLeftEdge(animalNewPosition)) {
                animalNewPosition = new Vector2d(rightEdge, animalNewPosition.getY());
            } else if (isBeyondRightEdge(animalNewPosition)) {
                animalNewPosition = new Vector2d(LEFT_EDGE, animalNewPosition.getY());
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
        return position.getY() > topEdge || position.getY() < BOTTOM_EDGE;
    }

    private boolean isBeyondRightEdge(Vector2d position) {
        return position.getX() > rightEdge;
    }

    private boolean isBeyondLeftEdge(Vector2d position) {
        return position.getX() < LEFT_EDGE;
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
}
