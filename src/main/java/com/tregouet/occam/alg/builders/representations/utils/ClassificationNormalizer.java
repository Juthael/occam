package com.tregouet.occam.alg.builders.representations.utils;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Set;

import org.jgrapht.graph.DirectedAcyclicGraph;

import com.tregouet.occam.alg.builders.representations.production_sets.productions.utils.MapVariablesToValues;
import com.tregouet.occam.data.representations.classifications.IClassification;
import com.tregouet.occam.data.representations.classifications.concepts.ConceptType;
import com.tregouet.occam.data.representations.classifications.concepts.IConcept;
import com.tregouet.occam.data.representations.classifications.concepts.IIsA;
import com.tregouet.occam.data.representations.classifications.concepts.impl.IsA;
import com.tregouet.occam.data.representations.classifications.impl.NormalizedClassification;
import com.tregouet.occam.data.structures.languages.alphabets.AVariable;
import com.tregouet.occam.data.structures.languages.alphabets.ISymbol;
import com.tregouet.occam.data.structures.languages.words.construct.IConstruct;
import com.tregouet.tree_finder.data.InvertedTree;

/**
 * Ensures that two denotations from different concepts cannot differ by the names of their variables only
 * @author Gael Tregouet
 *
 */
public interface ClassificationNormalizer {

	public static NormalizedClassification normalize(IClassification classification){
		InvertedTree<IConcept, IIsA> conceptTree = classification.asGraph();
		List<Integer> topoOrderIDs = new ArrayList<>();
		List<List<Integer>> topoOrderedStrictLowerSets = new ArrayList<>();
		List<List<IConstruct>> topoOrderedConstructSets = new ArrayList<>();
		//populate lists
		for (IConcept concept : conceptTree.getTopologicalOrder()) {
			if (concept.type() != ConceptType.PARTICULAR) {
				List<Integer> ancestorIDs = new ArrayList<>();
				for (IConcept ancestor : conceptTree.getAncestors(concept)) {
					if (ancestor.type() != ConceptType.PARTICULAR)
						ancestorIDs.add(ancestor.iD());
				}
				topoOrderIDs.add(concept.iD());
				topoOrderedStrictLowerSets.add(ancestorIDs);
				List<IConstruct> orderedConstructs = new ArrayList<>(concept.getDenotations());
				//Need of a stable arbitrary order
				orderedConstructs.sort((x, y) -> System.identityHashCode(x) - System.identityHashCode(y));
				topoOrderedConstructSets.add(orderedConstructs);
			}
		}
		//map replaced to substitute
		Map<AVariable, AVariable> replaced2Substitute = new HashMap<>();
		for (int i = 0 ; i < topoOrderIDs.size() ; i++) {
			for (IConstruct iConstruct : topoOrderedConstructSets.get(i)) {
				if (iConstruct.isAbstract()) {
					int iConstructNbOfTerm = iConstruct.getNbOfTerminals();
					for (Integer j : topoOrderedStrictLowerSets.get(i)) {
						for (IConstruct jConstruct : topoOrderedConstructSets.get(topoOrderIDs.indexOf(j))) {
							if (jConstruct.isAbstract()) {
								int jConstructNbOfTerm = jConstruct.getNbOfTerminals();
								if (jConstructNbOfTerm > iConstructNbOfTerm
										|| (jConstructNbOfTerm == iConstructNbOfTerm && !iConstruct.equals(jConstruct)))
									updateMapIfNeeded(iConstruct, jConstruct, replaced2Substitute);
							}
						}
					}
				}
			}
		}
		//build normalized tree
		DirectedAcyclicGraph<IConcept, IIsA> normalizedDAG = new DirectedAcyclicGraph<>(null, IsA::new, false);
		List<IConcept> normalizedTopoOrder = new ArrayList<>();
		Map<Integer, IConcept> iD2NormalizedConcept = new HashMap<>();
		for (IConcept concept : conceptTree.getTopologicalOrder()) {
			IConcept normalizedConcept = ConceptNormalizer.normalize(concept, replaced2Substitute);
			normalizedTopoOrder.add(normalizedConcept);
			normalizedDAG.addVertex(normalizedConcept);
			iD2NormalizedConcept.put(normalizedConcept.iD(), normalizedConcept);
		}
		for (IIsA isA : conceptTree.edgeSet()) {
			IConcept source = iD2NormalizedConcept.get(conceptTree.getEdgeSource(isA).iD());
			IConcept target = iD2NormalizedConcept.get(conceptTree.getEdgeTarget(isA).iD());
			normalizedDAG.addEdge(source, target);
		}
		IConcept normalizedRoot = iD2NormalizedConcept.get(conceptTree.getRoot().iD());
		Set<IConcept> normalizedleaves = new HashSet<>();
		for (IConcept leaf : conceptTree.getLeaves())
			normalizedleaves.add(iD2NormalizedConcept.get(leaf.iD()));
		InvertedTree<IConcept, IIsA> normalizedTree =
				new InvertedTree<>(normalizedDAG, normalizedRoot, normalizedleaves, normalizedTopoOrder);
		return new NormalizedClassification(normalizedTree, classification.mapConceptID2ExtentIDs(),
				classification.mapSpeciesID2GenusID(), classification.getParticularID2Particular(),
				classification.isFullyDeveloped(), classification.expansionIsRestricted());
	}

	private static void doUpdateMap(AVariable replaced, AVariable substitute, Map<AVariable, AVariable> replaced2Substitute) {
		Set<AVariable> replacedByReplaced = new HashSet<>();
		for (Entry<AVariable, AVariable> mapEntry : replaced2Substitute.entrySet()) {
			if (mapEntry.getValue().equals(replaced))
				replacedByReplaced.add(mapEntry.getKey());
		}
		for (AVariable repByRep : replacedByReplaced)
			replaced2Substitute.put(repByRep, substitute);
		replaced2Substitute.put(replaced, substitute);
	}

	private static void updateMapIfNeeded(IConstruct iConstruct, IConstruct jConstruct,
			Map<AVariable, AVariable> replaced2Substitute) {
		Map<AVariable, List<ISymbol>> var2Values = MapVariablesToValues.of(jConstruct.asList(), iConstruct.asList());
		if (var2Values != null) {
			for (Entry<AVariable, List<ISymbol>> var2Value : var2Values.entrySet()) {
				List<ISymbol> value = var2Value.getValue();
				if (value.size() == 1) {
					ISymbol symbolValue = value.get(0);
					if (symbolValue instanceof AVariable) {
						AVariable replaced = (AVariable) symbolValue;
						AVariable substitute = var2Value.getKey();
						if (!replaced.equals(substitute)) {
							doUpdateMap(replaced, substitute, replaced2Substitute);
						}
					}
				}
			}
		}
	}

}
