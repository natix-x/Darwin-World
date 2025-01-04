package agh.ics.poproject.util;

import agh.ics.poproject.Simulation;

import java.util.ArrayList;
import java.util.List;

public class SimulationEngine {

    private final List<Simulation> simulations;
    private final List<Thread> startedThreads;

    public SimulationEngine(List<Simulation> simulations) {
        this.simulations = simulations;
        this.startedThreads = new ArrayList<>();
    }

    public void runAsync() {
        for (Simulation simulation : simulations) {
            Thread thread = new Thread(simulation);
            startedThreads.add(thread);
            thread.start();
        }
        System.out.println("debugging: finished engine calculations");
    }
}
