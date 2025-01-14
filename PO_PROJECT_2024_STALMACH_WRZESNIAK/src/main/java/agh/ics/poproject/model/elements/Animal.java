package agh.ics.poproject.model.elements;

import agh.ics.poproject.inheritance.Genome;
import agh.ics.poproject.model.map.MapDirection;
import agh.ics.poproject.model.map.MoveValidator;
import agh.ics.poproject.model.Vector2d;

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

    public Animal(Vector2d position, Genome genome, int amountOfEnergy) {
        this.direction = MapDirection.getRandomDirection();
        this.genome = genome;
        this.position = position;
        this.remainingEnergy = amountOfEnergy;
        this.age = 1; //age: 1 when animal is created/born
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

    public UUID getAnimalId() {
        return animalId;
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
    }

    public void rotateAndMove(int steps, MoveValidator validator) {
        this.direction = direction.rotate(steps);
        Vector2d newPosition = this.position.add(this.direction.toUnitVector());
        if (validator.canMoveTo(newPosition)) {
            this.position = newPosition;
        }
        else {
            this.direction = direction.opposite();
        }
        genome.activateNextGene();
        remainingEnergy--;
    }

    public void reproduce(Animal reproductionPartner, int energyNeededForReproduce) {
        // TODO: implement
    }

    public void eat(int energyPerPlant) {
        this.remainingEnergy += energyPerPlant;
        this.consumedPlants ++;
        // TODO: implement
    }

    public void addAChild () {
        this.amountOfChildren ++;
    }

    public void ageAnimal() {
        this.age++;
    }

}
