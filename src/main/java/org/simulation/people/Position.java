package org.simulation.people;

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

    @Override
    public boolean equals(Object o) {
        return true; // stub
    }

    @Override
    public int hashCode() {
        return 0; // stub
    }
}
