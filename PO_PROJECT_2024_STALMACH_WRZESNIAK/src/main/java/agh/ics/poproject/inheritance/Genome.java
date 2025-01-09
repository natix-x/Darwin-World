package agh.ics.poproject.inheritance;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class Genome {

    private final ArrayList<Integer> genesSequence;

    private int activeGene;

    // for generated animals
    public Genome(int genesNumber) {
        this.genesSequence = generateRandomGenome(genesNumber);
        activeGene = activateRandomGene();
    }
    // for babies
    public Genome(List<Integer> genomes) {
        this.genesSequence = new ArrayList<>(genomes);
        activeGene = activateRandomGene();
    }

    public int getActiveGene() {
        return activeGene;
    }

    public ArrayList<Integer> getGenesSequence() {
        return new ArrayList<>(genesSequence);
    }

    /**
     Activates random gene in generated or born animals' genome.
     */
    private int activateRandomGene() {
        Random random = new Random();
        int randomIndex = random.nextInt(genesSequence.size());
        return genesSequence.get(randomIndex);
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
        activeGene = (activeGene + 1) % genesSequence.size();
    }

}
