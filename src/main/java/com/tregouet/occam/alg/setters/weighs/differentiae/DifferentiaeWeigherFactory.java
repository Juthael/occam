package com.tregouet.occam.alg.setters.weighs.differentiae;

import com.tregouet.occam.alg.setters.weighs.differentiae.impl.MaxNbOfNonRedundantProperties;
import com.tregouet.occam.alg.setters.weighs.differentiae.impl.MaxWeightForNonRedundantSubsetOfProp;
import com.tregouet.occam.alg.setters.weighs.differentiae.impl.MinNbOfInstantiatedVars;
import com.tregouet.occam.alg.setters.weighs.differentiae.impl.MinNbOfWeighedProperties;
import com.tregouet.occam.alg.setters.weighs.differentiae.impl.NbOfCalculatedDenotations;
import com.tregouet.occam.alg.setters.weighs.differentiae.impl.SumOfPropertyWeights;

public class DifferentiaeWeigherFactory {

	public static final DifferentiaeWeigherFactory INSTANCE = new DifferentiaeWeigherFactory();

	private DifferentiaeWeigherFactory() {
	}

	public DifferentiaeWeigher apply(DifferentiaeWeigherStrategy strategy) {
		switch (strategy) {
		case SUM_OF_PROPERTY_WEIGHTS:
			return SumOfPropertyWeights.INSTANCE;
		case MIN_NB_OF_INSTANTIATED_VARS :
			return new MinNbOfInstantiatedVars();
		case NB_OF_CALCULATED_DENOTATIONS :
			return NbOfCalculatedDenotations.INSTANCE;
		case MAX_NB_OF_NON_REDUNDANT_PROP :
			return MaxNbOfNonRedundantProperties.INSTANCE;
		case MIN_NB_OF_WEIGHED_PROP :
			return MinNbOfWeighedProperties.INSTANCE;
		case MAX_WEIGHT_FOR_NON_REDUNDANT :
			return MaxWeightForNonRedundantSubsetOfProp.INSTANCE;
		default:
			return null;
		}
	}

}
