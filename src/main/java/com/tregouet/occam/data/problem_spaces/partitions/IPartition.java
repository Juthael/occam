package com.tregouet.occam.data.problem_spaces.partitions;

import java.util.List;

import org.jgrapht.graph.DirectedAcyclicGraph;

import com.tregouet.occam.alg.setters.weighs.Weighed;
import com.tregouet.occam.data.logical_structures.orders.partial.PartiallyComparable;
import com.tregouet.occam.data.representations.descriptions.properties.AbstractDifferentiae;

public interface IPartition extends Weighed, PartiallyComparable<IPartition> {
	
	Integer getGenusID();
	
	/**
	 * 
	 * @return IDs in 
	 */
	Integer[] getSpeciesIDs();
	
	AbstractDifferentiae[] getDifferentiae();
	
	String toString();
	
	DirectedAcyclicGraph<Integer, AbstractDifferentiae> asGraph();
	
	void setWeight(double weight);
	
	@Override
	int hashCode();
	
	@Override
	boolean equals(Object o);
	
	//Positive integers in ascending order, then unordered negative
	public static Integer[] orderOverIDs(List<Integer> list) {
		list.sort((x, y) -> Integer.compare(x, y));
		Integer[] ordered = new Integer[list.size()];
		int shift = 0;
		for (int i = 0 ; i < ordered.length ; i++) {
			if (Integer.signum(list.get(i)) == -1) {
				ordered[(ordered.length - 1) - shift] = list.get(i);
				shift++;
			}
			else {
				ordered[i - shift] = list.get(i);
			}
		}
		return ordered;
	}

}
