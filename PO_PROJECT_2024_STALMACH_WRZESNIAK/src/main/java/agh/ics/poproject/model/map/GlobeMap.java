package agh.ics.poproject.model.map;


import agh.ics.poproject.inheritance.Genome;
import agh.ics.poproject.inheritance.Reproduce;
import agh.ics.poproject.model.Vector2d;
import agh.ics.poproject.model.elements.Animal;
import agh.ics.poproject.model.elements.Plant;
import agh.ics.poproject.model.elements.WorldElement;
import agh.ics.poproject.simulation.Simulation;
import agh.ics.poproject.util.Configuration;

import java.util.*;

public class GlobeMap extends AbstractWorldMap {
    private static final Vector2d LOWER_BOUND = new Vector2d(0, 0);
    private static final int LEFT_EDGE = LOWER_BOUND.x();
    private static final int BOTTOM_EDGE = LOWER_BOUND.y();
    private final Vector2d upperBound;
    private final int rightEdge;
    private final int topEdge;

    public GlobeMap(int width, int height) {
        this.upperBound = new Vector2d(width, height);
        this.rightEdge = upperBound.x();
        this.topEdge = upperBound.y();
    }

    @Override
    public boolean canMoveTo(Vector2d position) {
        return !isBeyondTopOrBottomEdge(position) && (position.follows(LOWER_BOUND) && position.precedes(upperBound));
    }

    @Override
    public Boundary getCurrentBounds() {
        return new Boundary(LOWER_BOUND, upperBound);
    }

    private boolean isBeyondTopOrBottomEdge(Vector2d position) {
        return position.y() > topEdge || position.y() < BOTTOM_EDGE;
    }

    private boolean isBeyondRightEdge(Vector2d position) {
        return position.x() > rightEdge;
    }

    private boolean isBeyondLeftEdge(Vector2d position) {
        return position.x() < LEFT_EDGE;
    }

    /**
     Moves the specified animal to a new position on the map.
     Throws an exception if the animal is not found at its current position.
     @param animal animal that will be moved
     */
    @Override
    public void move(Animal animal) {
        Vector2d currentPosition = animal.getPosition();
        List<Animal> animalsAtSelectedPosition = animals.get(currentPosition);
        if (animalsAtSelectedPosition !=null && animalsAtSelectedPosition.contains(animal)) {
            animalsAtSelectedPosition.remove(animal);
            if (animalsAtSelectedPosition.isEmpty()) {
                animals.remove(currentPosition);
            }
            animal.rotateAndMove(animal.getGenome().getActiveGene(), this);
            Vector2d animalNewPosition = animal.getPosition();
            if (isBeyondLeftEdge(animalNewPosition)) {
                animalNewPosition = new Vector2d(rightEdge, animalNewPosition.y());
            } else if (isBeyondRightEdge(animalNewPosition)) {
                animalNewPosition = new Vector2d(LEFT_EDGE, animalNewPosition.y());
            }
            animal.setPosition(animalNewPosition);
            animals.computeIfAbsent(animalNewPosition, k -> new ArrayList<>()).add(animal);
            super.mapChanged("Animal moved to the position " + animal.getPosition());
        } else {
            throw new IllegalArgumentException("Animal not found at position " + currentPosition);
        }
    }

    @Override
    public void placeWorldElement(WorldElement element) throws IncorrectPositionException {
        Vector2d position = element.getPosition();
        if (element instanceof Animal) {
            placeAnimal(position, (Animal) element);
        }
        else if (element instanceof Plant) {
            placePlant(position, (Plant) element);
        }
    }

    private void placeAnimal(Vector2d position, Animal animal) throws IncorrectPositionException {
        if (canMoveTo(position)) {
            animals.computeIfAbsent(position, k -> new ArrayList<>()).add(animal);
            mapChanged("Animal placed at position " + position);
        }
        else
            throw new IncorrectPositionException(position);
    }


    private void placePlant(Vector2d position, Plant plant) throws IncorrectPositionException {
        if (canPlantGrow(position)) {
            plants.put(position, plant);
            mapChanged("Plant placed at position " + position);
        }
        else
            throw new IncorrectPositionException(position);
    }

    /**
     Checks if plant can grow on selected position.
     Plant can grow on every position except position occupied by another plant.
     */
    private boolean canPlantGrow(Vector2d position) {
        return plants.get(position) == null;
    }

    /**
     Removes element from world map, first checking if its type is animal or plant
     */
    @Override
    public void removeElement(WorldElement element, Vector2d position) {
        if (element instanceof Animal) {
            List<Animal> animalsAtPosition = animals.get(element.getPosition());
                animalsAtPosition.remove(element);
                if (animalsAtPosition.isEmpty()) {
                    animals.remove(element.getPosition());
                }
                mapChanged("Removed animal at " + element.getPosition());
        }
        else if (element instanceof Plant) {
            plants.remove(position);
            mapChanged("Added plant at " + element.getPosition());
        }
    }

