package com.tregouet.occam.alg.builders.representations.descriptions.differentiae.differentiations.differentiation.impl;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import com.tregouet.occam.alg.builders.representations.descriptions.differentiae.differentiations.differentiation.DifferentiationBuilder;
import com.tregouet.occam.alg.setters.weights.properties.PropertyWeigher;
import com.tregouet.occam.data.structures.representations.classifications.concepts.denotations.IDenotation;
import com.tregouet.occam.data.structures.representations.descriptions.differentiae.differentiations.IDifferentiation;
import com.tregouet.occam.data.structures.representations.descriptions.differentiae.differentiations.impl.Differentiation;
import com.tregouet.occam.data.structures.representations.descriptions.differentiae.properties.IProperty;
import com.tregouet.occam.data.structures.representations.descriptions.differentiae.properties.computations.IComputation;
import com.tregouet.occam.data.structures.representations.descriptions.differentiae.properties.impl.ModularProperty;

public class MuteRedundanciesThenBuildThenWeigh implements DifferentiationBuilder {

	private PropertyWeigher weigher = null;

	public MuteRedundanciesThenBuildThenWeigh() {
	}

	@Override
	public IDifferentiation apply(IProperty[] permutation) {
		if (permutation.length == 0)
			return new Differentiation(new ArrayList<>());
		Set<IDenotation> computedDenotations = new HashSet<>();
		List<IProperty> properties = new ArrayList<>();
		//populate
		for (IProperty prop : permutation) {
			Set<IComputation> computations = new HashSet<>();
			for (IComputation comp : prop.getComputations()) {
				if (!comp.isIdentity() && !comp.getOutput().isRedundant() && computedDenotations.add(comp.getOutput()))
					computations.add(comp);
			}
			if (!computations.isEmpty()) {
				IProperty modProp = new ModularProperty(prop, computations);
				weigher.accept(modProp);
				properties.add(modProp);
			}
		}
		return new Differentiation(properties);
	}

	@Override
	public IDifferentiation apply(List<IProperty> permutation) {
		return apply(permutation.toArray(new IProperty[0]));
	}

	@Override
	public DifferentiationBuilder setUp(PropertyWeigher weigher) {
		this.weigher = weigher;
		return this;
	}

}
