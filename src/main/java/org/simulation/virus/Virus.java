package org.simulation.virus;

import org.simulation.config.VirusConfig;

public class  Virus {
    private double infectionProbability;
    private int mutationStage;
    private double lethality;
    private double recoverProbability;

    public Virus(VirusConfig virusConfig) {
        this.infectionProbability = virusConfig.initialInfectionProbability();
        this.mutationStage = 1;
        this.lethality = virusConfig.initialLethality();
        this.recoverProbability = virusConfig.initialRecoveryProbability();
    }

    public Virus(double infectionProbability, int mutationStage, double lethality, double recoverProbability) {
        this.infectionProbability = infectionProbability;
        this.mutationStage = mutationStage;
        this.lethality = lethality;
        this.recoverProbability = recoverProbability;
    }

    public double getInfectionProbability() {
        return infectionProbability;
    }

    public int getMutationStage() {
        return mutationStage;
    }

    public double getLethality() {
        return lethality;
    }

    public double getRecoverProbability() {
        return recoverProbability;
    }

    public void setInfectionProbability(double infectionProbability) {
        this.infectionProbability = infectionProbability;
    }

    public void setMutationStage(int mutationStage) {
        this.mutationStage = mutationStage;
    }

    public void setLethality(double lethality) {
        this.lethality = lethality;
    }

    public void setRecoverProbability(double recoverProbability) {
        this.recoverProbability = recoverProbability;
    }

    @Override
    public String toString() {
        return "Virus{" +
                "infectionProbability=" + String.format("%.2f", infectionProbability) +
                ", mutationStage=" + mutationStage +
                ", lethality=" + String.format("%.2f", lethality) +
                ", recoverProbability=" + String.format("%.2f", recoverProbability) +
                '}';
    }
}
