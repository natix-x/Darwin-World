package agh.ics.poproject.inheritance.mutations;

import agh.ics.poproject.inheritance.Genome;

import java.util.ArrayList;
import java.util.List;

public interface MutationMethod {

    /**
     * Mutate genome.
     *
     * @return Genome with inserted mutations.
     */
    List<Integer> mutate(List<Integer> genes, int mutationNumber);
}
