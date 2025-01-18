package agh.ics.poproject.inheritance;

import agh.ics.poproject.model.elements.Animal;

import java.util.ArrayList;
import java.util.Random;

public class Reproduction {

    private final int energyNeededToReproduce;
    MutationMethod mutationMethod;

    public Reproduction(int energyNeededToReproduce, MutationMethod mutationMethod) {
        this.energyNeededToReproduce = energyNeededToReproduce;
        this.mutationMethod = mutationMethod;
    }

    /**
    Checks if two animals have enough energy to reproduce based on parameters set in configuration
     */
    public boolean canReproduce(Animal animal1, Animal animal2) {
        return animal1.getRemainingEnergy() >= energyNeededToReproduce && animal2.getRemainingEnergy() >= energyNeededToReproduce;
    }

    /**
     * Shuffles parents' genomes accordingly to create baby's genome.
     * Calculates the percentage of inheritance of each parent based on energy level and combines the genes
     *
     * @param animal1 first parent
     * @param animal2 second parent
     * @return Genome of the baby animal
     */

    public Genome shuffleGenome(Animal animal1, Animal animal2) {
        ArrayList<Integer> babyGenomeList = new ArrayList<>();

        Random rand = new Random();
        int randomGenomeSide = rand.nextInt(2);

        Genome gene1 = animal1.getGenome();
        Genome gene2 = animal2.getGenome();
        int genomeLength = gene1.getGenesSequence().size();

        double energy1 = animal1.getRemainingEnergy();
        double energy2 = animal2.getRemainingEnergy();

        int splitIndex = Math.min((int) Math.round((energy1 / (energy1 + energy2)) * genomeLength), genomeLength - 1);

        if (randomGenomeSide == 0) {
            babyGenomeList.addAll(gene1.getGenesSequence().subList(0, splitIndex));
            babyGenomeList.addAll(gene2.getGenesSequence().subList(splitIndex, genomeLength));
        } else {
            babyGenomeList.addAll(gene2.getGenesSequence().subList(0, splitIndex));
            babyGenomeList.addAll(gene1.getGenesSequence().subList(splitIndex, genomeLength));
        }
        mutationMethod.mutateGenome(babyGenomeList);

        return new Genome(babyGenomeList);
    }

    /**
    Animals reproduce, their energy level goes down and genes are shuffled.
    @return Animal object with inherited genes and initial energy on the parents' position
     */
    // TODO: dodać mutacje
    public Animal reproduce (Animal animal1, Animal animal2){
        if (canReproduce(animal1, animal2)) {
            animal1.changeEnergy(-energyNeededToReproduce);
            animal2.changeEnergy(-energyNeededToReproduce);
            animal1.addAChild();
            animal2.addAChild();
            Genome babyGenome = shuffleGenome(animal1, animal2);
            return new Animal(animal1.getPosition(), babyGenome, energyNeededToReproduce * 2);
        }
        return null;
    };

}



