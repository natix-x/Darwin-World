package agh.ics.poproject.statistics;

import agh.ics.poproject.inheritance.Genome;
import agh.ics.poproject.model.elements.Animal;
import agh.ics.poproject.simulation.Simulation;

import java.util.List;

public class Stats {

    private Simulation simulation;

    public Stats(Simulation simulation) {
        this.simulation = simulation;
    }

    public int countAnimalsNumber() {
        return simulation.getAliveAnimals().size();
    }

    public int countPlantsNumber() {
        return simulation.getPlants().size();
    }

    // liczba wolnych pól to chyba liczba niezajętych przez zwierzaki? rośliny nie mają znaczenia? można dopytać
    public int countNumberOfPositionsUnoccupiedByAnyAnimal() {
        // TODO: implement
        return 0;
    }

    public Genome getMostPopularGenotype() {
        // TODO: implement
        return null;
    }

    // TODO: refaktoryzacja powtarzającego się kodu
    /**
     * Counts average energy of alive animals.
     * If there are no animals returns 0.
     *
     */
    public int countAverageEnergyOfAliveAnimals() {
        List<Animal> aliveAnimals = simulation.getAliveAnimals();
        if (aliveAnimals == null || aliveAnimals.isEmpty()) {
            return 0;
        }
        int energySum = 0;
        for (Animal animal : aliveAnimals) {
            energySum += animal.getRemainingEnergy();
        }
        return energySum / aliveAnimals.size();
    }


    /**
     * Counts average lifespan for dead animals.
     * If there are no dead animals count average energy of alive animals.
     *
     */
    public int countAverageAnimalLifeSpan() {
        List<Animal> deadAnimals = simulation.getDeadAnimals();
        if (deadAnimals == null || deadAnimals.isEmpty()) {
            return countAverageEnergyOfAliveAnimals();
        }
        int ageSum = 0;
        for (Animal animal : deadAnimals) {
            ageSum += animal.getAge();
        }
        return ageSum / deadAnimals.size();
    }

    /**
     * Counts average number of all children for alive animals.
     * If there are no animals with children returns 0.
     *
     */
    public int countAverageNumberOfChildrenForAliveAnimals() {
        List<Animal> aliveAnimals = simulation.getAliveAnimals();
        if (aliveAnimals == null || aliveAnimals.isEmpty()) {
            return 0;
        }
        int childrenSum = 0;
        for (Animal animal : aliveAnimals) {
            childrenSum += animal.getAmountOfChildren();
        }
        return childrenSum / aliveAnimals.size();
    }
}
