package com.tregouet.occam.alg.builders.representations;

import java.util.Set;
import java.util.function.BiFunction;

import com.tregouet.occam.alg.builders.GeneratorsAbstractFactory;
import com.tregouet.occam.alg.builders.representations.descriptions.DescriptionBuilder;
import com.tregouet.occam.alg.builders.representations.fact_evaluators.FactEvaluatorBuilder;
import com.tregouet.occam.alg.builders.representations.partitions.PartitionBuilder;
import com.tregouet.occam.alg.builders.representations.transition_functions.RepresentationTransFuncBuilder;
import com.tregouet.occam.alg.scorers.ScorersAbstractFactory;
import com.tregouet.occam.alg.scorers.representations.RepresentationScorer;
import com.tregouet.occam.data.representations.IRepresentation;
import com.tregouet.occam.data.representations.concepts.IConcept;
import com.tregouet.occam.data.representations.concepts.IIsA;
import com.tregouet.occam.data.representations.transitions.productions.IContextualizedProduction;
import com.tregouet.tree_finder.data.InvertedTree;

public interface IRepresentationBuilder 
	extends BiFunction<
		InvertedTree<IConcept, IIsA>, 
		Set<IContextualizedProduction>, 
		IRepresentation> {
	
	public static RepresentationTransFuncBuilder getTransFuncBuilder() {
		return GeneratorsAbstractFactory.INSTANCE.getRepresentationTransFuncBuilder();
	}
	
	public static FactEvaluatorBuilder getFactEvaluatorBuilder() {
		return GeneratorsAbstractFactory.INSTANCE.getFactEvaluatorBuilder();
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

}
