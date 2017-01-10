package org.uma.jmetal.algorithm.multiobjective.emas;

import org.uma.jmetal.algorithm.impl.AbstractEmasParameters;

/**
 * Created by macie on 10-Jan-17.
 */
public class EmasParameters extends AbstractEmasParameters {

    private final int startingEnergyLevel;
    private final int energyTransferredOnMeeting;
    private final int deathThreshold;
    private final int reproductionThreshold;
    private final int migrationThreshold;

    public EmasParameters(int startingEnergyLevel, int energyTransferredOnMeeting,
                          int deathThreshold, int reproductionThreshold,
                          int migrationThreshold) {
        this.startingEnergyLevel = startingEnergyLevel;
        this.energyTransferredOnMeeting = energyTransferredOnMeeting;
        this.deathThreshold = deathThreshold;
        this.reproductionThreshold = reproductionThreshold;
        this.migrationThreshold = migrationThreshold;
    }

    @Override
    public int getStartingEnergyLevel() {
        return startingEnergyLevel;
    }

    @Override
    public int getDeathThreshold() {
        return deathThreshold;
    }

    @Override
    public int getMigrationThreshold() {
        return migrationThreshold;
    }

    @Override
    public int getReproductionThreshold() {
        return reproductionThreshold;
    }

    @Override
    public int getEnergyTransferredOnMeeting() {
        return energyTransferredOnMeeting;
    }
}
