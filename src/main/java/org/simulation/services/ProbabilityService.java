package org.simulation.services;

/**
 * A service for verifying probabilistic events.
 */
public class ProbabilityService {

    /**
     * @param chancePercent Probability in percentage (0–100)
     * @return true, If the event has happened
     */
    public boolean happens(double chancePercent) {
        if (chancePercent <= 0) return false;
        if (chancePercent >= 100) return true;
        return Math.random() <= (chancePercent / 100.0);
    }
}
