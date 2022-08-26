package com.tregouet.occam.alg.displayers.formatters.differentiae.labeller.summarized.prop_opt_summarizer.impl;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Set;

import com.google.common.collect.Sets;
import com.tregouet.occam.alg.displayers.formatters.differentiae.DifferentiaeFormatter;
import com.tregouet.occam.alg.displayers.formatters.differentiae.labeller.summarized.prop_opt_summarizer.DifferentiaePropOptSummarizer;
import com.tregouet.occam.data.structures.representations.classifications.concepts.denotations.IDenotation;
import com.tregouet.occam.data.structures.representations.descriptions.differentiae.ADifferentiae;
import com.tregouet.occam.data.structures.representations.descriptions.differentiae.properties.IProperty;
import com.tregouet.occam.data.structures.representations.descriptions.differentiae.properties.computations.IComputation;

public class NonRedundantPropOptSubset implements DifferentiaePropOptSummarizer {

	public static final NonRedundantPropOptSubset INSTANCE = new NonRedundantPropOptSubset();

	private NonRedundantPropOptSubset() {
	}
	
	private static void addNewSubsetsAndRemoveRedundants(Set<Set<IProperty>> newSubsets, List<Set<IProperty>> previousSubsets) {
		for (Set<IProperty> newSubset : newSubsets) {
			Iterator<Set<IProperty>> prevSubsetsIte = previousSubsets.iterator();
			while (prevSubsetsIte.hasNext()) {
				Set<IProperty> previousSubset = prevSubsetsIte.next();
				if (previousSubset.containsAll(newSubset))
					prevSubsetsIte.remove();
			}
		}
		previousSubsets.addAll(newSubsets);
	}
	
	private static Set<Set<IProperty>> getSurjectiveSubsetsOfSpecifiedSize(Set<IProperty> properties,
			Set<IDenotation> requiredDenotations, int subsetSize) {
		Set<Set<IProperty>> subsets = new HashSet<>();
		for (Set<IProperty> subset : Sets.combinations(properties, subsetSize)) {
			if (isSurjective(subset, requiredDenotations))
				subsets.add(subset);
		}
		return subsets;
	}	
	
	private static boolean isSurjective(Set<IProperty> propSubset,
			Set<IDenotation> requiredDenotations) {
		Set<IDenotation> propSubsetDenotations = new HashSet<>();
		for (IProperty property : propSubset) {
			for (IComputation computation : property.getComputations()) {
				if (!computation.isIdentity() && !computation.getOutput().isRedundant()) {
					propSubsetDenotations.add(computation.getOutput());
				}
			}
		}
		return propSubsetDenotations.equals(requiredDenotations);
	}

	@Override
	public String apply(ADifferentiae diff) {
		Set<IProperty> nonBlankProperties = new HashSet<>();
		Set<IDenotation> requiredDenotations = new HashSet<>();
		for (IProperty property : diff.getProperties()) {
			if (!property.isBlank()) {
				nonBlankProperties.add(property);
				for (IComputation computation : property.getComputations()) {
					if (!computation.isIdentity() && !computation.getOutput().isRedundant())
						requiredDenotations.add(computation.getOutput());
				}
			}
		}
		if (requiredDenotations.isEmpty())
			return toString(new HashSet<>());
		int subsetSize = nonBlankProperties.size();
		List<Set<IProperty>> nonRedundantSurjectiveSubsets = new ArrayList<>();
		boolean newSurjectiveSubsetsFound = true;
		while (newSurjectiveSubsetsFound) {
			Set<Set<IProperty>> newSurjectiveSubsets =
					getSurjectiveSubsetsOfSpecifiedSize(nonBlankProperties, requiredDenotations, subsetSize);
			if (!newSurjectiveSubsets.isEmpty()) {
				addNewSubsetsAndRemoveRedundants(newSurjectiveSubsets, nonRedundantSurjectiveSubsets);
				subsetSize--;
			}
			else newSurjectiveSubsetsFound = false;
		}
		return toString(nonRedundantSurjectiveSubsets.get(0));
	}
	
	private static String toString(Set<IProperty> properties) {
		String nL = System.lineSeparator();
		Iterator<IProperty> propIte = properties.iterator();
		StringBuilder sBProp = new StringBuilder();
		int nbOfProp = 0;
		while (propIte.hasNext()) {
			IProperty nextProp = propIte.next();
			if (!nextProp.isBlank()) {
				if (sBProp.length() > 0)
					sBProp.append(nL);
				sBProp.append(DifferentiaeFormatter.getPropertyDisplayer().apply(nextProp));
				nbOfProp++;
			}
		}
		return sBProp.toString() + "max nb of prop. : " + nbOfProp;
	}	

}
