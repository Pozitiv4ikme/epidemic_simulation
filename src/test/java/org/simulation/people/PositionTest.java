package org.simulation.people;

import org.junit.jupiter.api.Test;

import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertIterableEquals;

public class PositionTest {
    Position position = new Position(1, 1);

    @Test
    void testGetNeighbours() {
        // actual
        List<Position> neighbours = position.getNeighbours();

        // expected
        List<Position> expected = List.of(
            new Position(0, 1), // up
            new Position(2, 1), // down
            new Position(1, 0), // left
            new Position(1, 2)  // right
        );

        assertIterableEquals(expected, neighbours);
    }

    @Test
    void testIsInBounds() {
        int height = 3;
        int width = 3;

        // actual
        boolean actual = position.isInBounds(height, width);

        // expected
        boolean expected = true;

        assertEquals(expected, actual);
    }
}
