package com.tregouet.occam.alg.builders;

import com.tregouet.occam.alg.builders.pb_space.ProblemSpaceExplorer;
import com.tregouet.occam.alg.builders.pb_space.ProblemSpaceExplorerFactory;
import com.tregouet.occam.alg.builders.pb_space.ProblemSpaceExplorerStrategy;
import com.tregouet.occam.alg.builders.pb_space.concept_lattices.ConceptLatticeBuilder;
import com.tregouet.occam.alg.builders.pb_space.concept_lattices.ConceptLatticeBuilderFactory;
import com.tregouet.occam.alg.builders.pb_space.concept_lattices.ConceptLatticeBuilderStrategy;
import com.tregouet.occam.alg.builders.pb_space.concept_lattices.denotations.DenotationBuilder;
import com.tregouet.occam.alg.builders.pb_space.concept_lattices.denotations.DenotationBuilderFactory;
import com.tregouet.occam.alg.builders.pb_space.concept_lattices.denotations.DenotationBuilderStrategy;
import com.tregouet.occam.alg.builders.pb_space.concepts_trees.ConceptTreeGrower;
import com.tregouet.occam.alg.builders.pb_space.concepts_trees.ConceptTreeGrowerFactory;
import com.tregouet.occam.alg.builders.pb_space.concepts_trees.ConceptTreeGrowerStrategy;
import com.tregouet.occam.alg.builders.pb_space.pb_transitions.ProblemTransitionBuilder;
import com.tregouet.occam.alg.builders.pb_space.pb_transitions.ProblemTransitionBuilderFactory;
import com.tregouet.occam.alg.builders.pb_space.pb_transitions.ProblemTransitionBuilderStrategy;
import com.tregouet.occam.alg.builders.pb_space.ranker.ProblemTransitionRanker;
import com.tregouet.occam.alg.builders.pb_space.ranker.ProblemTransitionRankerFactory;
import com.tregouet.occam.alg.builders.pb_space.ranker.ProblemTransitionRankerStrategy;
import com.tregouet.occam.alg.builders.pb_space.representations.RepresentationBuilder;
import com.tregouet.occam.alg.builders.pb_space.representations.RepresentationBuilderFactory;
import com.tregouet.occam.alg.builders.pb_space.representations.RepresentationBuilderStrategy;
import com.tregouet.occam.alg.builders.pb_space.representations.descriptions.DescriptionBuilder;
import com.tregouet.occam.alg.builders.pb_space.representations.descriptions.DescriptionBuilderFactory;
import com.tregouet.occam.alg.builders.pb_space.representations.descriptions.DescriptionBuilderStrategy;
import com.tregouet.occam.alg.builders.pb_space.representations.descriptions.differentiae.DifferentiaeBuilder;
import com.tregouet.occam.alg.builders.pb_space.representations.descriptions.differentiae.DifferentiaeBuilderFactory;
import com.tregouet.occam.alg.builders.pb_space.representations.descriptions.differentiae.DifferentiaeBuilderStrategy;
import com.tregouet.occam.alg.builders.pb_space.representations.descriptions.differentiae.properties.PropertyBuilder;
import com.tregouet.occam.alg.builders.pb_space.representations.descriptions.differentiae.properties.PropertyBuilderFactory;
import com.tregouet.occam.alg.builders.pb_space.representations.descriptions.differentiae.properties.PropertyBuilderStrategy;
import com.tregouet.occam.alg.builders.pb_space.representations.descriptions.metrics.SimilarityMetricsBuilder;
import com.tregouet.occam.alg.builders.pb_space.representations.descriptions.metrics.SimilarityMetricsBuilderFactory;
import com.tregouet.occam.alg.builders.pb_space.representations.descriptions.metrics.SimilarityMetricsBuilderStrategy;
import com.tregouet.occam.alg.builders.pb_space.representations.fact_evaluators.FactEvaluatorBuilder;
import com.tregouet.occam.alg.builders.pb_space.representations.fact_evaluators.FactEvaluatorBuilderFactory;
import com.tregouet.occam.alg.builders.pb_space.representations.fact_evaluators.FactEvaluatorBuilderStrategy;
import com.tregouet.occam.alg.builders.pb_space.representations.partitions.PartitionBuilder;
import com.tregouet.occam.alg.builders.pb_space.representations.partitions.PartitionBuilderFactory;
import com.tregouet.occam.alg.builders.pb_space.representations.partitions.PartitionBuilderStrategy;
import com.tregouet.occam.alg.builders.pb_space.representations.partitions.graphs.builder.PartitionGraphBuilder;
import com.tregouet.occam.alg.builders.pb_space.representations.partitions.graphs.builder.PartitionGraphBuilderFactory;
import com.tregouet.occam.alg.builders.pb_space.representations.partitions.graphs.builder.PartitionGraphBuilderStrategy;
import com.tregouet.occam.alg.builders.pb_space.representations.productions.ProductionBuilder;
import com.tregouet.occam.alg.builders.pb_space.representations.productions.ProductionBuilderFactory;
import com.tregouet.occam.alg.builders.pb_space.representations.productions.ProductionBuilderStrategy;
import com.tregouet.occam.alg.builders.pb_space.representations.productions.from_denotations.ProdBldrFromDenotationsFactory;
import com.tregouet.occam.alg.builders.pb_space.representations.productions.from_denotations.ProdBuilderFromDenotations;
import com.tregouet.occam.alg.builders.pb_space.representations.productions.from_denotations.ProdBuilderFromDenotationsStrategy;
import com.tregouet.occam.alg.builders.pb_space.representations.transition_functions.RepresentationTransFuncBuilder;
import com.tregouet.occam.alg.builders.pb_space.representations.transition_functions.RepresentationTransFuncBuilderFactory;
import com.tregouet.occam.alg.builders.pb_space.representations.transition_functions.RepresentationTransFuncBuilderStrategy;
import com.tregouet.occam.alg.builders.pb_space.representations.transition_functions.transition_saliences.TransitionSalienceSetter;
import com.tregouet.occam.alg.builders.pb_space.representations.transition_functions.transition_saliences.TransitionSalienceSetterFactory;
import com.tregouet.occam.alg.builders.pb_space.representations.transition_functions.transition_saliences.TransitionSalienceSetterStrategy;

