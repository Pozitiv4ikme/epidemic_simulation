package org.simulation.config;

/**
 * Configuration for the initial virus parameters.
 * @param initialInfectionProbability Probability that a healthy person becomes infected after contact.
 * @param initialLethality            Probability that an infected person dies due to the virus.
 * @param initialRecoveryProbability  Probability that an infected person recovers.
 */

import org.simulation.exceptions.VirusConfigException;

public record VirusConfig(double initialInfectionProbability, double initialLethality,
                          double initialRecoveryProbability) {

    // Validates the virus configuration parameters
    public void validate() {

        // If all values are zero, configuration is invalid
        if (initialInfectionProbability == 0 && initialLethality == 0 && initialRecoveryProbability == 0) {
            throw new VirusConfigException("Virus configuration cannot be empty.");
        }

        // Infection probability must be in the range (0, 100]
        if (initialInfectionProbability <= 0 || initialInfectionProbability > 100) {
            throw new VirusConfigException("Initial infection probability must be between 0 and 100.");
        }

        // Lethality must be in the range (0, 100]
        if (initialLethality <= 0 || initialLethality > 100) {
            throw new VirusConfigException("Initial lethality must be between 0 and 100.");
        }

        // Recovery probability must be in the range (0, 100]
        if (initialRecoveryProbability <= 0 || initialRecoveryProbability > 100) {
            throw new VirusConfigException("Initial recovery probability must be between 0 and 100.");
        }
    }
}
