package agh.ics.poproject.inheritance;


public class FullRandomMutation extends AbstractMutationMethod {

    public FullRandomMutation(int minMutations, int maxMutations) {
        super(minMutations, maxMutations);

    }

    /**
     Mutates the gene.
     Changes gene to randomly selected different gene (number from 0 to 7).
     @return mutated gene
     */
    @Override
    public int mutateGene(int gene) {
        return random.nextInt(8);
    }
}
