package com.tregouet.occam.alg.builders.pb_space.representations.descriptions.differentiae.properties.util;

import java.util.Arrays;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import com.tregouet.occam.data.logical_structures.languages.alphabets.AVariable;
import com.tregouet.occam.data.problem_space.states.classifications.concepts.denotations.IDenotation;
import com.tregouet.occam.data.problem_space.states.descriptions.differentiae.properties.computations.IComputation;
import com.tregouet.occam.data.problem_space.states.descriptions.differentiae.properties.computations.abstr_app.impl.AbstractionApplication;
import com.tregouet.occam.data.problem_space.states.descriptions.differentiae.properties.computations.impl.Computation;
import com.tregouet.occam.data.problem_space.states.productions.IBasicProduction;
import com.tregouet.occam.data.problem_space.states.productions.IContextualizedProduction;
import com.tregouet.occam.data.problem_space.states.productions.impl.IdentityProduction;

public class ProdCluster {

	private IDenotation input;
	private Set<IBasicProduction> productions = new HashSet<>();
	private IDenotation output;

	public ProdCluster(IContextualizedProduction production) {
		input = production.getTarget();
		productions.add(production.getUncontextualizedProduction());
		output = production.getSource();
	}

	public boolean add(IContextualizedProduction other, boolean unsafe) {
		if ((unsafe || other.getTarget().equals(input)) && other.getSource().equals(output)) {
			productions.add(other.getUncontextualizedProduction());
			return true;
		}
		return false;
	}

	public IComputation asComputation() {
		List<AVariable> boundVariables = input.getVariables();
		IBasicProduction[] prodArray = new IBasicProduction[boundVariables.size()];
		for (IBasicProduction production : productions)
			prodArray[boundVariables.indexOf(production.getVariable())] = production;
		for (int i = 0 ; i < boundVariables.size() ; i++) {
			if (prodArray[i] == null)
				prodArray[i] = new IdentityProduction(boundVariables.get(i));
		}
		return new Computation(input, new AbstractionApplication(Arrays.asList(prodArray)), output);
	}

}
