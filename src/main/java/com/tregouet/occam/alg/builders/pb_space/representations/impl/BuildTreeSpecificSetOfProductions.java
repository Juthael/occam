package com.tregouet.occam.alg.builders.pb_space.representations.impl;

import java.util.Set;

import com.tregouet.occam.alg.builders.pb_space.representations.RepresentationBuilder;
import com.tregouet.occam.alg.builders.pb_space.representations.utils.ClassificationNormalizer;
import com.tregouet.occam.data.problem_space.states.IRepresentation;
import com.tregouet.occam.data.problem_space.states.classifications.IClassification;
import com.tregouet.occam.data.problem_space.states.descriptions.IDescription;
import com.tregouet.occam.data.problem_space.states.evaluation.IFactEvaluator;
import com.tregouet.occam.data.problem_space.states.evaluation.impl.FactEvaluator;
import com.tregouet.occam.data.problem_space.states.impl.Representation;
import com.tregouet.occam.data.problem_space.states.productions.IContextualizedProduction;
import com.tregouet.occam.data.problem_space.states.transitions.IRepresentationTransitionFunction;
import com.tregouet.occam.data.problem_space.transitions.partitions.IPartition;

public class BuildTreeSpecificSetOfProductions implements RepresentationBuilder {
	
	public static final BuildTreeSpecificSetOfProductions INSTANCE = new BuildTreeSpecificSetOfProductions();
	
	private BuildTreeSpecificSetOfProductions() {
	}

	@Override
	public IRepresentation apply(IClassification classification) {
		IClassification normalizedClass = ClassificationNormalizer.normalize(classification);
		Set<IContextualizedProduction> productions = RepresentationBuilder.productionSetBuilder().apply(classification);
		IDescription description = RepresentationBuilder.descriptionBuilder().apply(classification, productions);
		IRepresentationTransitionFunction transFunc = RepresentationBuilder.transFuncBuilder()
				.apply(normalizedClass, description);
		IFactEvaluator factEvaluator = new FactEvaluator();
		factEvaluator.set(transFunc);
		Set<IPartition> partitions = RepresentationBuilder.partitionBuilder().apply(description, classification);
		IRepresentation representation = new Representation(classification, description, factEvaluator, partitions);
		return representation;
	}

}
