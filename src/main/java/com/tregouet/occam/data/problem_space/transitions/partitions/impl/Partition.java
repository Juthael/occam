package com.tregouet.occam.data.problem_space.transitions.partitions.impl;

import java.util.List;
import java.util.Map;
import java.util.Objects;

import org.jgrapht.graph.DirectedAcyclicGraph;

import com.tregouet.occam.data.problem_space.states.descriptions.differentiae.ADifferentiae;
import com.tregouet.occam.data.problem_space.transitions.partitions.IPartition;
import com.tregouet.tree_finder.data.Tree;

public class Partition implements IPartition {

	private final Tree<Integer, ADifferentiae> asGraph;
	private final String asString;
	private final Integer genusID;
	private final Integer[] speciesIDs;
	private final Map<Integer, List<Integer>> leaf2Extent;
	private final int rank;
	private Double weight = null;

	public Partition(Tree<Integer, ADifferentiae> asGraph, String asString, Integer genusID,
			Integer[] speciesIDs, Map<Integer, List<Integer>> leaf2Extent, int rank) {
		this.asGraph = asGraph;
		this.asString = asString;
		this.genusID = genusID;
		this.speciesIDs = speciesIDs;
		this.leaf2Extent = leaf2Extent;
		this.rank = rank;
	}

	@Override
	public Tree<Integer, ADifferentiae> asGraph() {
		return asGraph;
	}

	@Override
	public Integer compareTo(IPartition o) {
		if (this.asString.equals(o.toString()))
			return 0;
		DirectedAcyclicGraph<Integer, ADifferentiae> otherGraph = o.asGraph();
		if (this.asGraph.vertexSet().containsAll(otherGraph.vertexSet())
				&& this.asGraph.edgeSet().containsAll(otherGraph.edgeSet()))
			return 1;
		if (otherGraph.vertexSet().containsAll(this.asGraph.vertexSet())
				&& otherGraph.edgeSet().containsAll(this.asGraph.edgeSet()))
			return -1;
		return null;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		Partition other = (Partition) obj;
		return Objects.equals(asString, other.asString) && Objects.equals(asGraph, other.asGraph);
	}

	@Override
	public ADifferentiae[] getDifferentiae() {
		ADifferentiae[] diffArray = new ADifferentiae[speciesIDs.length];
		for (ADifferentiae diff : asGraph.edgeSet()) {
			int speciesIdx = containsAtReturnedIdx(speciesIDs, diff.getTarget());
			if (speciesIdx != -1)
				diffArray[speciesIdx] = diff;
		}
		return diffArray;
	}

	@Override
	public Integer getGenusID() {
		return genusID;
	}

	@Override
	public Map<Integer, List<Integer>> getLeaf2ExtentMap() {
		return leaf2Extent;
	}	
	
	/*
	
	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if ((obj == null) || (getClass() != obj.getClass()))
			return false;
		Partition other = (Partition) obj;
		return Objects.equals(asGraph, other.asGraph);
	}	

	@Override
	public int hashCode() {
		return Objects.hash(asGraph);
	}
	
	*/

	@Override
	public Integer[] getSpeciesIDs() {
		return speciesIDs;
	}

	@Override
	public int hashCode() {
		return Objects.hash(asString);
	}

	@Override
	public int rank() {
		return rank;
	}

	@Override
	public void setWeight(double weight) {
		this.weight = weight;
	}

	@Override
	public String toString() {
		return asString;
	}

	@Override
	public Double weight() {
		return weight;
	}

	private static int containsAtReturnedIdx(Integer[] array, Integer element) {
		for (int i = 0; i < array.length; i++) {
			if (array[i].equals(element))
				return i;
		}
		return -1;
	}

}
