package com.tregouet.occam.alg.builders.representations.descriptions.differentiae.differentiations.impl;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Comparator;
import java.util.List;
import java.util.Set;

import com.tregouet.occam.alg.builders.representations.descriptions.differentiae.differentiations.DifferentiationSetBuilder;
import com.tregouet.occam.alg.builders.representations.descriptions.differentiae.differentiations.differentiation.DifferentiationBuilder;
import com.tregouet.occam.alg.builders.representations.descriptions.differentiae.differentiations.utils.PermutationSupplier;
import com.tregouet.occam.data.structures.representations.descriptions.differentiae.differentiations.IDifferentiation;
import com.tregouet.occam.data.structures.representations.descriptions.differentiae.differentiations.IDifferentiationSet;
import com.tregouet.occam.data.structures.representations.descriptions.differentiae.differentiations.impl.DifferentiationSet;
import com.tregouet.occam.data.structures.representations.descriptions.differentiae.properties.IProperty;

/**
 * DO NOT USE. CURRENTLY NOT WORKING.
 * @author Gael Tregouet
 *
 */
public class RemoveRedundantPermutations implements DifferentiationSetBuilder {

	@SuppressWarnings("unused")
	private static class PropertyComparator implements Comparator<IProperty> {

		static final PropertyComparator INSTANCE = new PropertyComparator();

		private PropertyComparator() {
		}

		@Override
		public int compare(IProperty o1, IProperty o2) {
			int comparison = o1.getComputations().size() - o2.getComputations().size();
			if (comparison != 0)
				return - comparison;
			else return System.identityHashCode(o1) - System.identityHashCode(o2);
		}

	}

	private DifferentiationBuilder differentiationBuilder = null;

	public RemoveRedundantPermutations() {
	}

	@Override
	public IDifferentiationSet apply(Set<IProperty> properties) {
		if (properties.size() == 0) {
			IDifferentiation differentiation = differentiationBuilder.apply(new IProperty[0]);
			return new DifferentiationSet(Arrays.asList(new IDifferentiation[] {differentiation}));
		}
		if (properties.size() == 1) {
			IDifferentiation differentiation = differentiationBuilder.apply(new IProperty[] {properties.iterator().next()});
			return new DifferentiationSet(Arrays.asList(new IDifferentiation[] {differentiation}));
		}
		int nbOfProperties = properties.size();
		IProperty[] lexOrder = setLexOrder(properties, nbOfProperties);
		List<IDifferentiation> differentiations = new ArrayList<>();
		IDifferentiation lastDifferentiation = null;
		int[][] lexOrderedPerm = PermutationSupplier.INSTANCE.supply(properties.size());
		int[] lastValidPerm = null;
		int permIdx = 0;
		while (permIdx < lexOrderedPerm.length) {
			int[] iPerm = lexOrderedPerm[permIdx];
			IProperty[] permutation = new IProperty[nbOfProperties];
			for (int j = 0 ; j < nbOfProperties ; j++)
				permutation[j] = lexOrder[iPerm[j]];
			IDifferentiation newDifferentiation = differentiationBuilder.apply(permutation);
			if (newDifferentiation.equals(lastDifferentiation)) {
				int difference = howManyDifferentElements(lastValidPerm, iPerm, nbOfProperties);
				//HERE
				int test = factorial(difference) - 1;
				System.out.println(difference);
				if (test == 0) {
					System.out.println("here");
					howManyDifferentElements(lastValidPerm, iPerm, nbOfProperties);
				}
				//HERE
				permIdx += factorial(difference) - 1;
			}
			else {
				differentiations.add(newDifferentiation);
				lastDifferentiation = newDifferentiation;
				lastValidPerm = iPerm;
				permIdx++;
			}
		}
		return new DifferentiationSet(differentiations);
	}

	@Override
	public DifferentiationSetBuilder setUp(DifferentiationBuilder differentiationBuilder) {
		this.differentiationBuilder = differentiationBuilder;
		return this;
	}

	private static int factorial(int n) {
	    int fact = 1;
	    for (int i = 2; i <= n; i++) {
	        fact = fact * i;
	    }
	    return fact;
	}

	private static int howManyDifferentElements(int[] perm1, int[] perm2, int length) {
		for (int i = 0 ; i < length ; i++) {
			if (perm1[i] != perm2[i])
				return length - i;
		}
		return -1; //never happens
	}

	private static IProperty[] setLexOrder(Set<IProperty> properties, int nbOfProperties) {
		IProperty[] lexOrder = new IProperty[nbOfProperties];
		int idx = 0;
		for (IProperty property : properties)
			lexOrder[idx++] = property;
		// Arrays.sort(lexOrder, PropertyComparator.INSTANCE); useful ?
		return lexOrder;
	}

}
