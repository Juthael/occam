package com.tregouet.occam.alg.builders;

import com.tregouet.occam.alg.builders.categorizer.ProblemSpaceExplorer;
import com.tregouet.occam.alg.builders.categorizer.ProblemSpaceExplorerFactory;
import com.tregouet.occam.alg.builders.categorizer.ProblemSpaceExplorerStrategy;
import com.tregouet.occam.alg.builders.categorizer.graph_updater.expander.ProblemSpaceGraphExpander;
import com.tregouet.occam.alg.builders.categorizer.graph_updater.expander.ProblemSpaceGraphExpanderFactory;
import com.tregouet.occam.alg.builders.categorizer.graph_updater.expander.ProblemSpaceGraphExpanderStrategy;
import com.tregouet.occam.alg.builders.categorizer.graph_updater.restrictor.ProblemSpaceGraphRestrictor;
import com.tregouet.occam.alg.builders.categorizer.graph_updater.restrictor.ProblemSpaceGraphRestrictorFactory;
import com.tregouet.occam.alg.builders.categorizer.graph_updater.restrictor.ProblemSpaceGraphRestrictorStrategy;
import com.tregouet.occam.alg.builders.classifications.ClassificationBuilder;
import com.tregouet.occam.alg.builders.classifications.ClassificationBuilderFactory;
import com.tregouet.occam.alg.builders.classifications.ClassificationBuilderStrategy;
import com.tregouet.occam.alg.builders.concept_lattices.ConceptLatticeBuilder;
import com.tregouet.occam.alg.builders.concept_lattices.ConceptLatticeBuilderFactory;
import com.tregouet.occam.alg.builders.concept_lattices.ConceptLatticeBuilderStrategy;
import com.tregouet.occam.alg.builders.concept_lattices.denotations.DenotationBuilder;
import com.tregouet.occam.alg.builders.concept_lattices.denotations.DenotationBuilderFactory;
import com.tregouet.occam.alg.builders.concept_lattices.denotations.DenotationBuilderStrategy;
import com.tregouet.occam.alg.builders.concepts_trees.ConceptTreeGrower;
import com.tregouet.occam.alg.builders.concepts_trees.ConceptTreeGrowerFactory;
import com.tregouet.occam.alg.builders.concepts_trees.ConceptTreeGrowerStrategy;
import com.tregouet.occam.alg.builders.metricsDEP.SimilarityMetricsBuilderDEP;
import com.tregouet.occam.alg.builders.metricsDEP.SimilarityMetricsBuilderFactoryDEP;
import com.tregouet.occam.alg.builders.metricsDEP.SimilarityMetricsBuilderStrategyDEP;
import com.tregouet.occam.alg.builders.metricsDEP.matrices_DEP.asymetrical_sim.AsymmetricalSimilarityMatrixBuilderDEP;
import com.tregouet.occam.alg.builders.metricsDEP.matrices_DEP.asymetrical_sim.AsymmetricalSimilarityMatrixBuilderFactoryDEP;
import com.tregouet.occam.alg.builders.metricsDEP.matrices_DEP.asymetrical_sim.AsymmetricalSimilarityMatrixBuilderStrategyDEP;
import com.tregouet.occam.alg.builders.metricsDEP.matrices_DEP.difference.DifferenceMatrixBuilderDEP;
import com.tregouet.occam.alg.builders.metricsDEP.matrices_DEP.difference.DifferenceMatrixBuilderFactoryDEP;
import com.tregouet.occam.alg.builders.metricsDEP.matrices_DEP.difference.DifferenceMatrixBuilderStrategyDEP;
import com.tregouet.occam.alg.builders.metricsDEP.matrices_DEP.symmetrical_sim.SimilarityMatrixBuilderDEP;
import com.tregouet.occam.alg.builders.metricsDEP.matrices_DEP.symmetrical_sim.SimilarityMatrixBuilderFactoryDEP;
import com.tregouet.occam.alg.builders.metricsDEP.matrices_DEP.symmetrical_sim.SimilarityMatrixBuilderStrategyDEP;
import com.tregouet.occam.alg.builders.metricsDEP.matrices_DEP.typicality.TypicalityVectorBuilderDEP;
import com.tregouet.occam.alg.builders.metricsDEP.matrices_DEP.typicality.TypicalityVectorBuilderFactoryDEP;
import com.tregouet.occam.alg.builders.metricsDEP.matrices_DEP.typicality.TypicalityVectorBuilderStrategyDEP;
import com.tregouet.occam.alg.builders.representations.RepresentationBuilder;
import com.tregouet.occam.alg.builders.representations.RepresentationBuilderFactory;
import com.tregouet.occam.alg.builders.representations.RepresentationBuilderStrategy;
import com.tregouet.occam.alg.builders.representations.descriptions.DescriptionBuilder;
import com.tregouet.occam.alg.builders.representations.descriptions.DescriptionBuilderFactory;
import com.tregouet.occam.alg.builders.representations.descriptions.DescriptionBuilderStrategy;
import com.tregouet.occam.alg.builders.representations.descriptions.differentiae.DifferentiaeBuilder;
import com.tregouet.occam.alg.builders.representations.descriptions.differentiae.DifferentiaeBuilderFactory;
import com.tregouet.occam.alg.builders.representations.descriptions.differentiae.DifferentiaeBuilderStrategy;
import com.tregouet.occam.alg.builders.representations.descriptions.differentiae.properties.PropertyBuilder;
import com.tregouet.occam.alg.builders.representations.descriptions.differentiae.properties.PropertyBuilderFactory;
import com.tregouet.occam.alg.builders.representations.descriptions.differentiae.properties.PropertyBuilderStrategy;
import com.tregouet.occam.alg.builders.representations.descriptions.metrics.RelativeSimilarityMetricsBuilder;
import com.tregouet.occam.alg.builders.representations.descriptions.metrics.RelativeSimilarityMetricsBuilderFactory;
import com.tregouet.occam.alg.builders.representations.descriptions.metrics.RelativeSimilarityMetricsBuilderStrategy;
import com.tregouet.occam.alg.builders.representations.partitions.PartitionBuilder;
import com.tregouet.occam.alg.builders.representations.partitions.PartitionBuilderFactory;
import com.tregouet.occam.alg.builders.representations.partitions.PartitionBuilderStrategy;
import com.tregouet.occam.alg.builders.representations.partitions.graphs.PartitionGraphBuilder;
import com.tregouet.occam.alg.builders.representations.partitions.graphs.PartitionGraphBuilderFactory;
import com.tregouet.occam.alg.builders.representations.partitions.graphs.PartitionGraphBuilderStrategy;
import com.tregouet.occam.alg.builders.representations.production_sets.ProductionSetBuilder;
import com.tregouet.occam.alg.builders.representations.production_sets.ProductionSetBuilderFactory;
import com.tregouet.occam.alg.builders.representations.production_sets.ProductionSetBuilderStrategy;
import com.tregouet.occam.alg.builders.representations.production_sets.productions.ProductionBuilder;
import com.tregouet.occam.alg.builders.representations.production_sets.productions.ProductionBuilderFactory;
import com.tregouet.occam.alg.builders.representations.production_sets.productions.ProductionBuilderStrategy;
import com.tregouet.occam.alg.builders.representations.transition_functions.RepresentationTransFuncBuilder;
import com.tregouet.occam.alg.builders.representations.transition_functions.RepresentationTransFuncBuilderFactory;
import com.tregouet.occam.alg.builders.representations.transition_functions.RepresentationTransFuncBuilderStrategy;
import com.tregouet.occam.alg.builders.similarity_assessor.metrics.SimilarityMetricsBuilder;
import com.tregouet.occam.alg.builders.similarity_assessor.metrics.SimilarityMetricsBuilderFactory;
import com.tregouet.occam.alg.builders.similarity_assessor.metrics.SimilarityMetricsBuilderStrategy;
import com.tregouet.occam.alg.setters.salience.rule_detector.RuleDetector;
import com.tregouet.occam.alg.setters.salience.rule_detector.RuleDetectorFactory;
import com.tregouet.occam.alg.setters.salience.rule_detector.RuleDetectorStrategy;

