package org.simulation.exceptions;

/**
 * Exception thrown when the virus configuration is invalid.
 */

public class VirusConfigException extends RuntimeException {

    /**
     * Constructs a new VirusConfigException with the specified detail message.
     * @param message the detail message explaining the reason for the exception
     */

    public VirusConfigException(String message) {
        super(message);
    }
}
