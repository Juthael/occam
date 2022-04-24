package com.tregouet.occam.alg.builders.representations;

import java.util.Collection;
import java.util.function.Function;

import com.tregouet.occam.alg.builders.GeneratorsAbstractFactory;
import com.tregouet.occam.alg.builders.representations.concept_lattices.ConceptLatticeBuilder;
import com.tregouet.occam.alg.builders.representations.concept_trees.ConceptTreeBuilder;
import com.tregouet.occam.alg.builders.representations.descriptions.DescriptionBuilder;
import com.tregouet.occam.alg.builders.representations.fact_evaluators.FactEvaluatorBuilder;
import com.tregouet.occam.alg.builders.representations.partitions.PartitionBuilder;
import com.tregouet.occam.alg.builders.representations.productions.ProductionBuilder;
import com.tregouet.occam.alg.builders.representations.string_pattern.StringPatternBuilder;
import com.tregouet.occam.alg.builders.representations.transition_functions.RepresentationTransFuncBuilder;
import com.tregouet.occam.alg.displayers.formatters.FormattersAbstractFactory;
import com.tregouet.occam.alg.displayers.formatters.facts.FactDisplayer;
import com.tregouet.occam.alg.scorers.ScorersAbstractFactory;
import com.tregouet.occam.alg.scorers.representations.RepresentationScorer;
import com.tregouet.occam.data.representations.ICompleteRepresentations;
import com.tregouet.occam.data.representations.concepts.IContextObject;

public interface RepresentationSortedSetBuilder extends Function<Collection<IContextObject>, ICompleteRepresentations> {

	public static ConceptLatticeBuilder getConceptLatticeBuilder() {
		return GeneratorsAbstractFactory.INSTANCE.getConceptLatticeBuilder();
	}

	public static ConceptTreeBuilder getConceptTreeBuilder() {
		return GeneratorsAbstractFactory.INSTANCE.getConceptTreeBuilder();
	}

	public static DescriptionBuilder getDescriptionBuilder() {
		return GeneratorsAbstractFactory.INSTANCE.getDescriptionBuilder();
	}

	public static FactEvaluatorBuilder getFactEvaluatorBuilder() {
		return GeneratorsAbstractFactory.INSTANCE.getFactEvaluatorBuilder();
	}

	public static PartitionBuilder getPartitionBuilder() {
		return GeneratorsAbstractFactory.INSTANCE.getPartitionBuilder();
	}

	public static ProductionBuilder getProductionBuilder() {
		return GeneratorsAbstractFactory.INSTANCE.getProdBuilderFromConceptLattice();
	}

	public static RepresentationScorer getRepresentationHeuristicScorer() {
		return ScorersAbstractFactory.INSTANCE.getRepresentationScorer();
	}

	public static StringPatternBuilder getStringSchemeBuilder() {
		return GeneratorsAbstractFactory.INSTANCE.getStringPatternBuilder();
	}

	public static RepresentationTransFuncBuilder getTransFuncBuilder() {
		return GeneratorsAbstractFactory.INSTANCE.getRepresentationTransFuncBuilder();
	}
	
	public static FactDisplayer getFactDisplayer() {
		return FormattersAbstractFactory.INSTANCE.getFactDisplayer();
	}

	RepresentationSortedSetBuilder setMaxSize(Integer maxSize);

}
