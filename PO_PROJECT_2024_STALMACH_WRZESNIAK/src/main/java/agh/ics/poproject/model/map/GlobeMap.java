package agh.ics.poproject.model.map;


import agh.ics.poproject.model.MapChangeListener;
import agh.ics.poproject.model.Vector2d;
import agh.ics.poproject.model.elements.Animal;
import agh.ics.poproject.model.elements.Plant;
import agh.ics.poproject.model.elements.WorldElement;
import agh.ics.poproject.model.util.MapVisualizer;
import agh.ics.poproject.simulation.Simulation;
import agh.ics.poproject.util.Configuration;

import java.util.*;

// TODO: refaktoryzacja zgodna ze wzoracami projektowymi - niesprawdzanie istance of itp
public class GlobeMap implements WorldMap {
    private static final Vector2d LOWER_BOUND = new Vector2d(0, 0);
    private static final int LEFT_EDGE = LOWER_BOUND.x();
    private static final int BOTTOM_EDGE = LOWER_BOUND.y();
    private final Vector2d upperBound;
    private final int rightEdge;
    private final int topEdge;
    private final Map<Vector2d, List<Animal>> animals = new HashMap<>();
    private final Map<Vector2d, Plant> plants = new HashMap<>();
    private final List<MapChangeListener> mapChangeListeners = new ArrayList<>();
    private final UUID mapID = UUID.randomUUID();

    public GlobeMap(int width, int height) {
        this.upperBound = new Vector2d(width - 1, height - 1);
        this.rightEdge = upperBound.x();
        this.topEdge = upperBound.y();
    }

    @Override
    public List<WorldElement> getElements() {;
        List<WorldElement> elements = new ArrayList<>();
        animals.values().forEach(elements::addAll);
        elements.addAll(plants.values());
        return elements;
    }

    public Map<Vector2d, List<Animal>> getAnimals() {
        return animals;
    }

    public Map<Vector2d, Plant> getPlants() {
        return plants;
    }

    @Override
    public UUID getId() {
        return mapID;
    }

    @Override
    public void subscribe(MapChangeListener listener) {
        mapChangeListeners.add(listener);
    }

    public void unsubscribe(MapChangeListener listener) {
        mapChangeListeners.remove(listener);
    }

    @Override
    public String toString() {
        MapVisualizer map = new MapVisualizer(this);
        Boundary currentBounds = getCurrentBounds();
        return map.draw(currentBounds.LowerBound(), currentBounds.UpperBound());
    }

    protected void mapChanged(String message){
        mapChangeListeners.forEach(listener -> listener.mapChange(this, message));
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
            mapChanged("Animal moved to the position " + animal.getPosition());
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

    // TODO: zastosować wzorzec projektowy
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

    // TODO zastanowić się później co ta metoda ma zwracać w przypadku kiedy na jednym polu jest wiele zwierząt / zwierząt i roślina
    @Override
    public WorldElement objectAt(Vector2d position) {
        if (animals.containsKey(position)) {
            return animals.get(position).getFirst();  // for test purposes, pewnie trzeba zwrocic wszytsko?
        }
        else if (plants.containsKey(position)) {
            return plants.get(position);
        }
        return null;
    }

    // TODO: implenetacja PlantGrowingMethod
    public void growNewPlants(Simulation simulation) {
        Configuration config = simulation.getConfig();
        int numberOfPlants = config.dailyPlantGrowth();


        // TODO:wywołanie metody growPlants z worldMap -> implementacja  jej
    }

    public int calculateArea() {
        return rightEdge * topEdge;
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