public class BuildersAbstractFactory {

	public static final BuildersAbstractFactory INSTANCE = new BuildersAbstractFactory();

	private DenotationBuilderStrategy denotationBuilderStrategy = null;
	private ConceptLatticeBuilderStrategy conceptLatticeBuilderStrategy = null;
	private ConceptTreeGrowerStrategy conceptTreeGrowerStrategy = null;
	private ClassificationBuilderStrategy classificationBuilderStrategy = null;
	private ProductionBuilderStrategy productionBuilderStrategy = null;
	private RuleDetectorStrategy ruleDetectorStrategy = null;
	private ProductionSetBuilderStrategy productionSetBuilderStrategy = null;
	private RepresentationTransFuncBuilderStrategy representationTransFuncBuilderStrategy = null;
	private PropertyBuilderStrategy propertyBuilderStrategy = null;
	private DifferentiaeBuilderStrategy differentiaeBuilderStrategy = null;
	private DescriptionBuilderStrategy descriptionBuilderStrategy = null;
	private PartitionGraphBuilderStrategy partitionGraphBuilderStrategy = null;
	private PartitionBuilderStrategy partitionBuilderStrategy = null;
	private RepresentationBuilderStrategy representationBuilderStrategy = null;
	private RelativeSimilarityMetricsBuilderStrategy relativeSimilarityMetricsBuilderStrategy = null;
	private ProblemSpaceGraphExpanderStrategy problemSpaceGraphExpanderStrategy = null;
	private ProblemSpaceGraphRestrictorStrategy problemSpaceGraphRestrictorStrategy = null;
	private SimilarityMatrixBuilderStrategyDEP similarityMatrixBuilderStrategyDEP = null;
	private AsymmetricalSimilarityMatrixBuilderStrategyDEP asymmetricalSimilarityMatrixBuilderStrategyDEP = null;
	private DifferenceMatrixBuilderStrategyDEP differenceMatrixBuilderStrategyDEP = null;
	private TypicalityVectorBuilderStrategyDEP typicalityVectorBuilderStrategyDEP = null;
	private SimilarityMetricsBuilderStrategyDEP similarityMetricsBuilderStrategyDEP = null;
	private SimilarityMetricsBuilderStrategy similarityMetricsBuilderStrategy = null;
	private ProblemSpaceExplorerStrategy problemSpaceExplorerStrategy = null;

