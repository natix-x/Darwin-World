package agh.ics.poproject.model.elements;

import agh.ics.poproject.inheritance.Genome;
import agh.ics.poproject.model.map.MapDirection;
import agh.ics.poproject.model.map.MoveValidator;
import agh.ics.poproject.model.Vector2d;
import agh.ics.poproject.model.map.WorldElement;

public class Animal implements WorldElement {
    private MapDirection direction;
    private Vector2d position;
    private Genome genome;
    private int remainingEnergy;
    private int consumedPlants;
    private int age;
    private int amountOfChildren;


    public Animal(Vector2d position, Genome genome, int amountOfEnergy) {
        this.direction = MapDirection.getRandomDirection();
        this.genome = genome;
        this.position = position;
        this.remainingEnergy = amountOfEnergy;
    }

    public MapDirection getDirection() {
        return direction;
    }

    @Override
    public Vector2d getPosition() {
        return position;
    }

    public int getConsumedPlants() {
        return consumedPlants;
    }

    public int getRemainingEnergy() {
        return remainingEnergy;
    }

    public int getAmountOfChildren() {
        return amountOfChildren;
    }

    public int getAge() {
        return age;
    }

    public Genome getGenome() {
        return genome;
    }


    @Override
    public String toString() {
        return direction.getDirection();
    }

    public void rotateAndMove(int steps, MoveValidator validator) {
        this.direction = direction.rotate(steps);
        Vector2d newPosition = this.position.add(this.direction.toUnitVector());
        if (validator.canMoveTo(newPosition)) {
            this.position = newPosition;
        }
    }

}
