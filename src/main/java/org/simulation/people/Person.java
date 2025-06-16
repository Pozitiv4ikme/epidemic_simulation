package org.simulation.people;

import org.simulation.MakeId;
import org.simulation.virus.Virus;

import java.util.Optional;
import java.util.Random;

/**
 * Represents a person in the simulation with attributes like age, health status, and position.
 * Can be infected by a virus and is capable of moving.
 */

public class Person implements Movable, MakeId {
    private int age;
    private HealthStatus healthStatus;
    private Position position;
    private Optional<Virus> infectedBy;
    private String name;

    /**
     * Constructor for a healthy person.
     * @param age person's age
     * @param healthStatus current health status
     * @param position initial position in the city
     * @param name unique name or ID
     */

    public Person(int age, HealthStatus healthStatus, Position position, String name) {
        this.age = age;
        this.healthStatus = healthStatus;
        this.position = position;
        this.infectedBy = Optional.empty();
        this.name = name;
    }

    /**
     * Constructor for an infected person.
     * @param age person's age
     * @param healthStatus current health status
     * @param position initial position in the city
     * @param infectedBy virus that infected the person
     * @param name unique name or ID
     */
    public Person(int age, HealthStatus healthStatus, Position position, Virus infectedBy, String name) {
        this.age = age;
        this.healthStatus = healthStatus;
        this.position = position;
        this.infectedBy = Optional.of(infectedBy);
        this.name = name;
    }

    public void setHealthStatus(HealthStatus healthStatus) {
        this.healthStatus = healthStatus;
    }

    public void setInfectedBy(Optional<Virus> infectedBy) {
        this.infectedBy = infectedBy;
    }

    public int getAge() {
        return age;
    }
    public String getName() {return name;}

    public HealthStatus getHealthStatus() {
        return healthStatus;
    }

    public Position getPosition() {
        return position;
    }

    public  Optional<Virus>  getInfectedBy() {
        return infectedBy;
    }

    /**
     * Moves the person to a new position.
     * @param position new position
     */

    public void move(Position position) {
        this.position = position;
    }

    @Override
    public String toString() {
        return name;
    }

    @Override
    public String generateId() {
        Random random = new Random();
        return String.valueOf(random.nextInt(position.getX() + age) + 1 + position.getY());
    }
}
