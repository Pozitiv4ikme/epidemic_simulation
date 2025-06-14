package org.simulation.people;

import org.simulation.city.City;
import org.simulation.virus.Virus;

import java.util.*;

public class PersonFactory {
    private final Random random = new Random();

    public List<Person> generatePeople(City city, List<Virus> viruses) {
        int totalPopulation = city.getPopulation();
        int infectedCount = totalPopulation * city.getInfectedPercentage() / 100;

        int width = city.getWidth();
        int height = city.getHeight();

        List<Person> people = new ArrayList<>();

        Virus virus = viruses.get(viruses.size() - 1);

        for (int i = 0; i < totalPopulation; i++) {
            int x = random.nextInt(height);
            int y = random.nextInt(width);
            Position position = new Position(x, y);

            int age = random.nextInt(95) + 6;

            HealthStatus status;
            Optional<Virus> infectedBy = Optional.empty();

            if (i < infectedCount) {
                status = HealthStatus.INFECTED;
                infectedBy = Optional.of(virus);
            } else {
                status = HealthStatus.HEALTHY;
            }

            Person person = infectedBy.isPresent()
                    ? new Person(age, status, position, infectedBy.get(), "P"+i)
                    : new Person(age, status, position, "P"+i);

            people.add(person);
            city.getCityMap()[x][y].getPeople().add(person);
        }

        return people;
    }
}
