package agh.ics.poproject.simulation;

import agh.ics.poproject.model.Vector2d;
import agh.ics.poproject.model.elements.Animal;
import agh.ics.poproject.model.elements.Plant;
import agh.ics.poproject.model.map.GlobeMap;
import agh.ics.poproject.model.map.IncorrectPositionException;
import agh.ics.poproject.simulation.statistics.Stats;
import agh.ics.poproject.util.Configuration;
import agh.ics.poproject.util.SaveStats;
import javafx.beans.property.BooleanProperty;
import javafx.beans.property.SimpleBooleanProperty;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.UUID;

// TODO: obsłużyć to że jak zamykamy okno to symulacja się kończy (tak to nadal działa)
public class Simulation {
    private final Configuration config;
    private final BooleanProperty stopped = new SimpleBooleanProperty(false);
    private Stats stats;
    private GlobeMap worldMap;
    private ArrayList<Animal> aliveAnimals = new ArrayList<>();
    private ArrayList<Animal> deadAnimals = new ArrayList<>();
    private ArrayList<Plant> plants = new ArrayList<>();
    private final UUID simulationId = UUID.randomUUID();
    private SaveStats saveStats;

    private int dayCount = 0;

    public Simulation(Configuration config) throws IOException {
        this.config = config;

        if (config.isGlobeMap()) {
            this.setWorldMap(new GlobeMap(config.mapWidth(), config.mapHeight()));
        }
        this.stats = new Stats(this);
        if (config.saveStats()) {
            saveStats = new SaveStats(stats, simulationId);
        }
    }

    public ArrayList<Animal> getAliveAnimals() {
        return aliveAnimals;
    }

    public ArrayList<Animal> getDeadAnimals() {
        return deadAnimals;
    }

    public List<Plant> getPlants() {
        return plants;
    }

    public GlobeMap getWorldMap() {
        return worldMap;
    }

    public void setWorldMap(GlobeMap worldMap) {
        this.worldMap = worldMap;
    }

    public Configuration getConfig() {
        return config;
    }

    public Stats getStats() {
        return stats;
    }

    // TODO: osbługa errorów

    public void run() {
        System.out.println("Simulation started");
        Day simulationDay = new Day(this);
        try {
            simulationDay.firstDayActivities();
            dayCount++;
            System.out.println(dayCount);
        } catch (IncorrectPositionException e) {
            throw new RuntimeException(e);
        }

        while (!aliveAnimals.isEmpty()) {
            if (isStopped()) {
                continue;
            }
            try {
                simulationDay.everyDayActivities();
                if (saveStats != null) {
                    saveStats.saveDayStats();
                }
                dayCount++;
                // Wait for 1 second
                System.out.println(dayCount);
                Thread.sleep(1000);
            } catch (IncorrectPositionException e) {
                throw new RuntimeException(e);
            } catch (InterruptedException e) {
                Thread.currentThread().interrupt(); // Restore interrupt status
                throw new RuntimeException("Thread was interrupted", e);
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
        }
    }

    public void addAliveAnimal(Animal animal) {
        aliveAnimals.add(animal);
    }

    public void addDeadAnimal(Animal animal) {
        deadAnimals.add(animal);
    }

    public void addPlant(Plant plant) {
        plants.add(plant);
    }

    public void removePlant(Plant plant) {
        plants.remove(plant);
    }

    public void stop() {
        this.stopped.set(true);
    }

    public void resume() {
        this.stopped.set(false);
    }

    public boolean isStopped() {
        return stopped.get();
    }
}
