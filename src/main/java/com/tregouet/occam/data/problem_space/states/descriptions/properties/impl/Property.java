package com.tregouet.occam.data.problem_space.states.descriptions.properties.impl;

import java.util.Objects;
import java.util.Set;

import com.tregouet.occam.data.problem_space.states.concepts.denotations.IDenotation;
import com.tregouet.occam.data.problem_space.states.descriptions.properties.IProperty;
import com.tregouet.occam.data.problem_space.states.transitions.IApplication;

public class Property implements IProperty {

	private final int genusID;
	private final int speciesID;
	private final IDenotation function;
	private final Set<IApplication> applications;
	private final Set<IDenotation> resultingValues;
	private Double weight = null;

	public Property(int genusID, int speciesID, IDenotation function, Set<IApplication> applications, 
			Set<IDenotation> resultingValues) {
		this.genusID = genusID;
		this.speciesID = speciesID;
		this.function = function;
		this.applications = applications;
		this.resultingValues = resultingValues;
	}

	@Override
	public Set<IApplication> getApplications() {
		return applications;
	}

	@Override
	public IDenotation getFunction() {
		return function;
	}

	@Override
	public Set<IDenotation> getResultingValues() {
		return resultingValues;
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
	public int getGenusID() {
		return genusID;
	}

	@Override
	public int getSpeciesID() {
		return speciesID;
	}

	@Override
	public int hashCode() {
		return Objects.hash(applications, function, genusID, resultingValues, speciesID);
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		Property other = (Property) obj;
		return Objects.equals(applications, other.applications) && Objects.equals(function, other.function)
				&& genusID == other.genusID && Objects.equals(resultingValues, other.resultingValues)
				&& speciesID == other.speciesID;
	}

}
