package com.tregouet.occam.alg.setters.weighs.differentiae.impl;

import java.util.HashSet;
import java.util.Set;

import com.tregouet.occam.alg.setters.weighs.differentiae.DifferentiaeWeigher;
import com.tregouet.occam.data.structures.representations.classifications.concepts.denotations.IDenotation;
import com.tregouet.occam.data.structures.representations.descriptions.differentiae.ADifferentiae;
import com.tregouet.occam.data.structures.representations.descriptions.differentiae.properties.IProperty;
import com.tregouet.occam.data.structures.representations.descriptions.differentiae.properties.computations.IComputation;

public class NbOfCalculatedDenotations implements DifferentiaeWeigher {

	public static final NbOfCalculatedDenotations INSTANCE = new NbOfCalculatedDenotations();

	private NbOfCalculatedDenotations() {
	}

	@Override
	public void accept(ADifferentiae differentiae) {
		Set<IDenotation> values = new HashSet<>();
		for (IProperty property : differentiae.getProperties()) {
			for (IComputation computation : property.getComputations())
				values.add(computation.getOutput());
		}
		differentiae.setCoeffFreeWeight(values.size());
	}

}
