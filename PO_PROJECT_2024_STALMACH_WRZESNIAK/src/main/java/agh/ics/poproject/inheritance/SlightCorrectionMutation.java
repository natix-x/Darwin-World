package agh.ics.poproject.inheritance;

import java.util.Random;


public class SlightCorrectionMutation extends AbstractMutationMethod {

    public SlightCorrectionMutation(int minMutations, int maxMutations) {
        super(minMutations, maxMutations);
    }

    /**
     Mutates the gene.
     Slight correction changes gene up or down by one.
     @return mutated gene
     */
    @Override
    public int mutateGene(int gene) {
        Random random = new Random();
        int directionOfChange = random.nextBoolean() ? -1 : 1;
        int mutatedGene = gene + directionOfChange;
        if (mutatedGene == 8) {
            mutatedGene = 0;
        } else if (mutatedGene == -1) {
            mutatedGene = 7;
        }
        return mutatedGene;
    }
}




