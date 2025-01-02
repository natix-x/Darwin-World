package agh.ics.poproject.model.elements;

import agh.ics.poproject.inheritance.Genome;
import agh.ics.poproject.model.map.MapDirection;
import agh.ics.poproject.model.map.MoveValidator;
import agh.ics.poproject.model.Vector2d;
import agh.ics.poproject.model.map.WorldElement;
import agh.ics.poproject.presenters.Configuration;
import agh.ics.poproject.presenters.NewConfigurationPresenter;

import java.util.UUID;

public class Animal implements WorldElement {

    private MapDirection direction;
    private Vector2d position;
    private final Genome genome;
    private int remainingEnergy;
    private int consumedPlants;
    private int age;
    private int amountOfChildren;
    private final UUID animalId = UUID.randomUUID();
    private final Configuration config;


    public Animal(Vector2d position, Genome genome, int amountOfEnergy, Configuration config) {
        this.direction = MapDirection.getRandomDirection();
        this.genome = genome;
        this.position = position;
        this.remainingEnergy = amountOfEnergy;
        this.config = config; //chosen config passed
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

    public void setPosition(Vector2d position) {
        this.position = position;
    }

    @Override
    public String toString() {
        return direction.getDirection();
    }

    public boolean isDead() {
        return remainingEnergy <= 0;
    }

    public void changeEnergy(int amount) {
        remainingEnergy += amount;
        if (remainingEnergy <= 0) {
            remainingEnergy = 0;
        }
    }

    public void rotateAndMove(int steps, MoveValidator validator) {
        this.direction = direction.rotate(steps);
        System.out.println(direction);
        Vector2d newPosition = this.position.add(this.direction.toUnitVector());
        System.out.println(newPosition);
        if (validator.canMoveTo(newPosition)) {
            this.position = newPosition;
        }
        else {
            this.direction = direction.opposite();
        }
    }

}
