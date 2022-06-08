package com.tregouet.occam.data.problem_space.states.descriptions.differentiae.properties.applications.impl;

import java.util.Objects;
import java.util.Set;

import com.tregouet.occam.data.problem_space.states.classifications.concepts.denotations.IDenotation;
import com.tregouet.occam.data.problem_space.states.descriptions.differentiae.properties.applications.IApplication;
import com.tregouet.occam.data.problem_space.states.productions.IContextualizedProduction;

public class Application implements IApplication {
	
	private final IDenotation function;
	private final Set<IContextualizedProduction> arguments;
	private final IDenotation value;
	private Double weight = null;
	
	public Application(IDenotation function, Set<IContextualizedProduction> argument, IDenotation value) {
		this.function = function;
		this.arguments = argument;
		this.value = value;
	}

	@Override
	public IDenotation function() {
		return function;
	}

	@Override
	public Set<IContextualizedProduction> arguments() {
		return arguments;
	}

	@Override
	public IDenotation value() {
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
		return Objects.hash(arguments, function, value);
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
		return Objects.equals(arguments, other.arguments) && Objects.equals(function, other.function)
				&& Objects.equals(value, other.value);
	}

}
