package org.simulation.config;

/**
 * Configuration for running the simulation.
 * @param totalNumberOfEpochs   Total number of epochs the simulation will run for.
 * @param numberOfMovesPerEpoch Number of movements a person can make per epoch.
 * @param csvFilePath           Output path for the CSV log file.
 * @param cityConfig            Initial configuration for the simulated city.
 * @param initialVirusConfig    Configuration of the virus used at the beginning of the simulation.
 */

public record SimulationConfig(int totalNumberOfEpochs, int numberOfMovesPerEpoch, String csvFilePath, CityConfig cityConfig,
                               VirusConfig initialVirusConfig) {
}
