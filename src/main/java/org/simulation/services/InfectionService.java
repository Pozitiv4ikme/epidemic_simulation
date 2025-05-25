package org.simulation.services;

import org.simulation.locations.LocationHealthImpact;
import org.simulation.locations.LocationType;
import org.simulation.people.AgeGroupImpact;
import org.simulation.people.HealthStatus;
import org.simulation.people.Person;

public class InfectionService {
    private final ProbabilityService probabilityService;

    public InfectionService(ProbabilityService probabilityService) {
        this.probabilityService = probabilityService;
    }
    public void evaluateInfection(Person person, Person infectedPerson, LocationType locationType) {
        AgeGroupImpact personProfile = AgeGroupImpact.getProfileForAge(person.getAge());
        LocationHealthImpact locationImpact = LocationHealthImpact.getImpactForLocationType(locationType);

        if (!probabilityService.happens(personProfile.getBaseInfectionChancePercent()
                + infectedPerson.getInfectedBy().get().getInfectionProbability()
                + locationImpact.getPercentInfectionProbability()))
            return;
        person.setHealthStatus(HealthStatus.INFECTED);
        person.setInfectedBy(infectedPerson.getInfectedBy());
    }
}
