package org.simulation.people;

import org.simulation.virus.Virus;

import java.util.Optional;

public class Person implements Movable {
    private int age;
    private HealthStatus healthStatus;
    private Position position;
    private Optional<Virus> infectedBy;
    private String name;

    public Person(int age, HealthStatus healthStatus, Position position, String name) {
        this.age = age;
        this.healthStatus = healthStatus;
        this.position = position;
        this.infectedBy = Optional.empty();
        this.name = name;
    }

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

    public void move(Position position) {
        this.position = position;
    }

    @Override
    public String toString() {
        return name;
    }
}
