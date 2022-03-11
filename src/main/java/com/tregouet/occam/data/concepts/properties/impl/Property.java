package com.tregouet.occam.data.concepts.properties.impl;

import java.util.List;

import com.tregouet.occam.data.concepts.properties.IProperty;
import com.tregouet.occam.data.concepts.transitions.IApplication;
import com.tregouet.occam.data.preconcepts.IDenotation;

public class Property implements IProperty {

	private final IDenotation function;
	private final List<IApplication> applications;
	private final List<IDenotation> resultingValues;
	private Double weight = null; 
	
	public Property(IDenotation function, List<IApplication> applications, List<IDenotation> resultingValues) {
		this.function = function;
		this.applications = applications;
		this.resultingValues = resultingValues;
	}
	
	@Override
	public IDenotation getFunction() {
		return function;
	}

	@Override
	public List<IApplication> getApplications() {
		return applications;
	}

	@Override
	public List<IDenotation> getResultingValues() {
		return resultingValues;
	}

	@Override
	public Double getWeight() {
		return weight;
	}

	@Override
	public void setWeight(double wheight) {
		this.weight = wheight;
	}

}
