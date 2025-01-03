package agh.ics.poproject;

import agh.ics.poproject.util.Configuration;

public class Simulation implements Runnable {

    Configuration config;

    public Simulation(Configuration config) {
        this.config = config;
    }
    @Override
    public void run() {
        System.out.println("Simulation started");
    }
}
