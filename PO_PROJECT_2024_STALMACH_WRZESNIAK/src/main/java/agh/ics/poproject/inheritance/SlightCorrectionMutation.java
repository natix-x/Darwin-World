package agh.ics.poproject.inheritance;

import java.util.HashSet;
import java.util.List;
import java.util.Random;
import java.util.Set;


public class SlightCorrectionMutation implements MutationMethod {
    // TODO: można pomyśleć o refactoryzacji później
    SlightCorrectionMutation(int minMutations, int maxMutations) {
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
                    int directionOfChange = random.nextBoolean() ? -1 : 1;
                    int newGene = genes.get(geneIndex) + directionOfChange;
                    if (newGene == 8) {
                        newGene = 0;
                    }
                    else if (newGene == -1){
                        newGene = 7;
                    }
                    genes.set(geneIndex, newGene);
                    break;
                }
            }
        }
        return genes;
    }
}


