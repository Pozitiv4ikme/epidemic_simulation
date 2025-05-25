package org.simulation;

public record Epoch(int dayNumber, int newInfected, int totalInfected, int newDeaths, int totalDeaths) {
}
