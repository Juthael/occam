package com.tregouet.occam.alg.builders.pb_space.representations.impl;

import java.util.Set;

import com.tregouet.occam.alg.builders.pb_space.representations.RepresentationBuilder;
import com.tregouet.occam.data.modules.categorization.transitions.partitions.IPartition;
import com.tregouet.occam.data.representations.IRepresentation;
import com.tregouet.occam.data.representations.classifications.IClassification;
import com.tregouet.occam.data.representations.descriptions.IDescription;
import com.tregouet.occam.data.representations.evaluation.IFactEvaluator;
import com.tregouet.occam.data.representations.evaluation.impl.FactEvaluator;
import com.tregouet.occam.data.representations.impl.Representation;
import com.tregouet.occam.data.representations.productions.IContextualizedProduction;
import com.tregouet.occam.data.representations.transitions.IRepresentationTransitionFunction;

public class BuildTreeSpecificSetOfProductions implements RepresentationBuilder {

	public static final BuildTreeSpecificSetOfProductions INSTANCE = new BuildTreeSpecificSetOfProductions();

	private BuildTreeSpecificSetOfProductions() {
	}

	@Override
	public IRepresentation apply(IClassification classification) {
		Set<IContextualizedProduction> productions = RepresentationBuilder.productionSetBuilder().apply(classification.normalized());
		IDescription description = RepresentationBuilder.descriptionBuilder().apply(classification.normalized(), productions);
		IRepresentationTransitionFunction transFunc = RepresentationBuilder.transFuncBuilder()
				.apply(classification.normalized(), description);
		IFactEvaluator factEvaluator = new FactEvaluator();
		factEvaluator.set(transFunc);
		Set<IPartition> partitions = RepresentationBuilder.partitionBuilder().apply(description, classification.normalized());
		IRepresentation representation = new Representation(classification, description, factEvaluator, partitions);
		return representation;
	}

}
