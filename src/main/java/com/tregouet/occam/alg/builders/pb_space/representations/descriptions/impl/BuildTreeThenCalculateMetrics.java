package com.tregouet.occam.alg.builders.pb_space.representations.descriptions.impl;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Set;

import org.jgrapht.graph.DirectedAcyclicGraph;
import org.jgrapht.traverse.TopologicalOrderIterator;

import com.tregouet.occam.alg.builders.pb_space.representations.descriptions.DescriptionBuilder;
import com.tregouet.occam.alg.builders.pb_space.representations.descriptions.utils.DifferentiaeRankSetter;
import com.tregouet.occam.alg.setters.differentiae_coeff.DifferentiaeCoeffSetter;
import com.tregouet.occam.alg.setters.weighs.differentiae.DifferentiaeWeigher;
import com.tregouet.occam.data.representations.descriptions.IDescription;
import com.tregouet.occam.data.representations.descriptions.impl.Description;
import com.tregouet.occam.data.representations.descriptions.metrics.ISimilarityMetrics;
import com.tregouet.occam.data.representations.descriptions.properties.AbstractDifferentiae;
import com.tregouet.occam.data.representations.transitions.IRepresentationTransitionFunction;
import com.tregouet.tree_finder.data.Tree;

public class BuildTreeThenCalculateMetrics implements DescriptionBuilder {

	public static final BuildTreeThenCalculateMetrics INSTANCE = new BuildTreeThenCalculateMetrics();

	private BuildTreeThenCalculateMetrics() {
	}

	@Override
	public IDescription apply(IRepresentationTransitionFunction transFunc, Map<Integer, Integer> particularID2MostSpecificConceptID) {
		Set<AbstractDifferentiae> differentiae;
		Tree<Integer, AbstractDifferentiae> classification;
		differentiae = DescriptionBuilder.differentiaeBuilder().apply(transFunc);
		//build parameter graph for the tree constructor
		DirectedAcyclicGraph<Integer, AbstractDifferentiae> paramTree = new DirectedAcyclicGraph<>(null, null, false);
		for (AbstractDifferentiae diff : differentiae) {
			Integer genusID = diff.getSource();
			Integer speciesID = diff.getTarget();
			paramTree.addVertex(genusID);
			paramTree.addVertex(speciesID);
			paramTree.addEdge(genusID, speciesID, diff);
		}
		//build other parameters for the tree constructor
		List<Integer> topoOrderOverConcepts = new ArrayList<>();
		new TopologicalOrderIterator<>(paramTree).forEachRemaining(topoOrderOverConcepts::add);
		Integer ontologicalCommitmentID = topoOrderOverConcepts.get(0);
		Set<Integer> particularIDs = transFunc.getAcceptStateIDs();
		//build classification tree
		classification = new Tree<>(paramTree, ontologicalCommitmentID, particularIDs, topoOrderOverConcepts);
		//rank differentiae in tree
		DifferentiaeRankSetter.INSTANCE.accept(classification);
		//weigh differentiae
		DifferentiaeCoeffSetter differentiaeCoeffSetter = DescriptionBuilder.differentiaeCoeffSetter()
				.setContext(classification);
		DifferentiaeWeigher differentiaeWeigher = DescriptionBuilder.differentiaeWeigher();
		for (AbstractDifferentiae diff : classification.edgeSet()) {
			differentiaeCoeffSetter.accept(diff);
			differentiaeWeigher.accept(diff);
		}
		//build metrics
		ISimilarityMetrics similarityMetrics = 
				DescriptionBuilder.similarityMetricsBuilder().apply(classification, particularID2MostSpecificConceptID);
		//instantiate
		IDescription description = new Description(classification, similarityMetrics);
		return description;
	}

}
