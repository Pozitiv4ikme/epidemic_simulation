package org.simulation.services.mutation;

import org.simulation.city.City;
import org.simulation.people.HealthStatus;
import org.simulation.people.Person;
import org.simulation.virus.Virus;

import java.util.List;
import java.util.Optional;

/**
 * A handler responsible for managing virus mutations within a population.
 */

public class MutationHandler {
    private final VirusMutationService virusMutationService;

    /**
     * Constructs the MutationHandler with the provided VirusMutationService.
     * @param virusMutationService the service used to perform virus mutations
     */

    public MutationHandler(VirusMutationService virusMutationService) {
        this.virusMutationService = virusMutationService;
    }

    /**
     * Iterates over the list of people and attempts to mutate the virus in infected individuals.
     * If the next mutation stage exists in the provided virus list, it tries to mutate to it.
     * Otherwise, it attempts to create a new mutation stage and adds it to the virus list if successful.
     * @param people  list of people to check for mutations
     * @param viruses list of known virus stages
     */

    public void handleMutation(List<Person> people, List<Virus> viruses) {
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
