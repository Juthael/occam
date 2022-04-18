package com.tregouet.occam.alg.setters;

import com.tregouet.occam.alg.setters.differentiae_coeff.DifferentiaeCoeffSetter;
import com.tregouet.occam.alg.setters.differentiae_coeff.DifferentiaeCoeffSetterFactory;
import com.tregouet.occam.alg.setters.differentiae_coeff.DifferentiaeCoeffSetterStrategy;
import com.tregouet.occam.alg.setters.weighs.categorization_transitions.CategorizationTransitionWeigher;
import com.tregouet.occam.alg.setters.weighs.categorization_transitions.CategorizationTransitionWeigherFactory;
import com.tregouet.occam.alg.setters.weighs.categorization_transitions.CategorizationTransitionWeigherStrategy;
import com.tregouet.occam.alg.setters.weighs.differentiae.DifferentiaeWeigher;
import com.tregouet.occam.alg.setters.weighs.differentiae.DifferentiaeWeigherFactory;
import com.tregouet.occam.alg.setters.weighs.differentiae.DifferentiaeWeigherStrategy;
import com.tregouet.occam.alg.setters.weighs.partitions.PartitionWeigher;
import com.tregouet.occam.alg.setters.weighs.partitions.PartitionWeigherFactory;
import com.tregouet.occam.alg.setters.weighs.partitions.PartitionWeigherStrategy;
import com.tregouet.occam.alg.setters.weighs.properties.PropertyWeigher;
import com.tregouet.occam.alg.setters.weighs.properties.PropertyWeigherFactory;
import com.tregouet.occam.alg.setters.weighs.properties.PropertyWeigherStrategy;

public class SettersAbstractFactory {

	public static final SettersAbstractFactory INSTANCE = new SettersAbstractFactory();

	private PropertyWeigherStrategy propertyWeigherStrategy = null;
	private DifferentiaeCoeffSetterStrategy differentiaeCoeffSetterStrategy = null;
	private DifferentiaeWeigherStrategy differentiaeWeigherStrategy = null;
	private PartitionWeigherStrategy partitionWeigherStrategy = null;
	private CategorizationTransitionWeigherStrategy categorizationTransitionWeigherStrategy = null;

	private SettersAbstractFactory() {
	}

	public CategorizationTransitionWeigher getCategorizationTransitionWeigher() {
		return CategorizationTransitionWeigherFactory.INSTANCE.apply(categorizationTransitionWeigherStrategy);
	}

	public DifferentiaeCoeffSetter getDifferentiaeCoeffSetter() {
		return DifferentiaeCoeffSetterFactory.INSTANCE.apply(differentiaeCoeffSetterStrategy);
	}

	public DifferentiaeWeigher getDifferentiaeWeigher() {
		return DifferentiaeWeigherFactory.INSTANCE.apply(differentiaeWeigherStrategy);
	}

	public PartitionWeigher getPartitionWeigher() {
		return PartitionWeigherFactory.INSTANCE.apply(partitionWeigherStrategy);
	}

	public PropertyWeigher getPropertyWheigher() {
		return PropertyWeigherFactory.INSTANCE.apply(propertyWeigherStrategy);
	}

	public void setUpStrategy(SettingStrategy overallStrategy) {
		switch(overallStrategy) {
			case SETTING_STRATEGY_1 :
				propertyWeigherStrategy = PropertyWeigherStrategy.NB_OF_BOUND_VAR;
				differentiaeCoeffSetterStrategy = DifferentiaeCoeffSetterStrategy.SPECIES_CARDINALITY;
				differentiaeWeigherStrategy = DifferentiaeWeigherStrategy.SUM_OF_PROPERTY_WEIGHTS;
				partitionWeigherStrategy = PartitionWeigherStrategy.SUM_PARTITION_DIFFERENTIAE;
				categorizationTransitionWeigherStrategy =
						CategorizationTransitionWeigherStrategy.PARTITIONS_WEIGHT;
				break;
			default :
				break;
		}
	}

}
