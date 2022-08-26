package com.tregouet.occam.alg.setters;

import com.tregouet.occam.alg.setters.salience.ProductionSalienceSetter;
import com.tregouet.occam.alg.setters.salience.ProductionSalienceSetterFactory;
import com.tregouet.occam.alg.setters.salience.ProductionSalienceSetterStrategy;
import com.tregouet.occam.alg.setters.weights.categorization_transitions.ProblemTransitionWeigher;
import com.tregouet.occam.alg.setters.weights.categorization_transitions.ProblemTransitionWeigherFactory;
import com.tregouet.occam.alg.setters.weights.categorization_transitions.ProblemTransitionWeigherStrategy;
import com.tregouet.occam.alg.setters.weights.differentiae.DifferentiaeWeigher;
import com.tregouet.occam.alg.setters.weights.differentiae.DifferentiaeWeigherFactory;
import com.tregouet.occam.alg.setters.weights.differentiae.DifferentiaeWeigherStrategy;
import com.tregouet.occam.alg.setters.weights.differentiae.coeff.DifferentiaeCoeffSetter;
import com.tregouet.occam.alg.setters.weights.differentiae.coeff.DifferentiaeCoeffSetterFactory;
import com.tregouet.occam.alg.setters.weights.differentiae.coeff.DifferentiaeCoeffSetterStrategy;
import com.tregouet.occam.alg.setters.weights.partitions.PartitionWeigher;
import com.tregouet.occam.alg.setters.weights.partitions.PartitionWeigherFactory;
import com.tregouet.occam.alg.setters.weights.partitions.PartitionWeigherStrategy;
import com.tregouet.occam.alg.setters.weights.properties.PropertyWeigher;
import com.tregouet.occam.alg.setters.weights.properties.PropertyWeigherFactory;
import com.tregouet.occam.alg.setters.weights.properties.PropertyWeigherStrategy;

public class SettersAbstractFactory {

	public static final SettersAbstractFactory INSTANCE = new SettersAbstractFactory();

	private ProductionSalienceSetterStrategy productionSalienceSetterStrategy = null;
	private PropertyWeigherStrategy propertyWeigherStrategy = null;
	private DifferentiaeCoeffSetterStrategy differentiaeCoeffSetterStrategy = null;
	private DifferentiaeWeigherStrategy differentiaeWeigherStrategy = null;
	private PartitionWeigherStrategy partitionWeigherStrategy = null;
	private ProblemTransitionWeigherStrategy problemTransitionWeigherStrategy = null;

	private SettersAbstractFactory() {
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

	public ProblemTransitionWeigher getProblemTransitionWeigher() {
		return ProblemTransitionWeigherFactory.INSTANCE.apply(problemTransitionWeigherStrategy);
	}

	public ProductionSalienceSetter getProductionSalienceSetter() {
		return ProductionSalienceSetterFactory.INSTANCE.apply(productionSalienceSetterStrategy);
	}

	public PropertyWeigher getPropertyWeigher() {
		return PropertyWeigherFactory.INSTANCE.apply(propertyWeigherStrategy);
	}

	public void setUpStrategy(SettingStrategy overallStrategy) {
		switch (overallStrategy) {
		case SETTING_STRATEGY_1:
			productionSalienceSetterStrategy = ProductionSalienceSetterStrategy.HIDDEN_THEN_FIND_SPECIFICS;
			propertyWeigherStrategy = PropertyWeigherStrategy.NB_OF_BOUND_VAR;
			differentiaeCoeffSetterStrategy = DifferentiaeCoeffSetterStrategy.SPECIES_CARDINALITY;
			differentiaeWeigherStrategy = DifferentiaeWeigherStrategy.SUM_OF_PROPERTY_WEIGHTS;
			partitionWeigherStrategy = PartitionWeigherStrategy.SUM_PARTITION_DIFFERENTIAE;
			problemTransitionWeigherStrategy = ProblemTransitionWeigherStrategy.PART_PROB_WITH_TRIVIAL_TRANSITIONS_MANDATORY;
			break;
		case SETTING_STRATEGY_2 :
			productionSalienceSetterStrategy = ProductionSalienceSetterStrategy.HIDDEN_THEN_FIND_SPECIFICS;
			propertyWeigherStrategy = PropertyWeigherStrategy.WEIGHTLESS;
			differentiaeCoeffSetterStrategy = DifferentiaeCoeffSetterStrategy.SPECIES_CARDINALITY;
			differentiaeWeigherStrategy = DifferentiaeWeigherStrategy.MAX_NB_OF_NON_REDUNDANT_PROP;
			partitionWeigherStrategy = PartitionWeigherStrategy.SUM_PARTITION_DIFFERENTIAE;
			problemTransitionWeigherStrategy = ProblemTransitionWeigherStrategy.PART_PROB_WITH_TRIVIAL_TRANSITIONS_MANDATORY;
			break;
		case SETTING_STRATEGY_3 :
			productionSalienceSetterStrategy = ProductionSalienceSetterStrategy.HIDDEN_THEN_FIND_SPECIFICS;
			propertyWeigherStrategy = PropertyWeigherStrategy.RULENESS;
			differentiaeCoeffSetterStrategy = DifferentiaeCoeffSetterStrategy.SPECIES_CARDINALITY;
			differentiaeWeigherStrategy = DifferentiaeWeigherStrategy.MAX_WEIGHT_FOR_NON_REDUNDANT;
			partitionWeigherStrategy = PartitionWeigherStrategy.SUM_PARTITION_DIFFERENTIAE;
			problemTransitionWeigherStrategy = ProblemTransitionWeigherStrategy.PART_PROB_WITH_TRIVIAL_TRANSITIONS_MANDATORY;
			break;
		default:
			break;
		}
	}

}
