package org.simulation.services.movement;

import org.simulation.city.City;
import org.simulation.people.AgeGroupImpact;
import org.simulation.people.Person;
import org.simulation.people.Position;
import org.simulation.services.ProbabilityService;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class MovementService {
    private final ProbabilityService probabilityService;

    public MovementService(ProbabilityService probabilityService) {
        this.probabilityService = probabilityService;
    }

    private final Random random = new Random();

    private List<Position> getAvailableDirections(Person person, City city) {
        Position currentPos = person.getPosition();
        List<Position> neighbours = currentPos.getNeighbours();
        List<Position> validPositions = new ArrayList<>();

        for (Position pos : neighbours) {
            if (pos.isInBounds(city.getHeight(), city.getWidth())) {
                validPositions.add(pos);
            }
        }
        return validPositions;
    }

    public void moving(Person person, City city, int numberOfMoves) {
        for (int i = 0; i < numberOfMoves; i++) {
            Position currentPos = person.getPosition();
            List<Position> availableDirections = getAvailableDirections(person, city);

            AgeGroupImpact ageGroupImpact = AgeGroupImpact.getProfileForAge(person.getAge());

            if(probabilityService.happens(ageGroupImpact.getBaseMovementChancePercent())) {
                Position newPos = availableDirections.get(random.nextInt(availableDirections.size()));
                city.getCityMap()[currentPos.getX()][currentPos.getY()].getPeople().remove(person);
                city.getCityMap()[newPos.getX()][newPos.getY()].getPeople().add(person);
                person.move(newPos);
            }
        }
    }
}
