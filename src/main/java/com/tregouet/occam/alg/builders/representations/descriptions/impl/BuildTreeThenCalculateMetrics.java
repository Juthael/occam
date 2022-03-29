package com.tregouet.occam.alg.builders.representations.descriptions.impl;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;

import org.jgrapht.graph.DirectedAcyclicGraph;
import org.jgrapht.traverse.TopologicalOrderIterator;

import com.tregouet.occam.alg.builders.representations.descriptions.DescriptionBuilder;
import com.tregouet.occam.alg.setters.parameters.differentiae_coeff.DifferentiaeCoeffSetter;
import com.tregouet.occam.alg.setters.weighs.differentiae.DifferentiaeWeigher;
import com.tregouet.occam.data.representations.descriptions.IDescription;
import com.tregouet.occam.data.representations.descriptions.impl.Description;
import com.tregouet.occam.data.representations.descriptions.metrics.ISimilarityMetrics;
import com.tregouet.occam.data.representations.properties.AbstractDifferentiae;
import com.tregouet.occam.data.representations.properties.transitions.IRepresentationTransitionFunction;
import com.tregouet.tree_finder.data.Tree;

public class BuildTreeThenCalculateMetrics implements DescriptionBuilder {
	
	public static final BuildTreeThenCalculateMetrics INSTANCE = new BuildTreeThenCalculateMetrics();
	
	private BuildTreeThenCalculateMetrics() {
	}

	@Override
	public IDescription apply(IRepresentationTransitionFunction transFunc) {
		Set<AbstractDifferentiae> differentiae;
		Tree<Integer, AbstractDifferentiae> classification;
		differentiae = DescriptionBuilder.getDifferentiaeBuilder().apply(transFunc);
		DirectedAcyclicGraph<Integer, AbstractDifferentiae> paramTree = new DirectedAcyclicGraph<>(null, null, false);
		for (AbstractDifferentiae diff : differentiae) {
			Integer genusID = diff.getSource();
			Integer speciesID = diff.getTarget();
			paramTree.addVertex(genusID);
			paramTree.addVertex(speciesID);
			paramTree.addEdge(genusID, speciesID, diff);
		}
		List<Integer> topoOrderOverConcepts = new ArrayList<>();
		new TopologicalOrderIterator<Integer, AbstractDifferentiae>(paramTree).forEachRemaining(topoOrderOverConcepts::add);
		Integer ontologicalCommitmentID = topoOrderOverConcepts.get(0);
		Set<Integer> particularIDs = transFunc.getAcceptStateIDs();
		classification = 
				new Tree<Integer, AbstractDifferentiae>(
						paramTree, ontologicalCommitmentID, particularIDs, topoOrderOverConcepts);
		DifferentiaeCoeffSetter differentiaeCoeffSetter = 
				DescriptionBuilder.getDifferentiaeCoeffSetter().setContext(classification);
		DifferentiaeWeigher differentiaeWeigher = DescriptionBuilder.getDifferentiaeWeigher();
		for (AbstractDifferentiae diff : classification.edgeSet()) {
			differentiaeCoeffSetter.accept(diff);
			differentiaeWeigher.accept(diff);
		}
		ISimilarityMetrics similarityMetrics = DescriptionBuilder.getSimilarityMetricsBuilder().apply(classification);
		IDescription description = new Description(classification, similarityMetrics);
		return description;
	}

}
