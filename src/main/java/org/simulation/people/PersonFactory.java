package org.simulation.people;

import org.simulation.city.City;
import org.simulation.virus.Virus;

import java.util.*;

/**
 * Factory class responsible for generating initial population in the city.
 * Assigns people to random locations and determines who is infected at the beginning.
 */

public class PersonFactory {
    private final Random random = new Random();

    /**
     * Generates a list of people for the simulation.
     * A percentage of the population is randomly infected with the latest virus variant.
     * @param city the city in which people will be placed
     * @param viruses list of available virus variants
     * @return list of generated Person objects
     */

    public List<Person> generatePeople(City city, List<Virus> viruses) {
        int totalPopulation = city.getPopulation();
        int infectedCount = totalPopulation * city.getInfectedPercentage() / 100;

        int width = city.getWidth();
        int height = city.getHeight();

        List<Person> people = new ArrayList<>();

        // Use the latest virus (last in list) for initial infection
        Virus virus = viruses.get(viruses.size() - 1);

        for (int i = 0; i < totalPopulation; i++) {
            int x = random.nextInt(height);
            int y = random.nextInt(width);
            Position position = new Position(x, y);

            int age = random.nextInt(95) + 6;

            HealthStatus status;
            Optional<Virus> infectedBy = Optional.empty();

            // Determine if person is initially infected
            if (i < infectedCount) {
                status = HealthStatus.INFECTED;
                infectedBy = Optional.of(virus);
            } else {
                status = HealthStatus.HEALTHY;
            }

            // Create new person with or without infection
            Person person = infectedBy.isPresent()
                    ? new Person(age, status, position, infectedBy.get(), "P"+i)
                    : new Person(age, status, position, "P"+i);

            // Add person to list and to city map
            people.add(person);
            city.getCityMap()[x][y].getPeople().add(person);
        }

        return people;
    }
}
