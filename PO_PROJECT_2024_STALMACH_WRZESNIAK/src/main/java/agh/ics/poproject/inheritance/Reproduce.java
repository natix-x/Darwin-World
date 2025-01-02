package agh.ics.poproject.inheritance;

import agh.ics.poproject.model.Vector2d;
import agh.ics.poproject.model.elements.Animal;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class Reproduce {

    // TODO: połączyć te wartości z wartościami przekazywanej do symulacji konfiguracji
    int energyNeededToReproduce;
    int genomeLength;

    /*
    Checks if two animals have enough energy to reproduce based on parametes set in configuration
     */
    public boolean canReproduce(Animal animal1, Animal animal2) {
        return animal1.getRemainingEnergy() >= energyNeededToReproduce && animal2.getRemainingEnergy() >= energyNeededToReproduce;
    }

    /*
    Shuffles parents' genomes accordingly to create baby's genome.
    Calculates the percentage of inheritance of each parent based on energy level and combines the genes
    @return Genome of the baby animal
     */
    public Genome shuffleGenome(Animal animal1, Animal animal2) {
        ArrayList<Integer> babyGenomeList = new ArrayList<>();

        Random rand = new Random();
        int randomGenomeSide = rand.nextInt(2);

        Genome gene1 = animal1.getGenome();
        Genome gene2 = animal2.getGenome();

        double energy1 = animal1.getRemainingEnergy();
        double energy2 = animal2.getRemainingEnergy();

        double divisionRatioPercent = Math.floor((energy1 / (energy1 + energy2) * 100) * 8 / 100);
        int splitIndex = (int) Math.floor(genomeLength * divisionRatioPercent);

        // TODO: pewnie da się to jakoś zgrabniej
        if (randomGenomeSide == 0) {
            for (int i = 0; i < splitIndex; i++) {
                if (energy1 > energy2) {
                    babyGenomeList.add(gene1.getGenomes().get(i));
                    for (int j = splitIndex; j < genomeLength; j++) {
                        babyGenomeList.add(gene2.getGenomes().get(j));
                    }
                } else {
                    babyGenomeList.add(gene2.getGenomes().get(i));
                    for (int j = splitIndex; j < genomeLength; j++) {
                        babyGenomeList.add(gene1.getGenomes().get(j));
                    }
                }
            }

        } else {
            for (int i = splitIndex; i < genomeLength; i++) {
                if (energy1 > energy2) {
                    babyGenomeList.add(gene1.getGenomes().get(i));
                    for (int j = 0; j < splitIndex; j++) {
                        babyGenomeList.add(gene2.getGenomes().get(j));
                    }
                } else {
                    babyGenomeList.add(gene2.getGenomes().get(i));
                    for (int j = 0; j < splitIndex; j++) {
                        babyGenomeList.add(gene1.getGenomes().get(j));
                    }
                }
            }
        }

        return new Genome(babyGenomeList);
    };

    /*
    Animals reproduce, their energy level goes down and genes are shuffled.
    @return Animal object with inherited genes and initial energy on the parents' position
     */
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

