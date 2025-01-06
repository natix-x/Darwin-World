package agh.ics.poproject.inheritance;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class Genome {

    private final ArrayList<Integer> genomes;

    private int activateGene;

    // for generated animals
    public Genome(int genesNumber) {
        this.genomes = generateRandomGenome(genesNumber);
        activateGene = activateRandomGene();
    }

    // for babies
    public Genome(List<Integer> genomes) {
        this.genomes = new ArrayList<>(genomes);
        activateGene = activateRandomGene();
    }

    public int getActivateGene() {
        return activateGene;
    }

    public ArrayList<Integer> getGenomes() {
        return new ArrayList<>(genomes);
    }

    /**
     Activates random gene in generated or born animals' genome.
     */
    private int activateRandomGene() {
        Random random = new Random();
        return genomes.get(random.nextInt(genomes.size()));
    }

    /**
     Generates random genome (for animals generated at the begging of simulation).
     */
    private ArrayList<Integer> generateRandomGenome(int size) {
        Random random = new Random();
        ArrayList<Integer> randomGenome = new ArrayList<>();
        for (int i = 0; i < size; i++) {
        randomGenome.add(random.nextInt(8));
        };
        return randomGenome;
    }

    /**
     Activates next gene (used after the end of the move process).
     * */
    public void activateNextGene() {
        activateGene = (activateRandomGene() + 1) % genomes.size();
    }

}
