package com.tregouet.occam.alg.builders.pb_space.representations.impl;

import java.util.Set;

import com.tregouet.occam.alg.builders.pb_space.representations.RepresentationBuilder;
import com.tregouet.occam.data.problem_space.states.IRepresentation;
import com.tregouet.occam.data.problem_space.states.classifications.IClassification;
import com.tregouet.occam.data.problem_space.states.descriptions.IDescription;
import com.tregouet.occam.data.problem_space.states.evaluation.IFactEvaluator;
import com.tregouet.occam.data.problem_space.states.impl.Representation;
import com.tregouet.occam.data.problem_space.states.transitions.IRepresentationTransitionFunction;
import com.tregouet.occam.data.problem_space.states.transitions.productions.IContextualizedProduction;
import com.tregouet.occam.data.problem_space.transitions.partitions.IPartition;

public class FirstBuildTransitionFunction implements RepresentationBuilder {
	
	private Set<IContextualizedProduction> productions;
	
	public FirstBuildTransitionFunction() {
	}

	@Override
	public IRepresentation apply(IClassification classification) {
		IRepresentationTransitionFunction transFunc = RepresentationBuilder.getTransFuncBuilder()
				.apply(classification, productions);
		IFactEvaluator factEvaluator = RepresentationBuilder.getFactEvaluatorBuilder().apply(transFunc);
		IDescription description = RepresentationBuilder.getDescriptionBuilder().apply(transFunc, classification);
		Set<IPartition> partitions = RepresentationBuilder.getPartitionBuilder().apply(description, classification);
		IRepresentation representation = new Representation(classification, description, factEvaluator, partitions);
		return representation;
	}

	@Override
	public FirstBuildTransitionFunction setUp(Set<IContextualizedProduction> productions) {
		this.productions = productions;
		return this;
	}	

}
