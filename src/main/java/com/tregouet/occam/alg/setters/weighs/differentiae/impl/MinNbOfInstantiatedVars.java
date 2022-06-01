package com.tregouet.occam.alg.setters.weighs.differentiae.impl;

import java.util.HashSet;
import java.util.Set;

import com.google.common.collect.Sets;
import com.tregouet.occam.alg.setters.weighs.differentiae.DifferentiaeWeigher;
import com.tregouet.occam.data.logical_structures.languages.alphabets.AVariable;
import com.tregouet.occam.data.problem_space.states.classifications.concepts.denotations.IDenotation;
import com.tregouet.occam.data.problem_space.states.descriptions.properties.ADifferentiae;
import com.tregouet.occam.data.problem_space.states.descriptions.properties.IProperty;

public class MinNbOfInstantiatedVars implements DifferentiaeWeigher {
	
	private Set<IProperty> properties = null;
	Set<IDenotation> values = null;
	
	public MinNbOfInstantiatedVars() {
	}

	@Override
	public void accept(ADifferentiae differentiae) {
		properties = differentiae.getProperties();
		values = new HashSet<>();
		for (IProperty property : properties) {
			values.addAll(property.getResultingValues());
		}
		int weight = minNbOfInstantiatedVarsToCalculateValues();
		differentiae.setCoeffFreeWeight((double) weight);
	}
	
	private int minNbOfInstantiatedVarsToCalculateValues(){
		if (values.isEmpty())
			return 0;
		int propertySubsetSize = properties.size();
		int minNbOfInstantiatedVarsToCalculateValues = valueCalculationByNProperties(properties.size());
		int newMinimal = minNbOfInstantiatedVarsToCalculateValues;
		while (newMinimal != -1 && propertySubsetSize > 1) {
			propertySubsetSize--;
			newMinimal = valueCalculationByNProperties(propertySubsetSize);
			if (newMinimal != -1)
				minNbOfInstantiatedVarsToCalculateValues = newMinimal;
		}
		return minNbOfInstantiatedVarsToCalculateValues;
	}
	
	private int valueCalculationByNProperties(int n) {
		int minNbOfInstantiatedVar = -1;
		Set<Set<IProperty>> propSubsets = Sets.combinations(properties, n);
		for (Set<IProperty> propSubset : propSubsets) {
			Set<IDenotation> calculatedValues = new HashSet<>();
			for (IProperty property : propSubset) {
				calculatedValues.addAll(property.getResultingValues());
			}
			if (calculatedValues.equals(values)) {
				Set<AVariable> instantiatedVars = new HashSet<>();
				for (IProperty property : propSubset)
					instantiatedVars.addAll(property.getFunction().getVariables());
				int nbOfInstantiatedVars = instantiatedVars.size();
				if (minNbOfInstantiatedVar > nbOfInstantiatedVars || minNbOfInstantiatedVar == -1)
					minNbOfInstantiatedVar = nbOfInstantiatedVars;
			}
		}
		return minNbOfInstantiatedVar;
	}

}
