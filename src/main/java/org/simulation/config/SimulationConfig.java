package org.simulation.config;

public record SimulationConfig(int totalNumberOfEpochs, int numberOfMovesPerEpoch, String csvFilePath, CityConfig cityConfig,
                               VirusConfig initialVirusConfig) {
}
