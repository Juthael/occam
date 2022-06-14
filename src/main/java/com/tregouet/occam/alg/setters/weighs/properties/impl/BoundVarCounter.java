package com.tregouet.occam.alg.setters.weighs.properties.impl;

import java.util.HashSet;
import java.util.Set;

import com.tregouet.occam.alg.setters.weighs.properties.PropertyWeigher;
import com.tregouet.occam.data.logical_structures.languages.alphabets.AVariable;
import com.tregouet.occam.data.problem_space.states.descriptions.differentiae.properties.IProperty;
import com.tregouet.occam.data.problem_space.states.descriptions.differentiae.properties.computations.IComputation;
import com.tregouet.occam.data.problem_space.states.productions.IProduction;

public class BoundVarCounter implements PropertyWeigher {

	public static final BoundVarCounter INSTANCE = new BoundVarCounter();

	private BoundVarCounter() {
	}

	@Override
	public void accept(IProperty property) {
		Set<AVariable> boundVars = new HashSet<>();
		for (IComputation computation : property.getComputations()) {
			for (IProduction production : computation.getArguments()) {
				if (!production.isAlphaConversion()) {
					boundVars.add(production.getVariable());
				}
			}
		}
		property.setWeight(boundVars.size());
	}

}
