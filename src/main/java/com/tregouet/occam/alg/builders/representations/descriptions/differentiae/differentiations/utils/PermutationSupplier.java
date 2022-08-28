package com.tregouet.occam.alg.builders.representations.descriptions.differentiae.differentiations.utils;

import java.util.HashMap;
import java.util.Map;

public class PermutationSupplier {

	public static final PermutationSupplier INSTANCE = new PermutationSupplier();

	private Map<Integer, int[][]> size2Permutations = new HashMap<>();

	private PermutationSupplier() {
	}

	public int[][] supply(int n) {
		int[][] permutations = size2Permutations.get(n);
		if (permutations == null) {
			permutations = Permuter.permute(initializeArray(n));
			size2Permutations.put(n, permutations);
		}
		return permutations;
	}

	private int[] initializeArray(int length) {
		int[] array = new int[length];
		for (int i = 0 ; i < length ; i++) {
			array[i] = i;
		}
		return array;
	}



}
