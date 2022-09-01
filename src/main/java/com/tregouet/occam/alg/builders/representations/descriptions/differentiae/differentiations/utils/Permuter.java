package com.tregouet.occam.alg.builders.representations.descriptions.differentiae.differentiations.utils;

public interface Permuter {

	public static int[][] permute(int[] lexOrder) {
		if (lexOrder.length <= 1)
			return new int[][] {lexOrder};
		int length = lexOrder.length;
		int[][] permutations = new int[factorial(length)][];
		permutations[0] = lexOrder;
		int[] perm = new int[length];
		System.arraycopy(lexOrder, 0, perm, 0, length);
		return nextPermutation(perm, length, 1, permutations);
	}

	private static int factorial(int n) {
	    int fact = 1;
	    for (int i = 2; i <= n; i++) {
	        fact = fact * i;
	    }
	    return fact;
	}

	private static int[][] nextPermutation(int[] perm, int length, int idx, int[][] permutations) {
		for (int i = length - 2 ; i >= 0 ; i--) {
			if (perm[i] < perm[i+1]) {
				int iValue = perm[i];
				for (int j = length - 1 ; j > i ; j--) {
					if (perm[j] > iValue) {
						perm[i] = perm[j];
						perm[j] = iValue;
						partiallyReverse(perm, i, length);
						int[] newPerm = new int[length];
						System.arraycopy(perm, 0, newPerm, 0, length);
						permutations[idx++] = newPerm;
						return nextPermutation(perm, length, idx, permutations);
					}
				}
			}
		}
		return permutations;
	}

	private static void partiallyReverse(int[] sequence, int idx, int seqLength) {
		int maxIdx = idx + ((seqLength - (idx + 1)) / 2);
		int p = 1;
		int i = idx + p;
		while (i <= maxIdx) {
			int j = seqLength - p;
			int iBuffer = sequence[i];
			sequence[i] = sequence[j];
			sequence[j] = iBuffer;
			i++;
			p++;
		}
	}

}
