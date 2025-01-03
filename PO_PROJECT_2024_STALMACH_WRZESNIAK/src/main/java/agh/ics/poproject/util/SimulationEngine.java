package agh.ics.poproject.util;

import agh.ics.poproject.Simulation;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;

public class SimulationEngine {

    private final List<Simulation> simulations;
    private final List<Thread> startedThreads;
    private ExecutorService threadPool;


    public SimulationEngine(List<Simulation> simulations) {
        this.simulations = simulations;
        this.startedThreads = new ArrayList<>();
    }

    public void runSync() {
        for (Simulation simulation : simulations) {
            simulation.run();
        }
        System.out.println("finish");

    }
    public void runAsync() {
        for (Simulation simulation : simulations) {
            Thread thread = new Thread(simulation);
            startedThreads.add(thread);
            thread.start();
        }
        System.out.println("finish");
    }


    // TODO: tu nie ma metody join() dodac, w ogóle można stąd wywalić co nie trzeba
    public void awaitSimulationEnd() throws InterruptedException {
        if (threadPool != null) {
            threadPool.shutdown();
            try {
                if (!threadPool.awaitTermination(10, TimeUnit.SECONDS)) {
                    threadPool.shutdownNow();
                    System.out.println("Terminated due to timeout.");
                } else {
                    System.out.println("Terminated.");
                }
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }}

    public void runAsyncInThreadPool() {
        threadPool = Executors.newFixedThreadPool(4);
        for (Simulation simulation : simulations) {
            threadPool.submit(simulation);
        }
    }}
