package com.tregouet.occam.data.problem_space.states.descriptions.differentiae.properties.applications.impl;

import java.util.List;
import java.util.Objects;

import com.tregouet.occam.data.logical_structures.lambda_terms.IBindings;
import com.tregouet.occam.data.problem_space.states.classifications.concepts.denotations.IDenotation;
import com.tregouet.occam.data.problem_space.states.descriptions.differentiae.properties.applications.IApplication;
import com.tregouet.occam.data.problem_space.states.productions.IProduction;

public class Application implements IApplication {
	
	private final IDenotation construct;
	private final IBindings boundVariables;
	private final List<IProduction> arguments;
	private final IDenotation value;
	private Double weight = null;
	
	public Application(IDenotation construct, IBindings boundVariables, List<IProduction> arguments, IDenotation value) {
		this.construct = construct;
		this.boundVariables = boundVariables;
		this.arguments = arguments;
		this.value = value;
	}

	@Override
	public List<IProduction> getArguments() {
		return arguments;
	}

	@Override
	public IDenotation getValue() {
		return value;
	}

	@Override
	public Double weight() {
		return weight;
	}

	@Override
	public void setWeight(double weight) {
		this.weight = weight;
	}

	@Override
	public int hashCode() {
		return Objects.hash(arguments, construct, value);
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		Application other = (Application) obj;
		return Objects.equals(arguments, other.arguments) && Objects.equals(construct, other.construct)
				&& Objects.equals(value, other.value);
	}

	@Override
	public boolean isEpsilon() {
		return false;
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
	public IDenotation getConstruct() {
		return construct;
	}

	@Override
	public IBindings getBindings() {
		return boundVariables;
	}

}
