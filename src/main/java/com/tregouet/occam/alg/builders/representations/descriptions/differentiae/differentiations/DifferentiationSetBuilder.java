package com.tregouet.occam.alg.builders.representations.descriptions.differentiae.differentiations;

import java.util.Set;
import java.util.function.Function;

import com.tregouet.occam.alg.builders.BuildersAbstractFactory;
import com.tregouet.occam.alg.builders.representations.descriptions.differentiae.differentiations.differentiation.DifferentiationBuilder;
import com.tregouet.occam.data.structures.representations.descriptions.differentiae.differentiations.IDifferentiationSet;
import com.tregouet.occam.data.structures.representations.descriptions.differentiae.properties.IProperty;

public interface DifferentiationSetBuilder extends Function<Set<IProperty>, IDifferentiationSet> {

	public DifferentiationSetBuilder setUp(DifferentiationBuilder differentiationBuilder);

	public static DifferentiationBuilder differentiationBuilder() {
		return BuildersAbstractFactory.INSTANCE.getDifferentiationBuilder();
	}

}
