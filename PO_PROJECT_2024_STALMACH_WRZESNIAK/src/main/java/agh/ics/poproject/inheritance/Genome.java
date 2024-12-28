package agh.ics.poproject.inheritance;

import java.util.ArrayList;
import java.util.Random;

public class Genome {
    private final ArrayList<Integer> genomes;

    private int activateGene;

    public Genome(int genesNumber) {
        this.genomes = generateRandomGenome(genesNumber);
        activateGene = activateRandomGene();
    }

    public Genome(ArrayList<Integer> genomes) {
        this.genomes = new ArrayList<>(genomes);
        activateGene = activateRandomGene();
    }

    public int getActivateGene() {
        return activateGene;
    }

    private int activateRandomGene() {
        Random random = new Random();
        return genomes.get(random.nextInt(genomes.size()));
    }

    private ArrayList<Integer> generateRandomGenome(int size) {
        Random random = new Random();
        ArrayList<Integer> randomGenome = new ArrayList<>();
        for (int i = 0; i < size; i++) {
        randomGenome.add(random.nextInt(8));
        };
        return randomGenome;
    }
}
