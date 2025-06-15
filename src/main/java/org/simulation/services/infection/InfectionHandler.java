package org.simulation.services.infection;

import org.simulation.city.City;
import org.simulation.city.CityCell;
import org.simulation.locations.Location;
import org.simulation.people.HealthStatus;
import org.simulation.people.Person;
import org.simulation.services.CityMapService;
import org.simulation.services.InfectionService;
import org.simulation.virus.Virus;

import java.util.List;
import java.util.Map;
import java.util.Optional;

public class InfectionHandler {
    private final InfectionService infectionService;

    public InfectionHandler(InfectionService infectionService) {
        this.infectionService = infectionService;
    }

    private CityCell getCityCellForPerson(Person person, City city) {
        return city.getCityMap()[person.getPosition().getX()][person.getPosition().getY()];
    }

    private Location getLocationForPerson(Person person, City city) {
        return getCityCellForPerson(person, city).getLocation();
    }

    private Optional<Map<Integer, Virus>> getVirusStagesForPerson(Person person, City city) {
        CityCell currentCell = getCityCellForPerson(person, city);
        return CityMapService.allVirusStagesInCityCell(currentCell);
    }

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

    public void handleInfectionForInfectedPeople(List<Person> people, City city) {
        for (Person person : people) {
            if (person.getHealthStatus() == HealthStatus.INFECTED && person.getInfectedBy().isPresent()) {
                int currentVirusStage = person.getInfectedBy().get().getMutationStage();
                handleInfectionForPerson(person, city, currentVirusStage);
            }
        }
    }
}

