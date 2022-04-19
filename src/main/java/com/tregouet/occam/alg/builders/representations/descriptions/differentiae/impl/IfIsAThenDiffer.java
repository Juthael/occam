package com.tregouet.occam.alg.builders.representations.descriptions.differentiae.impl;

import java.util.HashMap;
import java.util.HashSet;
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

	@Override
	public Set<AbstractDifferentiae> apply(IRepresentationTransitionFunction transFunc) {
		init();
		populateMap(transFunc);
		return output();
	}

	private void init() {
		transitionToProperties = new HashMap<>();
	}

	private Set<AbstractDifferentiae> output() {
		Set<AbstractDifferentiae> differentiae = new HashSet<>();
		for (IntIntPair transition : transitionToProperties.keySet()) {
			differentiae.add(new Differentiae(transition.firstInt(), transition.secondInt(),
					transitionToProperties.get(transition)));
		}
		return differentiae;
	}

	private void populateMap(IRepresentationTransitionFunction transFunc) {
		Set<IntIntPair> sourceToTargetIDs = new HashSet<>();
		for (IConceptTransition transition : transFunc.getTransitions()) {
			int inputStateID = transition.getInputConfiguration().getInputStateID();
			if (inputStateID != IConcept.WHAT_IS_THERE_ID) {
				sourceToTargetIDs.add(new IntIntImmutablePair(inputStateID,
						transition.getOutputInternConfiguration().getOutputStateID()));
			}
		}
		for (IntIntPair sourceToTargetID : sourceToTargetIDs) {
			transitionToProperties.put(sourceToTargetID, new HashSet<>());
		}
		Set<IProperty> properties = DifferentiaeBuilder.propertyBuilder().apply(transFunc);
		for (IProperty property : properties) {
			IntIntPair transIDs = new IntIntImmutablePair(property.getFunction().getConceptID(),
					property.getResultingValues().iterator().next().getConceptID());
			transitionToProperties.get(transIDs).add(property);
		}
	}

}
