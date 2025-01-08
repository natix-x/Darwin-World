package agh.ics.poproject.simulation;

import agh.ics.poproject.model.Vector2d;
import agh.ics.poproject.model.map.IncorrectPositionException;
import agh.ics.poproject.util.Configuration;
import org.junit.jupiter.api.Test;

import java.util.Vector;

import static org.junit.jupiter.api.Assertions.*;

class DayTest {

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
    Day day = new Day(simulation);

    @Test
    void checkIfAnimalsGenerateCorrectly() throws IncorrectPositionException {
        day.firstDayActivities();
        assertEquals(5, simulation.getAnimals().size());
    }

    @Test
    void checkIfPlantsGenerateCorrectly() throws IncorrectPositionException {
        day.firstDayActivities();
        assertEquals(4, simulation.getPlants().size());
    }

    @Test
    void checkIfAgingWorks() throws IncorrectPositionException {
        day.firstDayActivities();
        assertEquals(1, simulation.getAnimals().getFirst().getAge());
        day.everyDayActivities();
        assertEquals(2, simulation.getAnimals().getFirst().getAge());
    }

    @Test
    void checkIfDaySimulationWorks() throws IncorrectPositionException {
        Day testDay = new Day(simulation);
        testDay.firstDayActivities();
        assertEquals(5, simulation.getAnimals().size());
        assertEquals(4, simulation.getPlants().size());

        Vector2d positionBefore = simulation.getAnimals().getFirst().getPosition();

        testDay.everyDayActivities();
        Vector2d positionAfter = simulation.getAnimals().getFirst().getPosition();

        //checks if animal is moving after simulating the day
        assertNotEquals(positionBefore, positionAfter);
    }

}