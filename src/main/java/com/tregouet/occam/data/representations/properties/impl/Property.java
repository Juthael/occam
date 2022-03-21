package com.tregouet.occam.data.representations.properties.impl;

import java.util.Set;

import com.tregouet.occam.alg.scorers.ScorersAbstractFactory;
import com.tregouet.occam.data.preconcepts.IDenotation;
import com.tregouet.occam.data.representations.properties.IProperty;
import com.tregouet.occam.data.representations.properties.transitions.IApplication;

public class Property implements IProperty {

	private final IDenotation function;
	private final Set<IApplication> applications;
	private final Set<IDenotation> resultingValues;
	private final Double weight; 
	
	public Property(IDenotation function, Set<IApplication> applications, 
			Set<IDenotation> resultingValues) {
		this.function = function;
		this.applications = applications;
		this.resultingValues = resultingValues;
		weight = ScorersAbstractFactory.INSTANCE.getPropertyWheigher().weigh(this);
	}
	
	@Override
	public IDenotation getFunction() {
		return function;
	}

	@Override
	public Set<IApplication> getApplications() {
		return applications;
	}

	@Override
	public Set<IDenotation> getResultingValues() {
		return resultingValues;
	}

	@Override
	public Double weight() {
		return weight;
	}

}
