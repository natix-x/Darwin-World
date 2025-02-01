package agh.ics.poproject.util;

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
                             int corpseDecompositionTime,
                             boolean isGlobeMap,
                             boolean isForestedEquator,
                             boolean isCemetery,
                             boolean isFullRandomMutation,
                             boolean isSlightCorrectionMutation,
                             boolean isFullPredestination,
                             boolean saveStats,
                             int simulationSpeed

)
{ }

