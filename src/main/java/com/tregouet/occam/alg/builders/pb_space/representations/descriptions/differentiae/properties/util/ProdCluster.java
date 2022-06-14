package com.tregouet.occam.alg.builders.pb_space.representations.descriptions.differentiae.properties.util;

import java.util.ArrayList;
import java.util.List;

import com.tregouet.occam.data.logical_structures.lambda_terms.impl.Bindings;
import com.tregouet.occam.data.logical_structures.languages.alphabets.AVariable;
import com.tregouet.occam.data.problem_space.states.classifications.concepts.denotations.IDenotation;
import com.tregouet.occam.data.problem_space.states.descriptions.differentiae.properties.computations.IComputation;
import com.tregouet.occam.data.problem_space.states.descriptions.differentiae.properties.computations.impl.Computation;
import com.tregouet.occam.data.problem_space.states.productions.IContextualizedProduction;
import com.tregouet.occam.data.problem_space.states.productions.IProduction;

public class ProdCluster {

	private IDenotation denotation;
	private List<IProduction> productions = new ArrayList<>();
	private IDenotation value;

	public ProdCluster(IContextualizedProduction production) {
		denotation = production.getTarget();
		productions.add(production.getUncontextualizedProduction());
		value = production.getSource();
	}

	public boolean add(IContextualizedProduction other, boolean unsafe) {
		if ((unsafe || other.getTarget().equals(denotation)) && other.getSource().equals(value)) {
			productions.add(other.getUncontextualizedProduction());
			return true;
		}
		return false;
	}

	public IComputation asComputation() {
		productions.sort((x, y) -> denotation.asList().indexOf(x.getVariable()) - denotation.asList().indexOf(y.getVariable()));
		List<AVariable> boundVariables = new ArrayList<>();
		for (IProduction production : productions)
			boundVariables.add(production.getVariable());
		return new Computation(denotation, new Bindings(boundVariables), productions, value);
	}

}
