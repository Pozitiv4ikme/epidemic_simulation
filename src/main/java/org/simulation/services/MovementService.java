package org.simulation.services;

import org.simulation.city.City;
import org.simulation.people.Person;
import org.simulation.people.Position;

import java.util.ArrayList;
import java.util.List;

public class MovementService {
    private List<Position> getAvailableDirections(Person person, City city) {
        List<Position> moves = new ArrayList<>();
        // patrzenie w jaką storonę możemy iść
        return moves;
    }

    public void moving(Person person, City city, int numberOfMoves) {
        List<Position> availableDirections = getAvailableDirections(person, city);
        person.move(availableDirections.get(0)); // stub
        // realizacja wszytkich kroków
    }
}
