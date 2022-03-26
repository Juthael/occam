package com.tregouet.occam.alg.builders.representations.descriptions.impl;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;

import org.jgrapht.graph.DirectedAcyclicGraph;
import org.jgrapht.traverse.TopologicalOrderIterator;

import com.tregouet.occam.alg.builders.representations.descriptions.DescriptionBuilder;
import com.tregouet.occam.data.representations.descriptions.IDescription;
import com.tregouet.occam.data.representations.properties.AbstractDifferentiae;
import com.tregouet.occam.data.representations.properties.transitions.IRepresentationTransitionFunction;
import com.tregouet.tree_finder.data.Tree;

public class BuildThenWeigh implements DescriptionBuilder {
	
	private Set<Integer> conceptIDs;
	private Set<AbstractDifferentiae> differentiae;
	private Tree<Integer, AbstractDifferentiae> classification;
	
	public BuildThenWeigh() {
	}

	@Override
	public IDescription apply(IRepresentationTransitionFunction transFunc) {
		differentiae = DescriptionBuilder.getDifferentiaeBuilder().apply(transFunc);
		DirectedAcyclicGraph<Integer, AbstractDifferentiae> tempGraph = new DirectedAcyclicGraph<>(null, null, false);
		for (AbstractDifferentiae diff : differentiae) {
			Integer genusID = diff.getSource();
			Integer speciesID = diff.getTarget();
			tempGraph.addVertex();
			tempGraph.addVertex();
			tempGraph.addEdge(genusID, speciesID, diff);
		}
		List<Integer> topoOrderOverConcepts = new ArrayList<>();
		new TopologicalOrderIterator<Integer, AbstractDifferentiae>(tempGraph).forEachRemaining(topoOrderOverConcepts::add);
		Integer truismID = topoOrderOverConcepts.get(topoOrderOverConcepts.size() - 1);
		Set<Integer> particularIDs = transFunc.getAcceptStateIDs();
		classification = new Tree<Integer, AbstractDifferentiae>(tempGraph, truismID, particularIDs, topoOrderOverConcepts);
	}

}
