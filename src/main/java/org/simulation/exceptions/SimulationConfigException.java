package org.simulation.exceptions;

/**
 * Exception thrown when the simulation configuration is invalid.
 */

public class SimulationConfigException extends RuntimeException {

    /**
     * Constructs a new SimulationConfigException with the specified detail message.
     * @param message the detail message explaining the reason for the exception
     */

    public SimulationConfigException (String message) {
        super(message);
    }
}
