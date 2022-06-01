package com.tregouet.occam.alg.builders.pb_space.representations.descriptions.differentiae.properties.util;

import java.util.HashSet;
import java.util.Set;

import com.tregouet.occam.data.problem_space.states.classifications.concepts.denotations.IDenotation;
import com.tregouet.occam.data.problem_space.states.descriptions.properties.IProperty;
import com.tregouet.occam.data.problem_space.states.descriptions.properties.impl.Property;
import com.tregouet.occam.data.problem_space.states.transitions.IApplication;

public class AppCluster {
	
	private final int genusID;
	private final int speciesID;
	private IDenotation function;
	private Set<IApplication> applications = new HashSet<>();
	private Set<IDenotation> values = new HashSet<>();
	
	public AppCluster(IApplication application) {
		genusID = application.getInputConfiguration().getInputStateID();
		speciesID = application.getOutputInternConfiguration().getOutputStateID();
		function = application.getInputConfiguration().getInputSymbol().getTarget();
		applications.add(application);
		values.add(application.getInputConfiguration().getInputSymbol().getSource());
	}
	
	public boolean add(IApplication other) {
		if (other.getInputConfiguration().getInputStateID() == genusID
				&& other.getOutputInternConfiguration().getOutputStateID() == speciesID
				&& other.getInputConfiguration().getInputSymbol().getTarget().equals(function)) {
			applications.add(other);
			values.add(other.getInputConfiguration().getInputSymbol().getSource());
			return true;
		}
		return false;
	}
	
	public IProperty asProperty() {
		return new Property(genusID, speciesID, function, applications, values);
	}

}
