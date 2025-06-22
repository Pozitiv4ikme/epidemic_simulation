package org.simulation.services.death;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.MockedStatic;
import org.mockito.Mockito;
import org.simulation.locations.LocationHealthImpact;
import org.simulation.locations.LocationType;
import org.simulation.people.AgeGroupImpact;
import org.simulation.people.HealthStatus;
import org.simulation.people.Person;
import org.simulation.services.ProbabilityService;
import org.simulation.virus.Virus;

import java.util.Optional;

import static org.mockito.Mockito.*;

class DeathServiceTest {

    private ProbabilityService probabilityService;
    private DeathService deathService;
    private Person person;

    @BeforeEach
    void setUp() {
        probabilityService = mock(ProbabilityService.class);
        deathService = new DeathService(probabilityService);
        person = mock(Person.class);
    }

    @Test
    void testHealthyPersonDies() {
        when(person.getHealthStatus()).thenReturn(HealthStatus.HEALTHY);
        when(person.getAge()).thenReturn(30);
        AgeGroupImpact profile = mock(AgeGroupImpact.class);
        when(profile.getBaseMortalityChancePercent()).thenReturn(10.0);

        try (MockedStatic<AgeGroupImpact> mocked = Mockito.mockStatic(AgeGroupImpact.class)) {
            mocked.when(() -> AgeGroupImpact.getProfileForAge(30)).thenReturn(profile);
            when(probabilityService.happens(10.0)).thenReturn(true);

            deathService.evaluateDeath(person);

            verify(person).setHealthStatus(HealthStatus.DEAD);
        }
    }

    @Test
    void testHealthyPersonSurvives() {
        when(person.getHealthStatus()).thenReturn(HealthStatus.HEALTHY);
        when(person.getAge()).thenReturn(30);
        AgeGroupImpact profile = mock(AgeGroupImpact.class);
        when(profile.getBaseMortalityChancePercent()).thenReturn(10.0);

        try (MockedStatic<AgeGroupImpact> mocked = Mockito.mockStatic(AgeGroupImpact.class)) {
            mocked.when(() -> AgeGroupImpact.getProfileForAge(30)).thenReturn(profile);
            when(probabilityService.happens(10.0)).thenReturn(false);

            deathService.evaluateDeath(person);

            verify(person, never()).setHealthStatus(HealthStatus.DEAD);
        }
    }

    @Test
    void testInfectedPersonDies() {
        when(person.getHealthStatus()).thenReturn(HealthStatus.INFECTED);
        when(person.getAge()).thenReturn(40);
        Virus virus = mock(Virus.class);
        when(virus.getLethality()).thenReturn(20.0);
        when(person.getInfectedBy()).thenReturn(Optional.of(virus));
        AgeGroupImpact profile = mock(AgeGroupImpact.class);
        when(profile.getBaseMortalityChancePercent()).thenReturn(5.0);

        try (MockedStatic<AgeGroupImpact> mocked = Mockito.mockStatic(AgeGroupImpact.class)) {
            mocked.when(() -> AgeGroupImpact.getProfileForAge(40)).thenReturn(profile);
            when(probabilityService.happens(25.0)).thenReturn(true);

            deathService.evaluateDeath(person);

            verify(person).setHealthStatus(HealthStatus.DEAD);
        }
    }

    @Test
    void testInfectedPersonSurvives() {
        when(person.getHealthStatus()).thenReturn(HealthStatus.INFECTED);
        when(person.getAge()).thenReturn(40);
        Virus virus = mock(Virus.class);
        when(virus.getLethality()).thenReturn(20.0);
        when(person.getInfectedBy()).thenReturn(Optional.of(virus));
        AgeGroupImpact profile = mock(AgeGroupImpact.class);
        when(profile.getBaseMortalityChancePercent()).thenReturn(5.0);

        try (MockedStatic<AgeGroupImpact> mocked = Mockito.mockStatic(AgeGroupImpact.class)) {
            mocked.when(() -> AgeGroupImpact.getProfileForAge(40)).thenReturn(profile);
            when(probabilityService.happens(25.0)).thenReturn(false);

            deathService.evaluateDeath(person);

            verify(person, never()).setHealthStatus(HealthStatus.DEAD);
        }
    }
}

