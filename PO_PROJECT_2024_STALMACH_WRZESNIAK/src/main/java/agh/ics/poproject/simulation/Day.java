package agh.ics.poproject.simulation;

import agh.ics.poproject.inheritance.Genome;
import agh.ics.poproject.model.Vector2d;
import agh.ics.poproject.model.elements.Animal;
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

    public Day(Simulation simulation) {
        this.simulation = simulation;
        this.config = simulation.getConfig();

    }

    public void FirstDayActivities() throws IncorrectPositionException {
        generateAnimals();
        generatePlants();
    }


    private void generateAnimals() throws IncorrectPositionException {
        int numberOfAnimalsToGenerate = config.initialAnimals();
        // zakładamy, że zwierzaki nie mogą się pojawiać na tym samym polu, można o to dopytać
        Set<Vector2d> generatedAnimalsRandomPositions = getRandomPositions(numberOfAnimalsToGenerate);
        for (Vector2d position : generatedAnimalsRandomPositions) {
            Genome genome = new Genome(config.genomeLength());
            Animal animal = new Animal(position, genome, config.initialEnergy());
            simulation.addAnimal(animal);
            simulation.getWorldMap().place(animal);  // pokazanie na mapie
        }
    }

    private void generatePlants() {
        int numberOfPlantsToGenerate = config.initialPlants();
        Set<Vector2d> generatedAnimalsRandomPositions = getRandomPositions(numberOfPlantsToGenerate);
        for (Vector2d position : generatedAnimalsRandomPositions) {
            Genome genome = new Genome(config.genomeLength());
            Animal animal = new Animal(position, genome, config.initialEnergy());
            simulation.addAnimal(animal);
            // TODO:simulation.getWorldMap().place(animal); musi byc tez place dla roślinek, bo ta na razie działa tylko dla zwierzakó
        }

    }

    /**
     * Generate random position of elements knowing that only one element from one category can be on each position.
     */
    private Set<Vector2d> getRandomPositions (int numberOfElementsToGenerate) {
        Random random = new Random();
        Set<Vector2d> uniquePositions = new HashSet<>();
        while (uniquePositions.size() < numberOfElementsToGenerate) {
            int x = random.nextInt(config.mapWidth() + 1);
            int y = random.nextInt(config.mapHeight() + 1);
            Vector2d position = new Vector2d(x, y);
            uniquePositions.add(position);
        }
        return uniquePositions;
    }


    public void EveryDayActivities() {
        removeDeadAnimals();
        moveAndRotateAnimals();
        consumePlants();
        reproduceAnimals();
        growNewPlants();
    }

    /**
     * Removes dead animal from animals list in Simulation class.
     */
    private void removeDeadAnimals() {
        List<Animal> animals = simulation.getAnimals();
        animals.removeIf(Animal::isDead);
        // TODO:wywołanie metody remove z mapy -> usuń zwierzaka
    }

    private void moveAndRotateAnimals() {
        List<Animal> animals = simulation.getAnimals();
        for (Animal animal : animals) {
            simulation.getWorldMap().move(animal);
        }
        }


    private void growNewPlants() {
        int numberOfPlants = config.dailyPlantGrowth();
        // TODO:wywołanie metody growPlants z worldMap -> implementacja  jej
    }

    private void reproduceAnimals() {
    }

    private void consumePlants() {
    }


}
