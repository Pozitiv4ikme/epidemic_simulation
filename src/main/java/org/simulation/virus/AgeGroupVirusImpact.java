package org.simulation.virus;

public enum AgeGroupVirusImpact {
    CHILD(6, 24, 25.0, 0.0, 0.0, 0.0),
    ADULT(25, 44, 17.5, 0.0, 0.0, 0.0),
    MIDDLE_AGED(45, 64, 12.0, 0.0, 0.0, 0.0),
    SENIOR(65, 100, 50, 80.0, 70.0, -60.0);

    private final int minAge;
    private final int maxAge;
    private final double percentVirusMutation;
    private final double percentLethality;
    private final double percentInfectionProbability;
    private final double percentRecoveryProbability;

    AgeGroupVirusImpact(int minAge, int maxAge,
                        double percentVirusMutation, double percentLethalityInfluence,
                        double percentInfectionProbabilityInfluence, double percentRecoveryProbabilityInfluence) {
        this.minAge = minAge;
        this.maxAge = maxAge;
        this.percentVirusMutation = percentVirusMutation;
        this.percentLethality = percentLethalityInfluence;
        this.percentInfectionProbability = percentInfectionProbabilityInfluence;
        this.percentRecoveryProbability = percentRecoveryProbabilityInfluence;
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