	private BuildersAbstractFactory() {
	}

	public AsymmetricalSimilarityMatrixBuilderDEP getAsymmetricalSimilarityMatrixBuilder() {
		return AsymmetricalSimilarityMatrixBuilderFactoryDEP.INSTANCE.apply(asymmetricalSimilarityMatrixBuilderStrategyDEP);
	}

	public ClassificationBuilder getClassificationBuilder() {
		return ClassificationBuilderFactory.INSTANCE.apply(classificationBuilderStrategy);
	}

	public ConceptLatticeBuilder getConceptLatticeBuilder() {
		return ConceptLatticeBuilderFactory.INSTANCE.apply(conceptLatticeBuilderStrategy);
	}

	public ConceptTreeGrower getConceptTreeGrower() {
		return ConceptTreeGrowerFactory.INSTANCE.apply(conceptTreeGrowerStrategy);
	}

	public DenotationBuilder getDenotationBuilder() {
		return DenotationBuilderFactory.INSTANCE.apply(denotationBuilderStrategy);
	}

	public DescriptionBuilder getDescriptionBuilder() {
		return DescriptionBuilderFactory.INSTANCE.apply(descriptionBuilderStrategy);
	}

	public DifferenceMatrixBuilderDEP getDifferenceMatrixBuilder() {
		return DifferenceMatrixBuilderFactoryDEP.INSTANCE.apply(differenceMatrixBuilderStrategyDEP);
	}

	public DifferentiaeBuilder getDifferentiaeBuilder() {
		return DifferentiaeBuilderFactory.INSTANCE.apply(differentiaeBuilderStrategy);
	}

	public PartitionBuilder getPartitionBuilder() {
		return PartitionBuilderFactory.INSTANCE.apply(partitionBuilderStrategy);
	}

	public PartitionGraphBuilder getPartitionGraphBuilder() {
		return PartitionGraphBuilderFactory.INSTANCE.apply(partitionGraphBuilderStrategy);
	}

	public ProblemSpaceExplorer getProblemSpaceExplorer() {
		return ProblemSpaceExplorerFactory.INSTANCE.apply(problemSpaceExplorerStrategy);
	}

	public ProblemSpaceGraphExpander getProblemSpaceGraphExpander() {
		return ProblemSpaceGraphExpanderFactory.INSTANCE.apply(problemSpaceGraphExpanderStrategy);
	}

	public ProblemSpaceGraphRestrictor getProblemSpaceGraphRestrictor() {
		return ProblemSpaceGraphRestrictorFactory.INSTANCE.apply(problemSpaceGraphRestrictorStrategy);
	}

	public ProductionBuilder getProductionBuilder() {
		return ProductionBuilderFactory.INSTANCE.apply(productionBuilderStrategy);
	}

	public ProductionSetBuilder getProductionSetBuilder() {
		return ProductionSetBuilderFactory.INSTANCE.apply(productionSetBuilderStrategy);
	}

	public PropertyBuilder getPropertyBuilder() {
		return PropertyBuilderFactory.INSTANCE.apply(propertyBuilderStrategy);
	}

	public RelativeSimilarityMetricsBuilder getRelativeSimilarityMetricsBuilder() {
		return RelativeSimilarityMetricsBuilderFactory.INSTANCE.apply(relativeSimilarityMetricsBuilderStrategy);
	}

	public RepresentationBuilder getRepresentationBuilder() {
		return RepresentationBuilderFactory.INSTANCE.apply(representationBuilderStrategy);
	}

	public RepresentationTransFuncBuilder getRepresentationTransFuncBuilder() {
		return RepresentationTransFuncBuilderFactory.INSTANCE.apply(representationTransFuncBuilderStrategy);
	}

