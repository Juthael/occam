package com.tregouet.occam.alg.builders.pb_space.representations;

import java.util.Set;
import java.util.function.Function;

import com.tregouet.occam.alg.builders.BuildersAbstractFactory;
import com.tregouet.occam.alg.builders.pb_space.representations.descriptions.DescriptionBuilder;
import com.tregouet.occam.alg.builders.pb_space.representations.fact_evaluators.FactEvaluatorBuilder;
import com.tregouet.occam.alg.builders.pb_space.representations.partitions.PartitionBuilder;
import com.tregouet.occam.alg.builders.pb_space.representations.transition_functions.RepresentationTransFuncBuilder;
import com.tregouet.occam.data.problem_space.states.IRepresentation;
import com.tregouet.occam.data.problem_space.states.classifications.IClassification;
import com.tregouet.occam.data.problem_space.states.transitions.productions.IContextualizedProduction;

public interface RepresentationBuilder 
	extends Function<IClassification, IRepresentation> {
	
	public RepresentationBuilder setUp(Set<IContextualizedProduction> productions);
	
	public static RepresentationTransFuncBuilder getTransFuncBuilder() {
		return BuildersAbstractFactory.INSTANCE.getRepresentationTransFuncBuilder();
	}
	
	public static FactEvaluatorBuilder getFactEvaluatorBuilder() {
		return BuildersAbstractFactory.INSTANCE.getFactEvaluatorBuilder();
	}	
	
	public static DescriptionBuilder getDescriptionBuilder() {
		return BuildersAbstractFactory.INSTANCE.getDescriptionBuilder();
	}	
	
	public static PartitionBuilder getPartitionBuilder() {
		return BuildersAbstractFactory.INSTANCE.getPartitionBuilder();
	}

}
