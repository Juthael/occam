package com.tregouet.occam.alg.builders.pb_space.representations.descriptions.differentiae.impl;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Set;

import com.tregouet.occam.alg.builders.pb_space.representations.descriptions.differentiae.DifferentiaeBuilder;
import com.tregouet.occam.data.problem_space.states.classifications.concepts.IConcept;
import com.tregouet.occam.data.problem_space.states.descriptions.properties.ADifferentiae;
import com.tregouet.occam.data.problem_space.states.descriptions.properties.IProperty;
import com.tregouet.occam.data.problem_space.states.descriptions.properties.impl.Differentiae;
import com.tregouet.occam.data.problem_space.states.transitions.IConceptTransition;
import com.tregouet.occam.data.problem_space.states.transitions.IRepresentationTransitionFunction;

import it.unimi.dsi.fastutil.ints.IntIntImmutablePair;
import it.unimi.dsi.fastutil.ints.IntIntPair;

public class IfIsAThenDiffer implements DifferentiaeBuilder {

	public IfIsAThenDiffer() {
	}

	@Override
	public Set<ADifferentiae> apply(IRepresentationTransitionFunction transFunc) {
		Set<ADifferentiae> differentiae = new HashSet<>();
		Set<IntIntPair> sourceToTargetIDs = new HashSet<>();
		//populate sourceToTargetIDs set
		for (IConceptTransition transition : transFunc.getTransitions()) {
			int inputStateID = transition.getInputConfiguration().getInputStateID();
			if (inputStateID != IConcept.WHAT_IS_THERE_ID) {
				sourceToTargetIDs.add(new IntIntImmutablePair(inputStateID,
						transition.getOutputInternConfiguration().getOutputStateID()));
			}
		}
		//populate property set
		Set<IProperty> properties = DifferentiaeBuilder.propertyBuilder().apply(transFunc);
		//for each (source,target) pair, build a differentia 
		for (IntIntPair sourceToTargetID : sourceToTargetIDs) {
			//populate this differentia subset of properties
			Set<IProperty> thisDiffProperties = new HashSet<>();
			Iterator<IProperty> propIte = properties.iterator();
			List<IProperty> toBeRemoved = new ArrayList<>();
			while (propIte.hasNext()) {
				IProperty property = propIte.next();
				if (property.getGenusID() == sourceToTargetID.firstInt() 
						&& property.getSpeciesID() == sourceToTargetID.secondInt()) {
					thisDiffProperties.add(property);
					toBeRemoved.add(property);
				}
			}
			differentiae.add(
					new Differentiae(sourceToTargetID.firstInt(), sourceToTargetID.secondInt(), thisDiffProperties));
			properties.removeAll(toBeRemoved);
		}
		return differentiae;
	}

}
