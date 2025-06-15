package org.simulation.services.mutation;

import org.simulation.city.City;
import org.simulation.people.HealthStatus;
import org.simulation.people.Person;
import org.simulation.virus.Virus;

import java.util.List;
import java.util.Optional;

public class MutationHandler {
    private final VirusMutationService virusMutationService;

    public MutationHandler(VirusMutationService virusMutationService) {
        this.virusMutationService = virusMutationService;
    }

    public void handleMutation(List<Person> people, City city, List<Virus> viruses) {
        for (Person person : people) {
            if (person.getHealthStatus() == HealthStatus.INFECTED) {
                int currentVirusStage = person.getInfectedBy().get().getMutationStage();
                Virus prevoiusVirus = person.getInfectedBy().get();
                boolean isVirusExist = viruses.stream()
                        .anyMatch(virus -> virus.getMutationStage() == currentVirusStage + 1);
                if (isVirusExist) {
                    virusMutationService.tryMutateVirus(person, viruses);
                } else {
                    Optional<Virus> newStageVirus = virusMutationService.tryNewMutateVirus(person);
                    if (newStageVirus.isPresent() && !newStageVirus.get().equals(prevoiusVirus)) {
                        viruses.add(newStageVirus.get());
                    }
                }
            }
        }
    }
}
