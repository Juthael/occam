package com.tregouet.occam.alg.builders.representations.impl;

import java.util.Set;

import com.tregouet.occam.alg.builders.representations.RepresentationBuilder;
import com.tregouet.occam.data.modules.categorization.transitions.partitions.IPartition;
import com.tregouet.occam.data.structures.representations.IRepresentation;
import com.tregouet.occam.data.structures.representations.classifications.IClassification;
import com.tregouet.occam.data.structures.representations.descriptions.IDescription;
import com.tregouet.occam.data.structures.representations.evaluation.IFactEvaluator;
import com.tregouet.occam.data.structures.representations.evaluation.impl.FactEvaluator;
import com.tregouet.occam.data.structures.representations.impl.Representation;
import com.tregouet.occam.data.structures.representations.productions.IContextualizedProduction;
import com.tregouet.occam.data.structures.representations.transitions.IRepresentationTransitionFunction;

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
