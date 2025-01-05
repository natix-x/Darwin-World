package agh.ics.poproject.simulation;

import agh.ics.poproject.inheritance.Genome;
import agh.ics.poproject.inheritance.Reproduce;
import agh.ics.poproject.model.Vector2d;
import agh.ics.poproject.model.elements.Animal;
import agh.ics.poproject.model.elements.Plant;
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

    /**
    Generates all necessary elements for simulation launch
     */
    public void FirstDayActivities() throws IncorrectPositionException {
        generateAnimals();
        generatePlants();
    }

    /**
     Generates and updated all map elements in the timeframe of one day
     */
    public void EveryDayActivities() throws IncorrectPositionException {
        removeDeadAnimals();
        moveAndRotateAnimals();
        consumePlants();
        reproduceAnimals();
        growNewPlants();
        //TODO: można pomyśleć nad jakimś counterem dni
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
     * @param numberOfElementsToGenerate is the number of map elements that are going be placed on the map
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

    private void reproduceAnimals() throws IncorrectPositionException {
        List<Animal> animals = simulation.getAnimals();
        for (Animal animal1 : animals) {
            for (Animal animal2 : animals) {
                if (animal1.getPosition().equals(animal2.getPosition())) {
                    Reproduce reproduction = new Reproduce();
                    Animal animal3 = reproduction.reproduce(animal1, animal2);
                    simulation.addAnimal(animal3);
                    simulation.getWorldMap().place(animal3);
                }
            }
        }

    }

    private void consumePlants() {
        List<Animal> animals = simulation.getAnimals();
        List<Plant> plants = simulation.getPlants();
        for (Animal animal : animals) {
            for (Plant plant : plants) {
                if (animal.getPosition().equals(plant.getPosition())) {
                    animal.eat();
                    plants.remove(plant);
                }
            }
        }
    }


}
