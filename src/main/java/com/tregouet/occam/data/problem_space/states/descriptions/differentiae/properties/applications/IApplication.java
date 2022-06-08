package com.tregouet.occam.data.problem_space.states.descriptions.differentiae.properties.applications;

import java.util.Set;

import com.tregouet.occam.alg.setters.weighs.Weighed;
import com.tregouet.occam.data.problem_space.states.classifications.concepts.denotations.IDenotation;
import com.tregouet.occam.data.problem_space.states.productions.IContextualizedProduction;

public interface IApplication extends Weighed {
	
	IDenotation function();

	Set<IContextualizedProduction> arguments();

	IDenotation value();

	@Override
	int hashCode();

	@Override
	boolean equals(Object o);
	
	void setWeight(double weight);

}
