package com.tregouet.occam.alg.builders.representations.descriptions.differentiae.properties.util;

import java.util.Set;

import com.tregouet.occam.data.structures.representations.descriptions.differentiae.properties.IProperty;
import com.tregouet.occam.data.structures.representations.descriptions.differentiae.properties.computations.IComputation;
import com.tregouet.occam.data.structures.representations.productions.IContextualizedProduction;

public interface ICompCluster {

	boolean add(IContextualizedProduction production);

	IProperty asProperty();

	public static int nbOfSignificantComp(Set<IComputation> computations) {
		int count = 0;
		for (IComputation comp : computations) {
			if (!comp.isIdentity() && !comp.isEpsilon())
				count++;
		}
		return count;
	}

}