public class GeneratorsAbstractFactory {

	public static final GeneratorsAbstractFactory INSTANCE = new GeneratorsAbstractFactory();

	private DenotationBuilderStrategy denotationBuilderStrategy = null;
	private ConceptLatticeBuilderStrategy conceptLatticeBuilderStrategy = null;
	private ConceptTreeGrowerStrategy conceptTreeGrowerStrategy = null;
	private ProdBuilderFromDenotationsStrategy prodBuilderFromDenotationsStrategy = null;
	private ProductionBuilderStrategy productionBuilderStrategy = null;
	private TransitionSalienceSetterStrategy transitionSalienceSetterStrategy = null;
	private RepresentationTransFuncBuilderStrategy representationTransFuncBuilderStrategy = null;
	private PropertyBuilderStrategy propertyBuilderStrategy = null;
	private DifferentiaeBuilderStrategy differentiaeBuilderStrategy = null;
	private DescriptionBuilderStrategy descriptionBuilderStrategy = null;
	private PartitionGraphBuilderStrategy partitionGraphBuilderStrategy = null;
	private PartitionBuilderStrategy partitionBuilderStrategy = null;
	private FactEvaluatorBuilderStrategy factEvaluatorBuilderStrategy = null;
	private RepresentationBuilderStrategy representationBuilderStrategy = null;
	private SimilarityMetricsBuilderStrategy similarityMetricsBuilderStrategy = null;
	private ProblemTransitionRankerStrategy problemTransitionRankerStrategy = null;
	private ProblemTransitionBuilderStrategy problemTransitionBuilderStrategy = null;
	private ProblemSpaceExplorerStrategy problemSpaceExplorerStrategy = null;

