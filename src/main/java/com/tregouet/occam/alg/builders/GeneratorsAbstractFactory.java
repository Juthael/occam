package com.tregouet.occam.alg.builders;

import com.tregouet.occam.alg.builders.problem_spaces.ProblemSpaceBuilder;
import com.tregouet.occam.alg.builders.problem_spaces.ProblemSpaceBuilderFactory;
import com.tregouet.occam.alg.builders.problem_spaces.ProblemSpaceBuilderStrategy;
import com.tregouet.occam.alg.builders.problem_spaces.modifier.ProblemSpaceModifier;
import com.tregouet.occam.alg.builders.problem_spaces.modifier.ProblemSpaceModifierFactory;
import com.tregouet.occam.alg.builders.problem_spaces.modifier.ProblemSpaceModifierStrategy;
import com.tregouet.occam.alg.builders.problem_spaces.partial_representations.PartialRepresentationLateSetter;
import com.tregouet.occam.alg.builders.problem_spaces.partial_representations.PartialRepresentationLateSetterFactory;
import com.tregouet.occam.alg.builders.problem_spaces.partial_representations.PartialRepresentationLateSetterStrategy;
import com.tregouet.occam.alg.builders.problem_spaces.transitions.TransitionBuilder;
import com.tregouet.occam.alg.builders.problem_spaces.transitions.TransitionBuilderFactory;
import com.tregouet.occam.alg.builders.problem_spaces.transitions.TransitionBuilderStrategy;
import com.tregouet.occam.alg.builders.representations.RepresentationSortedSetBuilder;
import com.tregouet.occam.alg.builders.representations.RepresentationSortedSetBuilderFactory;
import com.tregouet.occam.alg.builders.representations.RepresentationSortedSetBuilderStrategy;
import com.tregouet.occam.alg.builders.representations.concept_lattices.ConceptLatticeBuilder;
import com.tregouet.occam.alg.builders.representations.concept_lattices.ConceptLatticeBuilderFactory;
import com.tregouet.occam.alg.builders.representations.concept_lattices.ConceptLatticeBuilderStrategy;
import com.tregouet.occam.alg.builders.representations.concept_lattices.denotations.DenotationBuilder;
import com.tregouet.occam.alg.builders.representations.concept_lattices.denotations.DenotationBuilderFactory;
import com.tregouet.occam.alg.builders.representations.concept_lattices.denotations.DenotationBuilderStrategy;
import com.tregouet.occam.alg.builders.representations.concept_trees.ConceptTreeBuilder;
import com.tregouet.occam.alg.builders.representations.concept_trees.ConceptTreeBuilderFactory;
import com.tregouet.occam.alg.builders.representations.concept_trees.ConceptTreeBuilderStrategy;
import com.tregouet.occam.alg.builders.representations.descriptions.DescriptionBuilder;
import com.tregouet.occam.alg.builders.representations.descriptions.DescriptionBuilderFactory;
import com.tregouet.occam.alg.builders.representations.descriptions.DescriptionBuilderStrategy;
import com.tregouet.occam.alg.builders.representations.descriptions.differentiae.DifferentiaeBuilder;
import com.tregouet.occam.alg.builders.representations.descriptions.differentiae.DifferentiaeBuilderFactory;
import com.tregouet.occam.alg.builders.representations.descriptions.differentiae.DifferentiaeBuilderStrategy;
import com.tregouet.occam.alg.builders.representations.descriptions.differentiae.properties.PropertyBuilder;
import com.tregouet.occam.alg.builders.representations.descriptions.differentiae.properties.PropertyBuilderFactory;
import com.tregouet.occam.alg.builders.representations.descriptions.differentiae.properties.PropertyBuilderStrategy;
import com.tregouet.occam.alg.builders.representations.descriptions.metrics.SimilarityMetricsBuilder;
import com.tregouet.occam.alg.builders.representations.descriptions.metrics.SimilarityMetricsBuilderFactory;
import com.tregouet.occam.alg.builders.representations.descriptions.metrics.SimilarityMetricsBuilderStrategy;
import com.tregouet.occam.alg.builders.representations.fact_evaluators.FactEvaluatorBuilder;
import com.tregouet.occam.alg.builders.representations.fact_evaluators.FactEvaluatorBuilderFactory;
import com.tregouet.occam.alg.builders.representations.fact_evaluators.FactEvaluatorBuilderStrategy;
import com.tregouet.occam.alg.builders.representations.partitions.PartitionBuilder;
import com.tregouet.occam.alg.builders.representations.partitions.PartitionBuilderFactory;
import com.tregouet.occam.alg.builders.representations.partitions.PartitionBuilderStrategy;
import com.tregouet.occam.alg.builders.representations.partitions.as_graphs.PartitionGraphBuilder;
import com.tregouet.occam.alg.builders.representations.partitions.as_graphs.PartitionGraphBuilderFactory;
import com.tregouet.occam.alg.builders.representations.partitions.as_graphs.PartitionGraphBuilderStrategy;
import com.tregouet.occam.alg.builders.representations.productions.ProductionBuilder;
import com.tregouet.occam.alg.builders.representations.productions.ProductionBuilderFactory;
import com.tregouet.occam.alg.builders.representations.productions.ProductionBuilderStrategy;
import com.tregouet.occam.alg.builders.representations.productions.from_denotations.ProdBldrFromDenotationsFactory;
import com.tregouet.occam.alg.builders.representations.productions.from_denotations.ProdBuilderFromDenotations;
import com.tregouet.occam.alg.builders.representations.productions.from_denotations.ProdBuilderFromDenotationsStrategy;
import com.tregouet.occam.alg.builders.representations.string_pattern.StringPatternBuilder;
import com.tregouet.occam.alg.builders.representations.string_pattern.StringPatternBuilderFactory;
import com.tregouet.occam.alg.builders.representations.string_pattern.StringPatternBuilderStrategy;
import com.tregouet.occam.alg.builders.representations.transition_functions.RepresentationTransFuncBuilder;
import com.tregouet.occam.alg.builders.representations.transition_functions.RepresentationTransFuncBuilderFactory;
import com.tregouet.occam.alg.builders.representations.transition_functions.RepresentationTransFuncBuilderStrategy;
import com.tregouet.occam.alg.builders.representations.transition_functions.transition_saliences.TransitionSalienceSetter;
import com.tregouet.occam.alg.builders.representations.transition_functions.transition_saliences.TransitionSalienceSetterFactory;
import com.tregouet.occam.alg.builders.representations.transition_functions.transition_saliences.TransitionSalienceSetterStrategy;

