package com.tregouet.occam.alg.builders.pb_space.representations.impl;

import java.util.Set;

import com.tregouet.occam.alg.builders.pb_space.representations.RepresentationBuilder;
import com.tregouet.occam.data.problem_space.states.IRepresentation;
import com.tregouet.occam.data.problem_space.states.concepts.IConcept;
import com.tregouet.occam.data.problem_space.states.concepts.IIsA;
import com.tregouet.occam.data.problem_space.states.descriptions.IDescription;
import com.tregouet.occam.data.problem_space.states.evaluation.IFactEvaluator;
import com.tregouet.occam.data.problem_space.states.impl.Representation;
import com.tregouet.occam.data.problem_space.states.transitions.IRepresentationTransitionFunction;
import com.tregouet.occam.data.problem_space.states.transitions.productions.IContextualizedProduction;
import com.tregouet.occam.data.problem_space.transitions.partitions.IPartition;
import com.tregouet.tree_finder.data.InvertedTree;

public class FirstBuildTransitionFunction implements RepresentationBuilder {
	
	private Set<IContextualizedProduction> productions;
	
	public FirstBuildTransitionFunction() {
	}

	@Override
	public IRepresentation apply(InvertedTree<IConcept, IIsA> conceptTree) {
		IRepresentationTransitionFunction transFunc = RepresentationBuilder.getTransFuncBuilder()
				.apply(conceptTree, productions);
		IFactEvaluator factEvaluator = RepresentationBuilder.getFactEvaluatorBuilder().apply(transFunc);
		IDescription description = RepresentationBuilder.getDescriptionBuilder().apply(transFunc, conceptTree);
		Set<IPartition> partitions = RepresentationBuilder.getPartitionBuilder().apply(description, conceptTree);
		IRepresentation representation = new Representation(conceptTree, description, factEvaluator, partitions);
		return representation;
	}

	@Override
	public FirstBuildTransitionFunction setUp(Set<IContextualizedProduction> productions) {
		this.productions = productions;
		return this;
	}	

}
