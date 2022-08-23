package com.tregouet.occam.alg.builders.representations.descriptions.differentiae;

import java.util.Set;
import java.util.function.BiFunction;

import com.tregouet.occam.alg.builders.BuildersAbstractFactory;
import com.tregouet.occam.alg.builders.representations.descriptions.differentiae.properties.PropertyBuilder;
import com.tregouet.occam.data.structures.representations.classifications.IClassification;
import com.tregouet.occam.data.structures.representations.descriptions.differentiae.ADifferentiae;
import com.tregouet.occam.data.structures.representations.productions.IContextualizedProduction;

@FunctionalInterface
public interface DifferentiaeBuilder extends BiFunction<IClassification, Set<IContextualizedProduction>, Set<ADifferentiae>> {

	public static PropertyBuilder propertyBuilder() {
		return BuildersAbstractFactory.INSTANCE.getPropertyBuilder();
	}

}
