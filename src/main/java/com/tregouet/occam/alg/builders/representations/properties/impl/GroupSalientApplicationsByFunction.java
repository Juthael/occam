package com.tregouet.occam.alg.builders.representations.properties.impl;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import com.tregouet.occam.alg.builders.representations.properties.IPropertyBuilder;
import com.tregouet.occam.data.preconcepts.IDenotation;
import com.tregouet.occam.data.representations.properties.IProperty;
import com.tregouet.occam.data.representations.properties.impl.Property;
import com.tregouet.occam.data.representations.properties.transitions.IApplication;
import com.tregouet.occam.data.representations.properties.transitions.Salience;

public class GroupSalientApplicationsByFunction implements IPropertyBuilder {

	private final List<IDenotation> functions = new ArrayList<>();
	private final List<Set<IApplication>> applicationSets = new ArrayList<>();
	private final List<Set<IDenotation>> valueSets = new ArrayList<>();
	
	public GroupSalientApplicationsByFunction() {
	}
	
	@Override
	public void intput(Set<IApplication> applications) {
		for (IApplication application : applications) {
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
	}

	@Override
	public Set<IProperty> output() {
		Set<IProperty> properties = new HashSet<>();
		for (int i = 0 ; i < functions.size() ; i++) {
			properties.add(new Property(functions.get(i), applicationSets.get(i), valueSets.get(i)));
		}
		return properties;
	}

}
