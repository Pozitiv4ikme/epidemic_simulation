package org.simulation.services.infection;

import org.simulation.city.City;
import org.simulation.city.CityCell;
import org.simulation.locations.Location;
import org.simulation.people.HealthStatus;
import org.simulation.people.Person;
import org.simulation.services.CityMapService;
import org.simulation.virus.Virus;

import java.util.List;
import java.util.Map;
import java.util.Optional;

/**
 * A handler responsible for managing infection spread among people in the city.
 */

public class InfectionHandler {
    private final InfectionService infectionService;

    /**
     * Constructs the InfectionHandler with a given infection evaluation service.
     * @param infectionService the service used to evaluate infection probability
     */

    public InfectionHandler(InfectionService infectionService) {
        this.infectionService = infectionService;
    }

    /**
     * Returns the city cell (grid position) where the person is currently located.
     * @param person the person whose position is checked
     * @param city the city where the person is located
     * @return the city cell corresponding to the person's position
     */

    private CityCell getCityCellForPerson(Person person, City city) {
        return city.getCityMap()[person.getPosition().getX()][person.getPosition().getY()];
    }

    /**
     * Returns the location object associated with the person's current position.
     * @param person the person whose location is retrieved
     * @param city the city where the person is located
     * @return the location associated with the person's cell
     */

    private Location getLocationForPerson(Person person, City city) {
        return getCityCellForPerson(person, city).getLocation();
    }

    /**
     * Retrieves the virus stages currently present in the person's city cell.
     * @param person the person in question
     * @param city the city to check
     * @return optional map of mutation stages to virus instances
     */

    private Optional<Map<Integer, Virus>> getVirusStagesForPerson(Person person, City city) {
        CityCell currentCell = getCityCellForPerson(person, city);
        return CityMapService.allVirusStagesInCityCell(currentCell);
    }

    /**
     * Checks if the person can be infected by any more advanced virus mutation
     * present in their current location.
     * @param person the person to evaluate
     * @param city the city where the person is located
     * @param currentMutationStage the mutation stage of the virus the person may already carry
     */

    private void handleInfectionForPerson(Person person, City city, int currentMutationStage) {
        Location currentLocation = getLocationForPerson(person, city);
        Optional<Map<Integer, Virus>> virusStages = getVirusStagesForPerson(person, city);
        if (virusStages.isPresent() && !virusStages.get().isEmpty()) {
            for (Virus virus : virusStages.get().values()) {
                if(virus.getMutationStage() > currentMutationStage) {
                    infectionService.evaluateInfection(person, virus, currentLocation.getType());
                }
            }
        }
    }

    /**
     * Evaluates infection for all healthy people in the list. If infection occurs,
     * their status is updated accordingly.
     * @param people list of people to evaluate
     * @param city the city context
     * @return number of newly infected individuals
     */

    public int handleInfectionForHealthPeople(List<Person> people, City city) {
        int newInfected = 0;
        for(Person person: people) {
            if (person.getHealthStatus() == HealthStatus.HEALTHY) {
                handleInfectionForPerson(person, city, -1);
                if(person.getHealthStatus() == HealthStatus.INFECTED) {
                    newInfected++;
                }
            }
        }
        return newInfected;
    }

    /**
     * Evaluates possible reinfection or mutation infection for already infected people,
     * allowing more advanced virus stages to take effect.
     * @param people list of infected people
     * @param city the city context
     */

    public void handleInfectionForInfectedPeople(List<Person> people, City city) {
        for (Person person : people) {
            if (person.getHealthStatus() == HealthStatus.INFECTED && person.getInfectedBy().isPresent()) {
                int currentVirusStage = person.getInfectedBy().get().getMutationStage();
                handleInfectionForPerson(person, city, currentVirusStage);
            }
        }
    }
}

