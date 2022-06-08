package com.tregouet.occam.alg.builders.pb_space.representations.descriptions.differentiae.properties.util;

import java.util.HashSet;
import java.util.Set;

import com.tregouet.occam.data.problem_space.states.classifications.concepts.denotations.IDenotation;
import com.tregouet.occam.data.problem_space.states.descriptions.differentiae.properties.applications.IApplication;
import com.tregouet.occam.data.problem_space.states.descriptions.differentiae.properties.applications.impl.Application;
import com.tregouet.occam.data.problem_space.states.productions.IContextualizedProduction;

public class ProdCluster {
	
	private IDenotation function;
	private Set<IContextualizedProduction> productions = new HashSet<>();
	private IDenotation value;
	
	public ProdCluster(IContextualizedProduction production) {
		function = production.getTarget();
		productions.add(production);
		value = production.getSource();
	}
	
	public boolean add(IContextualizedProduction other, boolean unsafe) {
		if ((unsafe || other.getTarget().equals(function)) && other.getSource().equals(value)) {
			productions.add(other);
			return true;
		}
		return false;
	}
	
	public IApplication asApplication() {
		return new Application(function, productions, value);
	}

}
