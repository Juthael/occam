package com.tregouet.occam.alg.builders.problem_spaces.partial_representations.impl;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Set;

import org.jgrapht.Graphs;
import org.jgrapht.graph.DirectedAcyclicGraph;
import org.jgrapht.traverse.TopologicalOrderIterator;

import com.google.common.collect.Sets;
import com.tregouet.occam.alg.builders.problem_spaces.partial_representations.PartialRepresentationLateSetter;
import com.tregouet.occam.data.representations.ICompleteRepresentation;
import com.tregouet.occam.data.representations.IPartialRepresentation;
import com.tregouet.occam.data.representations.concepts.IConcept;
import com.tregouet.occam.data.representations.concepts.IIsA;
import com.tregouet.occam.data.representations.descriptions.IDescription;
import com.tregouet.occam.data.representations.evaluation.IFactEvaluator;
import com.tregouet.occam.data.representations.transitions.IConceptTransition;
import com.tregouet.occam.data.representations.transitions.IRepresentationTransitionFunction;
import com.tregouet.occam.data.representations.transitions.impl.RepresentationTransitionFunction;
import com.tregouet.tree_finder.data.InvertedTree;

public class InferNullParameters implements PartialRepresentationLateSetter {

	public static final InferNullParameters INSTANCE = new InferNullParameters();

	private InferNullParameters() {
	}

	private static InvertedTree<IConcept, IIsA> buildClassification(Set<ICompleteRepresentation> completions) {
		DirectedAcyclicGraph<IConcept, IIsA> tempClass = new DirectedAcyclicGraph<>(null, null, false);
		IConcept ontologicalCommitment = null;
		Set<IConcept> leaves = new HashSet<>();
		List<IConcept> topoOrderedSet = new ArrayList<>();
		Iterator<ICompleteRepresentation> repIte = completions.iterator();
		if (repIte.hasNext()) {
			InvertedTree<IConcept, IIsA> nextCompleteClass = repIte.next().getTreeOfConcepts();
			Graphs.addAllVertices(tempClass, nextCompleteClass.vertexSet());
			Graphs.addAllEdges(tempClass, nextCompleteClass, nextCompleteClass.edgeSet());
			ontologicalCommitment = nextCompleteClass.getRoot();
		}
		while (repIte.hasNext()) {
			tempClass.removeAllVertices(
					Sets.difference(tempClass.vertexSet(), repIte.next().getTreeOfConcepts().vertexSet()));
		}
		for (IConcept concept : tempClass.vertexSet()) {
			if (tempClass.inDegreeOf(concept) == 0)
				leaves.add(concept);
		}
		new TopologicalOrderIterator<>(tempClass).forEachRemaining(topoOrderedSet::add);
		return new InvertedTree<>(tempClass, ontologicalCommitment, leaves, topoOrderedSet);
	}

	private static IRepresentationTransitionFunction buildTransitionFunction(Set<ICompleteRepresentation> completions) {
		Set<IConceptTransition> transitions = new HashSet<>();
		Iterator<ICompleteRepresentation> repIte = completions.iterator();
		if (repIte.hasNext())
			transitions.addAll(repIte.next().getTransitionFunction().getTransitions());
		while (repIte.hasNext())
			transitions.retainAll(repIte.next().getTransitionFunction().getTransitions());
		return new RepresentationTransitionFunction(transitions);
	}

	@Override
	public void accept(IPartialRepresentation partialRepresentation) {
		IRepresentationTransitionFunction transFunc = buildTransitionFunction(
				partialRepresentation.getRepresentationCompletions());
		IFactEvaluator factEvaluator = PartialRepresentationLateSetter.getfactEvaluatorBuilder().apply(transFunc);
		InvertedTree<IConcept, IIsA> classification = buildClassification(
				partialRepresentation.getRepresentationCompletions());
		IDescription description = PartialRepresentationLateSetter.descriptionBuilder().apply(transFunc);
		partialRepresentation.setClassification(classification);
		partialRepresentation.setFactEvaluator(factEvaluator);
		partialRepresentation.setDescription(description);
	}

}
