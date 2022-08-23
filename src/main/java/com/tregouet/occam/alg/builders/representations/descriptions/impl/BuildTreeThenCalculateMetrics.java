package com.tregouet.occam.alg.builders.representations.descriptions.impl;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import org.jgrapht.graph.DirectedAcyclicGraph;
import org.jgrapht.traverse.TopologicalOrderIterator;

import com.tregouet.occam.alg.builders.representations.descriptions.DescriptionBuilder;
import com.tregouet.occam.alg.builders.representations.descriptions.utils.DifferentiaeRankSetter;
import com.tregouet.occam.alg.setters.weighs.differentiae.DifferentiaeWeigher;
import com.tregouet.occam.alg.setters.weighs.differentiae.coeff.DifferentiaeCoeffSetter;
import com.tregouet.occam.data.structures.representations.classifications.IClassification;
import com.tregouet.occam.data.structures.representations.classifications.concepts.IConcept;
import com.tregouet.occam.data.structures.representations.descriptions.IDescription;
import com.tregouet.occam.data.structures.representations.descriptions.differentiae.ADifferentiae;
import com.tregouet.occam.data.structures.representations.descriptions.impl.Description;
import com.tregouet.occam.data.structures.representations.productions.IContextualizedProduction;
import com.tregouet.tree_finder.data.Tree;

public class BuildTreeThenCalculateMetrics implements DescriptionBuilder {

	public static final BuildTreeThenCalculateMetrics INSTANCE = new BuildTreeThenCalculateMetrics();

	private BuildTreeThenCalculateMetrics() {
	}

	@Override
	public IDescription apply(IClassification classification, Set<IContextualizedProduction> productions) {
		Set<ADifferentiae> differentiae;
		Tree<Integer, ADifferentiae> descGraph;
		differentiae = DescriptionBuilder.differentiaeBuilder().apply(classification, productions);
		//build parameter graph for the tree constructor
		DirectedAcyclicGraph<Integer, ADifferentiae> paramTree = new DirectedAcyclicGraph<>(null, null, false);
		for (ADifferentiae diff : differentiae) {
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
		Set<Integer> mostSpecificConceptIDs = getMostSpecificConceptIDs(classification);
		//build classification tree
		descGraph = new Tree<>(paramTree, ontologicalCommitmentID, mostSpecificConceptIDs, topoOrderOverConcepts);
		//rank differentiae in tree
		DifferentiaeRankSetter.INSTANCE.accept(descGraph);
		//weigh differentiae
		DifferentiaeCoeffSetter differentiaeCoeffSetter = DescriptionBuilder.differentiaeCoeffSetter()
				.setContext(classification.asGraph());
		DifferentiaeWeigher differentiaeWeigher = DescriptionBuilder.differentiaeWeigher();
		for (ADifferentiae diff : descGraph.edgeSet()) {
			differentiaeCoeffSetter.accept(diff);
			differentiaeWeigher.accept(diff);
		}
		//instantiate
		IDescription description = new Description(descGraph);
		return description;
	}

	private Set<Integer> getMostSpecificConceptIDs(IClassification classification){
		Set<Integer> mostSpecificConceptIDs = new HashSet<>();
		for (IConcept leaf : classification.asGraph().getLeaves())
			mostSpecificConceptIDs.add(leaf.iD());
		return mostSpecificConceptIDs;
	}

}
