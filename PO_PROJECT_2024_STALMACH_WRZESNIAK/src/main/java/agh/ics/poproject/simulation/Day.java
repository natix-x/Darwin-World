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
        worldMap.generateAnimals(simulation);
        worldMap.generatePlants(simulation);
    }

    // TODO refaktoryzacja metod - wszystkie wywoływane z Day a później aktualizowane w GLobe (jak moveAndRotate)
    /**
     * Generates and updated all map elements in the timeframe of one day
     */
    void everyDayActivities() throws IncorrectPositionException {
        ageAllAnimals(); //adds +1 to each animal's ageworldMap.removeDeadAnimals();
        moveAndRotateAnimals();
        simulation.getWorldMap().consumePlants(simulation);
        simulation.getWorldMap().reproduceAnimal(simulation);
        //simulation.getWorldMap().growNewPlants(simulation);
    }


    /**
     * Adds one to each animal age.
     */
    private void ageAllAnimals() {
        for (Animal animal : simulation.getAliveAnimals()) {
            animal.ageAnimal();
        }
    }

    private void moveAndRotateAnimals() {
        List<Animal> animals = simulation.getAliveAnimals();
        for (Animal animal : animals) {
            simulation.getWorldMap().move(animal);
        }
    }
}
