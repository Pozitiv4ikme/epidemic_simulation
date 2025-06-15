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

/**
 * Main class responsible for processing a single epoch (simulation step).
 * Executes movement, infection, recovery, mutation and death handling logic.
 */

public class EpochProcessor {
    private final DeathHandler deathHandler;
    private final MovementHandler movementHandler;
    private final InfectionHandler infectionHandler;
    private final RecoveryHandler recoveryHandler;
    private final MutationHandler mutationHandler;

    /**
     * Constructor to initialize all simulation services.
     */

    public EpochProcessor(DeathHandler deathHandler, MovementHandler movementHandler,
                          InfectionHandler infectionHandler, RecoveryHandler recoveryHandler,
                          MutationHandler mutationHandler) {
        this.deathHandler = deathHandler;
        this.movementHandler = movementHandler;
        this.infectionHandler = infectionHandler;
        this.recoveryHandler = recoveryHandler;
        this.mutationHandler = mutationHandler;
    }

    /**
     * Processes a single epoch of the simulation:
     * - Handles movement, death, infections, recovery and mutations
     * - Updates city population data
     * - Removes dead people from the map
     * - Returns summary statistics as an Epoch object
     * @param epochNumber current epoch number
     * @param numberOfMovesPerEpoch number of movements per person
     * @param city the city being simulated
     * @param viruses list of known virus mutations
     * @return Epoch object containing statistics
     */

    public Epoch processEpoch(int epochNumber, int numberOfMovesPerEpoch, City city, List<Virus> viruses) {
        Map<HealthStatus, List<Person>> currentPeople = city.getPeople();

        int currentDeaths = currentPeople.getOrDefault(HealthStatus.DEAD, new ArrayList<>()).size();

        // Gather all living people
        List<Person> allPeople = new ArrayList<>();
        currentPeople.forEach((healthStatus, people) -> {
            if(healthStatus != HealthStatus.DEAD) {
                allPeople.addAll(people);
            }
        });

        // Process movements
        movementHandler.handleMovement(allPeople, city, numberOfMovesPerEpoch);

        // Process deaths and count new deaths
        int newDeaths = deathHandler.handleDeath(allPeople);

        // Process new infections
        List<Person> healthyPeople = currentPeople.getOrDefault(HealthStatus.HEALTHY, new ArrayList<>());
        int newInfected = infectionHandler.handleInfectionForHealthPeople(healthyPeople, city);

        // Process recovery and further infection spread
        List<Person> infectedPeople = currentPeople.getOrDefault(HealthStatus.INFECTED, new ArrayList<>());
        recoveryHandler.handleRecovery(infectedPeople, city);
        if (epochNumber != 1) {
            infectionHandler.handleInfectionForInfectedPeople(infectedPeople, city);
        }

        // Process virus mutations
        mutationHandler.handleMutation(infectedPeople, viruses);

        // Recalculate population groups and update the city
        Map<HealthStatus, List<Person>> regrouped = currentPeople.values().stream()
                .flatMap(List::stream)
                .collect(Collectors.groupingBy(Person::getHealthStatus));
        city.updatePopulation(regrouped);

        // Remove dead from city map
        List<Person> deadPeople = regrouped.getOrDefault(HealthStatus.DEAD, new ArrayList<>());
        for (Person person: deadPeople) {
            city.deletePersonFromMap(person);
        }

        // Get updated stats
        int allInfected = regrouped.getOrDefault(HealthStatus.INFECTED, new ArrayList<>()).size();
        int allDeaths = currentDeaths + newDeaths;

        // Return new Epoch statistics object
        return new Epoch(epochNumber, newInfected, allInfected, newDeaths, allDeaths);
    }
}
