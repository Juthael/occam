package com.tregouet.occam.alg.builders.pb_space.representations.descriptions.differentiae;

import java.util.Set;
import java.util.function.BiFunction;

import com.tregouet.occam.alg.builders.BuildersAbstractFactory;
import com.tregouet.occam.alg.builders.pb_space.representations.descriptions.differentiae.properties.PropertyBuilder;
import com.tregouet.occam.data.problem_space.states.classifications.IClassification;
import com.tregouet.occam.data.problem_space.states.descriptions.differentiae.ADifferentiae;
import com.tregouet.occam.data.problem_space.states.productions.IContextualizedProduction;

@FunctionalInterface
public interface DifferentiaeBuilder extends BiFunction<IClassification, Set<IContextualizedProduction>, Set<ADifferentiae>> {

	public static PropertyBuilder propertyBuilder() {
		return BuildersAbstractFactory.INSTANCE.getPropertyBuilder();
	}

}
