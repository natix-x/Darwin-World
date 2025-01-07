package agh.ics.poproject.inheritance;

import java.util.Random;


public class SlightCorrectionMutation extends AbstractMutationMethod {

    SlightCorrectionMutation(int minMutations, int maxMutations) {
        super(minMutations, maxMutations);
    }

    @Override
    public int mutateGene(int gene) {
        Random random = new Random();
        int directionOfChange = random.nextBoolean() ? -1 : 1;
        int newGene = gene + directionOfChange;
        if (newGene == 8) {
            newGene = 0;
        } else if (newGene == -1) {
            newGene = 7;
        }
        return newGene;
    }
}




