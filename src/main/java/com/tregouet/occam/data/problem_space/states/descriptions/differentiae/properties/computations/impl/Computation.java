package com.tregouet.occam.data.problem_space.states.descriptions.differentiae.properties.computations.impl;

import java.util.List;
import java.util.Objects;

import com.tregouet.occam.data.logical_structures.lambda_terms.IBindings;
import com.tregouet.occam.data.problem_space.states.classifications.concepts.denotations.IDenotation;
import com.tregouet.occam.data.problem_space.states.descriptions.differentiae.properties.computations.IComputation;
import com.tregouet.occam.data.problem_space.states.productions.IProduction;

public class Computation implements IComputation {

	private final IDenotation construct;
	private final IBindings boundVariables;
	private final List<IProduction> arguments;
	private final IDenotation value;
	private Double weight = null;

	public Computation(IDenotation construct, IBindings boundVariables, List<IProduction> arguments, IDenotation value) {
		this.construct = construct;
		this.boundVariables = boundVariables;
		this.arguments = arguments;
		this.value = value;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if ((obj == null) || (getClass() != obj.getClass()))
			return false;
		Computation other = (Computation) obj;
		return Objects.equals(arguments, other.arguments) && Objects.equals(construct, other.construct)
				&& Objects.equals(value, other.value);
	}

	@Override
	public List<IProduction> getArguments() {
		return arguments;
	}

	@Override
	public IBindings getBindings() {
		return boundVariables;
	}

	@Override
	public IDenotation getConstruct() {
		return construct;
	}

	@Override
	public IDenotation getValue() {
		return value;
	}

	@Override
	public int hashCode() {
		return Objects.hash(arguments, construct, value);
	}

	@Override
	public boolean isBlank() {
		for (IProduction argument : arguments) {
			if (!argument.isBlank())
				return false;
		}
		return true;
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

}
