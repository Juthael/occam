package com.tregouet.occam.alg.setters.weighs.differentiae.impl;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Set;

import com.google.common.collect.Sets;
import com.tregouet.occam.alg.setters.weighs.differentiae.DifferentiaeWeigher;
import com.tregouet.occam.data.problem_space.states.classifications.concepts.denotations.IDenotation;
import com.tregouet.occam.data.problem_space.states.descriptions.differentiae.ADifferentiae;
import com.tregouet.occam.data.problem_space.states.descriptions.differentiae.properties.IProperty;
import com.tregouet.occam.data.problem_space.states.descriptions.differentiae.properties.computations.IComputation;

public class MaxWeightForNonRedundantSubsetOfProp implements DifferentiaeWeigher {
	
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
	
	public static final MaxWeightForNonRedundantSubsetOfProp INSTANCE = new MaxWeightForNonRedundantSubsetOfProp();
	
	private MaxWeightForNonRedundantSubsetOfProp() {
	}

	@Override
	public void accept(ADifferentiae diff) {
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
			diff.setCoeffFreeWeight(0.0);
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
			for (SubsetOfProperties subsetOfProperties : nonRedundantSurjectiveSubsets) {
				if (subsetOfProperties.weightSum > diffWeight)
					diffWeight = subsetOfProperties.weightSum;
			}
			diff.setCoeffFreeWeight(diffWeight);
		}
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

}
