//package agh.ics.poproject.model;
//
//import agh.ics.poproject.Simulation;
//
//import java.util.ArrayList;
//import java.util.List;
//import java.util.concurrent.ExecutorService;
//import java.util.concurrent.Executors;
//import java.util.concurrent.TimeUnit;
//
//public class SimulationEngine {
//
//    private final List<Simulation> simulations;
//    private final List<Thread> simulationThreads = new ArrayList<>();
//    private ExecutorService executorService;
//
//
//    public SimulationEngine(List<Simulation> simulations) {
//        this.simulations = simulations;
//    }
//
//    public void runSync() {
//        for (Simulation simulation : simulations) {
//            simulation.run();
//        }
//    }
//
//    public void runAsync() throws InterruptedException {
//        for (Simulation simulation : simulations) {
//            Thread simulationThread = new Thread(simulation::run);
//            simulationThreads.add(simulationThread);
//            simulationThread.start();
//        }
//    }
//
//    public void awaitSimulationsEnd() throws InterruptedException {
//        if (this.executorService != null) {
//            this.executorService.shutdown();
//            this.executorService.awaitTermination(10, TimeUnit.SECONDS);
//        }
//        else {
//        for (Thread thread : simulationThreads) {
//            thread.join();
//
//        }}
//    }
//
//    public void runAsyncInThreadPool() throws InterruptedException {
//        this.executorService = Executors.newFixedThreadPool(4);
//        executorService.submit(() -> simulations.forEach(Simulation::run));
//        awaitSimulationsEnd();
//    }
//
//}
