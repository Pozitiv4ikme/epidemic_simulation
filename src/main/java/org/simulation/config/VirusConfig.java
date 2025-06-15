package org.simulation.config;

/**
 * Configuration for the initial virus parameters.
 * @param initialInfectionProbability Probability that a healthy person becomes infected after contact.
 * @param initialLethality            Probability that an infected person dies due to the virus.
 * @param initialRecoveryProbability  Probability that an infected person recovers.
 */

public record VirusConfig(double initialInfectionProbability, double initialLethality,
                          double initialRecoveryProbability) {
}
