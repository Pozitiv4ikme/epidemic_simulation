package org.simulation.services.movement;

import org.simulation.city.City;
import org.simulation.people.AgeGroupImpact;
import org.simulation.people.Person;
import org.simulation.people.Position;
import org.simulation.services.ProbabilityService;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

/**
 * Service responsible for handling movement of people within the city grid.
 */

public class MovementService {
    private final ProbabilityService probabilityService;

    /**
     * Constructs a new MovementService with the given probability logic.
     * @param probabilityService Service used to determine if movement occurs.
     */

    public MovementService(ProbabilityService probabilityService) {
        this.probabilityService = probabilityService;
    }

    /**
     * Random generator used to choose movement direction.
     */

    private final Random random = new Random();

    /**
     * Returns a list of valid directions a person can move to from their current position,
     * considering city boundaries.
     * @param person the person whose movement is being evaluated
     * @param city the city grid
     * @return list of available neighbouring positions within city bounds
     */

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

    /**
     * Performs movement for a person a given number of times, with each move happening
     * based on a probabilistic chance defined by their age group.
     * @param person the person to move
     * @param city the city environment
     * @param numberOfMoves number of movement attempts
     */

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
