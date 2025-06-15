package org.simulation.virus;

public enum AgeGroupVirusImpact {
    CHILD(6, 24, 60, 4.0, 25.5, 5.0),
    ADULT(25, 44, 70, 13.0, 35.0, 3.0),
    MIDDLE_AGED(45, 64, 80, 22.0, 52.0, 2.0),
    SENIOR(65, 100, 80, 34.0, 25.0, 1.0);

    private final int minAge;
    private final int maxAge;
    private final double percentVirusMutation;
    private final double percentLethality;
    private final double percentInfectionProbability;
    private final double percentRecoveryProbability;

    AgeGroupVirusImpact(int minAge, int maxAge,
                        double percentVirusMutation, double percentLethality,
                        double percentInfectionProbability, double percentRecoveryProbability) {
        this.minAge = minAge;
        this.maxAge = maxAge;
        this.percentVirusMutation = percentVirusMutation;
        this.percentLethality = percentLethality;
        this.percentInfectionProbability = percentInfectionProbability;
        this.percentRecoveryProbability = percentRecoveryProbability;
    }

    public int getMinAge() { return minAge; }
    public int getMaxAge() { return maxAge; }
    public double getPercentVirusMutation() { return percentVirusMutation; }
    public double getPercentLethality() { return percentLethality; }
    public double getPercentInfectionProbability() { return percentInfectionProbability; }
    public double getPercentRecoveryProbability() { return percentRecoveryProbability; }

    public static AgeGroupVirusImpact getProfileForAge(int age) {
        for (AgeGroupVirusImpact profile : values()) {
            if (age >= profile.getMinAge() && age <= profile.getMaxAge()) {
                return profile;
            }
        }
        throw new IllegalArgumentException("No virus impact profile found for age: " + age);
    }
}