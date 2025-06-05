package org.simulation.people;

import org.simulation.city.City;
import org.simulation.city.CityCell;
import org.simulation.virus.Virus;

import java.util.*;
import java.util.concurrent.ThreadLocalRandom;

public class PersonFactory {
    private final Random random = new Random();

    public List<Person> generatePeople(City city, List<Virus> viruses) {
        int totalPopulation = city.getPopulation();
        int infectedCount = totalPopulation * city.getInfectedPercentage() / 100;

        int width = city.getWidth();
        int height = city.getHeight();
//        CityCell[][] cityMap = city.getCityMap();

        List<Person> people = new ArrayList<>();

        Virus virus = viruses.get(viruses.size() - 1);

        for (int i = 0; i < totalPopulation; i++) {
            int x = random.nextInt(width);
            int y = random.nextInt(height);
            Position position = new Position(x, y);

            int age = ThreadLocalRandom.current().nextInt(6, 101);

            HealthStatus status;
            Optional<Virus> infectedBy = Optional.empty();

            if (i < infectedCount) {
                status = HealthStatus.INFECTED;
                infectedBy = Optional.of(virus);
            } else {
                status = HealthStatus.HEALTHY;
            }

            Person person = infectedBy.isPresent()
                    ? new Person(age, status, position, infectedBy.get())
                    : new Person(age, status, position);

            people.add(person);
            city.getCityMap()[x][y].getPeople().add(person);
        }

        return people;
    }
}
