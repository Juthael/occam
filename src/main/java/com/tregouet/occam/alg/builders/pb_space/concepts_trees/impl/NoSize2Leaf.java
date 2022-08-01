package com.tregouet.occam.alg.builders.pb_space.concepts_trees.impl;

import java.util.HashSet;
import java.util.Set;

import org.jgrapht.graph.DirectedAcyclicGraph;

import com.google.common.collect.Sets;
import com.tregouet.occam.alg.builders.pb_space.concepts_trees.ConceptTreeGrower;
import com.tregouet.occam.data.problem_space.states.classifications.concepts.ConceptType;
import com.tregouet.occam.data.problem_space.states.classifications.concepts.IConcept;
import com.tregouet.occam.data.problem_space.states.classifications.concepts.IIsA;
import com.tregouet.tree_finder.data.InvertedUpperSemilattice;

public class NoSize2Leaf extends IfLeafIsUniversalThenSort implements ConceptTreeGrower {

	@Override
	protected void complyToAdditionalConstraints(DirectedAcyclicGraph<IConcept, IIsA> treeDAG,
			InvertedUpperSemilattice<IConcept, IIsA> searchSpace) {
		Set<Integer> searchSpaceParticularIDs = new HashSet<>();
		for (IConcept searchSpaceLeaf : getLeaves(searchSpace))
			searchSpaceParticularIDs.add(searchSpaceLeaf.iD());
		for(IConcept leaf : getLeaves(treeDAG)) {
			if (leaf.type() != ConceptType.PARTICULAR) {
				Set<Integer> extentIDs = Sets.intersection(leaf.getMaxExtentIDs(), searchSpaceParticularIDs);
				if (extentIDs.size() == 2) {
					for (Integer extentID : extentIDs) {
						IConcept particular = getConceptWithID(extentID, searchSpace);
						treeDAG.addVertex(particular);
						treeDAG.addEdge(particular, leaf);
					}
				}
			}
		}
	}

	private static IConcept getConceptWithID(int iD, InvertedUpperSemilattice<IConcept, IIsA> searchSpace) {
		for (IConcept concept : searchSpace.vertexSet()) {
			if (concept.iD() == iD)
				return concept;
		}
		return null;
	}

}
