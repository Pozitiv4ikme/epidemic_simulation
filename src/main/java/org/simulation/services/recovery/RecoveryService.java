package org.simulation.services.recovery;

import org.simulation.people.AgeGroupImpact;
import org.simulation.people.HealthStatus;
import org.simulation.people.Person;
import org.simulation.locations.LocationType;
import org.simulation.locations.LocationHealthImpact;
import org.simulation.services.ProbabilityService;

import java.util.Optional;

public class RecoveryService {
    private final ProbabilityService probabilityService;

    public RecoveryService(ProbabilityService probabilityService) {
        this.probabilityService = probabilityService;
    }

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
