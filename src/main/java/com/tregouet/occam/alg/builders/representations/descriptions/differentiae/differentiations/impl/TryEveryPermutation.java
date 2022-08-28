package com.tregouet.occam.alg.builders.representations.descriptions.differentiae.differentiations.impl;

import java.util.Collection;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import com.google.common.collect.Collections2;
import com.tregouet.occam.alg.builders.representations.descriptions.differentiae.differentiations.DifferentiationSetBuilder;
import com.tregouet.occam.alg.builders.representations.descriptions.differentiae.differentiations.differentiation.DifferentiationBuilder;
import com.tregouet.occam.data.structures.representations.descriptions.differentiae.differentiations.IDifferentiation;
import com.tregouet.occam.data.structures.representations.descriptions.differentiae.differentiations.IDifferentiationSet;
import com.tregouet.occam.data.structures.representations.descriptions.differentiae.differentiations.impl.DifferentiationSet;
import com.tregouet.occam.data.structures.representations.descriptions.differentiae.properties.IProperty;

/**
 * DO NOT USE. CIRRENTLY NOT WORKING
 * @author Gael Tregouet
 *
 */
public class TryEveryPermutation implements DifferentiationSetBuilder {

	private DifferentiationBuilder differentiationBuilder = null;

	public TryEveryPermutation() {
	}

	/**
	 * DifferentiationBuilder argument must be set up
	 */
	@Override
	public IDifferentiationSet apply(Set<IProperty> properties) {
		Set<IDifferentiation> differentiations = new HashSet<>();
		Collection<List<IProperty>> permutations = Collections2.permutations(properties);
		for (List<IProperty> permutation : permutations)
			differentiations.add(differentiationBuilder.apply(permutation));
		return new DifferentiationSet(differentiations);
	}

	@Override
	public DifferentiationSetBuilder setUp(DifferentiationBuilder differentiationBuilder) {
		this.differentiationBuilder = differentiationBuilder;
		return this;
	}

}
