package org.simulation.exceptions;

/**
 * Exception thrown when the configuration data for a location type is invalid.
 */

public class LocationConfigDataException extends RuntimeException {

    /**
     * Constructs a new LocationConfigDataException with the specified detail message.
     * @param message the detail message explaining the reason for the exception
     */

    public LocationConfigDataException (String message) {
        super(message);
    }
}
