package org.simulation;

import org.simulation.city.City;
import org.simulation.config.SimulationConfig;
import org.simulation.people.HealthStatus;
import org.simulation.people.Person;
import org.simulation.people.PersonFactory;
import org.simulation.services.*;
import org.simulation.services.InfectionService;
import org.simulation.services.death.DeathHandler;
import org.simulation.services.death.DeathService;
import org.simulation.services.infection.InfectionHandler;
import org.simulation.services.movement.MovementHandler;
import org.simulation.services.movement.MovementService;
import org.simulation.services.mutation.MutationHandler;
import org.simulation.services.mutation.VirusMutationService;
import org.simulation.services.recovery.RecoveryHandler;
import org.simulation.services.recovery.RecoveryService;
import org.simulation.virus.Virus;

import java.util.*;

public class Simulation {
    private final City city;
    private final List<Virus> viruses;
    private final SimulationConfig config;
    private final List<Epoch> epochs;
    private final int maxEpochs;
    private final int numberOfMovesPerEpoch;
    private final CsvLogger csvLogger;
    private final EpochProcessor epochProcessor;

    public Simulation(SimulationConfig config) {
        this.config = config;
        this.epochs = new ArrayList<>();
        this.maxEpochs = config.totalNumberOfEpochs();
        this.numberOfMovesPerEpoch = config.numberOfMovesPerEpoch();
        this.city = new City(config.cityConfig());
        this.csvLogger = new CsvLogger(config.csvFilePath(), "epoch,new_infected,total_infected,new_deaths,total_deaths");

        this.epochProcessor = createEpochProcessor();

        Virus initialVirus = new Virus(config.initialVirusConfig());
        this.viruses = new ArrayList<>();
        this.viruses.add(initialVirus);

        CityMapService.fillCityMap(city);
        PersonFactory personFactory = new PersonFactory();
        List<Person> generatedPeople = personFactory.generatePeople(city, viruses);
        city.setPeople(generatedPeople);
    }

    private EpochProcessor createEpochProcessor() {
        ProbabilityService probabilityService = new ProbabilityService();
        VirusMutationService virusMutationService = new VirusMutationService(probabilityService);
        RecoveryService recoveryService = new RecoveryService(probabilityService);
        InfectionService infectionService = new InfectionService(probabilityService);
        MovementService movementService = new MovementService(probabilityService);
        DeathService deathService = new DeathService(probabilityService);

        MutationHandler mutationHandler = new MutationHandler(virusMutationService);
        RecoveryHandler recoveryHandler = new RecoveryHandler(recoveryService);
        InfectionHandler infectionHandler = new InfectionHandler(infectionService);
        MovementHandler movementHandler = new MovementHandler(movementService);
        DeathHandler deathHandler = new DeathHandler(deathService);

        return new EpochProcessor(deathHandler, movementHandler, infectionHandler, recoveryHandler, mutationHandler);
    }

    public City getCity() {
        return city;
    }

    public List<Virus> getViruses() {
        return viruses;
    }

    public SimulationConfig getConfig() {
        return config;
    }

    public List<Epoch> getEpochs() {
        return epochs;
    }

    public void worldSimulation() {
        int currentEpoch = 0;

        while(currentEpoch < this.maxEpochs) {
            currentEpoch++;
            Epoch epoch = epochProcessor.processEpoch(currentEpoch, numberOfMovesPerEpoch, city, viruses);

            this.csvLogger.log(epoch);
            System.out.println("Epoch " + currentEpoch + " completed.");

            if(city.getPeople().get(HealthStatus.DEAD).size() == city.getPopulation())
                return;

            if(!city.getPeople().containsKey(HealthStatus.INFECTED)) return;
        }
    }
}
