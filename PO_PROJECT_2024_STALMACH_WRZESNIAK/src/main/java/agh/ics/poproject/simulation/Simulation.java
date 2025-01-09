package agh.ics.poproject.simulation;

import agh.ics.poproject.model.elements.Animal;
import agh.ics.poproject.model.elements.Plant;
import agh.ics.poproject.model.map.GlobeMap;
import agh.ics.poproject.model.map.IncorrectPositionException;
import agh.ics.poproject.model.map.WorldMap;
import agh.ics.poproject.util.Configuration;

import java.util.ArrayList;
import java.util.List;

public class Simulation implements Runnable {
    //to tak jakby nasz engine do backendu, tu powinny być wszystkie listy zwierząt itd
    private final Configuration config;


    private WorldMap worldMap;  // można pomyśleć czy tu nie stworzyć GlobeMap a nie w SImulationPresenter?
    private ArrayList<Animal> animals;
    private ArrayList<Plant> plants;

    public Simulation(Configuration config) {
        this.config = config;

        if (config.isGlobeMap()) {
            this.setWorldMap(new GlobeMap(config.mapWidth(), config.mapHeight()));
        }

        this.animals = new ArrayList<>();
        this.plants = new ArrayList<>();
    }

    public List<Animal> getAnimals() {
        return animals;
    }

    public List<Plant> getPlants() {
        return plants;
    }

    public WorldMap getWorldMap() {
        return worldMap;
    }


    public void setWorldMap(WorldMap worldMap) {
        this.worldMap = worldMap;
    }

    public Configuration getConfig() {
        return config;
    }

    // TODO: osbługa errorów
    @Override
    public void run()  {
        System.out.println("Simulation started");
        Day simulationDay = new Day(this);
        try {
            simulationDay.firstDayActivities();
        } catch (IncorrectPositionException e) {
            throw new RuntimeException(e);
        }
        for (Animal animal : animals) {
            System.out.println(animal.getPosition());
        }
        int day = 0;
        while (!animals.isEmpty()) {
            try {
                System.out.println(day++);
                simulationDay.everyDayActivities();
            } catch (IncorrectPositionException e) {
                throw new RuntimeException(e);
            }
        }
    }

    public void addAnimal(Animal animal) {
        animals.add(animal);
    }

    public void addPlant(Plant plant) {
        plants.add(plant);
    }


}
