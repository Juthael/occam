package com.tregouet.occam.alg.builders.pb_space.representations.impl;

import java.util.Set;

import com.tregouet.occam.alg.builders.pb_space.representations.RepresentationBuilder;
import com.tregouet.occam.data.problem_spaces.partitions.IPartition;
import com.tregouet.occam.data.representations.IRepresentation;
import com.tregouet.occam.data.representations.concepts.IConcept;
import com.tregouet.occam.data.representations.concepts.IIsA;
import com.tregouet.occam.data.representations.descriptions.IDescription;
import com.tregouet.occam.data.representations.evaluation.IFactEvaluator;
import com.tregouet.occam.data.representations.impl.Representation;
import com.tregouet.occam.data.representations.transitions.IRepresentationTransitionFunction;
import com.tregouet.occam.data.representations.transitions.productions.IContextualizedProduction;
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
