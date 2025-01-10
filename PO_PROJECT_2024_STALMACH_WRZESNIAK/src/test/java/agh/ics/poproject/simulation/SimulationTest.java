package agh.ics.poproject.simulation;

import agh.ics.poproject.inheritance.Genome;
import agh.ics.poproject.model.Vector2d;
import agh.ics.poproject.model.elements.Animal;
import agh.ics.poproject.model.elements.Plant;
import agh.ics.poproject.model.map.GlobeMap;
import agh.ics.poproject.model.map.IncorrectPositionException;
import agh.ics.poproject.util.Configuration;
import org.junit.jupiter.api.Test;


import static org.junit.jupiter.api.Assertions.*;

class SimulationTest {

Configuration config = new Configuration(   10,
                                            10,
                                            4,
                                            3,
                                            1,
                                            5,
                                            20,
                                            8,
                                            8,
                                            2,
                                            8,
                                            16,
                                            true,
                                            true,
                                            true,
                                            true,
                                            false,
                                            true,
                                            true);
    Simulation simulation = new Simulation(config);
    Day day1 = new Day(simulation);

    @Test
    void checkGetAnimals() throws IncorrectPositionException {
        Animal animal1 = new Animal(new Vector2d(1, 1), new Genome(config.genomeLength()), config.initialEnergy());
        simulation.addAliveAnimal(animal1);
        assertTrue(simulation.getAliveAnimals().contains(animal1));
        assertFalse(simulation.getAliveAnimals().isEmpty());
    }

    @Test
    void checkGetPlants() {
        Plant plant = new Plant(new Vector2d(2, 2));
        simulation.addPlant(plant);
        assertTrue(simulation.getPlants().contains(plant));
        assertFalse(simulation.getPlants().isEmpty());
    }

    @Test
    void checkGetWorldMap() {
        assertNotNull((simulation.getWorldMap()));
        assertInstanceOf(GlobeMap.class, simulation.getWorldMap());
    }
}