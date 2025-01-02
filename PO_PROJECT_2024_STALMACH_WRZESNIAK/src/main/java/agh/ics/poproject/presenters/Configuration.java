package agh.ics.poproject.presenters;

public class Configuration {

    private final int energyNeededToReproduce;

    public Configuration(int energyNeededToReproduce) {
        this.energyNeededToReproduce = energyNeededToReproduce;
    }

    public int getEnergyNeededToReproduce() {
        return energyNeededToReproduce;
    }

    // Add other getters and setters as needed
}

