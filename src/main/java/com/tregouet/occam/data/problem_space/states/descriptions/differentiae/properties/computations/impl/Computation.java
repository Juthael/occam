package com.tregouet.occam.data.problem_space.states.descriptions.differentiae.properties.computations.impl;

import com.tregouet.occam.data.problem_space.states.classifications.concepts.denotations.IDenotation;
import com.tregouet.occam.data.problem_space.states.descriptions.differentiae.properties.computations.IComputation;
import com.tregouet.occam.data.problem_space.states.descriptions.differentiae.properties.computations.applications.IAbstractionApplication;

public class Computation implements IComputation {

	private final IDenotation input;
	private final IAbstractionApplication operator;
	private final IDenotation output;
	private Double weight = null;

	public Computation(IDenotation input, IAbstractionApplication operator, IDenotation output) {
		this.input = input;
		this.operator = operator;
		this.output = output;
	}

	@Override
	public IDenotation getInput() {
		return input;
	}

	@Override
	public IDenotation getOutput() {
		return output;
	}

	@Override
	public boolean returnsInput() {
		return operator.isIdentityOperator();
	}

	@Override
	public boolean isEpsilon() {
		return false;
	}

	@Override
	public void setWeight(double weight) {
		this.weight = weight;
	}

	@Override
	public Double weight() {
		return weight;
	}

	@Override
	public IAbstractionApplication getOperator() {
		return operator;
	}

}
