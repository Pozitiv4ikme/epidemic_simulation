package org.simulation.services.recovery;

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

class RecoveryServiceTest {

    private ProbabilityService probabilityService;
    private RecoveryService recoveryService;
    private Person person;
    private Virus virus;

    @BeforeEach
    void setUp() {
        probabilityService = mock(ProbabilityService.class);
        recoveryService = new RecoveryService(probabilityService);
        person = mock(Person.class);
        virus = mock(Virus.class);
    }

    @Test
    void testPersonRecovers() {
        when(person.getAge()).thenReturn(30);
        AgeGroupImpact profile = mock(AgeGroupImpact.class);
        when(profile.getBaseRecoveryChancePercent()).thenReturn(10.0);
        when(person.getInfectedBy()).thenReturn(Optional.of(virus));
        when(virus.getRecoverProbability()).thenReturn(20.0);
        LocationHealthImpact locationImpact = mock(LocationHealthImpact.class);
        when(locationImpact.getPercentRecoveryProbability()).thenReturn(5.0);

        try (MockedStatic<AgeGroupImpact> ageGroupMock = Mockito.mockStatic(AgeGroupImpact.class);
             MockedStatic<LocationHealthImpact> locationMock = Mockito.mockStatic(LocationHealthImpact.class)) {
            ageGroupMock.when(() -> AgeGroupImpact.getProfileForAge(30)).thenReturn(profile);
            locationMock.when(() -> LocationHealthImpact.getImpactForLocationType(LocationType.HOUSE)).thenReturn(locationImpact);

            when(probabilityService.happens(35.0)).thenReturn(true);

            recoveryService.evaluateRecovery(person, LocationType.HOUSE);

            verify(person).setHealthStatus(HealthStatus.HEALTHY);
            verify(person).setInfectedBy(Optional.empty());
        }
    }

    @Test
    void testPersonDoesNotRecover() {
        when(person.getAge()).thenReturn(30);
        AgeGroupImpact profile = mock(AgeGroupImpact.class);
        when(profile.getBaseRecoveryChancePercent()).thenReturn(10.0);
        when(person.getInfectedBy()).thenReturn(Optional.of(virus));
        when(virus.getRecoverProbability()).thenReturn(20.0);
        LocationHealthImpact locationImpact = mock(LocationHealthImpact.class);
        when(locationImpact.getPercentRecoveryProbability()).thenReturn(5.0);

        try (MockedStatic<AgeGroupImpact> ageGroupMock = Mockito.mockStatic(AgeGroupImpact.class);
             MockedStatic<LocationHealthImpact> locationMock = Mockito.mockStatic(LocationHealthImpact.class)) {
            ageGroupMock.when(() -> AgeGroupImpact.getProfileForAge(30)).thenReturn(profile);
            locationMock.when(() -> LocationHealthImpact.getImpactForLocationType(LocationType.HOUSE)).thenReturn(locationImpact);

            when(probabilityService.happens(35.0)).thenReturn(false);

            recoveryService.evaluateRecovery(person, LocationType.HOUSE);

            verify(person, never()).setHealthStatus(HealthStatus.HEALTHY);
            verify(person, never()).setInfectedBy(Optional.empty());
        }
    }
}

