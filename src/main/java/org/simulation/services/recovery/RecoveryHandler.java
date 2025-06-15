package org.simulation.services.recovery;

import org.simulation.city.City;
import org.simulation.city.CityCell;
import org.simulation.locations.Location;
import org.simulation.people.HealthStatus;
import org.simulation.people.Person;

import java.util.List;

public class RecoveryHandler {
    private final RecoveryService recoveryService;

    public RecoveryHandler(RecoveryService recoveryService) {
        this.recoveryService = recoveryService;
    }

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
