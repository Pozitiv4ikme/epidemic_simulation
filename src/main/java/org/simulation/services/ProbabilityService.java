package org.simulation.services;

public class ProbabilityService {
    public boolean happens(double chancePercent) {
        return Math.random() < (chancePercent / 100.0);
    }
}
