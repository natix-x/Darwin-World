package agh.ics.poproject.inheritance;

import java.util.*;

public class FullRandomMutation extends AbstractMutationMethod {

    FullRandomMutation(int minMutations, int maxMutations) {
        super(minMutations, maxMutations);

    }


    @Override
    public int mutateGene(int gene) {
        return random.nextInt(8);
    }
}
