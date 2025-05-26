package org.simulation.config;

public record SimulationConfig(int totalNumberOfEpochs, int numberOfMovesPerEpoch, CityConfig cityConfig,
                               VirusConfig initialVirusConfig) {
}
