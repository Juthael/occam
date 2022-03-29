package com.tregouet.occam.alg.builders.representations.descriptions.differentiae.properties.impl;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import com.tregouet.occam.alg.builders.representations.descriptions.differentiae.properties.PropertyBuilder;
import com.tregouet.occam.data.representations.concepts.IDenotation;
import com.tregouet.occam.data.representations.properties.IProperty;
import com.tregouet.occam.data.representations.properties.impl.Property;
import com.tregouet.occam.data.representations.properties.transitions.IApplication;
import com.tregouet.occam.data.representations.properties.transitions.IRepresentationTransitionFunction;
import com.tregouet.occam.data.representations.properties.transitions.Salience;

public class GroupSalientApplicationsByFunction implements PropertyBuilder {

	private List<IDenotation> functions;
	private List<Set<IApplication>> applicationSets;
	private List<Set<IDenotation>> valueSets;
	
	public GroupSalientApplicationsByFunction() {
	}
	
	@Override
	public Set<IProperty> apply(IRepresentationTransitionFunction transFunction) {
		init();
		for (IApplication application : transFunction.getSalientApplications()) {
			Salience salience = application.getSalience();
			if (salience == Salience.COMMON_FEATURE || salience == Salience.TRANSITION_RULE) {
				IDenotation function = application.getInputConfiguration().getInputSymbol().getTarget();
				int functionIdx = functions.indexOf(function);
				if (functionIdx == -1) {
					functions.add(function);
					applicationSets.add(new HashSet<>(Arrays.asList(new IApplication[] {application})));
					valueSets.add(new HashSet<>(Arrays.asList(new IDenotation[] {
							application.getInputConfiguration().getInputSymbol().getSource()})));
				}
				else {
					applicationSets.get(functionIdx).add(application);
					valueSets.get(functionIdx).add(application.getInputConfiguration().getInputSymbol().getSource());
				}
			}
		}
		return output();
	}

	private Set<IProperty> output() {
		Set<IProperty> properties = new HashSet<>();
		for (int i = 0 ; i < functions.size() ; i++) {
			properties.add(new Property(functions.get(i), applicationSets.get(i), valueSets.get(i)));
		}
		for (IProperty property : properties)
			PropertyBuilder.propertyWeigher().accept(property);
		return properties;
	}
	
	private void init() {
		functions = new ArrayList<>();
		applicationSets = new ArrayList<>();
		valueSets = new ArrayList<>();
	}

}