    public void generateAnimals(Simulation simulation) throws IncorrectPositionException {
        Configuration config = simulation.getConfig();

        int numberOfAnimalsToGenerate = config.initialAnimals();
        // zakładamy, że zwierzaki nie mogą się pojawiać na tym samym polu, można o to dopytać
        Set<Vector2d> generatedAnimalsRandomPositions = getRandomPositions(numberOfAnimalsToGenerate);
        for (Vector2d position : generatedAnimalsRandomPositions) {
            Genome genome = new Genome(config.genomeLength());
            Animal animal = new Animal(position, genome, config.initialEnergy());
            simulation.addAliveAnimal(animal);
            simulation.getWorldMap().placeWorldElement(animal);  // pokazanie na mapie
        }
    }

    public void generatePlants(Simulation simulation) throws IncorrectPositionException {
        Configuration config = simulation.getConfig();
        int numberOfPlantsToGenerate = config.initialPlants();
        Set<Vector2d> generatedPlantsRandomPositions = getRandomPositions(numberOfPlantsToGenerate);
        for (Vector2d position : generatedPlantsRandomPositions) {
            Plant plant = new Plant(position);
            simulation.addPlant(plant);
            simulation.getWorldMap().placeWorldElement(plant);
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
            int x = random.nextInt(rightEdge);
            int y = random.nextInt(topEdge);
            Vector2d position = new Vector2d(x, y);
            uniquePositions.add(position);
        }
        return uniquePositions;
    }


    private List<Animal> checkAnimalStatus(Simulation simulation) {
        List<Animal> deadAnimals = new ArrayList<>();
        for (Animal animal : simulation.getAliveAnimals()) {
            if (animal.isDead()) {
                deadAnimals.add(animal);
            }
        }
        return deadAnimals;
    }

    /**
     * Removes dead animals from the map
     * @param simulation chosen simulation
     */
    public void removeDeadAnimals(Simulation simulation) {
        List<Animal> deadAnimals = checkAnimalStatus(simulation);
        WorldMap worldMap = simulation.getWorldMap();

        for (Animal deadAnimal : deadAnimals) {
            simulation.removeFromAliveAnimal(deadAnimal);
            simulation.addDeadAnimal(deadAnimal);
            worldMap.removeElement(deadAnimal, deadAnimal.getPosition());
            mapChanged("Removed dead animal at " + deadAnimal.getPosition());
        }
    }


    public void growNewPlants(Simulation simulation) {
        Configuration config = simulation.getConfig();
        int numberOfPlants = config.dailyPlantGrowth();

        // TODO:wywołanie metody growPlants z worldMap -> implementacja  jej
    }

    /**
     * Establishes the animal that will consume the Plant, resolves conflicts in case of multiple animals on a position.
     * Handles the simulation update post plant consumption
     * @param simulation chosen simulation
     */
    public void consumePlants(Simulation simulation) {
        int energyPerPlant = simulation.getConfig().energyPerPlant();

        for (Plant plant : new ArrayList<>(plants.values())) { // Avoid concurrent modification
            Vector2d plantPosition = plant.getPosition();

            if (animals.containsKey(plantPosition)) {
                List<Animal> animalsAtPosition = animals.get(plantPosition);
                Animal highestPriorityAnimal = animalsAtPosition.stream()
                        .max(Comparator.comparingInt(Animal::getRemainingEnergy)
                                .thenComparingInt(Animal::getAge))
                        .orElse(null);

                if (highestPriorityAnimal != null) {
                    highestPriorityAnimal.eat(energyPerPlant);
                    plants.remove(plantPosition);
                    simulation.getPlants().remove(plant);
                    mapChanged("Plant at " + plantPosition + " was eaten.");
                }
            }
        }
    }


    /**
     * Establishes the animals that will reproduce, resolves conflicts in case of multiple animals on a position.
     * Handles the simulation update post reproduction
     * @param simulation chosen simulation
     */
    public void reproduceAnimals(Simulation simulation) throws IncorrectPositionException {
        for (Map.Entry<Vector2d, List<Animal>> entry : animals.entrySet()) {
            Vector2d position = entry.getKey();
            List<Animal> animalsAtPosition = entry.getValue().stream()
                    .filter(animal -> animal.getRemainingEnergy() >= simulation.getConfig().neededEnergyForReproduction())
                    .sorted(Comparator.comparingInt(Animal::getRemainingEnergy).reversed()
                            .thenComparingInt(Animal::getAge))
                    .toList();

            if (animalsAtPosition.size() >= 2) {
                Animal parent1 = animalsAtPosition.get(0);
                Animal parent2 = animalsAtPosition.get(1);

                Reproduce reproduction = new Reproduce();
                Animal offspring = reproduction.reproduce(parent1, parent2);

                simulation.addAliveAnimal(offspring);
                simulation.getWorldMap().placeWorldElement(offspring);
                mapChanged("New animal born at " + position);
            }
        }
    }


//    /**
//     * Sorts animals according to requirements to resolve reproduction and plant consumption conflicts
//     * @param simulation chosen simulation
//     * @return List of sorted animals
//     */
//    private List<Animal> getSortedAnimals(Simulation simulation) {
//        return simulation.getAliveAnimals().stream()
//                .filter(animal -> animal.getRemainingEnergy() >= simulation.getConfig().neededEnergyForReproduction())
//                .sorted(Comparator.comparingInt(Animal::getRemainingEnergy).reversed()
//                        .thenComparingInt(Animal::getAge))
//                .toList();
//    }







}

