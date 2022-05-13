package com.tregouet.occam.alg.builders.representations.impl;

import java.util.Set;

import com.tregouet.occam.alg.builders.representations.IRepresentationBuilder;
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

public class FirstBuildTransitionFunction implements IRepresentationBuilder {
	
	public static final FirstBuildTransitionFunction INSTANCE = new FirstBuildTransitionFunction();
	
	private FirstBuildTransitionFunction() {
	}

	@Override
	public IRepresentation apply(InvertedTree<IConcept, IIsA> conceptTree, Set<IContextualizedProduction> productions) {
		IRepresentationTransitionFunction transFunc = IRepresentationBuilder.getTransFuncBuilder()
				.apply(conceptTree, productions);
		IFactEvaluator factEvaluator = IRepresentationBuilder.getFactEvaluatorBuilder().apply(transFunc);
		IDescription description = IRepresentationBuilder.getDescriptionBuilder().apply(transFunc, null);
		Set<IPartition> partitions = IRepresentationBuilder.getPartitionBuilder().apply(description, conceptTree);
		IRepresentation representation = new Representation(conceptTree, description, factEvaluator, partitions);
		representation.setScore(IRepresentationBuilder.getRepresentationHeuristicScorer().apply(representation));
		return representation;
	}

}
