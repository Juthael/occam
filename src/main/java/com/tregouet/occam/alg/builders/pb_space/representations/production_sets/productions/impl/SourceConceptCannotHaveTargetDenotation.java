package com.tregouet.occam.alg.builders.pb_space.representations.production_sets.productions.impl;

import java.util.HashSet;
import java.util.List;
import java.util.Set;

import com.tregouet.occam.alg.builders.pb_space.representations.production_sets.productions.ProductionBuilder;
import com.tregouet.occam.data.logical_structures.languages.alphabets.ISymbol;
import com.tregouet.occam.data.problem_space.states.classifications.concepts.IConcept;
import com.tregouet.occam.data.problem_space.states.classifications.concepts.denotations.IDenotation;

public class SourceConceptCannotHaveTargetDenotation extends MapTargetVarsToSourceValues
		implements ProductionBuilder {

	private Set<List<ISymbol>> sourceRedundantConstructs;

	public SourceConceptCannotHaveTargetDenotation() {
	}

	@Override
	public ProductionBuilder setUp(IConcept sourceConcept) {
		sourceRedundantConstructs = new HashSet<>();
		for (IDenotation redundantDenotation : sourceConcept.getRedundantDenotations())
			sourceRedundantConstructs.add(redundantDenotation.asList());
		return this;
	}

	@Override
	protected boolean isValid(IDenotation source, IDenotation target) {
		if (!target.isRedundant() || sourceRedundantConstructs.isEmpty())
			return true;
		List<ISymbol> sourceConstruct = source.asList();
		List<ISymbol> targetConstruct = target.asList();
		if (sourceConstruct.equals(targetConstruct))
			return true;
		else return (!sourceRedundantConstructs.contains(targetConstruct));
	}

}
