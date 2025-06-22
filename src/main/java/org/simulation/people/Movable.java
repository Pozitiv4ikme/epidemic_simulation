package org.simulation.people;

/**
 * Interface representing an entity that can move.
 */

public interface Movable {

    /**
     * Moves the entity to a new position.
     * @param newPosition the new position to move to
     */

    void move(Position newPosition);
}
