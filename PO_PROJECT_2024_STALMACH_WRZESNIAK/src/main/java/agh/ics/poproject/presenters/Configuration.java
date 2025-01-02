package agh.ics.poproject.presenters;

public record Configuration( int mapHeight,
                             int mapWidth,
                             int initialPlants,
                             int energyPerPlant,
                             int dailyPlantGrowth,
                             int initialAnimals,
                             int initialEnergy,
                             int neededEnergyForReproduction,
                             int reproductionEnergyLost,
                             int minMutations,
                             int maxMutations,
                             int genomeLength,

                             boolean isGlobeMap,
                             boolean isForestedEquator,
                             boolean isFullRandomMutation,
                             boolean isFullPredestination,
                             boolean saveConfig

) {
}

