package com.tregouet.occam.alg.builders.representations.descriptions.differentiae.impl;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.ListIterator;
import java.util.Map;
import java.util.Set;

import com.tregouet.occam.alg.builders.representations.descriptions.differentiae.DifferentiaeBuilder;
import com.tregouet.occam.data.representations.concepts.IConcept;
import com.tregouet.occam.data.representations.descriptions.properties.AbstractDifferentiae;
import com.tregouet.occam.data.representations.descriptions.properties.IProperty;
import com.tregouet.occam.data.representations.descriptions.properties.impl.Differentiae;
import com.tregouet.occam.data.representations.transitions.IConceptTransition;
import com.tregouet.occam.data.representations.transitions.IRepresentationTransitionFunction;

import it.unimi.dsi.fastutil.ints.IntIntImmutablePair;
import it.unimi.dsi.fastutil.ints.IntIntPair;

public class IfIsAThenDiffer implements DifferentiaeBuilder {

	Map<IntIntPair, Set<IProperty>> transitionToProperties;
	
	public IfIsAThenDiffer() {
	}

	@Override
	public Set<AbstractDifferentiae> apply(IRepresentationTransitionFunction transFunc) {
		init();
		Set<AbstractDifferentiae> differentiae = new HashSet<>();
		Set<IntIntPair> sourceToTargetIDs = new HashSet<>();
		for (IConceptTransition transition : transFunc.getTransitions()) {
			int inputStateID = transition.getInputConfiguration().getInputStateID();
			if (inputStateID != IConcept.WHAT_IS_THERE_ID) {
				sourceToTargetIDs.add(new IntIntImmutablePair(inputStateID,
						transition.getOutputInternConfiguration().getOutputStateID()));
			}
		}
		List<IProperty> properties = new ArrayList<>(DifferentiaeBuilder.propertyBuilder().apply(transFunc));
		for (IntIntPair sourceToTargetID : sourceToTargetIDs) {
			Set<IProperty> thisDiffProperties = new HashSet<>();
			ListIterator<IProperty> propIte = properties.listIterator();
			while (propIte.hasNext()) {
				IProperty property = propIte.next();
				if (property.getGenusID() == sourceToTargetID.firstInt() 
						&& property.getSpeciesID() == sourceToTargetID.secondInt()) {
					thisDiffProperties.add(property);
					propIte.remove();
				}
			}
			differentiae.add(
					new Differentiae(
							sourceToTargetID.firstInt(), sourceToTargetID.secondInt(), thisDiffProperties));
		}
		return differentiae;
	}

	private void init() {
		transitionToProperties = new HashMap<>();
	}

}