	private GeneratorsAbstractFactory() {
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

	public DifferentiaeBuilder getDifferentiaeBuilder() {
		return DifferentiaeBuilderFactory.INSTANCE.apply(differentiaeBuilderStrategy);
	}

	public FactEvaluatorBuilder getFactEvaluatorBuilder() {
		return FactEvaluatorBuilderFactory.INSTANCE.apply(factEvaluatorBuilderStrategy);
	}
	
	public SimilarityMetricsBuilder getSimilarityMetricsBuilder() {
		return SimilarityMetricsBuilderFactory.INSTANCE.apply(similarityMetricsBuilderStrategy);
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

	public ProblemTransitionBuilder getProblemTransitionBuilder() {
		return ProblemTransitionBuilderFactory.INSTANCE.apply(problemTransitionBuilderStrategy);
	}

	public ProductionBuilder getProdBuilderFromConceptLattice() {
		return ProductionBuilderFactory.INSTANCE.apply(productionBuilderStrategy);
	}

	public ProdBuilderFromDenotations getProdBuilderFromDenotations() {
		return ProdBldrFromDenotationsFactory.INSTANCE.apply(prodBuilderFromDenotationsStrategy);
	}

	public PropertyBuilder getPropertyBuilder() {
		return PropertyBuilderFactory.INSTANCE.apply(propertyBuilderStrategy);
	}

	public RepresentationTransFuncBuilder getRepresentationTransFuncBuilder() {
		return RepresentationTransFuncBuilderFactory.INSTANCE.apply(representationTransFuncBuilderStrategy);
	}
	
	public RepresentationBuilder getRepresentationBuilder() {
		return RepresentationBuilderFactory.INSTANCE.apply(representationBuilderStrategy);
	}

	public TransitionSalienceSetter getTransitionSalienceSetter() {
		return TransitionSalienceSetterFactory.INSTANCE.apply(transitionSalienceSetterStrategy);
	}
	
	public ProblemTransitionRanker getProblemTransitionRanker() {
		return ProblemTransitionRankerFactory.INSTANCE.apply(problemTransitionRankerStrategy);
	}

	public void setUpStrategy(GenerationStrategy overallStrategy) {
		switch (overallStrategy) {
		case GENERATION_STRATEGY_1:
			denotationBuilderStrategy = DenotationBuilderStrategy.MAX_SYMBOL_SUBSEQUENCES;
			conceptLatticeBuilderStrategy = ConceptLatticeBuilderStrategy.GALOIS_CONNECTION;
			conceptTreeGrowerStrategy = ConceptTreeGrowerStrategy.IF_LEAF_IS_UNIVERSAL_THEN_SORT;
			prodBuilderFromDenotationsStrategy = ProdBuilderFromDenotationsStrategy.MAP_TARGET_VARS_TO_SOURCE_VALUES;
			productionBuilderStrategy = ProductionBuilderStrategy.IF_SUBORDINATE_THEN_BUILD_PRODUCTIONS;
			transitionSalienceSetterStrategy = TransitionSalienceSetterStrategy.HIDDEN_BY_DEFAULT_THEN_FIND_SPECIFICS;
			representationTransFuncBuilderStrategy = RepresentationTransFuncBuilderStrategy.BUILD_EXHAUSTIVELY;
			propertyBuilderStrategy = PropertyBuilderStrategy.GROUP_APPLICATIONS_BY_FUNCTION;
			differentiaeBuilderStrategy = DifferentiaeBuilderStrategy.IF_IS_A_THEN_DIFFER;
			descriptionBuilderStrategy = DescriptionBuilderStrategy.BUILD_TREE_THEN_CALCULATE_METRICS;
			partitionGraphBuilderStrategy = PartitionGraphBuilderStrategy.RECURSIVE_FORK_EXPLORATION;
			partitionBuilderStrategy = PartitionBuilderStrategy.BUILD_GRAPH_FIRST;
			factEvaluatorBuilderStrategy = FactEvaluatorBuilderStrategy.SALIENCE_AWARE;
			representationBuilderStrategy = RepresentationBuilderStrategy.FIRST_BUILD_TRANSITION_FUNC;
			similarityMetricsBuilderStrategy = SimilarityMetricsBuilderStrategy.MOST_SPECIFIC_CONCEPT;
			problemTransitionBuilderStrategy = ProblemTransitionBuilderStrategy.USE_PARTIAL_ORDER;
			problemTransitionRankerStrategy = ProblemTransitionRankerStrategy.TOP_DOWN_RANKER;
			problemSpaceExplorerStrategy = ProblemSpaceExplorerStrategy.EXPAND_TRIVIAL_LEAVES;
			break;
		default:
			break;
		}
	}

}
