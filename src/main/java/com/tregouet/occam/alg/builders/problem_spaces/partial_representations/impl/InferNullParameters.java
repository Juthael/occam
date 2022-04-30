package com.tregouet.occam.alg.builders.problem_spaces.partial_representations.impl;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
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
import com.tregouet.occam.data.representations.descriptions.properties.AbstractDifferentiae;
import com.tregouet.occam.data.representations.evaluation.IFactEvaluator;
import com.tregouet.occam.data.representations.transitions.IConceptTransition;
import com.tregouet.occam.data.representations.transitions.IRepresentationTransitionFunction;
import com.tregouet.occam.data.representations.transitions.impl.RepresentationTransitionFunction;
import com.tregouet.tree_finder.data.InvertedTree;
import com.tregouet.tree_finder.data.Tree;
import com.tregouet.tree_finder.utils.Functions;

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
					new HashSet<>(
							Sets.difference(tempClass.vertexSet(), repIte.next().getTreeOfConcepts().vertexSet())));
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
		Map<Integer, Integer> particular2MostSpecificGenus = mapParticularToMostSpecificGenus(partialRepresentation);
		IDescription description = 
				PartialRepresentationLateSetter.descriptionBuilder().apply(transFunc, particular2MostSpecificGenus);
		partialRepresentation.setClassification(classification);
		partialRepresentation.setFactEvaluator(factEvaluator);
		partialRepresentation.setDescription(description);
	}
	
	private static Map<Integer, Integer> mapParticularToMostSpecificGenus(IPartialRepresentation partialRepresentation) {
		Map<Integer, Integer> particular2MostSpecificGenus = new HashMap<>();
		Tree<Integer, AbstractDifferentiae> partialTree = partialRepresentation.getDescription().asGraph();
		Tree<Integer, AbstractDifferentiae> anyCompletion = 
				partialRepresentation.getRepresentationCompletions().iterator().next().getDescription().asGraph();
		for (Integer particular : anyCompletion.getLeaves())
			particular2MostSpecificGenus.put(
					particular, 
					mostSpecificGenusInPartialClassification(particular, partialTree, anyCompletion));
		return particular2MostSpecificGenus;		
	}
	
	private static Integer mostSpecificGenusInPartialClassification(Integer particularID, 
			Tree<Integer, AbstractDifferentiae> partialTree, Tree<Integer, AbstractDifferentiae> anyCompletion) {
		Integer mostSpecificGenus = null;
		Iterator<Integer> leafIte = partialTree.getLeaves().iterator();
		while (mostSpecificGenus == null) {
			Integer nextLeaf = leafIte.next();
			if (Functions.upperSet(partialTree, nextLeaf).contains(particularID))
				mostSpecificGenus = nextLeaf;
		}
		return mostSpecificGenus;
	}

}
