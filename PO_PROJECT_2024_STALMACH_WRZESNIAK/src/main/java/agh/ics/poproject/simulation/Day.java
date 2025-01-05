package agh.ics.poproject.simulation;

public class Day {
    private final Simulation simulation;

    public Day(Simulation simulation) {
        this.simulation = simulation;
    }

    public void FirstDayActivities() {
        generateAnimals();
        generatePlants();
    }


    private void generateAnimals() {
    }

    private void generatePlants() {
    }

    public void EveryDayActivities() {
        removeDeadAnimals();
        moveAndRotateAnimals();
        consumePlants();
        reproduceAnimals();
        growNewPlants();
    }

    private void removeDeadAnimals() {
    }

    private void growNewPlants() {
    }

    private void reproduceAnimals() {
    }

    private void consumePlants() {
    }

    private void moveAndRotateAnimals() {
    }


}
