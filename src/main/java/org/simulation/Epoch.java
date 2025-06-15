package org.simulation;

/**
 * Immutable record representing a snapshot of the simulation state for a specific day (epoch).
 * @param dayNumber     The sequential number of the current simulation day.
 * @param newInfected   Number of people who became infected on this day.
 * @param totalInfected Total number of currently infected people.
 * @param newDeaths     Number of people who died during this day.
 * @param totalDeaths   Cumulative number of deaths since the beginning of the simulation.
 */

public record Epoch(int dayNumber, int newInfected, int totalInfected, int newDeaths, int totalDeaths) {
}
