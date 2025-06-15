package org.simulation.services;

import org.simulation.Epoch;
import org.simulation.city.City;
import org.simulation.people.HealthStatus;
import org.simulation.people.Person;
import org.simulation.services.death.DeathHandler;
import org.simulation.services.infection.InfectionHandler;
import org.simulation.services.movement.MovementHandler;
import org.simulation.services.mutation.MutationHandler;
import org.simulation.services.recovery.RecoveryHandler;
import org.simulation.virus.Virus;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

public class EpochProcessor {
    private final DeathHandler deathHandler;
    private final MovementHandler movementHandler;
    private final InfectionHandler infectionHandler;
    private final RecoveryHandler recoveryHandler;
    private final MutationHandler mutationHandler;

    public EpochProcessor(DeathHandler deathHandler, MovementHandler movementHandler,
                          InfectionHandler infectionHandler, RecoveryHandler recoveryHandler,
                          MutationHandler mutationHandler) {
        this.deathHandler = deathHandler;
        this.movementHandler = movementHandler;
        this.infectionHandler = infectionHandler;
        this.recoveryHandler = recoveryHandler;
        this.mutationHandler = mutationHandler;
    }

    public Epoch processEpoch(int epochNumber, int numberOfMovesPerEpoch, City city, List<Virus> viruses) {
        Map<HealthStatus, List<Person>> currentPeople = city.getPeople();

        int currentDeaths = currentPeople.getOrDefault(HealthStatus.DEAD, new ArrayList<>()).size();

        List<Person> allPeople = new ArrayList<>();
        currentPeople.forEach((healthStatus, people) -> {
            if(healthStatus != HealthStatus.DEAD) {
                allPeople.addAll(people);
            }
        });

        movementHandler.handleMovement(allPeople, city, numberOfMovesPerEpoch);
        int newDeaths = deathHandler.handleDeath(allPeople);

        List<Person> healthyPeople = currentPeople.getOrDefault(HealthStatus.HEALTHY, new ArrayList<>());
        int newInfected = infectionHandler.handleInfectionForHealthPeople(healthyPeople, city);

        List<Person> infectedPeople = currentPeople.getOrDefault(HealthStatus.INFECTED, new ArrayList<>());
        recoveryHandler.handleRecovery(infectedPeople, city);
        if (epochNumber != 1) {
            infectionHandler.handleInfectionForInfectedPeople(infectedPeople, city);
        }
        mutationHandler.handleMutation(infectedPeople, city, viruses);

        Map<HealthStatus, List<Person>> regrouped = currentPeople.values().stream()
                .flatMap(List::stream)
                .collect(Collectors.groupingBy(Person::getHealthStatus));
        city.updatePopulation(regrouped);

        List<Person> deadPeople = regrouped.getOrDefault(HealthStatus.DEAD, new ArrayList<>());
        for (Person person: deadPeople) {
            city.deletePersonFromMap(person);
        }
        int allInfected = regrouped.getOrDefault(HealthStatus.INFECTED, new ArrayList<>()).size();

        int allDeaths = currentDeaths + newDeaths;
        return new Epoch(epochNumber, newInfected, allInfected, newDeaths, allDeaths);
    }
}
