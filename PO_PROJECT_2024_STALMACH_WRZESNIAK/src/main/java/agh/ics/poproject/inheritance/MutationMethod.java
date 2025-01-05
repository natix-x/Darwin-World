package agh.ics.poproject.inheritance;

import java.util.List;

public interface MutationMethod {

    /**
     * Mutate genome.
     *
     * @return Genome with inserted mutations.
     */
    List<Integer> mutate(List<Integer> genes, int mutationNumber);
}
