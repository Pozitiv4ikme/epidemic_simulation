package org.simulation.config;

import java.util.Map;

public class SimulationConfig {
    private int width;
    private int height;
    private int totalNumberOfEpochs;
    private int population;
    private int numberOfMovesPerEpoch;
    private int infectedPercentage;
    private int initialInfectionProbability;
    private int initialVirusLethality;
    private Map<String, Integer> initialLocationsAmount;
}
