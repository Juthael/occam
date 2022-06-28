package com.tregouet.occam.alg.setters.weighs.differentiae.impl;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import com.google.common.collect.Sets;
import com.tregouet.occam.alg.setters.weighs.differentiae.DifferentiaeWeigher;
import com.tregouet.occam.data.logical_structures.languages.words.construct.IConstruct;
import com.tregouet.occam.data.problem_space.states.descriptions.differentiae.ADifferentiae;
import com.tregouet.occam.data.problem_space.states.descriptions.differentiae.properties.IProperty;
import com.tregouet.occam.data.problem_space.states.descriptions.differentiae.properties.computations.IComputation;

public class MaxNbOfNonRedundantProperties implements DifferentiaeWeigher {

	public static final MaxNbOfNonRedundantProperties INSTANCE = new MaxNbOfNonRedundantProperties();

	private MaxNbOfNonRedundantProperties() {
	}

	@Override
	public void accept(ADifferentiae differentiae) {
		List<IConstruct> denotations;
		Set<IConstruct> denotationSet = new HashSet<>();
		Set<IProperty> nonBlankProperties = new HashSet<>();
		for (IProperty property : differentiae.getProperties()) {
			if (!property.isBlank()) {
				nonBlankProperties.add(property);
				for (IComputation computation : property.getComputations()) {
					if (!computation.isIdentity())
						denotationSet.add(computation.getOutput().copy());
				}
			}
		}
		denotations = new ArrayList<>(denotationSet);
		int maxNbOfNonRedundantProperties = nonBlankProperties.size();
		boolean maxNbFound = false;
		while (!maxNbFound && maxNbOfNonRedundantProperties > 1) {
			int nbOfAntecedents = maxNbOfNonRedundantProperties - 1;
			if (surjectiveProp2DenotRelationCanBeBuiltWithNProperties(nonBlankProperties, denotations, nbOfAntecedents))
				maxNbOfNonRedundantProperties--;
			else maxNbFound = true;
		}
		differentiae.setCoeffFreeWeight(maxNbOfNonRedundantProperties);
	}

	private static boolean setOfAntecedentsIsSurjective(Set<IProperty> setOfAntecedents,
			List<IConstruct> images) {
		boolean[] hasAnAntecedent = new boolean[images.size()];
		//populate
		for (IProperty property : setOfAntecedents) {
			for (IComputation computation : property.getComputations()) {
				if (!computation.isIdentity()) {
					int imageIdx = images.indexOf(computation.getOutput().copy());
					hasAnAntecedent[imageIdx] = true;
				}
			}
		}
		//test if surjective
		for (boolean thisImage : hasAnAntecedent) {
			if (!thisImage)
				return false;
		}
		return true;
	}

	private static boolean surjectiveProp2DenotRelationCanBeBuiltWithNProperties(Set<IProperty> properties,
			List<IConstruct> denotations, int subsetSize) {
		for (Set<IProperty> subset : Sets.combinations(properties, subsetSize)) {
			if(setOfAntecedentsIsSurjective(subset, denotations))
				return true;
		}
		return false;
	}

}
