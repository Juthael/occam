package com.tregouet.occam.data.modules.sorting.transitions.partitions;

import java.util.List;
import java.util.Map;

import com.tregouet.occam.alg.setters.weights.Weighed;
import com.tregouet.occam.data.structures.partial_order.PartiallyComparable;
import com.tregouet.occam.data.structures.representations.descriptions.differentiae.ADifferentiae;
import com.tregouet.tree_finder.data.Tree;

public interface IPartition extends Weighed, PartiallyComparable<IPartition> {

	Tree<Integer, ADifferentiae> asGraph();

	@Override
	boolean equals(Object o);

	ADifferentiae[] getDifferentiae();

	Integer getGenusID();

	Map<Integer, List<Integer>> getLeaf2ExtentMap();

	/**
	 *
	 * @return IDs in
	 */
	Integer[] getSpeciesIDs();

	@Override
	int hashCode();

	void setWeight(double weight);

	@Override
	String toString();

	// Positive integers in ascending order, then unordered negative
	public static Integer[] orderOverIDs(List<Integer> list) {
		list.sort((x, y) -> Integer.compare(x, y));
		Integer[] ordered = new Integer[list.size()];
		int shift = 0;
		for (int i = 0; i < ordered.length; i++) {
			if (Integer.signum(list.get(i)) == -1) {
				ordered[(ordered.length - 1) - shift] = list.get(i);
				shift++;
			} else {
				ordered[i - shift] = list.get(i);
			}
		}
		return ordered;
	}

}
