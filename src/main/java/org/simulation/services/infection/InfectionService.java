package org.simulation.services.infection;

import org.simulation.locations.LocationHealthImpact;
import org.simulation.locations.LocationType;
import org.simulation.people.AgeGroupImpact;
import org.simulation.people.HealthStatus;
import org.simulation.people.Person;
import org.simulation.services.ProbabilityService;
import org.simulation.virus.Virus;

import java.util.Optional;

/**
 * A service responsible for evaluating whether a person becomes infected,
 * based on age, virus characteristics, and environmental impact.
 */

public class InfectionService {
    private final ProbabilityService probabilityService;

    /**
     * Constructs the InfectionService with a given probability service.
     * @param probabilityService the probability service used to determine infection
     */

    public InfectionService(ProbabilityService probabilityService) {
        this.probabilityService = probabilityService;
    }

    /**
     * Evaluates whether a person gets infected by a specific virus in a given location.
     * Takes into account personal infection risk, virus infectivity, and environmental factors.
     * If infection occurs, updates the person's status and stores the virus.
     * @param person the person being evaluated
     * @param toInfectBy the virus that may infect the person
     * @param locationType the type of location the person is in
     */

    public void evaluateInfection(Person person, Virus toInfectBy, LocationType locationType) {
        AgeGroupImpact personProfile = AgeGroupImpact.getProfileForAge(person.getAge());
        LocationHealthImpact locationImpact = LocationHealthImpact.getImpactForLocationType(locationType);

        if (!probabilityService.happens(personProfile.getBaseInfectionChancePercent()
                + toInfectBy.getInfectionProbability()
                + locationImpact.getPercentInfectionProbability()))
            return;
        person.setHealthStatus(HealthStatus.INFECTED);
        person.setInfectedBy(Optional.of(toInfectBy));
        System.out.println("Person " + person + " infected by " + toInfectBy);
    }
}
