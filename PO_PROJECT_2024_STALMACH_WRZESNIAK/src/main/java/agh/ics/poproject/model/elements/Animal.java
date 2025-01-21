package agh.ics.poproject.model.elements;

import agh.ics.poproject.inheritance.Genome;
import agh.ics.poproject.model.map.MapDirection;
import agh.ics.poproject.model.map.MoveValidator;
import agh.ics.poproject.model.Vector2d;

import java.util.HashSet;
import java.util.Map;
import java.util.Set;
import java.util.UUID;

// TODO: implementacja śledzenia potomków niekoniecznie będących bezpośrednio dziećmi
public class Animal implements WorldElement {

    private MapDirection direction;
    private Vector2d position;
    private final Genome genome;
    private int remainingEnergy;
    private int consumedPlants;
    private int age;
    private Set<Animal> children;

    private final UUID animalId = UUID.randomUUID();

    public Animal(Vector2d position, Genome genome, int amountOfEnergy) {
        this.direction = MapDirection.getRandomDirection();
        this.genome = genome;
        this.position = position;
        this.remainingEnergy = amountOfEnergy;
        this.age = 1; //age: 1 when animal is created/born
        this.children = new HashSet<>();
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
        return children.size();
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

    /**
     * Changes animal energy by a set amount
     * @param amount added or subtracted amount of energy
     */
    public void changeEnergy(int amount) {
        remainingEnergy += amount;
    }

    /**
     * Moves and rotates animal on the map
     * @param steps rotates animal around itself
     * @param validator passed validator checks if a particular move is allowed
     */
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

    /**
     * Provides animal with energy when a plant is eaten
     * @param energyPerPlant passed from map configuration, will be added to animal's energy
     */
    public void eat(int energyPerPlant) {
        this.remainingEnergy += energyPerPlant;
        this.consumedPlants ++;
    }

    /**
     * Tracks the count of children for an animal. Tracks UUID of a baby for descendant tracking
     * @param baby
     */
    public void addAChild (Animal baby) {
        this.children.add(baby);
    }

    public void ageAnimal() {
        this.age++;
    }

    /**
     * Traverses through sets of UUIDs to count non-direct descendants
     *
     */
    public int countDescendants() {
    int count =0;
        for (Animal child : this.children) {
            if (child != null) {
                count++; //count the child
                count += child.countDescendants(); //count all its descendants
            }
        }
        return count;
    }

}
