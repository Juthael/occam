package com.tregouet.occam.alg.scorers.difference.impl;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import org.jgrapht.graph.DirectedAcyclicGraph;

import com.tregouet.occam.alg.scorers.difference.DifferenceScorer;
import com.tregouet.occam.data.problem_space.states.classifications.IClassification;
import com.tregouet.occam.data.problem_space.states.classifications.concepts.IConcept;
import com.tregouet.occam.data.problem_space.states.classifications.concepts.IConceptLattice;
import com.tregouet.occam.data.problem_space.states.classifications.concepts.IIsA;
import com.tregouet.occam.data.problem_space.states.classifications.concepts.impl.IsA;
import com.tregouet.occam.data.problem_space.states.descriptions.IDescription;
import com.tregouet.occam.data.problem_space.states.descriptions.differentiae.ADifferentiae;
import com.tregouet.occam.data.problem_space.states.productions.IContextualizedProduction;
import com.tregouet.tree_finder.data.InvertedTree;
import com.tregouet.tree_finder.data.Tree;
import com.tregouet.tree_finder.utils.Functions;

public class TwoLeafTree implements DifferenceScorer {
	
	public static final TwoLeafTree INSTANCE = new TwoLeafTree();
	
	private TwoLeafTree() {
	}

	@Override
	public double score(int particularID1, int particularID2, IConceptLattice lattice) {
		Set<Integer> particularIDs = new HashSet<>();
		particularIDs.add(particularID1);
		particularIDs.add(particularID2);
		InvertedTree<IConcept, IIsA> twoLeafTree = setUpClassificationTree(particularID1, particularID2, lattice);
		IClassification classification = DifferenceScorer.classificationBuilder().apply(twoLeafTree, particularIDs);
		Set<IContextualizedProduction> productions = DifferenceScorer.productionSetBuilder().apply(classification.normalized());
		IDescription description = DifferenceScorer.descriptionBuilder().apply(classification.normalized(), productions);
		Tree<Integer, ADifferentiae> descriptionAsGraph = description.asGraph();
		ADifferentiae concept1Diff = descriptionAsGraph.incomingEdgeOf(particularID1);
		return concept1Diff.weight();
	}
	
	private InvertedTree<IConcept, IIsA> setUpClassificationTree(int particularID1, int particularID2, IConceptLattice lattice) {
		IConcept particular1 = null;
		IConcept particular2 = null;
		//retrieve vertices
		for (IConcept particular : lattice.getParticulars()) {
			if (particular.iD() == particularID1)
				particular1 = particular;
			else if (particular.iD() == particularID2)
				particular2 = particular;
		}
		IConcept truism = lattice.getTruism();
		IConcept ontologicalCommitment = lattice.getOntologicalCommitment();
		IConcept genus = Functions.supremum(lattice.getOntologicalUpperSemilattice(), particular1, particular2);
		//build dag
		DirectedAcyclicGraph<IConcept, IIsA> dag = new DirectedAcyclicGraph<IConcept, IIsA>(null, IsA::new, false);
		dag.addVertex(particular1);
		dag.addVertex(particular2);
		dag.addVertex(genus);
		dag.addVertex(truism);
		dag.addVertex(ontologicalCommitment);
		dag.addEdge(particular1, genus);
		dag.addEdge(particular2, genus);
		boolean genusIsTruism = genus.equals(truism);
		if (!genusIsTruism)
			dag.addEdge(genus, truism);
		dag.addEdge(truism, ontologicalCommitment);
		//build topo order
		List<IConcept> topoOrder = new ArrayList<>(5);
		topoOrder.add(particular1);
		topoOrder.add(particular2);
		topoOrder.add(genus);
		if (!genusIsTruism)
			topoOrder.add(truism);
		topoOrder.add(ontologicalCommitment);
		return new InvertedTree<IConcept, IIsA>(dag, ontologicalCommitment,	new HashSet<>(lattice.getParticulars()), topoOrder);
	}

}