	public RuleDetector getRuleDetector() {
		return RuleDetectorFactory.INSTANCE.apply(ruleDetectorStrategy);
	}

	public SimilarityMatrixBuilderDEP getSimilarityMatrixBuilder() {
		return SimilarityMatrixBuilderFactoryDEP.INSTANCE.apply(similarityMatrixBuilderStrategyDEP);
	}

	public SimilarityMetricsBuilderDEP getSimilarityMetricsBuilderDEP() {
		return SimilarityMetricsBuilderFactoryDEP.INSTANCE.apply(similarityMetricsBuilderStrategyDEP);
	}
	
	public SimilarityMetricsBuilder getSimilarityMetricsBuilder() {
		return SimilarityMetricsBuilderFactory.INSTANCE.apply(similarityMetricsBuilderStrategy);
	}

	public TypicalityVectorBuilderDEP getTypicalityVectorBuilder() {
		return TypicalityVectorBuilderFactoryDEP.INSTANCE.apply(typicalityVectorBuilderStrategyDEP);
	}

	public void setUpStrategy(BuildStrategy overallStrategy) {
		switch (overallStrategy) {
		case GENERATION_STRATEGY_2 :
			denotationBuilderStrategy = DenotationBuilderStrategy.MAX_SYMBOL_SUBSEQUENCES;
			conceptLatticeBuilderStrategy = ConceptLatticeBuilderStrategy.GALOIS_CONNECTION;
			conceptTreeGrowerStrategy = ConceptTreeGrowerStrategy.IF_LEAF_IS_UNIVERSAL_THEN_SORT;
			classificationBuilderStrategy = ClassificationBuilderStrategy.BUILD_PARAM_THEN_INST;
			productionBuilderStrategy = ProductionBuilderStrategy.MAP_TARGET_VARS_TO_SOURCE_VALUES;
			ruleDetectorStrategy = RuleDetectorStrategy.INST_COMPLETE_AND_DISTINCTIVE; //OK
			productionSetBuilderStrategy = ProductionSetBuilderStrategy.BUILD_FROM_SCRATCH_NO_EPSILON;
			representationTransFuncBuilderStrategy = RepresentationTransFuncBuilderStrategy.EVERY_APP_IS_RELEVANT;
			propertyBuilderStrategy = PropertyBuilderStrategy.GROUP_PRODUCTIONS_BY_COMPUTATION;
			differentiaeBuilderStrategy = DifferentiaeBuilderStrategy.IF_IS_A_THEN_DIFFER;
			descriptionBuilderStrategy = DescriptionBuilderStrategy.BUILD_TREE_THEN_CALCULATE_METRICS;
			partitionGraphBuilderStrategy = PartitionGraphBuilderStrategy.RECURSIVE_FORK_EXPLORATION;
			partitionBuilderStrategy = PartitionBuilderStrategy.BUILD_GRAPH_FIRST;
			representationBuilderStrategy = RepresentationBuilderStrategy.BUILD_TREE_SPECIFIC_PRODUCTION_SET;
			relativeSimilarityMetricsBuilderStrategy = RelativeSimilarityMetricsBuilderStrategy.MOST_SPECIFIC_CONCEPT;
			problemSpaceGraphExpanderStrategy = ProblemSpaceGraphExpanderStrategy.ADD_NEW_STATES_THEN_BUILD_TRANSITIONS;
			problemSpaceGraphRestrictorStrategy = ProblemSpaceGraphRestrictorStrategy.BUILD_NEW_GRAPH;
			similarityMatrixBuilderStrategyDEP = SimilarityMatrixBuilderStrategyDEP.MAXIMAL_RELATIVE_SIMILARITY;
			asymmetricalSimilarityMatrixBuilderStrategyDEP = AsymmetricalSimilarityMatrixBuilderStrategyDEP.SIM_DIV_BY_TOTAL_SIM;
			differenceMatrixBuilderStrategyDEP = DifferenceMatrixBuilderStrategyDEP.TWO_LEAVES_TREE_FOR_EACH_PAIR;
			typicalityVectorBuilderStrategyDEP = TypicalityVectorBuilderStrategyDEP.ASYMM_SIM_MATRIX_ROW_AVERAGE;
			similarityMetricsBuilderStrategyDEP = SimilarityMetricsBuilderStrategyDEP.SIM_THEN_DIFF_THEN_TYPICALITY;
			similarityMetricsBuilderStrategy = SimilarityMetricsBuilderStrategy.SIM_THEN_DIFF;
			problemSpaceExplorerStrategy = ProblemSpaceExplorerStrategy.DISCARD_UNINFORMATIVE_STATES;
			break;
		default:
			break;
		}
	}

}
