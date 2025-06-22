package org.simulation.exceptions;

/**
 * Exception thrown when the city configuration is invalid or incomplete.
 */

public class CityConfigException extends RuntimeException {

    /**
     * Constructs a new CityConfigException with a specified detail message.
     * @param message the detail message explaining the reason for the exception
     */

    public CityConfigException (String message) {
        super(message);
    }
}
