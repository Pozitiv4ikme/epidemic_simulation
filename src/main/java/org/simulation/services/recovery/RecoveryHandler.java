package org.simulation.services.recovery;

import org.simulation.city.City;
import org.simulation.city.CityCell;
import org.simulation.locations.Location;
import org.simulation.people.HealthStatus;
import org.simulation.people.Person;

import java.util.List;

/**
 * A handler that processes recovery for infected persons within a city.
 */

public class RecoveryHandler {
    private final RecoveryService recoveryService;

    /**
     * Constructs a RecoveryHandler with the given RecoveryService.
     * @param recoveryService service responsible for evaluating recovery
     */

    public RecoveryHandler(RecoveryService recoveryService) {
        this.recoveryService = recoveryService;
    }

    /**
     * Processes recovery evaluation for a list of people within the city.
     * For each infected person, retrieves their current location and
     * uses RecoveryService to determine if recovery occurs.
     * @param people list of people to process
     * @param city   city object containing the map and location data
     */

    public void handleRecovery(List<Person> people, City city) {
        for (Person person : people) {
            if (person.getHealthStatus() == HealthStatus.INFECTED) {
                CityCell currentCell = city.getCityMap()[person.getPosition().getX()][person.getPosition().getY()];
                Location currentLocation = currentCell.getLocation();
                recoveryService.evaluateRecovery(person, currentLocation.getType());
            }
        }
    }
}
