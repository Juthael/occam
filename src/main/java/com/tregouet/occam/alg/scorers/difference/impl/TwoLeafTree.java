package com.tregouet.occam.alg.scorers.difference.impl;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import org.jgrapht.graph.DirectedAcyclicGraph;

import com.tregouet.occam.alg.scorers.difference.DifferenceScorer;
import com.tregouet.occam.data.representations.classifications.IClassification;
import com.tregouet.occam.data.representations.classifications.concepts.IConcept;
import com.tregouet.occam.data.representations.classifications.concepts.IConceptLattice;
import com.tregouet.occam.data.representations.classifications.concepts.IIsA;
import com.tregouet.occam.data.representations.classifications.concepts.denotations.IDenotation;
import com.tregouet.occam.data.representations.classifications.concepts.impl.Concept;
import com.tregouet.occam.data.representations.classifications.concepts.impl.IsA;
import com.tregouet.occam.data.representations.descriptions.IDescription;
import com.tregouet.occam.data.representations.descriptions.differentiae.ADifferentiae;
import com.tregouet.occam.data.representations.productions.IContextualizedProduction;
import com.tregouet.occam.data.structures.languages.words.construct.IConstruct;
import com.tregouet.tree_finder.data.InvertedTree;
import com.tregouet.tree_finder.data.Tree;
import com.tregouet.tree_finder.utils.Functions;

public class TwoLeafTree implements DifferenceScorer {

	public static final TwoLeafTree INSTANCE = new TwoLeafTree();

	private TwoLeafTree() {
	}

	@Override
	public double score(int particularID1, int particularID2, IConceptLattice lattice) {
		InvertedTree<IConcept, IIsA> twoLeafTree = setUpClassificationTree(particularID1, particularID2, lattice);
		IClassification classification =
				DifferenceScorer.classificationBuilder().apply(twoLeafTree, lattice.getParticularID2Particular());
		Set<IContextualizedProduction> productions = DifferenceScorer.productionSetBuilder().apply(classification.normalized());
		IDescription description = DifferenceScorer.descriptionBuilder().apply(classification.normalized(), productions);
		Tree<Integer, ADifferentiae> descriptionAsGraph = description.asGraph();
		ADifferentiae concept1Diff = descriptionAsGraph.incomingEdgeOf(particularID1);
		return concept1Diff.weight();
	}

	private InvertedTree<IConcept, IIsA> setUpClassificationTree(
			int particularID1, int particularID2, IConceptLattice lattice) {
		Set<Integer> extentIDs = new HashSet<>();
		extentIDs.add(particularID1);
		extentIDs.add(particularID2);
		IConcept particular1 = null;
		IConcept particular2 = null;
		//retrieve vertices
		for (IConcept particular : lattice.getParticulars()) {
			if (particular.iD() == particularID1)
				particular1 = particular;
			else if (particular.iD() == particularID2)
				particular2 = particular;
		}
		IConcept ontologicalCommitment = copyConceptWithRestrictedExtent(lattice.getOntologicalCommitment(), extentIDs);
		IConcept genus = copyConceptWithRestrictedExtent(
				Functions.supremum(lattice.getOntologicalUpperSemilattice(), particular1, particular2),
				extentIDs) ;
		//build dag
		DirectedAcyclicGraph<IConcept, IIsA> dag = new DirectedAcyclicGraph<>(null, IsA::new, false);
		dag.addVertex(particular1);
		dag.addVertex(particular2);
		dag.addVertex(genus);
		dag.addVertex(ontologicalCommitment);
		dag.addEdge(particular1, genus);
		dag.addEdge(particular2, genus);
		dag.addEdge(genus, ontologicalCommitment);
		//build topo order
		List<IConcept> topoOrder = new ArrayList<>(5);
		topoOrder.add(particular1);
		topoOrder.add(particular2);
		topoOrder.add(genus);
		topoOrder.add(ontologicalCommitment);
		return new InvertedTree<>(
				dag, ontologicalCommitment,
				new HashSet<>(Arrays.asList(new IConcept[] {particular1, particular2})),
				topoOrder);
	}

	private static IConcept copyConceptWithRestrictedExtent(IConcept concept, Set<Integer> restrictedExtentIDs) {
		IConcept restrictedCopy;
		int nbOfDenotations = concept.getDenotations().size();
		IConstruct[] constructs = new IConstruct[nbOfDenotations];
		boolean[] redundant = new boolean[nbOfDenotations];
		int idx = 0;
		for (IDenotation denotation : concept.getDenotations()) {
			constructs[idx] = denotation.copy();
			redundant[idx] = denotation.isRedundant();
			idx++;
		}
		restrictedCopy = new Concept(constructs, redundant, restrictedExtentIDs, concept.iD());
		restrictedCopy.setType(concept.type());
		return restrictedCopy;
	}

}
