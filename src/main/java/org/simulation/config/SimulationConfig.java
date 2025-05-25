package org.simulation.config;

import org.simulation.locations.LocationType;

import java.util.Map;

public record SimulationConfig(int width, int height, int totalNumberOfEpochs, int numberOfMovesPerEpoch,
                               int population, int infectedPercentage, int initialRecoveryProbability,
                               int initialVirusLethality, int initialInfectionProbability,
                               Map<LocationType, LocationConfigData> initialLocationsConfig) {

    @Override
    public String toString() {
        return "SimulationConfig{" +
                "width=" + width +
                ", height=" + height +
                ", totalNumberOfEpochs=" + totalNumberOfEpochs +
                ", numberOfMovesPerEpoch=" + numberOfMovesPerEpoch +
                ", population=" + population +
                ", infectedPercentage=" + infectedPercentage +
                ", initialRecoveryProbability=" + initialRecoveryProbability +
                ", initialVirusLethality=" + initialVirusLethality +
                ", initialInfectionProbability=" + initialInfectionProbability +
                ", initialLocationsConfig=" + initialLocationsConfig +
                '}';
    }
}
