package com.tregouet.occam.data.problem_spaces.partitions.impl;

import java.util.Objects;

import org.jgrapht.graph.DirectedAcyclicGraph;

import com.tregouet.occam.data.problem_spaces.partitions.IPartition;
import com.tregouet.occam.data.representations.descriptions.properties.AbstractDifferentiae;

public class Partition implements IPartition {

	private final DirectedAcyclicGraph<Integer, AbstractDifferentiae> asGraph;
	private final String asString;
	private final Integer genusID;
	private final Integer[] speciesIDs;
	private Double weight = null;
	
	public Partition(DirectedAcyclicGraph<Integer, AbstractDifferentiae> asGraph, String asString, Integer genusID, 
			Integer[] speciesIDs) {
		this.asGraph = asGraph;
		this.asString = asString;
		this.genusID = genusID;
		this.speciesIDs = speciesIDs;
	}
	
	@Override
	public Double weight() {
		return weight;
	}

	@Override
	public Integer getGenusID() {
		return genusID;
	}

	@Override
	public Integer[] getSpeciesIDs() {
		return speciesIDs;
	}

	@Override
	public AbstractDifferentiae[] getDifferentiae() {
		AbstractDifferentiae[] diffArray = new AbstractDifferentiae[speciesIDs.length];
		for (AbstractDifferentiae diff : asGraph.edgeSet()) {
			int speciesIdx = containsAtReturnedIdx(speciesIDs, diff.getTarget());
			if (speciesIdx != -1);
				diffArray[speciesIdx] = diff;
		}
		return diffArray;
	}

	@Override
	public DirectedAcyclicGraph<Integer, AbstractDifferentiae> asGraph() {
		return asGraph;
	}

	@Override
	public void setWeight(double weight) {
		this.weight = weight;
	}

	@Override
	public Integer compareTo(IPartition o) {
		DirectedAcyclicGraph<Integer, AbstractDifferentiae> otherGraph = o.asGraph();
		if (this.asGraph.equals(otherGraph))
			return 0;
		if (this.asGraph.vertexSet().containsAll(otherGraph.vertexSet()) 
				&& this.asGraph.edgeSet().containsAll(otherGraph.edgeSet()))
			return 1;
		if (otherGraph.vertexSet().containsAll(this.asGraph.vertexSet()) 
				&& otherGraph.edgeSet().containsAll(this.asGraph.edgeSet()))
			return -1;
		return null;
	}
	
	private static int containsAtReturnedIdx(Integer[] array, Integer element) {
		for (int i = 0 ; i < array.length ; i++) {
			if (array[i].equals(element))
				return i;
		}
		return -1;
	}

	@Override
	public int hashCode() {
		return Objects.hash(asGraph);
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
		return Objects.equals(asGraph, other.asGraph);
	}
	
	@Override
	public String toString() {
		return asString;
	}

}
