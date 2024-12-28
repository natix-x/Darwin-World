package agh.ics.poproject;

import agh.ics.poproject.model.*;
import agh.ics.poproject.model.elements.Animal;
import agh.ics.poproject.model.map.WorldMap;

import java.util.ArrayList;
import java.util.List;

public class Simulation {
    private final List<MoveDirection> directions;
    private final List<Animal> animals;
    private final WorldMap worldMap;

    public Simulation(List<Vector2d> positions, List<MoveDirection> directions, WorldMap worldMap) {
        this.directions = directions;
        this.animals = new ArrayList<>();
        this.worldMap = worldMap;

        for (Vector2d position : positions) {
            try {
                Animal newAnimal = new Animal(position);
                worldMap.place(newAnimal);
                animals.add(newAnimal);
            } catch (IncorrectPositionException e) {
                e.printStackTrace();
            }
        }
    }

    public List<Animal> getAnimals() {
        return animals;
    }

    public void run() {
        int index = 0;
        for (MoveDirection direction : directions) {
            int animalIndex = index % animals.size();
            Animal animal = animals.get(animalIndex);
            worldMap.move(animal, direction);
            index++;
            try {
                Thread.sleep(500);
            }
            catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }
}
