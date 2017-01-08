package org.uma.jmetal.algorithm.multiobjective.emas;

import org.uma.jmetal.algorithm.multiobjective.nsgaii.NSGAII;
import org.uma.jmetal.algorithm.multiobjective.nsgaii.NSGAIIMeasures;
import org.uma.jmetal.algorithm.multiobjective.nsgaii.SteadyStateNSGAII;
import org.uma.jmetal.operator.CrossoverOperator;
import org.uma.jmetal.operator.MutationOperator;
import org.uma.jmetal.operator.SelectionOperator;
import org.uma.jmetal.operator.impl.selection.BinaryTournamentSelection;
import org.uma.jmetal.problem.Problem;
import org.uma.jmetal.solution.Solution;
import org.uma.jmetal.util.AlgorithmBuilder;
import org.uma.jmetal.util.JMetalException;
import org.uma.jmetal.util.comparator.RankingAndCrowdingDistanceComparator;
import org.uma.jmetal.util.evaluator.SolutionListEvaluator;
import org.uma.jmetal.util.evaluator.impl.SequentialSolutionListEvaluator;

import java.util.Comparator;

/**
 * Created by Piotr on 2017-01-08.
 */
public class EmasBuilder<S extends Solution<?>> implements AlgorithmBuilder<Emas<S>> {

    /**
     * EmasBuilder class
     */
    private final Problem<S> problem;
    private int maxIterations;
    private int populationSize;
    private CrossoverOperator<S> crossoverOperator;
    private MutationOperator<S> mutationOperator;
    //private SelectionOperator<List<S>, S> selectionOperator;
    //private SolutionListEvaluator<S> evaluator;
    private Comparator<S> solutionComparator;


    /**
     * EmasBuilderBuilder constructor
     */
    public EmasBuilder(Problem<S> problem, CrossoverOperator<S> crossoverOperator,
                         MutationOperator<S> mutationOperator, Comparator<S> solutionComparator) {
        this.problem = problem;
        maxIterations = 25000;
        populationSize = 100;
        this.crossoverOperator = crossoverOperator ;
        this.mutationOperator = mutationOperator ;
        this.solutionComparator = solutionComparator;
        //selectionOperator = new BinaryTournamentSelection<S>(new RankingAndCrowdingDistanceComparator<S>()) ;
        //evaluator = new SequentialSolutionListEvaluator<S>();

    }

    public org.uma.jmetal.algorithm.multiobjective.emas.EmasBuilder<S> setMaxIterations(int maxIterations) {
        if (maxIterations < 0) {
            throw new JMetalException("maxIterations is negative: " + maxIterations);
        }
        this.maxIterations = maxIterations;

        return this;
    }

    public org.uma.jmetal.algorithm.multiobjective.emas.EmasBuilder<S> setPopulationSize(int populationSize) {
        if (populationSize < 0) {
            throw new JMetalException("Population size is negative: " + populationSize);
        }

        this.populationSize = populationSize;

        return this;
    }

//    public org.uma.jmetal.algorithm.multiobjective.emas.EmasBuilder<S> setSelectionOperator(SelectionOperator<List<S>, S> selectionOperator) {
//        if (selectionOperator == null) {
//            throw new JMetalException("selectionOperator is null");
//        }
//        this.selectionOperator = selectionOperator;
//
//        return this;
//    }

//    public org.uma.jmetal.algorithm.multiobjective.emas.EmasBuilder<S> setSolutionListEvaluator(SolutionListEvaluator<S> evaluator) {
//        if (evaluator == null) {
//            throw new JMetalException("evaluator is null");
//        }
//        this.evaluator = evaluator;
//
//        return this;
//    }


//    public org.uma.jmetal.algorithm.multiobjective.nsgaii.NSGAIIBuilder<S> setVariant(org.uma.jmetal.algorithm.multiobjective.nsgaii.NSGAIIBuilder.NSGAIIVariant variant) {
//        this.variant = variant;
//
//        return this;
//    }

    public Emas<S> build() {
        Emas<S> algorithm = new Emas<S>(problem, crossoverOperator,
                    mutationOperator, solutionComparator);
        return algorithm ;
    }

    /* Getters */
    public Problem<S> getProblem() {
        return problem;
    }

    public int getMaxIterations() {
        return maxIterations;
    }

    public int getPopulationSize() {
        return populationSize;
    }

    public CrossoverOperator<S> getCrossoverOperator() {
        return crossoverOperator;
    }

    public MutationOperator<S> getMutationOperator() {
        return mutationOperator;
    }

    public Comparator<S> getSolutionComparator() {
        return solutionComparator;
    }
//
//    public SolutionListEvaluator<S> getSolutionListEvaluator() {
//        return evaluator;
//    }
}