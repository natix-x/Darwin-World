package agh.ics.poproject;

import agh.ics.poproject.util.Configuration;

public class Simulation implements Runnable {
    //to tak jakby nasz engine do backendu, tu powinny być wszystkie listy zwierząt itd
    public Configuration config;

    public Simulation(Configuration config) {
        this.config = config;
    }

    @Override
    public void run() {
        System.out.println("Engine imulation started");
    }
}
