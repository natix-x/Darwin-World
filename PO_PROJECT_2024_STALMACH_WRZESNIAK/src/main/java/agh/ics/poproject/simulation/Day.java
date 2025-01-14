package agh.ics.poproject.simulation;

import agh.ics.poproject.inheritance.*;
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
    private final GlobeMap worldMap;
    private Reproduction reproduction;

    public Day(Simulation simulation) {
        this.simulation = simulation;
        this.config = simulation.getConfig();
        this.worldMap = simulation.getWorldMap();
        setReproductionParameters();
    }

    private void setReproductionParameters() {
        MutationMethodType mutationMethodType = config.isFullRandomMutation() ? MutationMethodType.FULL_RANDOM : MutationMethodType.SLIGHT_CORRECTION;
        int minMutations = config.minMutations();
        int maxMutations = config.maxMutations();

        MutationMethod mutationMethod = switch (mutationMethodType) {
            case FULL_RANDOM -> new FullRandomMutation(minMutations, maxMutations);
            case SLIGHT_CORRECTION -> new SlightCorrectionMutation(minMutations, maxMutations);
        };
        this.reproduction = new Reproduction(config.neededEnergyForReproduction(), mutationMethod);
    }

    /**
     * Generates all necessary elements for simulation launch
     */
    void firstDayActivities() throws IncorrectPositionException {
        worldMap.generateAnimals(simulation);  // TODO zastanowic sie czy powinno sie tu przekazywac cala symulacje
        worldMap.generatePlants(simulation);
    }

    // TODO refaktoryzacja metod - wszystkie wywoływane z Day a później aktualizowane w GLobe (jak moveAndRotate)
    /**
     * Generates and updated all map elements in the timeframe of one day
     */
    void everyDayActivities() throws IncorrectPositionException {
        removeDeadAnimals();
        moveAndRotateAnimals();
        consumePlants();
        reproduceAnimals();
        ageAllAnimals();
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
            worldMap.move(animal);
        }
    }

    /**
     * Establishes the animals that will reproduce, resolves conflicts in case of multiple animals on a position.
     * Handles the simulation update post reproduction
     *
     */
    public void reproduceAnimals() throws IncorrectPositionException {

        for (Map.Entry<Vector2d, List<Animal>> entry : worldMap.getAnimals().entrySet()) {

            List<Animal> animalsAtPosition = entry.getValue().stream()
                    .filter(animal -> animal.getRemainingEnergy() >= config.neededEnergyForReproduction())
                    .sorted(Comparator.comparingInt(Animal::getRemainingEnergy).reversed()
                            .thenComparingInt(Animal::getAge))
                    .toList();

            if (animalsAtPosition.size() >= 2) {
                Animal parent1 = animalsAtPosition.get(0);
                Animal parent2 = animalsAtPosition.get(1);

                Animal offspring = reproduction.reproduce(parent1, parent2);
                simulation.addAliveAnimal(offspring);
                System.out.println("babyMade" + offspring.getRemainingEnergy());
                worldMap.placeWorldElement(offspring);
            }
        }
    }

    /**
     * Establishes the animal that will consume the Plant, resolves conflicts in case of multiple animals on a position.
     * Handles the simulation update post plant consumption
     *
     */
    public void consumePlants() {
        int energyPerPlant = config.energyPerPlant();

        for (Plant plant : new ArrayList<>(worldMap.getPlants().values())) { // Avoid concurrent modification
            Vector2d plantPosition = plant.getPosition();

            if (worldMap.getAnimals().containsKey(plantPosition)) {
                List<Animal> animalsAtPosition = worldMap.getAnimals().get(plantPosition);
                Animal highestPriorityAnimal = animalsAtPosition.stream()
                        .max(Comparator.comparingInt(Animal::getRemainingEnergy)
                                .thenComparingInt(Animal::getAge))
                        .orElse(null);

                if (highestPriorityAnimal != null) {
                    highestPriorityAnimal.eat(energyPerPlant);
                    worldMap.getPlants().remove(plantPosition);
                    simulation.removePlant(plant);
                }
            }
        }
    }

    /**
     * Removes dead animals from the map
     *
     */
    public void removeDeadAnimals() {
        Iterator<Animal> aliveAnimalsIterator = simulation.getAliveAnimals().iterator();
        while (aliveAnimalsIterator.hasNext()) {
            Animal animal = aliveAnimalsIterator.next();
            if (animal.isDead()) {
                aliveAnimalsIterator.remove();
                simulation.addDeadAnimal(animal);
                System.out.println("umarł" + animal.getAge());
                worldMap.removeElement(animal, animal.getPosition());
            }
        }
    }
}
