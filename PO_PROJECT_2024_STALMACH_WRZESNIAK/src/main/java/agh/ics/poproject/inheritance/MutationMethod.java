package agh.ics.poproject.inheritance;

import java.util.List;

public interface MutationMethod {

    /**
     * Mutate genome.
     *
     * @return Genome with inserted mutations.
     */
    List<Integer> mutateGenome(List<Integer> genes);
}
