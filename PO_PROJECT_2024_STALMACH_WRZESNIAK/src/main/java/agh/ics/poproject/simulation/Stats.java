package agh.ics.poproject.simulation;

import agh.ics.poproject.inheritance.Genome;
import agh.ics.poproject.model.elements.Animal;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class Stats {

    private final Simulation simulation;

    public Stats(Simulation simulation) {
        this.simulation = simulation;
    }

    // zakładam, że chodzi o żywe zwierzaki
    public int countAnimalsNumber() {
        return simulation.getAliveAnimals().size();
    }

    public int countPlantsNumber() {
        return simulation.getPlants().size();
    }

    public int countNumberOfPositionsUnoccupiedByAnyAnimal() {
        int allPositions = simulation.getWorldMap().calculateCurrentSurface();
        int occupiedPositionsByAnimals = simulation.getWorldMap().getAnimals().size();
        return allPositions - occupiedPositionsByAnimals;
    }

    public Genome getMostPopularGenotype() {
        ArrayList<Animal> allAnimals = simulation.getAliveAnimals();

        Map<Genome, Integer> genotypeCounts = new HashMap<>();
        for (Animal animal : allAnimals) {
            Genome genotype = animal.getGenome();
            genotypeCounts.put(genotype, genotypeCounts.getOrDefault(genotype, 0) + 1);
        }

        Genome mostPopularGenotype = null;
        int maxCount = 0;
        for (Map.Entry<Genome, Integer> entry : genotypeCounts.entrySet()) {
            if (entry.getValue() > maxCount) {
                maxCount = entry.getValue();
                mostPopularGenotype = entry.getKey();
            }
        }
        return mostPopularGenotype;
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
    public double countAverageNumberOfChildrenForAliveAnimals() {
        List<Animal> aliveAnimals = simulation.getAliveAnimals();
        if (aliveAnimals == null || aliveAnimals.isEmpty()) {
            return 0;
        }
        double childrenSum = 0;
        for (Animal animal : aliveAnimals) {
            childrenSum += animal.getAmountOfChildren();
        }
        return childrenSum / aliveAnimals.size();
    }
}