public class GeneratorsAbstractFactory {

	public static final GeneratorsAbstractFactory INSTANCE = new GeneratorsAbstractFactory();

	private DenotationBuilderStrategy denotationBuilderStrategy = null;
	private ConceptLatticeBuilderStrategy conceptLatticeBuilderStrategy = null;
	private ConceptTreeBuilderStrategy conceptTreeBuilderStrategy = null;
	private ProdBuilderFromDenotationsStrategy prodBuilderFromDenotationsStrategy = null;
	private ProductionBuilderStrategy productionBuilderStrategy = null;
	private TransitionSalienceSetterStrategy transitionSalienceSetterStrategy = null;
	private RepresentationTransFuncBuilderStrategy representationTransFuncBuilderStrategy = null;
	private PropertyBuilderStrategy propertyBuilderStrategy = null;
	private DifferentiaeBuilderStrategy differentiaeBuilderStrategy = null;
	private SimilarityMetricsBuilderStrategy similarityMetricsBuilderStrategy = null;
	private DescriptionBuilderStrategy descriptionBuilderStrategy = null;
	private PartitionGraphBuilderStrategy partitionGraphBuilderStrategy = null;
	private StringPatternBuilderStrategy stringPatternBuilderStrategy = null;
	private PartitionBuilderStrategy partitionBuilderStrategy = null;
	private FactEvaluatorBuilderStrategy factEvaluatorBuilderStrategy = null;
	private RepresentationSortedSetBuilderStrategy representationSortedSetBuilderStrategy = null;
	private Integer representationSortedSetMaxSize = null;
	private PartialRepresentationLateSetterStrategy partialRepresentationLateSetterStrategy = null;
	private TransitionBuilderStrategy transitionBuilderStrategy = null;
	private ProblemSpaceBuilderStrategy problemSpaceBuilderStrategy = null;
	private ProblemSpaceModifierStrategy problemSpaceModifierStrategy = null;

	private GeneratorsAbstractFactory() {
	}

	public ConceptLatticeBuilder getConceptLatticeBuilder() {
		return ConceptLatticeBuilderFactory.INSTANCE.apply(conceptLatticeBuilderStrategy);
	}

