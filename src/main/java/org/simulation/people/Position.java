package org.simulation.people;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

public class Position {
    private final int x;
    private final int y;

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

    public List<Position> getNeighbours() {
        List<Position> neighbours = new ArrayList<>();
        neighbours.add(this);
        neighbours.add(up());
        neighbours.add(down());
        neighbours.add(left());
        neighbours.add(right());
        return neighbours;
    }

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
