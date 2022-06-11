package com.tregouet.occam.data.problem_space.states.descriptions.differentiae.properties.impl;

import java.util.Objects;
import java.util.Set;

import com.tregouet.occam.data.problem_space.states.classifications.concepts.denotations.IDenotation;
import com.tregouet.occam.data.problem_space.states.descriptions.differentiae.properties.IProperty;
import com.tregouet.occam.data.problem_space.states.descriptions.differentiae.properties.applications.IApplication;

public class Property implements IProperty {

	private final int genusID;
	private final int speciesID;
	private final IDenotation function;
	private final Set<IApplication> applications;
	private Double weight = null;

	public Property(int genusID, int speciesID, IDenotation function, Set<IApplication> applications) {
		this.genusID = genusID;
		this.speciesID = speciesID;
		this.function = function;
		this.applications = applications;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if ((obj == null) || (getClass() != obj.getClass()))
			return false;
		Property other = (Property) obj;
		return genusID == other.genusID && speciesID == other.speciesID &&
				Objects.equals(applications, other.applications) && Objects.equals(function, other.function);
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
	public int getGenusID() {
		return genusID;
	}

	@Override
	public int getSpeciesID() {
		return speciesID;
	}

	@Override
	public int hashCode() {
		return Objects.hash(applications, function, genusID, speciesID);
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
