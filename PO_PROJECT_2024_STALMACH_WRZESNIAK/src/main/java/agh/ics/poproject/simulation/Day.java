package agh.ics.poproject.simulation;

import agh.ics.poproject.inheritance.Genome;
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

    Map<Vector2d, List<Animal>> positionMap = new HashMap<>(); //dict key: position vector, val: animal

    private GlobeMap worldMap;

    public Day(Simulation simulation) {
        this.simulation = simulation;
        this.config = simulation.getConfig();
        this.worldMap = simulation.getWorldMap();
    }

    /**
    Generates all necessary elements for simulation launch
     */
    void firstDayActivities() throws IncorrectPositionException {
        dayCount++;
        worldMap.generateAnimals(simulation);
        worldMap.generatePlants(simulation);
    }

    /**
     Generates and updated all map elements in the timeframe of one day
     */
     void everyDayActivities() throws IncorrectPositionException {
        dayCount++;
         worldMap.ageAllAnimals(simulation); //adds +1 to each animal's ageworldMap.removeDeadAnimals();
         worldMap.moveAndRotateAnimals(simulation);
//       worldMap.consumePlants(simulation);
         worldMap.reproduceAnimals(positionMap, simulation);
         worldMap.growNewPlants(simulation);
        //TODO: można pomyśleć nad jakimś counterem dni
    }

    public int getDayCount() {
        return dayCount;
    }


//
//    /**
//     * Establishes the animal that will consume the Plant, resolves conflicts in case of multiple animals on a position.
//     * Handles the simulation update post plant consumption
//     */
//    public void consumePlants() {
//        List<Plant> plants = simulation.getPlants();
//
//        groupAnimalsByPositions();
//
//        for (List<Animal> animals : positionMap.values()) {
//            List<Animal> priorityForFood = animals.stream()
//                    .sorted((animal1, animal2) -> {
//                        int energyComparison = Integer.compare(animal2.getRemainingEnergy(), animal1.getRemainingEnergy());
//                        if (energyComparison != 0) {
//                            return energyComparison;
//                        }
//                        return Integer.compare(animal2.getAge(), animal1.getAge());
//                    }).toList();
//            Iterator<Plant> iterator = plants.iterator();
//            while (iterator.hasNext()) {
//                Plant plant = iterator.next();
//                Vector2d plantPosition = plant.getPosition();
//
//                if (positionMap.containsKey(plantPosition)) {
//                    List<Animal> animalsPositions = positionMap.get(plantPosition);
//
//                    if (!animalsPositions.isEmpty()) {
//                        Animal animal = priorityForFood.getFirst();
//                        animal.eat(config.energyPerPlant());
//                        iterator.remove();
//                        simulation.getWorldMap().removeElement(plant, plant.getPosition());
//                    }
//                }
//            }
//        }
//
//
//    }
}
