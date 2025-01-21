package agh.ics.poproject.simulation;

import agh.ics.poproject.inheritance.*;
import agh.ics.poproject.model.Vector2d;
import agh.ics.poproject.model.elements.Animal;
import agh.ics.poproject.model.elements.Plant;
import agh.ics.poproject.model.map.*;
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
    private PlantGrowthMethod plantGrowthMethod;
    private final Carcasses carcasses;

    public Day(Simulation simulation) {
        this.simulation = simulation;
        this.config = simulation.getConfig();
        this.worldMap = simulation.getWorldMap();
        this.carcasses = new Carcasses(config.corpseDecompostion());  // harcodowane, ale kiedyś mogłoby być ustalane przez usera:)
        setReproductionParameters();
        setPlantGrowthParameters();
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

    private void setPlantGrowthParameters() {
        PlantGrowthMethodType plantGrowthMethodType = config.isForestedEquator() ? PlantGrowthMethodType.FORESTED_EQUATOR : PlantGrowthMethodType.ZYCIODAJNE_TRUCHLA;

        this.plantGrowthMethod = switch (plantGrowthMethodType) {
            case FORESTED_EQUATOR -> new ForestedEquator(worldMap);
            case ZYCIODAJNE_TRUCHLA -> new ZyciodajneTruchla(worldMap, carcasses);
        };
    }

    /**
     * Generates all necessary elements for simulation launch
     */
    void firstDayActivities() throws IncorrectPositionException {
        generateInitialAnimals();  // TODO zastanowic sie czy powinno sie tu przekazywac cala symulacje
        growNewPlants(config.initialPlants());
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
        growNewPlants(config.dailyPlantGrowth());
        carcasses.changeCarcassPriority();
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
                worldMap.removeElement(animal, animal.getPosition());
                carcasses.addCarcass(animal.getPosition());
            }
        }

    }

    public void generateInitialAnimals() throws IncorrectPositionException {

        int numberOfAnimalsToGenerate = config.initialAnimals();
        Set<Vector2d> generatedAnimalsRandomPositions = getRandomPositions(numberOfAnimalsToGenerate);
        for (Vector2d position : generatedAnimalsRandomPositions) {
            Genome genome = new Genome(config.genomeLength());
            Animal animal = new Animal(position, genome, config.initialEnergy());
            simulation.addAliveAnimal(animal);
            simulation.getWorldMap().placeWorldElement(animal);
        }
    }

    public void growNewPlants(int numberOfPlants) throws IncorrectPositionException {
        Set<Vector2d> plantsPositions = plantGrowthMethod.generatePlantPositions(numberOfPlants);
        for (Vector2d position : plantsPositions) {
            Plant plant = new Plant(position);
            simulation.addPlant(plant);
            worldMap.placeWorldElement(plant);
        }
    }

    /**
     * Generate random position of elements knowing that only one element from one category can be on each position.
     * @param numberOfElementsToGenerate is the number of map elements that are going be placed on the map
     */
    private Set<Vector2d> getRandomPositions (int numberOfElementsToGenerate) {
        Random random = new Random();
        Set<Vector2d> uniquePositions = new HashSet<>();
        while (uniquePositions.size() < numberOfElementsToGenerate) {
            int x = random.nextInt(config.mapWidth());
            int y = random.nextInt(config.mapHeight());
            Vector2d position = new Vector2d(x, y);
            uniquePositions.add(position);
        }
        return uniquePositions;
    }
}
