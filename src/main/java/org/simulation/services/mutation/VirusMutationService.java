package org.simulation.services.mutation;

import org.simulation.people.Person;
import org.simulation.services.ProbabilityService;
import org.simulation.virus.AgeGroupVirusImpact;
import org.simulation.virus.Virus;

import java.util.List;
import java.util.Optional;

public class VirusMutationService {
    private final ProbabilityService probabilityService;

    public VirusMutationService(ProbabilityService probabilityService) {
        this.probabilityService = probabilityService;
    }

    public Optional<Virus> tryNewMutateVirus(Person person) {
        AgeGroupVirusImpact profile = AgeGroupVirusImpact.getProfileForAge(person.getAge());

        Virus current = person.getInfectedBy().get();
        if (!probabilityService.happens(profile.getPercentVirusMutation())) return Optional.of(current);
        Virus mutated = new Virus(
                adjust(current.getInfectionProbability(), profile.getPercentInfectionProbability()),
                current.getMutationStage() + 1,
                adjust(current.getLethality(), profile.getPercentLethality()),
                adjust(current.getRecoverProbability(), profile.getPercentRecoveryProbability())
        );
        
        person.setInfectedBy(Optional.of(mutated));
        return Optional.of(mutated);
    }

    // why we need here return type Optional<Virus>?
    public Optional<Virus> tryMutateVirus(Person person, List<Virus> viruses) {
        AgeGroupVirusImpact profile = AgeGroupVirusImpact.getProfileForAge(person.getAge());

        Virus current = person.getInfectedBy().get();
        Virus newVirus = viruses.get(current.getMutationStage());
        if (!probabilityService.happens(profile.getPercentVirusMutation())) return Optional.of(current);
        person.setInfectedBy(Optional.of(newVirus));
        return Optional.of(newVirus);
    }

    private double adjust(double base, double percentChange) {
        double difference;
        if(base > 0)
            difference = 100.0 - base;
        else
            difference = 100.0 + base;
        return base + difference * percentChange / 100.0;
    }
}
