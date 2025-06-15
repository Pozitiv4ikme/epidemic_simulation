package org.simulation.people;

public enum AgeGroupImpact {
    YOUNG(6, 24, 15.0, 0.5, 80.0, 95.0),
    ADULT(25, 44, 10.0, 5.5, 65.5, 80.0),
    MIDDLE_AGED(45, 64, 25.0, 17.5, 50.0, 65.0),
    SENIOR(65, 100, 40.0, 30.0, 30.0, 30.0);

    private final int minAge;
    private final int maxAge;
    private final double baseMortalityChancePercent;
    private final double baseRecoveryChancePercent;
    private final double baseInfectionChancePercent;
    private final double baseMovementChancePercent;

    AgeGroupImpact(int minAge, int maxAge,
                   double baseInfectionChancePercent,
                   double baseMortalityChancePercent,
                   double baseRecoveryChancePercent,
                   double baseMovementChancePercent) {
        this.minAge = minAge;
        this.maxAge = maxAge;
        this.baseMortalityChancePercent = baseMortalityChancePercent;
        this.baseRecoveryChancePercent = baseRecoveryChancePercent;
        this.baseInfectionChancePercent = baseInfectionChancePercent;
        this.baseMovementChancePercent = baseMovementChancePercent;
    }

    public int getMinAge() { return minAge; }
    public int getMaxAge() { return maxAge; }
    public double getBaseMortalityChancePercent() { return baseMortalityChancePercent; }
    public double getBaseRecoveryChancePercent() { return baseRecoveryChancePercent; }
    public double getBaseInfectionChancePercent() { return baseInfectionChancePercent; }
    public double getBaseMovementChancePercent() { return baseMovementChancePercent; }

    public static AgeGroupImpact getProfileForAge(int age) {
        for (AgeGroupImpact profile : values()) {
            if (age >= profile.getMinAge() && age <= profile.getMaxAge()) {
                return profile;
            }
        }
        throw new IllegalArgumentException("No mortality/recovery profile found for age: " + age);
    }
}