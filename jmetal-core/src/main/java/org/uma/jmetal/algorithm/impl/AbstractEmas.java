package org.uma.jmetal.algorithm.impl;

import org.uma.jmetal.algorithm.Algorithm;
import org.uma.jmetal.solution.Solution;

import java.util.List;

public abstract class AbstractEmas<S extends Solution<?>, R, A extends AbstractEmasAgent> implements Algorithm<R> {

    private List<A> population;

    protected List<A> getPopulation() {
        return population;
    }

    protected abstract void initProgress();
    protected abstract void updateProgress();
    protected abstract boolean isStoppingConditionReached();
    protected abstract List<A> createInitialPopulation();

    @Override
    public abstract R getResult();

    @Override
    public void run() {
        population = createInitialPopulation();
        initProgress();

        while (!isStoppingConditionReached()) {
            for (A agent: population) {
                agent.executeLifeStep(population);
            }
            updateProgress();
        }
    }
}
