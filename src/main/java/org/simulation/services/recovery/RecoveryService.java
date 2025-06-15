package org.simulation.services.recovery;

import org.simulation.people.AgeGroupImpact;
import org.simulation.people.HealthStatus;
import org.simulation.people.Person;
import org.simulation.locations.LocationType;
import org.simulation.locations.LocationHealthImpact;
import org.simulation.services.ProbabilityService;

import java.util.Optional;

/**
 * A service responsible for evaluating and handling recovery of infected individuals.
 */

public class RecoveryService {
    private final ProbabilityService probabilityService;

    /**
     * Constructs the RecoveryService with the given ProbabilityService.
     * @param probabilityService service used for probabilistic checks
     */

    public RecoveryService(ProbabilityService probabilityService) {
        this.probabilityService = probabilityService;
    }

    /**
     * Evaluates whether a given person recovers based on their age group profile,
     * current virus recovery probability, and the impact of the location type.
     * If the recovery occurs, the person's health status is set to HEALTHY and
     * infection is cleared.
     * @param person       the person to evaluate for recovery
     * @param locationType the type of location where the person is
     */

    public void evaluateRecovery(Person person, LocationType locationType) {
        AgeGroupImpact personProfile = AgeGroupImpact.getProfileForAge(person.getAge());
        LocationHealthImpact locationHealthImpact = LocationHealthImpact.getImpactForLocationType(locationType);

        if(probabilityService.happens(personProfile.getBaseRecoveryChancePercent()
                + person.getInfectedBy().get().getRecoverProbability()
                + locationHealthImpact.getPercentRecoveryProbability())){
            person.setHealthStatus(HealthStatus.HEALTHY);
            person.setInfectedBy(Optional.empty());
        }
    }
}
