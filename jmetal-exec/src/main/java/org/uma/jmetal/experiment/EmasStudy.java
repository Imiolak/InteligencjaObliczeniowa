package org.uma.jmetal.experiment;

import org.uma.jmetal.algorithm.Algorithm;
import org.uma.jmetal.algorithm.multiobjective.nsgaii.NSGAIIBuilder;
import org.uma.jmetal.operator.impl.crossover.SBXCrossover;
import org.uma.jmetal.operator.impl.mutation.PolynomialMutation;
import org.uma.jmetal.problem.Problem;
import org.uma.jmetal.problem.multiobjective.zdt.*;
import org.uma.jmetal.qualityindicator.impl.*;
import org.uma.jmetal.qualityindicator.impl.hypervolume.PISAHypervolume;
import org.uma.jmetal.solution.DoubleSolution;
import org.uma.jmetal.util.JMetalException;
import org.uma.jmetal.util.comparator.DominanceComparator;
import org.uma.jmetal.util.experiment.Experiment;
import org.uma.jmetal.util.experiment.ExperimentBuilder;
import org.uma.jmetal.util.experiment.component.*;
import org.uma.jmetal.util.experiment.util.TaggedAlgorithm;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 * Created by macie on 06-Jan-17.
 */
public class EmasStudy {
    private static final int INDEPENDENT_RUNS = 25 ;

    public static void main(String[] args) throws IOException {
        if (args.length != 1) {
            throw new JMetalException("Missing argument: experiment base directory") ;
        }
        String experimentBaseDirectory = args[0] ;

        List<Problem<DoubleSolution>> problemList = Arrays.asList(new ZDT1(), new ZDT2(),
                new ZDT3(), new ZDT4(), new ZDT6()) ;

        List<TaggedAlgorithm<List<DoubleSolution>>> algorithmList = configureAlgorithmList(problemList, INDEPENDENT_RUNS) ;

        List<String> referenceFrontFileNames = Arrays.asList("ZDT1.pf", "ZDT2.pf", "ZDT3.pf", "ZDT4.pf", "ZDT6.pf") ;

        Experiment<DoubleSolution, List<DoubleSolution>> experiment =
                new ExperimentBuilder<DoubleSolution, List<DoubleSolution>>("EMASStudy")
                        .setAlgorithmList(algorithmList)
                        .setProblemList(problemList)
                        .setExperimentBaseDirectory(experimentBaseDirectory)
                        .setOutputParetoFrontFileName("FUN")
                        .setOutputParetoSetFileName("VAR")
                        .setReferenceFrontDirectory("/pareto_fronts")
                        .setReferenceFrontFileNames(referenceFrontFileNames)
                        .setIndicatorList(Arrays.asList(
                                new Epsilon<>(), new Spread<>(), new GenerationalDistance<>(), new PISAHypervolume<>(),
                                new InvertedGenerationalDistance<>(), new InvertedGenerationalDistancePlus<>()))
                        .setIndependentRuns(INDEPENDENT_RUNS)
                        .setNumberOfCores(8)
                        .build();

        new ExecuteAlgorithms<>(experiment).run();
        new ComputeQualityIndicators<>(experiment).run() ;
        new GenerateLatexTablesWithStatistics(experiment).run() ;
        new GenerateWilcoxonTestTablesWithR<>(experiment).run() ;
        new GenerateFriedmanTestTables<>(experiment).run();
        new GenerateBoxplotsWithR<>(experiment).setRows(3).setColumns(3).run() ;
    }

    /**
     * The algorithm list is composed of pairs {@link Algorithm} + {@link Problem} which form part of a
     * {@link TaggedAlgorithm}, which is a decorator for class {@link Algorithm}. The {@link TaggedAlgorithm}
     * has an optional tag component, that can be set as it is shown in this example, where four variants of a
     * same algorithm are defined.
     *
     * @param problemList
     * @return
     */
    static List<TaggedAlgorithm<List<DoubleSolution>>> configureAlgorithmList(List<Problem<DoubleSolution>> problemList,
                                                                              int independentRuns) {
        List<TaggedAlgorithm<List<DoubleSolution>>> algorithms = new ArrayList<>() ;

        for (int run = 0; run < independentRuns; run++) {

            for (int i = 0; i < problemList.size(); i++) {
                Algorithm<List<DoubleSolution>> algorithm = new EmasBuilder<>(problemList.get(i), new SBXCrossover(1.0, 5),
                        new PolynomialMutation(1.0 / problemList.get(i).getNumberOfVariables(), 10.0),
                        new DominanceComparator<DoubleSolution>())
                        .setMaxIterations(25000)
                        .setPopulationSize(100)
                        .build();

                algorithms.add(new TaggedAlgorithm<>(algorithm, "EMASa", problemList.get(i), run));
            }

            for (int i = 0; i < problemList.size(); i++) {
                Algorithm<List<DoubleSolution>> algorithm = new EmasBuilder<>(problemList.get(i), new SBXCrossover(1.0, 20.0),
                        new PolynomialMutation(1.0 / problemList.get(i).getNumberOfVariables(), 20.0),
                        new DominanceComparator<DoubleSolution>())
                        .setMaxIterations(25000)
                        .setPopulationSize(100)
                        .build();

                algorithms.add(new TaggedAlgorithm<>(algorithm, "EMASb", problemList.get(i), run));
            }

            for (int i = 0; i < problemList.size(); i++) {
                Algorithm<List<DoubleSolution>> algorithm = new EmasBuilder<>(problemList.get(i), new SBXCrossover(1.0, 40.0),
                        new PolynomialMutation(1.0 / problemList.get(i).getNumberOfVariables(), 40.0),
                        new DominanceComparator<DoubleSolution>())
                        .setMaxIterations(25000)
                        .setPopulationSize(100)
                        .build();

                algorithms.add(new TaggedAlgorithm<>(algorithm, "EMASc", problemList.get(i), run));
            }

            for (int i = 0; i < problemList.size(); i++) {
                Algorithm<List<DoubleSolution>> algorithm = new EmasBuilder<>(problemList.get(i), new SBXCrossover(1.0, 80.0),
                        new PolynomialMutation(1.0 / problemList.get(i).getNumberOfVariables(), 80.0),
                        new DominanceComparator<DoubleSolution>())
                        .setMaxIterations(25000)
                        .setPopulationSize(100)
                        .build();

                algorithms.add(new TaggedAlgorithm<>(algorithm, "EMASd", problemList.get(i), run));
            }
        }

        return algorithms ;
    }
}
