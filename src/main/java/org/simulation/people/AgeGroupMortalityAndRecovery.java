package org.simulation.people;

public enum AgeGroupMortalityAndRecovery {
    YOUNG(6, 24, 0.1, 90.0, 12.0),
    ADULT(25, 44, 0.2, 80.0, 16.0),
    MIDDLE_AGED(45, 64, 0.5, 60.0, 25.0),
    SENIOR(65, 100, 1.5, 40.0, 30.0);

    private final int minAge;
    private final int maxAge;
    private final double baseMortalityChancePercent;
    private final double baseRecoveryChancePercent;
    private final double baseInfectionChancePercent;

    AgeGroupMortalityAndRecovery(int minAge, int maxAge,
                                 double baseInfectionChancePercent,
                                 double baseMortalityChancePercent,
                                 double baseRecoveryChancePercent) {
        this.minAge = minAge;
        this.maxAge = maxAge;
        this.baseMortalityChancePercent = baseMortalityChancePercent;
        this.baseRecoveryChancePercent = baseRecoveryChancePercent;
        this.baseInfectionChancePercent = baseInfectionChancePercent;
    }

    public int getMinAge() { return minAge; }
    public int getMaxAge() { return maxAge; }
    public double getBaseMortalityChancePercent() { return baseMortalityChancePercent; }
    public double getBaseRecoveryChancePercent() { return baseRecoveryChancePercent; }
    public double getBaseInfectionChancePercent() { return baseInfectionChancePercent; }

    public static AgeGroupMortalityAndRecovery getProfileForAge(int age) {
        for (AgeGroupMortalityAndRecovery profile : values()) {
            if (age >= profile.getMinAge() && age <= profile.getMaxAge()) {
                return profile;
            }
        }
        throw new IllegalArgumentException("No mortality/recovery profile found for age: " + age);
    }
}