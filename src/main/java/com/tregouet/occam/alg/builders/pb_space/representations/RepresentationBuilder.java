package com.tregouet.occam.alg.builders.pb_space.representations;

import java.util.Set;
import java.util.function.Function;

import com.tregouet.occam.alg.builders.GeneratorsAbstractFactory;
import com.tregouet.occam.alg.builders.pb_space.representations.descriptions.DescriptionBuilder;
import com.tregouet.occam.alg.builders.pb_space.representations.fact_evaluators.FactEvaluatorBuilder;
import com.tregouet.occam.alg.builders.pb_space.representations.partitions.PartitionBuilder;
import com.tregouet.occam.alg.builders.pb_space.representations.transition_functions.RepresentationTransFuncBuilder;
import com.tregouet.occam.data.representations.IRepresentation;
import com.tregouet.occam.data.representations.concepts.IConcept;
import com.tregouet.occam.data.representations.concepts.IIsA;
import com.tregouet.occam.data.representations.transitions.productions.IContextualizedProduction;
import com.tregouet.tree_finder.data.InvertedTree;

public interface RepresentationBuilder 
	extends Function<InvertedTree<IConcept, IIsA>, IRepresentation> {
	
	public RepresentationBuilder setUp(Set<IContextualizedProduction> productions);
	
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

}
