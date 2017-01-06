package org.uma.jmetal.algorithm.multiobjective.emas;

import org.uma.jmetal.algorithm.impl.AbstractEmas;
import org.uma.jmetal.algorithm.impl.AbstractEmasParameters;
import org.uma.jmetal.operator.CrossoverOperator;
import org.uma.jmetal.operator.MutationOperator;
import org.uma.jmetal.problem.Problem;
import org.uma.jmetal.solution.Solution;
import org.uma.jmetal.util.SolutionListUtils;

import java.util.Comparator;
import java.util.List;
import java.util.concurrent.CopyOnWriteArrayList;
import java.util.stream.Collectors;

public class Emas<S extends Solution<S>> extends AbstractEmas<S, List<S>, EmasAgent<S>> {

    private final Problem<S> problem;
    private final int maxIterations;
    private final int populationSize;
    private final AbstractEmasParameters parameters;
    private final CrossoverOperator<S> crossoverOperator;
    private final MutationOperator<S> mutationOperator;
    private final Comparator<S> solutionComparator;

    private int iteration;

    public Emas(Problem<S> problem, int maxIterations, int populationSize, AbstractEmasParameters parameters,
                CrossoverOperator<S> crossoverOperator, MutationOperator<S> mutationOperator,
                Comparator<S> solutionComparator) {

        this.problem = problem;
        this.maxIterations = maxIterations;
        this.populationSize = populationSize;
        this.parameters = parameters;
        this.crossoverOperator = crossoverOperator;
        this.mutationOperator = mutationOperator;
        this.solutionComparator = solutionComparator;
    }

    @Override
    public String getName() {
        return "EMAS";
    }

    @Override
    public String getDescription() {
        return "Evolutionary Multi-Agent System Algorithm";
    }

    @Override
    protected void initProgress() {
        iteration = 1;
    }

    @Override
    protected void updateProgress() {
        iteration++;
    }

    @Override
    protected boolean isStoppingConditionReached() {
        return iteration > maxIterations;
    }

    @Override
    protected List<EmasAgent<S>> createInitialPopulation() {
        List<EmasAgent<S>> population = new CopyOnWriteArrayList<>();

        for (int i = 0; i < populationSize; i++) {
            population.add(new EmasAgent<>(problem, parameters, crossoverOperator,
                    mutationOperator, solutionComparator));
        }

        return population;
    }

    @Override
    public List<S> getResult() {
        List<S> population = getPopulation().stream()
                .map(EmasAgent<S>::getSolution)
                .collect(Collectors.toList());

        return SolutionListUtils.getNondominatedSolutions(population);
    }
}
