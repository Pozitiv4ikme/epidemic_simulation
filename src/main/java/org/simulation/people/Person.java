package org.simulation.people;

import org.simulation.services.DeathService;
import org.simulation.services.InfectionService;
import org.simulation.services.RecoveryService;
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

    public void checkInfection() {
        boolean infected = InfectionService.evaluateInfection(this);
        // zmiana healtStatus
    }

    private void chanceOfRecover() {
        boolean recovered = RecoveryService.evaluateRecovery(this);
        // zmiana healtStatus
    }

    private void chanceOfDie(DeathService deathService) {
        if(deathService.evaluateDeath(this))
            setHealthStatus(HealthStatus.DEAD);
    }

}
