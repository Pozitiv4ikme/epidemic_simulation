package org.simulation;

import org.simulation.city.City;
import org.simulation.city.CityCell;
import org.simulation.config.SimulationConfig;
import org.simulation.locations.Location;
import org.simulation.people.HealthStatus;
import org.simulation.people.Person;
import org.simulation.people.PersonFactory;
import org.simulation.services.*;
import org.simulation.virus.Virus;

import java.util.*;
import java.util.stream.Collectors;

public class Simulation {
    private final City city;
    private final List<Virus> viruses;
    private final SimulationConfig config;
    private final List<Epoch> epochs;
    private final int maxEpochs;
    private final int numberOfMovesPerEpoch;
    private final CsvLogger csvLogger;

    private final ProbabilityService probabilityService = new ProbabilityService();
    private final MovementService movementService = new MovementService(probabilityService);
    private final DeathService deathService = new DeathService(probabilityService);
    private final InfectionService infectionService = new InfectionService(probabilityService);
    private final VirusMutationService virusMutationService = new VirusMutationService(probabilityService);
    private final RecoveryService recoveryService = new RecoveryService(probabilityService);

    public Simulation(SimulationConfig config) {
        this.config = config;
        this.epochs = new ArrayList<>();
        this.maxEpochs = config.totalNumberOfEpochs();
        this.numberOfMovesPerEpoch = config.numberOfMovesPerEpoch();
        this.city = new City(config.cityConfig());
        this.csvLogger = new CsvLogger(config.csvFilePath(), "epoch,new_infected,total_infected,new_deaths,total_deaths");

        Virus initialVirus = new Virus(config.initialVirusConfig());
        this.viruses = new ArrayList<>();
        this.viruses.add(initialVirus);

        CityMapService.fillCityMap(city);
        PersonFactory personFactory = new PersonFactory();
        List<Person> generatedPeople = personFactory.generatePeople(city, viruses);
        city.setPeople(generatedPeople);
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
        int newDeath = 0;
        int startEpochInfected = 0;
        int allDead = 0;


        while(currentEpoch < this.maxEpochs) {
            Map<HealthStatus, List<Person>> currentCityPeople = city.getPeople();
            List<Person> allPeopleInThisEpoch = new ArrayList<>();

            currentCityPeople.forEach((healthStatus, people) -> {
                if(healthStatus != HealthStatus.DEAD) {
                    allPeopleInThisEpoch.addAll(people);
                }
            });

            Map<HealthStatus, List<Person>> nextEpochPeopleMap = new HashMap<>();

            for(Person person: allPeopleInThisEpoch) {
                movementService.moving(person, city, numberOfMovesPerEpoch);

                if (person.getHealthStatus() == HealthStatus.INFECTED) {
                    startEpochInfected++;
                }

                deathService.evaluateDeath(person);
                HealthStatus endEpochStatus = person.getHealthStatus();
                if (person.getHealthStatus()==HealthStatus.DEAD) {
                    newDeath++;
                }
                nextEpochPeopleMap.computeIfAbsent(endEpochStatus, k -> new ArrayList<>()).add(person);
            }

            nextEpochPeopleMap.forEach(((healthStatus, people) -> {
                if (healthStatus == HealthStatus.HEALTHY) {
                    people.forEach(person -> {
                        if(person.getHealthStatus() == HealthStatus.HEALTHY) {
                            CityCell currentCell = city.getCityMap()[person.getPosition().getX()][person.getPosition().getY()];
                            Location currentLocation = currentCell.getLocation();
                            Optional<Map<Integer, Virus>> virusStages = CityMapService.allVirusStagesInCityCell(currentCell);
                            if(virusStages.isPresent() && !virusStages.get().isEmpty()) {
                                for (Virus virus : virusStages.get().values()) {
                                    infectionService.evaluateInfection(person, virus, currentLocation.getType());
                                }
                            }
                        }
                    });
                }
            }));

            nextEpochPeopleMap.forEach(((healthStatus, people) -> {
                if (healthStatus == HealthStatus.INFECTED) {
                    people.forEach(person -> {
                        if (person.getHealthStatus() == HealthStatus.INFECTED) {
                            CityCell currentCell = city.getCityMap()[person.getPosition().getX()][person.getPosition().getY()];
                            Location currentLocation = currentCell.getLocation();
                            recoveryService.evaluateRecovery(person, currentLocation.getType());
                        }
                    });
                }
            }));

            if(currentEpoch != 0) {
                nextEpochPeopleMap.forEach(((healthStatus, people) -> {
                    if (healthStatus == HealthStatus.INFECTED) {
                        people.forEach(person -> {
                            if(person.getHealthStatus() == HealthStatus.INFECTED) {
                                int currentVirusStage = person.getInfectedBy().get().getMutationStage();
                                CityCell currentCell = city.getCityMap()[person.getPosition().getX()][person.getPosition().getY()];
                                Location currentLocation = currentCell.getLocation();
                                Optional<Map<Integer, Virus>> virusStages = CityMapService.allVirusStagesInCityCell(currentCell);
                                if(virusStages.isPresent() && !virusStages.get().isEmpty()) {
                                    int infectedChecked = 0;
                                    while (virusStages.get().size() != infectedChecked) {
                                        virusStages.get().forEach((key, virus) -> {
                                            if(virus.getMutationStage() > currentVirusStage) {
                                                infectionService.evaluateInfection(person, virus, currentLocation.getType());
                                            }
                                        });
                                        infectedChecked++;
                                    }
                                }
                            }
                        });
                    }
                }));
            }

            nextEpochPeopleMap.forEach(((healthStatus, people) -> {
                if (healthStatus == HealthStatus.INFECTED) {
                    people.forEach(person -> {
                        if(person.getHealthStatus() == HealthStatus.INFECTED) {
                            int currentVirusStage = person.getInfectedBy().get().getMutationStage();
                            Virus prevoiusVirus = person.getInfectedBy().get();
                            boolean isVirusExist = viruses.stream()
                                    .anyMatch(virus -> virus.getMutationStage() == currentVirusStage+1);
                            if(isVirusExist) {
                                virusMutationService.tryMutateVirus(person, viruses);
                            } else {
                                Optional<Virus> newStageVirus = virusMutationService.tryNewMutateVirus(person);
                                if (newStageVirus.isPresent() && !newStageVirus.get().equals(prevoiusVirus)) {
                                    viruses.add(newStageVirus.get());
                                }
                            }
                        }
                    });
                }
            }));

            List<Person> deadPeople = nextEpochPeopleMap.get(HealthStatus.DEAD);
            if (deadPeople != null) {
                for (Person person: nextEpochPeopleMap.get(HealthStatus.DEAD)) {
                    city.getCityMap()[person.getPosition().getX()][person.getPosition().getY()].getPeople().remove(person);
                }
            }
            List<Person> allPeople = nextEpochPeopleMap.values().stream()
                    .flatMap(List::stream)
                    .collect(Collectors.toList());

            Map<HealthStatus, List<Person>> regrouped = allPeople.stream()
                    .collect(Collectors.groupingBy(Person::getHealthStatus));

            System.out.println("start " + startEpochInfected);
            int endEpochInfected = regrouped.getOrDefault(HealthStatus.INFECTED, Collections.emptyList()).size();
            System.out.println("end " + endEpochInfected);
            int newInfected = endEpochInfected - startEpochInfected;
            int allInfected = 0;

            if (startEpochInfected > endEpochInfected) {
                allInfected = startEpochInfected;
            } else if (startEpochInfected < endEpochInfected) {
                allInfected = endEpochInfected;
            } else {
                allInfected = startEpochInfected;
            }
            city.updatePopulation(regrouped);

            currentEpoch++;
            Epoch currentEpochStats = new Epoch(currentEpoch, newInfected, allInfected, newDeath, allDead);
            this.csvLogger.log(currentEpochStats);
            System.out.println("Epoch " + currentEpoch + " completed.");

            if(newDeath == getConfig().cityConfig().population())
                return;

            List<Person> infectedPeople = nextEpochPeopleMap.get(HealthStatus.INFECTED);
            if(infectedPeople == null) return;
        }
    }
}
