package agh.ics.poproject.simulation;

import agh.ics.poproject.inheritance.Reproduce;
import agh.ics.poproject.model.Vector2d;
import agh.ics.poproject.model.elements.Animal;
import agh.ics.poproject.model.elements.Plant;
import agh.ics.poproject.model.map.GlobeMap;
import agh.ics.poproject.model.map.IncorrectPositionException;
import agh.ics.poproject.util.Configuration;

import java.util.*;
// TODO: aktualizować wszystko na mapie
// TODO: osługa błędów w momencie gdzie nie ma już na mapie miejsca dla nowych zwierzaków bądź nowych roślin

/**
 * Class for handling activities for each day for each simulation.
 */
public class Day {
    private final Simulation simulation;
    private final Configuration config;
    private int dayCount = 0;
    private GlobeMap worldMap;

    public Day(Simulation simulation) {
        this.simulation = simulation;
        this.config = simulation.getConfig();
        this.worldMap = simulation.getWorldMap();
    }

    /**
     * Generates all necessary elements for simulation launch
     */
    void firstDayActivities() throws IncorrectPositionException {
        dayCount++;
        worldMap.generateAnimals(simulation);
        worldMap.generatePlants(simulation);
    }

    /**
     * Generates and updated all map elements in the timeframe of one day
     */
    void everyDayActivities() throws IncorrectPositionException {
        dayCount++;
        ageAllAnimals(); //adds +1 to each animal's ageworldMap.removeDeadAnimals();
        moveAndRotateAnimals();
        simulation.getWorldMap().consumePlants(config.energyPerPlant());
        //reproduceAnimals();
        //worldMap.reproduceAnimals(positionMap, simulation);
        //worldMap.growNewPlants(simulation);
        //TODO: można pomyśleć nad jakimś counterem dni
    }

    public int getDayCount() {
        return dayCount;
    }

    /**
     * Removes dead animals from animals list in Simulation class.
     */
    private void ageAllAnimals() {
        for (Animal animal : simulation.getAnimals()) {
            animal.ageAnimal();
        }
    }

    public void moveAndRotateAnimals() {
        List<Animal> animals = simulation.getAnimals();
        for (Animal animal : animals) {
            simulation.getWorldMap().move(animal);
        }
    }


    /**
     * Establishes the animals that will reproduce, resolves conflicts in case of multiple animals on a position.
     * Handles the simulation update post reproduction
     *
     */
//    public void reproduceAnimals() throws IncorrectPositionException {
//        Configuration config = simulation.getConfig();
//
//        //groupAnimalsByPositions(positionMap, simulation);
//
//        for (List<Animal> animals : positionMap.values()) {
//            List<Animal> priorityForReproduction = animals.stream()
//                    .filter(animal -> animal.getRemainingEnergy() > config.neededEnergyForReproduction()) //only those with sufficient energy
//                    .sorted((animal1, animal2) -> { //sort for reproduction priority
//                        int energyComparison = Integer.compare(animal2.getRemainingEnergy(), animal1.getRemainingEnergy());
//                        if (energyComparison != 0) {
//                            return energyComparison;
//                        }
//                        return Integer.compare(animal2.getAge(), animal1.getAge());
//                    }).toList();
//
//            if (priorityForReproduction.size() >= 2) {
//                Animal animal1 = priorityForReproduction.get(0);
//                Animal animal2 = priorityForReproduction.get(1);
//
//                Reproduce reproduction = new Reproduce();
//                Animal babyAnimal = reproduction.reproduce(animal1, animal2);
//                simulation.addAnimal(babyAnimal);
//                simulation.getWorldMap().placeWorldElement(babyAnimal);
//                System.out.println("Baby made!");
//            }
//        }
//    }

}
