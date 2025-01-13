package agh.ics.poproject.util;

import agh.ics.poproject.simulation.Simulation;

import java.util.ArrayList;
import java.util.List;

public class SimulationEngine {

    private final List<Simulation> simulations;

    public SimulationEngine(List<Simulation> simulations) {
        this.simulations = simulations;
    }

    public void runAsync() {
        for (Simulation simulation : simulations) {
            Thread simulationThread = new Thread(simulation::run);
            simulationThread.start();
        }
        System.out.println("debugging: finished engine calculations");
    }
}
