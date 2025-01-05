package agh.ics.poproject.inheritance;

import java.util.*;

public class FullRandomMutation implements MutationMethod {

    // TODO: można pomyśleć o refactoryzacji później
    FullRandomMutation(int minMutations, int maxMutations) {
        Random random = new Random();
        int mutationNumber = random.nextInt(maxMutations - minMutations) + minMutations;

    }

    @Override
    public List<Integer> mutate(List<Integer> genes, int mutationNumber) {
        Random random = new Random();

        Set<Integer> mutatedGenes = new HashSet<>();

        for (int i = 0; i < mutationNumber; i++) {
            while (true) {
                int geneIndex = random.nextInt(genes.size());
                if (mutatedGenes.add(geneIndex)) {
                    genes.set(geneIndex, random.nextInt(8));
                    break;
                }
            }
        }
        return genes;
    }
}
