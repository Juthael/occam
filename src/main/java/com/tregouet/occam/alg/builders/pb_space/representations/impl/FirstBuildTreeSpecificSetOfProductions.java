package com.tregouet.occam.alg.builders.pb_space.representations.impl;

import java.util.Set;

import com.tregouet.occam.alg.builders.pb_space.representations.RepresentationBuilder;
import com.tregouet.occam.data.problem_space.states.IRepresentation;
import com.tregouet.occam.data.problem_space.states.classifications.IClassification;
import com.tregouet.occam.data.problem_space.states.descriptions.IDescription;
import com.tregouet.occam.data.problem_space.states.evaluation.IFactEvaluator;
import com.tregouet.occam.data.problem_space.states.evaluation.impl.FactEvaluator;
import com.tregouet.occam.data.problem_space.states.impl.Representation;
import com.tregouet.occam.data.problem_space.states.productions.IClassificationProductions;
import com.tregouet.occam.data.problem_space.states.productions.IContextualizedProduction;
import com.tregouet.occam.data.problem_space.states.transitions.IRepresentationTransitionFunction;
import com.tregouet.occam.data.problem_space.transitions.partitions.IPartition;

public class FirstBuildTreeSpecificSetOfProductions implements RepresentationBuilder {
	
	private Set<IContextualizedProduction> productions;
	
	public FirstBuildTreeSpecificSetOfProductions() {
	}

	@Override
	public IRepresentation apply(IClassification classification) {
		IClassificationProductions classProd = 
				RepresentationBuilder.classificationProductionSetBuilder().apply(classification, productions);
		IDescription description = RepresentationBuilder.descriptionBuilder().apply(classification, classProd);
		IRepresentationTransitionFunction transFunc = RepresentationBuilder.transFuncBuilder()
				.apply(classification, classProd);
		IFactEvaluator factEvaluator = new FactEvaluator();
		factEvaluator.set(transFunc);
		Set<IPartition> partitions = RepresentationBuilder.partitionBuilder().apply(description, classification);
		IRepresentation representation = new Representation(classification, description, factEvaluator, partitions);
		return representation;
	}

	@Override
	public FirstBuildTreeSpecificSetOfProductions setUp(Set<IContextualizedProduction> productions) {
		this.productions = productions;
		return this;
	}	

}
