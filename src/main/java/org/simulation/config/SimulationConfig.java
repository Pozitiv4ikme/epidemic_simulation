package org.simulation.config;

import org.simulation.exceptions.SimulationConfigException;

/**
 * Configuration for running the simulation.
 * @param totalNumberOfEpochs   Total number of epochs the simulation will run for.
 * @param numberOfMovesPerEpoch Number of movements a person can make per epoch.
 * @param resultsCsvFilePath           Output path for the CSV log file.
 * @param cityConfig            Initial configuration for the simulated city.
 * @param initialVirusConfig    Configuration of the virus used at the beginning of the simulation.
 */

public record SimulationConfig(int totalNumberOfEpochs, int numberOfMovesPerEpoch, String resultsCsvFilePath, CityConfig cityConfig,
                               VirusConfig initialVirusConfig) {

    // Validation method to ensure the configuration data is correct
    public void validate() {

        // Total number of epochs must be greater than 0
        if (totalNumberOfEpochs <= 0) {
            throw new SimulationConfigException("Total number of epochs must be greater than 0.");
        }

        // Number of moves per epoch must be greater than 0
        if (numberOfMovesPerEpoch <= 0) {
            throw new SimulationConfigException("Number of moves per epoch must be greater than 0.");
        }

        // Path to results file cannot be empty
        if (resultsCsvFilePath.isEmpty()) {
            throw new SimulationConfigException("CSV file path with results of simulation cannot be empty.");
        }

        // City configuration must not be null
        if (cityConfig == null) {
            throw new SimulationConfigException("City configuration cannot be null.");
        }

        // Virus configuration must not be null
        if (initialVirusConfig == null) {
            throw new SimulationConfigException("Initial virus configuration cannot be null.");
        }

        // Validate city and virus configurations
        cityConfig.validate();
        initialVirusConfig.validate();
    }
}
