package com.tregouet.occam.alg.builders.pb_space.concepts_trees.impl;

import java.util.HashSet;
import java.util.Set;

import org.jgrapht.graph.DirectedAcyclicGraph;

import com.tregouet.occam.alg.builders.pb_space.concepts_trees.ConceptTreeGrower;
import com.tregouet.occam.data.representations.concepts.IConcept;
import com.tregouet.occam.data.representations.concepts.IIsA;
import com.tregouet.tree_finder.data.InvertedUpperSemilattice;

public class NoSize2UnsortedUniversal extends IfLeafIsUniversalThenSort implements ConceptTreeGrower {
	
	@Override
	protected void complyToAdditionalConstraints(DirectedAcyclicGraph<IConcept, IIsA> treeDAG, 
			InvertedUpperSemilattice<IConcept, IIsA> searchSpace) {
		Set<IConcept> treeLeaves = new HashSet<>();
		for (IConcept concept : treeDAG.vertexSet()) {
			if (treeDAG.inDegreeOf(concept) == 0)
				treeLeaves.add(concept);
		}
		for (IConcept leaf : treeLeaves) {
			Set<Integer> extentIDs = leaf.getExtentIDs();
			if (extentIDs.size() == 2) {
				for (Integer iD : extentIDs) {
					IConcept particular = getConceptWithID(iD, searchSpace);
					treeDAG.addVertex(particular);
					treeDAG.addEdge(particular, leaf);
				}
			}
		}
	}
	
	private IConcept getConceptWithID(int iD, InvertedUpperSemilattice<IConcept, IIsA> searchSpace) {
		for (IConcept concept : searchSpace.vertexSet()) {
			if (concept.iD() == iD)
				return concept;
		}
		return null; //never happens
	}

}
