package com.tregouet.occam.alg.setters.weights.differentiae.impl;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import com.google.common.collect.Sets;
import com.tregouet.occam.alg.setters.weights.differentiae.DifferentiaeWeigher;
import com.tregouet.occam.data.structures.languages.alphabets.ISymbol;
import com.tregouet.occam.data.structures.representations.classifications.concepts.IConcept;
import com.tregouet.occam.data.structures.representations.descriptions.differentiae.ADifferentiae;
import com.tregouet.occam.data.structures.representations.descriptions.differentiae.properties.IProperty;
import com.tregouet.occam.data.structures.representations.descriptions.differentiae.properties.computations.IComputation;

public class MinNbOfWeighedProperties implements DifferentiaeWeigher {

	public static class SubsetOfProperties {

		private final Set<IProperty> subset;
		private final double weightSum;

		public SubsetOfProperties(Set<IProperty> subset, double weightSum) {
			this.subset = subset;
			this.weightSum = weightSum;
		}

		public Set<IProperty> getSubset(){
			return subset;
		}

		public double getWeightSum() {
			return weightSum;
		}

	}

	public static final MinNbOfWeighedProperties INSTANCE = new MinNbOfWeighedProperties();

	private MinNbOfWeighedProperties() {
	}

	@Override
	public void accept(ADifferentiae differentiae) {
		if (differentiae.getGenusID() == IConcept.ONTOLOGICAL_COMMITMENT_ID)
			differentiae.setCoeffFreeWeight(1.0);
		else {
			Set<List<ISymbol>> denotationSet = new HashSet<>();
			Set<IProperty> nonBlankProperties = new HashSet<>();
			for (IProperty property : differentiae.getProperties()) {
				if (!property.isBlank()) {
					nonBlankProperties.add(property);
					for (IComputation computation : property.getComputations()) {
						if (!computation.isIdentity() && !computation.getOutput().isRedundant())
							denotationSet.add(computation.getOutput().asList());
					}
				}
			}
			List<List<ISymbol>> denotationList = new ArrayList<>(denotationSet);
			int minNbOfProperties = nonBlankProperties.size();
			boolean maxNbFound = false;
			SubsetOfProperties optimalSubset = new SubsetOfProperties(nonBlankProperties, sumWeights(nonBlankProperties));
			while (!maxNbFound && minNbOfProperties > 1) {
				int nbOfAntecedents = minNbOfProperties - 1;
				SubsetOfProperties newOptimalSubset =
						getSurjectiveOptimalSubsetOfProperties(nonBlankProperties, denotationList, nbOfAntecedents);
				if (newOptimalSubset != null) {
					optimalSubset = newOptimalSubset;
					minNbOfProperties--;
				}
				else maxNbFound = true;
			}
			differentiae.setCoeffFreeWeight(optimalSubset.weightSum);
		}
	}

	private static SubsetOfProperties getSurjectiveOptimalSubsetOfProperties(Set<IProperty> properties,
			List<List<ISymbol>> denotations, int subsetSize) {
		SubsetOfProperties optimalSubset = null;
		for (Set<IProperty> subset : Sets.combinations(properties, subsetSize)) {
			if(setOfAntecedentsIsSurjective(subset, denotations)) {
				double weightSum = sumWeights(subset);
				if (optimalSubset == null || weightSum > optimalSubset.getWeightSum())
					optimalSubset = new SubsetOfProperties(subset, weightSum);
			}
		}
		return optimalSubset;
	}

	private static boolean setOfAntecedentsIsSurjective(Set<IProperty> setOfAntecedents,
			List<List<ISymbol>> images) {
		boolean[] hasAnAntecedent = new boolean[images.size()];
		//populate
		for (IProperty property : setOfAntecedents) {
			for (IComputation computation : property.getComputations()) {
				if (!computation.isIdentity() && !computation.getOutput().isRedundant()) {
					int imageIdx = images.indexOf(computation.getOutput().asList());
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

	private static double sumWeights(Set<IProperty> properties) {
		double weightSum = 0.0;
		for (IProperty prop : properties)
			weightSum += prop.weight();
		return weightSum;
	}

}
