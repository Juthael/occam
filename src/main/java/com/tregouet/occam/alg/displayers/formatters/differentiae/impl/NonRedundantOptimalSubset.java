package com.tregouet.occam.alg.displayers.formatters.differentiae.impl;

import java.math.BigDecimal;
import java.math.MathContext;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Set;

import com.google.common.collect.Sets;
import com.tregouet.occam.alg.displayers.formatters.differentiae.DifferentiaeLabeller;
import com.tregouet.occam.data.structures.representations.classifications.concepts.denotations.IDenotation;
import com.tregouet.occam.data.structures.representations.descriptions.differentiae.ADifferentiae;
import com.tregouet.occam.data.structures.representations.descriptions.differentiae.properties.IProperty;
import com.tregouet.occam.data.structures.representations.descriptions.differentiae.properties.computations.IComputation;

public class NonRedundantOptimalSubset implements DifferentiaeLabeller {

	public static class SubsetOfProperties {

		private final Set<IProperty> subset;
		private final double weightSum;

		public SubsetOfProperties(Set<IProperty> subset, double weightSum) {
			this.subset = subset;
			this.weightSum = weightSum;
		}

		public boolean contains(SubsetOfProperties other) {
			return this.subset.containsAll(other.subset);
		}

	}

	public static final NonRedundantOptimalSubset INSTANCE = new NonRedundantOptimalSubset();
	private static final MathContext mathContext = new MathContext(3);

	private NonRedundantOptimalSubset() {
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
		if (requiredDenotations.isEmpty()) {
			return "weight : 0.0";
		}
		else {
			int subsetSize = nonBlankProperties.size();
			List<SubsetOfProperties> nonRedundantSurjectiveSubsets = new ArrayList<>();
			boolean newSurjectiveSubsetsFound = true;
			while (newSurjectiveSubsetsFound) {
				Set<SubsetOfProperties> newSurjectiveSubsets =
						getSurjectiveSubsetsOfSpecifiedSize(nonBlankProperties, requiredDenotations, subsetSize);
				if (!newSurjectiveSubsets.isEmpty()) {
					addNewSubsetsAndRemoveRedundants(newSurjectiveSubsets, nonRedundantSurjectiveSubsets);
					subsetSize--;
				}
				else newSurjectiveSubsetsFound = false;
			}
			//calculate weight
			double diffWeight = 0.0;
			SubsetOfProperties optimalSubset = null;
			for (SubsetOfProperties subsetOfProperties : nonRedundantSurjectiveSubsets) {
				if (optimalSubset == null || subsetOfProperties.weightSum > diffWeight) {
					optimalSubset = subsetOfProperties;
					diffWeight = subsetOfProperties.weightSum;
				}
			}
			return toString(diff, optimalSubset.subset);
		}
	}

	private static void addNewSubsetsAndRemoveRedundants(Set<SubsetOfProperties> newSubsets, List<SubsetOfProperties> previousSubsets) {
		for (SubsetOfProperties newSubset : newSubsets) {
			Iterator<SubsetOfProperties> prevSubsetsIte = previousSubsets.iterator();
			while (prevSubsetsIte.hasNext()) {
				SubsetOfProperties previousSubset = prevSubsetsIte.next();
				if (previousSubset.contains(newSubset))
					prevSubsetsIte.remove();
			}
		}
		previousSubsets.addAll(newSubsets);
	}

	private static Set<SubsetOfProperties> getSurjectiveSubsetsOfSpecifiedSize(Set<IProperty> properties,
			Set<IDenotation> requiredDenotations, int subsetSize) {
		Set<SubsetOfProperties> subsets = new HashSet<>();
		for (Set<IProperty> subset : Sets.combinations(properties, subsetSize)) {
			if (isSurjective(subset, requiredDenotations)) {
				double weightSum = 0.0;
				for (IProperty property : subset)
					weightSum += property.weight();
				subsets.add(new SubsetOfProperties(subset, weightSum));
			}
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

	private static String toString(ADifferentiae diff, Set<IProperty> properties) {
		String nL = System.lineSeparator();
		Iterator<IProperty> propIte = properties.iterator();
		StringBuilder sBProp = new StringBuilder();
		while (propIte.hasNext()) {
			IProperty nextProp = propIte.next();
			if (!nextProp.isBlank()) {
				if (sBProp.length() > 0)
					sBProp.append(nL);
				sBProp.append(DifferentiaeLabeller.getPropertyDisplayer().apply(nextProp));
			}
		}
		return "weight : " + weightAsString(diff.weight()) + nL + sBProp.toString();
	}

	private static String weightAsString(double weight) {
		return new BigDecimal(weight).round(mathContext).toString();
	}

}
