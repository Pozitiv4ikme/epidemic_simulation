package org.simulation.config;

public record VirusConfig(double initialInfectionProbability, double initialLethality,
                          double initialRecoveryProbability) {
}
