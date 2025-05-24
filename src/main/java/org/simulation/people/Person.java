package org.simulation.people;

import org.simulation.virus.Virus;

public class Person {
    private int age;
    private HealthStatus healthStatus;
    private Position position;
    private Virus infectedBy;

    public void setHealthStatus(HealthStatus healthStatus) {
        this.healthStatus = healthStatus;
    }

    public void setInfectedBy(Virus infectedBy) {
        this.infectedBy = infectedBy;
    }

    public int getAge() {
        return age;
    }

    public HealthStatus getHealthStatus() {
        return healthStatus;
    }

    public Position getPosition() {
        return position;
    }

    public Virus getInfectedBy() {
        return infectedBy;
    }

    public void move(Position position) {
        this.position = position;
    }
}
