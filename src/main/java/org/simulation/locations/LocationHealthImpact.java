package org.simulation.city.locations;

public enum LocationHealthImpact {
    MEDICAL_CENTRE(LocationType.MEDICAL_CENTRE, 10.0, 30.0),
    HOUSE(LocationType.HOUSE, 3.0, 10.0),
    WORKPLACE(LocationType.WORKPLACE, 15.0, 1.5),
    SCHOOL(LocationType.SCHOOL, 25.0, 5.0),
    NONE(null, 0.0, 0.0);

    private final LocationType type;
    private final double percentInfectionProbability;
    private final double percentRecoveryProbability;

    LocationHealthImpact(LocationType type, double percentInfectionProbability, double percentRecoveryProbability) {
        this.type = type;
        this.percentInfectionProbability = percentInfectionProbability;
        this.percentRecoveryProbability = percentRecoveryProbability;
    }

    public double getPercentInfectionProbability() {
        return percentInfectionProbability;
    }

    public LocationType getType() {
        return type;
    }

    public double getPercentRecoveryProbability() {
        return percentRecoveryProbability;
    }

    public static LocationHealthImpact getImpactForLocationType(LocationType type) {
        for (LocationHealthImpact impact: values()) {
            if(impact.type == type){
                return impact;
            }
        }

        return NONE;
    }
}
