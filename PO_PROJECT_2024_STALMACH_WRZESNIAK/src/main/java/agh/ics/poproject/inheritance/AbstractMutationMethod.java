package agh.ics.poproject.inheritance;

import java.util.HashSet;
import java.util.List;
import java.util.Random;
import java.util.Set;

public abstract class AbstractMutationMethod implements MutationMethod {
    protected Random random = new Random();
    private final int minMutations;
    private final int maxMutations;


    AbstractMutationMethod(int minMutations, int maxMutations) {
        this.minMutations = minMutations;
        this.maxMutations = maxMutations;

    }

    @Override
    public List<Integer> mutateGenome(List<Integer> genes) {
        int mutationNumber = random.nextInt(maxMutations - minMutations) + minMutations;
        Set<Integer> mutatedGenes = new HashSet<>();

        for (int i = 0; i < mutationNumber; i++) {
            while (true) {
                int geneIndex = random.nextInt(genes.size());
                if (mutatedGenes.add(geneIndex)) {
                    int mutatedGene = mutateGene(genes.get(geneIndex));
                    genes.set(geneIndex, mutatedGene);
                    break;
                }
            }
        }
        return genes;
    }

    public abstract int mutateGene(int gene);
}
