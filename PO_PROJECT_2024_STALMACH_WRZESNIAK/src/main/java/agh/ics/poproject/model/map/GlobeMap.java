package agh.ics.poproject.model.map;


import agh.ics.poproject.model.Vector2d;
import agh.ics.poproject.model.elements.Animal;
import jdk.incubator.vector.VectorOperators;

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
        System.out.println(isOnTopOrBottomEdge(position));
        return !isOnTopOrBottomEdge(position);
    }

    @Override
    public void move(Animal animal) {
        Vector2d currentPosition = animal.getPosition();
        if (Objects.equals(objectAt(currentPosition), animal)) {
            animals.remove(currentPosition);
            animal.rotateAndMove(animal.getGenome().getActivateGene(), this);
            Vector2d animalNewPosition = animal.getPosition();
            if (isOnLeftEdge(animalNewPosition)) {
                animalNewPosition = new Vector2d(rightEdge, animalNewPosition.getY());
            } else if (isOnRightEdge(animalNewPosition)) {
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


    private boolean isOnTopOrBottomEdge(Vector2d position) {
        System.out.println(topEdge);
        System.out.println(BOTTOM_EDGE);
        return position.getY() == topEdge || position.getY() == BOTTOM_EDGE;
    }

    private boolean isOnRightEdge(Vector2d position) {
        return position.getX() == rightEdge;
    }

    private boolean isOnLeftEdge(Vector2d position) {
        return position.getX() == LEFT_EDGE;
    }

}
