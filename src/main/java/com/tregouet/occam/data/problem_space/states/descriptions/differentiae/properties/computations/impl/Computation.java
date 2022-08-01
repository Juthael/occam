package com.tregouet.occam.data.problem_space.states.descriptions.differentiae.properties.computations.impl;

import java.util.Objects;

import com.tregouet.occam.data.problem_space.states.classifications.concepts.denotations.IDenotation;
import com.tregouet.occam.data.problem_space.states.descriptions.differentiae.properties.computations.IComputation;
import com.tregouet.occam.data.problem_space.states.descriptions.differentiae.properties.computations.abstr_app.IAbstractionApplication;

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
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if ((obj == null) || (getClass() != obj.getClass()))
			return false;
		Computation other = (Computation) obj;
		return Objects.equals(input, other.input) && Objects.equals(output, other.output) &&
				Objects.equals(operator, other.operator);
	}

	@Override
	public IDenotation getInput() {
		return input;
	}

	@Override
	public IAbstractionApplication getOperator() {
		return operator;
	}

	@Override
	public IDenotation getOutput() {
		return output;
	}

	@Override
	public int hashCode() {
		return Objects.hash(input, operator, output);
	}

	@Override
	public boolean isEpsilon() {
		return false;
	}

	@Override
	public boolean isIdentity() {
		return operator.isIdentityOperator();
	}

	@Override
	public boolean returnsInput() {
		return operator.isIdentityOperator();
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
	public boolean isMereLabelling() {
		return output.isArbitraryLabel();
	}

}
