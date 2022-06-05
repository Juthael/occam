package com.tregouet.occam.alg.builders.pb_space.representations.descriptions.differentiae.properties.util;

import java.util.HashSet;
import java.util.Set;

import com.tregouet.occam.data.problem_space.states.classifications.concepts.denotations.IDenotation;
import com.tregouet.occam.data.problem_space.states.descriptions.properties.IProperty;
import com.tregouet.occam.data.problem_space.states.descriptions.properties.impl.Property;
import com.tregouet.occam.data.problem_space.states.productions.IContextualizedProduction;

public class ProdCluster {
	
	private final int genusID;
	private final int speciesID;
	private IDenotation function;
	private Set<IContextualizedProduction> applications = new HashSet<>();
	private Set<IDenotation> values = new HashSet<>();
	
	public ProdCluster(IContextualizedProduction production, int genusID) {
		this.genusID = genusID;
		speciesID = production.getSubordinateID();
		function = production.getTarget();
		applications.add(production);
		values.add(production.getSource());
	}
	
	public boolean add(IContextualizedProduction other) {
		if (other.getSuperordinateID() == speciesID && other.getTarget().equals(function)) {
			applications.add(other);
			values.add(other.getSource());
			return true;
		}
		return false;
	}
	
	public IProperty asProperty() {
		return new Property(genusID, speciesID, function, applications, values);
	}

}
