package com.tregouet.occam.data.problem_space.states.descriptions.differentiae.properties.computations;

import com.tregouet.occam.alg.setters.weighs.Weighed;
import com.tregouet.occam.data.problem_space.states.classifications.concepts.denotations.IDenotation;
import com.tregouet.occam.data.problem_space.states.descriptions.differentiae.properties.computations.abstr_app.IAbstractionApplication;

public interface IComputation extends Weighed {

	@Override
	boolean equals(Object o);

	IDenotation getInput();

	IAbstractionApplication getOperator();

	IDenotation getOutput();

	@Override
	int hashCode();

	boolean isEpsilon();

	boolean isIdentity();

	boolean returnsInput();

	void setWeight(double weight);

}
