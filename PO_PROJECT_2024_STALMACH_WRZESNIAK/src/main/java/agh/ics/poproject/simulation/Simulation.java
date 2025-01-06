package agh.ics.poproject.simulation;

import agh.ics.poproject.model.elements.Animal;
import agh.ics.poproject.model.elements.Plant;
import agh.ics.poproject.model.map.WorldMap;
import agh.ics.poproject.util.Configuration;

import java.util.ArrayList;
import java.util.List;

public class Simulation implements Runnable {
    //to tak jakby nasz engine do backendu, tu powinny być wszystkie listy zwierząt itd
    private final Configuration config;


    private  WorldMap worldMap;  // można pomyśleć czy tu nie stworzyć GlobeMap a nie w SImulationPresenter?
    private ArrayList<Animal> animals;
    private ArrayList<Plant> plants;

    public Simulation(Configuration config) {
        this.config = config;
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



    @Override
    public void run() {
        System.out.println("Engine simulation started");

    }

    public void addAnimal(Animal animal) {
        animals.add(animal);
    }

    public void addPlant(Plant plant) {
        plants.add(plant);
    }





}
