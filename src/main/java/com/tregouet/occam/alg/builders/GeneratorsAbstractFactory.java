package com.tregouet.occam.alg.builders;

import com.tregouet.occam.alg.builders.problem_spaces.partial_representations.PartialRepresentationLateSetter;
import com.tregouet.occam.alg.builders.problem_spaces.partial_representations.PartialRepresentationLateSetterFactory;
import com.tregouet.occam.alg.builders.problem_spaces.partial_representations.PartialRepresentationLateSetterStrategy;
import com.tregouet.occam.alg.builders.problem_spaces.transitions.CategorizationTransitionBuilder;
import com.tregouet.occam.alg.builders.problem_spaces.transitions.CategorizationTransitionBuilderFactory;
import com.tregouet.occam.alg.builders.problem_spaces.transitions.CategorizationTransitionBuilderStrategy;
import com.tregouet.occam.alg.builders.representations.RepresentationSortedSetBuilder;
import com.tregouet.occam.alg.builders.representations.RepresentationSortedSetBuilderFactory;
import com.tregouet.occam.alg.builders.representations.RepresentationSortedSetBuilderStrategy;
import com.tregouet.occam.alg.builders.representations.concept_lattices.ConceptLatticeBuilder;
import com.tregouet.occam.alg.builders.representations.concept_lattices.ConceptLatticeBuilderFactory;
import com.tregouet.occam.alg.builders.representations.concept_lattices.ConceptLatticeBuilderStrategy;
import com.tregouet.occam.alg.builders.representations.concept_lattices.denotations.DenotationBuilderFactory;
import com.tregouet.occam.alg.builders.representations.concept_lattices.denotations.DenotationBuilderStrategy;
import com.tregouet.occam.alg.builders.representations.concept_lattices.denotations.IDenotationBuilder;
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
import com.tregouet.occam.alg.builders.representations.descriptions.ranks.DifferentiaeRankSetter;
import com.tregouet.occam.alg.builders.representations.descriptions.ranks.DifferentiaeRankSetterFactory;
import com.tregouet.occam.alg.builders.representations.descriptions.ranks.DifferentiaeRankSetterStrategy;
import com.tregouet.occam.alg.builders.representations.partitions.PartitionBuilder;
import com.tregouet.occam.alg.builders.representations.partitions.PartitionBuilderFactory;
import com.tregouet.occam.alg.builders.representations.partitions.PartitionBuilderStrategy;
import com.tregouet.occam.alg.builders.representations.partitions.as_graphs.PartitionGraphBuilder;
import com.tregouet.occam.alg.builders.representations.partitions.as_graphs.PartitionGraphBuilderFactory;
import com.tregouet.occam.alg.builders.representations.partitions.as_graphs.PartitionGraphBuilderStrategy;
import com.tregouet.occam.alg.builders.representations.partitions.as_strings.PartitionStringBuilder;
import com.tregouet.occam.alg.builders.representations.partitions.as_strings.PartitionStringBuilderFactory;
import com.tregouet.occam.alg.builders.representations.partitions.as_strings.PartitionStringBuilderStrategy;
import com.tregouet.occam.alg.builders.representations.productions.ProductionBuilder;
import com.tregouet.occam.alg.builders.representations.productions.ProductionBuilderFactory;
import com.tregouet.occam.alg.builders.representations.productions.ProductionBuilderStrategy;
import com.tregouet.occam.alg.builders.representations.productions.from_denotations.ProdBldrFromDenotationsFactory;
import com.tregouet.occam.alg.builders.representations.productions.from_denotations.ProdBuilderFromDenotations;
import com.tregouet.occam.alg.builders.representations.productions.from_denotations.ProdBuilderFromDenotationsStrategy;
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
	private DifferentiaeRankSetterStrategy differentiaeRankSetterStrategy = null;
	private PartitionGraphBuilderStrategy partitionGraphBuilderStrategy = null;
	private PartitionStringBuilderStrategy partitionStringBuilderStrategy = null;
	private PartitionBuilderStrategy partitionBuilderStrategy = null;
	private RepresentationSortedSetBuilderStrategy representationSortedSetBuilderStrategy = null;
	private Integer representationSortedSetMaxSize = null;
	private PartialRepresentationLateSetterStrategy partialRepresentationLateSetterStrategy = null;
	private CategorizationTransitionBuilderStrategy categorizationTransitionBuilderStrategy = null;
	
	private GeneratorsAbstractFactory() {
	}
	
	public void setUpStrategy(GenerationStrategy overallStrategy) {
		switch(overallStrategy) {
			case GENERATION_STRATEGY_1 : 
				denotationBuilderStrategy = DenotationBuilderStrategy.MAX_SYMBOL_SUBSEQUENCES;
				conceptLatticeBuilderStrategy = ConceptLatticeBuilderStrategy.GALOIS_CONNECTION;
				conceptTreeBuilderStrategy = ConceptTreeBuilderStrategy.UNIDIMENSIONAL_SORTING;
				prodBuilderFromDenotationsStrategy = ProdBuilderFromDenotationsStrategy.MAP_TARGET_VARS_TO_SOURCE_VALUES;
				productionBuilderStrategy = ProductionBuilderStrategy.IF_SUBORDINATE_THEN_BUILD_PRODUCTIONS;
				transitionSalienceSetterStrategy = TransitionSalienceSetterStrategy.HIDDEN_BY_DEFAULT_THEN_FIND_SPECIFICS;
				representationTransFuncBuilderStrategy = RepresentationTransFuncBuilderStrategy.ABSTRACT_FACTS_ACCEPTED;
				propertyBuilderStrategy = PropertyBuilderStrategy.GROUP_APPLICATIONS_BY_FUNCTION;
				differentiaeBuilderStrategy = DifferentiaeBuilderStrategy.IF_IS_A_THEN_DIFFER;
				similarityMetricsBuilderStrategy = SimilarityMetricsBuilderStrategy.DEFERRED_MATRICES_INSTANTIATION;
				descriptionBuilderStrategy = DescriptionBuilderStrategy.BUILD_TREE_THEN_CALCULATE_METRICS;
				differentiaeRankSetterStrategy = DifferentiaeRankSetterStrategy.DEPTH_FIRST;
				partitionGraphBuilderStrategy = PartitionGraphBuilderStrategy.RECURSIVE_FORK_EXPLORATION;
				partitionStringBuilderStrategy = PartitionStringBuilderStrategy.RECURSIVE_FRAMING;
				partitionBuilderStrategy = PartitionBuilderStrategy.BUILD_GRAPH_FIRST;
				representationSortedSetBuilderStrategy = 
						RepresentationSortedSetBuilderStrategy.FIND_EVERY_CLASSIFICATION_FIRST;
				representationSortedSetMaxSize = 50;
				partialRepresentationLateSetterStrategy = PartialRepresentationLateSetterStrategy.INFER_NULL_MEMBERS;
				categorizationTransitionBuilderStrategy = CategorizationTransitionBuilderStrategy.USE_PARTIAL_ORDER;
				break;
			default : 
				break;
		}
	}
	
	public DifferentiaeBuilder getDifferentiaeBuilder() {
		return DifferentiaeBuilderFactory.INSTANCE.apply(differentiaeBuilderStrategy);
	}
	
	public TransitionSalienceSetter getTransitionSalienceSetter() {
		return TransitionSalienceSetterFactory.INSTANCE.apply(transitionSalienceSetterStrategy);
	}
	
	public ProductionBuilder getProdBuilderFromConceptLattice() {
		return ProductionBuilderFactory.INSTANCE.apply(productionBuilderStrategy);
	}
	
	public ProdBuilderFromDenotations getProdBuilderFromDenotations() {
		return ProdBldrFromDenotationsFactory.INSTANCE.apply(prodBuilderFromDenotationsStrategy);
	}
	
	public IDenotationBuilder getDenotationBuilder() {
		return DenotationBuilderFactory.INSTANCE.apply(denotationBuilderStrategy);
	}
	
	public ConceptLatticeBuilder getConceptLatticeBuilder() {
		return ConceptLatticeBuilderFactory.INSTANCE.apply(conceptLatticeBuilderStrategy);
	}
	
	public ConceptTreeBuilder getConceptTreeBuilder() {
		return ConceptTreeBuilderFactory.INSTANCE.apply(conceptTreeBuilderStrategy);
	}
	
	public RepresentationTransFuncBuilder getRepresentationTransFuncBuilder() {
		return RepresentationTransFuncBuilderFactory.INSTANCE.apply(representationTransFuncBuilderStrategy);
	}
	
	public PropertyBuilder getPropertyBuilder() {
		return PropertyBuilderFactory.INSTANCE.apply(propertyBuilderStrategy);
	}
	
	public SimilarityMetricsBuilder getSimilarityMetricsBuilder() {
		return SimilarityMetricsBuilderFactory.INSTANCE.apply(similarityMetricsBuilderStrategy);
	}
	
	public DescriptionBuilder getDescriptionBuilder() {
		return DescriptionBuilderFactory.INSTANCE.apply(descriptionBuilderStrategy);
	}
	
	public DifferentiaeRankSetter getDifferentiaeRankSetter() {
		return DifferentiaeRankSetterFactory.INSTANCE.apply(differentiaeRankSetterStrategy);
	}
	
	public PartitionGraphBuilder getPartitionGraphBuilder() {
		return PartitionGraphBuilderFactory.INSTANCE.apply(partitionGraphBuilderStrategy);
	}
	
	public PartitionStringBuilder getPartitionStringBuilder() {
		return PartitionStringBuilderFactory.INSTANCE.apply(partitionStringBuilderStrategy);
	}
	
	public PartitionBuilder getPartitionBuilder() {
		return PartitionBuilderFactory.INSTANCE.apply(partitionBuilderStrategy);
	}
	
	public RepresentationSortedSetBuilder getRepresentationLexOrderedSetBuilder() {
		return RepresentationSortedSetBuilderFactory.INSTANCE
				.apply(representationSortedSetBuilderStrategy)
				.setMaxSize(representationSortedSetMaxSize);
	}
	
	public CategorizationTransitionBuilder getCategorizationTransitionBuilder() {
		return CategorizationTransitionBuilderFactory.INSTANCE.apply(categorizationTransitionBuilderStrategy);
	}
	
	public PartialRepresentationLateSetter getPartialRepresentationLateSetter() {
		return PartialRepresentationLateSetterFactory.INSTANCE.apply(partialRepresentationLateSetterStrategy);
	}

}
