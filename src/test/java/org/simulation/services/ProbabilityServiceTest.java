package org.simulation.services;

import org.junit.jupiter.api.Test;
import org.mockito.Mockito;

import java.util.Random;

import static org.junit.jupiter.api.Assertions.*;

class ProbabilityServiceTest {

    @Test
    void testHappensZeroPercent() {
        Random mockRandom = Mockito.mock(Random.class);
        ProbabilityService service = new ProbabilityService(mockRandom);
        assertFalse(service.happens(0));
    }

    @Test
    void testHappensNegativePercent() {
        Random mockRandom = Mockito.mock(Random.class);
        ProbabilityService service = new ProbabilityService(mockRandom);
        assertFalse(service.happens(-10));
    }

    @Test
    void testHappensHundredPercent() {
        Random mockRandom = Mockito.mock(Random.class);
        ProbabilityService service = new ProbabilityService(mockRandom);
        assertTrue(service.happens(100));
    }

    @Test
    void testHappensAboveHundredPercent() {
        Random mockRandom = Mockito.mock(Random.class);
        ProbabilityService service = new ProbabilityService(mockRandom);
        assertTrue(service.happens(150));
    }

    @Test
    void testHappensFiftyPercentTrue() {
        Random mockRandom = Mockito.mock(Random.class);
        Mockito.when(mockRandom.nextDouble()).thenReturn(0.4); // less than 0.5
        ProbabilityService service = new ProbabilityService(mockRandom);
        assertTrue(service.happens(50));
    }

    @Test
    void testHappensFiftyPercentFalse() {
        Random mockRandom = Mockito.mock(Random.class);
        Mockito.when(mockRandom.nextDouble()).thenReturn(0.6); // greater than 0.5
        ProbabilityService service = new ProbabilityService(mockRandom);
        assertFalse(service.happens(50));
    }
}

