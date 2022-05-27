package com.tregouet.occam.alg.builders.pb_space.representations.productions.from_denotations.impl;

import java.util.HashSet;
import java.util.List;
import java.util.Set;

import com.tregouet.occam.alg.builders.pb_space.representations.productions.from_denotations.ProdBuilderFromDenotations;
import com.tregouet.occam.data.logical_structures.languages.alphabets.ISymbol;
import com.tregouet.occam.data.problem_space.states.concepts.IConcept;
import com.tregouet.occam.data.problem_space.states.concepts.denotations.IDenotation;

public class SourceConceptCannotHaveTargetDenotation extends MapTargetVarsToSourceValues
		implements ProdBuilderFromDenotations {
	
	private Set<List<ISymbol>> sourceRedundantConstructs;
	
	public SourceConceptCannotHaveTargetDenotation() {
	}
	
	protected boolean isValid(IDenotation source, IDenotation target) {
		if (!target.isRedundant() || sourceRedundantConstructs.isEmpty())
			return true;
		List<ISymbol> sourceConstruct = source.asList();
		List<ISymbol> targetConstruct = target.asList();
		if (sourceConstruct.equals(targetConstruct))
			return true;
		else return (!sourceRedundantConstructs.contains(targetConstruct));
	}

	@Override
	public ProdBuilderFromDenotations setUp(IConcept sourceConcept) {
		sourceRedundantConstructs = new HashSet<>();
		for (IDenotation redundantDenotation : sourceConcept.getRedundantDenotations())
			sourceRedundantConstructs.add(redundantDenotation.asList());
		return this;
	}	

}
