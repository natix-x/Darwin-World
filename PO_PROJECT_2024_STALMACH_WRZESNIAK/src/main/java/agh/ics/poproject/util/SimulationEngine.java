package agh.ics.poproject.util;

import agh.ics.poproject.simulation.Simulation;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class SimulationEngine {

    private final List<Simulation> simulations;
    private Map<String, Simulation> allSimulations = new HashMap<String, Simulation>();

    public SimulationEngine(List<Simulation> simulations) {
        this.simulations = simulations;
        for (Simulation simulation : simulations) {
            allSimulations.put(simulation.getWorldMap().getId().toString(), simulation);
        }
    }

    public void runAsync() {
        for (Simulation simulation : simulations) {
            Thread simulationThread = new Thread(simulation::run);
            simulationThread.start();
        }
    }
}
