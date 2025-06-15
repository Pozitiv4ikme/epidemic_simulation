package org.simulation.people;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

/**
 * Represents a position in the city grid.
 * Provides utility methods for movement and neighborhood calculation.
 */

public class Position {
    private final int x;
    private final int y;

    /**
     * Creates a new Position with given coordinates.
     * @param x vertical coordinate
     * @param y horizontal coordinate
     */

    public Position(int x, int y) {
        this.x = x;
        this.y = y;
    }

    public int getX() {
        return x;
    }

    public int getY() {
        return y;
    }

    public Position up() {
        return new Position(x - 1, y);
    }

    public Position down() {
        return new Position(x + 1, y);
    }

    public Position left() {
        return new Position(x, y - 1);
    }

    public Position right() {
        return new Position(x, y + 1);
    }

    /**
     * Returns a list of adjacent positions (up, down, left, right).
     * @return list of neighboring positions
     */

    public List<Position> getNeighbours() {
        List<Position> neighbours = new ArrayList<>();
        neighbours.add(up());
        neighbours.add(down());
        neighbours.add(left());
        neighbours.add(right());
        return neighbours;
    }

    /**
     * Checks if this position is within the bounds of the grid.
     * @param rows number of rows in the grid
     * @param cols number of columns in the grid
     * @return true if within bounds
     */

    public boolean isInBounds(int rows, int cols) {
        return x >= 0 && y >= 0 && x < rows && y < cols;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Position)) return false;
        Position pos = (Position) o;
        return x == pos.x && y == pos.y;
    }

    @Override
    public int hashCode() {
        return Objects.hash(x, y);
    }
}
