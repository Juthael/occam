import java.util.Arrays;
import java.util.List;

import com.google.common.collect.Collections2;
import com.tregouet.occam.alg.builders.representations.descriptions.differentiae.differentiations.utils.Permuter;

public class tempTest {

	public static void main(String[] args) {
		System.out.println("DUMB WAY");
		List<Integer> list = Arrays.asList(new Integer[] {1, 2, 3, 4, 5});
		for (List<Integer> permutation : Collections2.orderedPermutations(list)) {
			System.out.println(permutation.toString());
		}
		System.out.println(System.lineSeparator() + "OPT WAY");
		int[][] permutations = Permuter.permute(new int[] {1, 2, 3, 4});
		for (int[] permutation : permutations) {
			System.out.println(Arrays.toString(permutation));
		}
	}

}
