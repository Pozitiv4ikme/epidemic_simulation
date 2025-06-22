package org.simulation.services;

import java.util.Random;

/**
 * A service for verifying probabilistic events.
 */
public class ProbabilityService {

    private final Random random;

    public ProbabilityService() {
        this(new Random());
    }

    public ProbabilityService(Random random) {
        this.random = random;
    }

    /**
     * @param chancePercent Probability in percentage (0–100)
     * @return true, If the event has happened
     */
    public boolean happens(double chancePercent) {
        if (chancePercent <= 0) return false;
        if (chancePercent >= 100) return true;
        return random.nextDouble() <= (chancePercent / 100.0);
    }
}
