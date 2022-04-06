package com.tregouet.occam.alg.builders.representations;

import java.util.Set;
import java.util.function.Function;

import com.tregouet.occam.alg.builders.GeneratorsAbstractFactory;
import com.tregouet.occam.alg.builders.representations.concept_lattices.ConceptLatticeBuilder;
import com.tregouet.occam.alg.builders.representations.concept_trees.ConceptTreeBuilder;
import com.tregouet.occam.alg.builders.representations.descriptions.DescriptionBuilder;
import com.tregouet.occam.alg.builders.representations.partitions.PartitionBuilder;
import com.tregouet.occam.alg.builders.representations.productions.ProductionBuilder;
import com.tregouet.occam.alg.builders.representations.transition_functions.RepresentationTransFuncBuilder;
import com.tregouet.occam.alg.scorers.ScorersAbstractFactory;
import com.tregouet.occam.alg.scorers.representations.RepresentationLexicographicScorer;
import com.tregouet.occam.data.representations.IRepresentations;
import com.tregouet.occam.data.representations.concepts.IContextObject;

public interface RepresentationSortedSetBuilder
	extends Function<Set<IContextObject>, IRepresentations> {
	
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
	
	public static RepresentationLexicographicScorer getRepresentationScorer() {
		return ScorersAbstractFactory.INSTANCE.getRepresentationLexicographicScorer();
	}

}