	public ConceptTreeBuilder getConceptTreeBuilder() {
		return ConceptTreeBuilderFactory.INSTANCE.apply(conceptTreeBuilderStrategy);
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

	public PartialRepresentationLateSetter getPartialRepresentationLateSetter() {
		return PartialRepresentationLateSetterFactory.INSTANCE.apply(partialRepresentationLateSetterStrategy);
	}

	public PartitionBuilder getPartitionBuilder() {
		return PartitionBuilderFactory.INSTANCE.apply(partitionBuilderStrategy);
	}

	public PartitionGraphBuilder getPartitionGraphBuilder() {
		return PartitionGraphBuilderFactory.INSTANCE.apply(partitionGraphBuilderStrategy);
	}

	public ProblemSpaceBuilder getProblemSpaceBuilder() {
		return ProblemSpaceBuilderFactory.INSTANCE.apply(problemSpaceBuilderStrategy);
	}

	public ProblemSpaceModifier getProblemSpaceModifier() {
		return ProblemSpaceModifierFactory.INSTANCE.apply(problemSpaceModifierStrategy);
	}

	public TransitionBuilder getProblemTransitionBuilder() {
		return TransitionBuilderFactory.INSTANCE.apply(transitionBuilderStrategy);
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

	public RepresentationSortedSetBuilder getRepresentationSortedSetBuilder() {
		return RepresentationSortedSetBuilderFactory.INSTANCE.apply(representationSortedSetBuilderStrategy)
				.setMaxSize(representationSortedSetMaxSize);
	}

	public RepresentationTransFuncBuilder getRepresentationTransFuncBuilder() {
		return RepresentationTransFuncBuilderFactory.INSTANCE.apply(representationTransFuncBuilderStrategy);
	}

	public SimilarityMetricsBuilder getSimilarityMetricsBuilder() {
		return SimilarityMetricsBuilderFactory.INSTANCE.apply(similarityMetricsBuilderStrategy);
	}

	public StringPatternBuilder getStringSchemeBuilder() {
		return StringPatternBuilderFactory.INSTANCE.apply(stringPatternBuilderStrategy);
	}

	public TransitionSalienceSetter getTransitionSalienceSetter() {
		return TransitionSalienceSetterFactory.INSTANCE.apply(transitionSalienceSetterStrategy);
	}

	public void setUpStrategy(GenerationStrategy overallStrategy) {
		switch (overallStrategy) {
		case GENERATION_STRATEGY_1:
			denotationBuilderStrategy = DenotationBuilderStrategy.MAX_SYMBOL_SUBSEQUENCES;
			conceptLatticeBuilderStrategy = ConceptLatticeBuilderStrategy.GALOIS_CONNECTION;
			conceptTreeBuilderStrategy = ConceptTreeBuilderStrategy.UNIDIMENSIONAL_SORTING;
			prodBuilderFromDenotationsStrategy = ProdBuilderFromDenotationsStrategy.MAP_TARGET_VARS_TO_SOURCE_VALUES;
			productionBuilderStrategy = ProductionBuilderStrategy.IF_SUBORDINATE_THEN_BUILD_PRODUCTIONS;
			transitionSalienceSetterStrategy = TransitionSalienceSetterStrategy.HIDDEN_BY_DEFAULT_THEN_FIND_SPECIFICS;
			representationTransFuncBuilderStrategy = RepresentationTransFuncBuilderStrategy.BUILD_EXHAUSTIVELY;
			propertyBuilderStrategy = PropertyBuilderStrategy.GROUP_APPLICATIONS_BY_FUNCTION;
			differentiaeBuilderStrategy = DifferentiaeBuilderStrategy.IF_IS_A_THEN_DIFFER;
			similarityMetricsBuilderStrategy = SimilarityMetricsBuilderStrategy.DEFERRED_MATRICES_INSTANTIATION;
			descriptionBuilderStrategy = DescriptionBuilderStrategy.BUILD_TREE_THEN_CALCULATE_METRICS;
			partitionGraphBuilderStrategy = PartitionGraphBuilderStrategy.RECURSIVE_FORK_EXPLORATION;
			stringPatternBuilderStrategy = StringPatternBuilderStrategy.RECURSIVE_FRAMING;
			partitionBuilderStrategy = PartitionBuilderStrategy.BUILD_GRAPH_FIRST;
			factEvaluatorBuilderStrategy = FactEvaluatorBuilderStrategy.SALIENCE_BLIND;
			representationSortedSetBuilderStrategy = RepresentationSortedSetBuilderStrategy.FIND_EVERY_CLASSIFICATION_FIRST;
			representationSortedSetMaxSize = 50;
			partialRepresentationLateSetterStrategy = PartialRepresentationLateSetterStrategy.INFER_NULL_MEMBERS;
			transitionBuilderStrategy = TransitionBuilderStrategy.USE_PARTIAL_ORDER;
			problemSpaceBuilderStrategy = ProblemSpaceBuilderStrategy.GALOIS_LATTICE_OF_REPRESENTATIONS;
			problemSpaceModifierStrategy = ProblemSpaceModifierStrategy.REBUILD_FROM_SCRATCH;
			break;
		default:
			break;
		}
	}

}
