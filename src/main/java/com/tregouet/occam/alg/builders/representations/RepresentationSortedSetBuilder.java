package com.tregouet.occam.alg.builders.representations;

import java.util.Set;
import java.util.function.Function;

import com.tregouet.occam.alg.builders.GeneratorsAbstractFactory;
import com.tregouet.occam.alg.builders.representations.concept_lattices.ConceptLatticeBuilder;
import com.tregouet.occam.alg.builders.representations.concept_trees.ConceptTreeBuilder;
import com.tregouet.occam.alg.builders.representations.descriptions.DescriptionBuilder;
import com.tregouet.occam.alg.builders.representations.fact_evaluators.FactEvaluatorBuilder;
import com.tregouet.occam.alg.builders.representations.partitions.PartitionBuilder;
import com.tregouet.occam.alg.builders.representations.productions.ProductionBuilder;
import com.tregouet.occam.alg.builders.representations.string_scheme.StringSchemeBuilder;
import com.tregouet.occam.alg.builders.representations.transition_functions.RepresentationTransFuncBuilder;
import com.tregouet.occam.alg.scorers.ScorersAbstractFactory;
import com.tregouet.occam.alg.scorers.representations.RepresentationScorer;
import com.tregouet.occam.data.representations.ICompleteRepresentations;
import com.tregouet.occam.data.representations.concepts.IContextObject;

public interface RepresentationSortedSetBuilder
	extends Function<Set<IContextObject>, ICompleteRepresentations> {
	
	public static ConceptLatticeBuilder getConceptLatticeBuilder() {
		return GeneratorsAbstractFactory.INSTANCE.getConceptLatticeBuilder();
	}
	
	public static ConceptTreeBuilder getConceptTreeBuilder() {
		return GeneratorsAbstractFactory.INSTANCE.getConceptTreeBuilder();
	}
	
	public static ProductionBuilder getProductionBuilder() {
		return GeneratorsAbstractFactory.INSTANCE.getProdBuilderFromConceptLattice();
	}
	
	public static RepresentationTransFuncBuilder getTransFuncBuilder() {
		return GeneratorsAbstractFactory.INSTANCE.getRepresentationTransFuncBuilder();
	}
	
	public static DescriptionBuilder getDescriptionBuilder() {
		return GeneratorsAbstractFactory.INSTANCE.getDescriptionBuilder();
	}
	
	public static PartitionBuilder getPartitionBuilder() {
		return GeneratorsAbstractFactory.INSTANCE.getPartitionBuilder();
	}
	
	public static RepresentationScorer getRepresentationHeuristicScorer() {
		return ScorersAbstractFactory.INSTANCE.getRepresentationScorer();
	}
	
	public static FactEvaluatorBuilder getFactEvaluatorBuilder() {
		return GeneratorsAbstractFactory.INSTANCE.getFactEvaluatorBuilder();
	}
	
	public static StringSchemeBuilder getStringSchemeBuilder() {
		return GeneratorsAbstractFactory.INSTANCE.getStringSchemeBuilder();
	}
	
	RepresentationSortedSetBuilder setMaxSize(Integer maxSize);

}
