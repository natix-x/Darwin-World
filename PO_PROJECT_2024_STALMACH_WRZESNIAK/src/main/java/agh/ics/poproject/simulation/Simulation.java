package agh.ics.poproject.simulation;

import agh.ics.poproject.model.map.WorldMap;
import agh.ics.poproject.util.Configuration;

public class Simulation implements Runnable {
    //to tak jakby nasz engine do backendu, tu powinny być wszystkie listy zwierząt itd
    private final Configuration config;
    private  WorldMap worldMap;

    public Simulation(Configuration config) {
        this.config = config;
    }

    public void setWorldMap(WorldMap worldMap) {
        this.worldMap = worldMap;
    }

    public Configuration getConfig() {
        return config;
    }

    @Override
    public void run() {
        System.out.println("Engine imulation started");

    }


// for test purposes

}
