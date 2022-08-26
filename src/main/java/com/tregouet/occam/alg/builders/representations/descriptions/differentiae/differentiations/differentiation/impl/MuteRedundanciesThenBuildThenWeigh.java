package com.tregouet.occam.alg.builders.representations.descriptions.differentiae.differentiations.differentiation.impl;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import com.tregouet.occam.alg.builders.representations.descriptions.differentiae.differentiations.differentiation.DifferentiationBuilder;
import com.tregouet.occam.alg.setters.weights.properties.PropertyWeigher;
import com.tregouet.occam.data.structures.representations.classifications.IClassification;
import com.tregouet.occam.data.structures.representations.classifications.concepts.denotations.IDenotation;
import com.tregouet.occam.data.structures.representations.descriptions.differentiae.differentiations.IDifferentiation;
import com.tregouet.occam.data.structures.representations.descriptions.differentiae.differentiations.impl.Differentiation;
import com.tregouet.occam.data.structures.representations.descriptions.differentiae.properties.IProperty;
import com.tregouet.occam.data.structures.representations.descriptions.differentiae.properties.IWeighedProperty;
import com.tregouet.occam.data.structures.representations.descriptions.differentiae.properties.computations.IComputation;
import com.tregouet.occam.data.structures.representations.descriptions.differentiae.properties.impl.WeighedProperty;

public class MuteRedundanciesThenBuildThenWeigh implements DifferentiationBuilder {
	
	public static final MuteRedundanciesThenBuildThenWeigh INSTANCE = new MuteRedundanciesThenBuildThenWeigh();
	
	private MuteRedundanciesThenBuildThenWeigh() {
	}

	@Override
	public IDifferentiation apply(List<IProperty> properties, IClassification classification) {
		PropertyWeigher weigher = DifferentiationBuilder.propertyWeigher().setUp(classification);
		Set<IDenotation> computedDenotations = new HashSet<>();
		List<IWeighedProperty> weighedProperties = new ArrayList<>();
		//populate
		for (IProperty prop : properties) {
			Set<IComputation> computations = new HashSet<>();
			for (IComputation comp : prop.getComputations()) {
				if (!comp.isIdentity() && !comp.getOutput().isRedundant() && computedDenotations.add(comp.getOutput()))
					computations.add(comp);
			}
			if (!computations.isEmpty()) {
				IWeighedProperty weighedProp = new WeighedProperty(prop, computations);
				weigher.accept(weighedProp);
				weighedProperties.add(weighedProp);
			}
		}
		return new Differentiation(weighedProperties);
	}

}
