package com.tregouet.occam.alg.builders.pb_space.impl;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Set;

import org.jgrapht.graph.DirectedAcyclicGraph;

import com.tregouet.occam.alg.builders.pb_space.ProblemSpaceExplorer;
import com.tregouet.occam.data.problem_spaces.AProblemStateTransition;
import com.tregouet.occam.data.representations.IRepresentation;
import com.tregouet.occam.data.representations.concepts.IConcept;
import com.tregouet.occam.data.representations.concepts.IConceptLattice;
import com.tregouet.occam.data.representations.concepts.IContextObject;
import com.tregouet.occam.data.representations.concepts.IIsA;
import com.tregouet.occam.data.representations.transitions.productions.IContextualizedProduction;
import com.tregouet.tree_finder.data.InvertedTree;

public class WithClusteringComesMeaning implements ProblemSpaceExplorer {
	
	private IConceptLattice conceptLattice;
	private Set<IContextualizedProduction> productions;
	
	public WithClusteringComesMeaning() {
	}

	@Override
	public DirectedAcyclicGraph<IRepresentation, AProblemStateTransition> apply(Integer t) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public DirectedAcyclicGraph<IRepresentation, AProblemStateTransition> initialize(
			Collection<IContextObject> context) {
		conceptLattice = ProblemSpaceExplorer.getConceptLatticeBuilder().apply(context);
		productions = ProblemSpaceExplorer.getProductionBuilder().apply(conceptLattice);
		InvertedTree<IConcept, IIsA> initialTree = 
				new ArrayList<InvertedTree<IConcept, IIsA>>(
						ProblemSpaceExplorer.getConceptTreeGrower().apply(conceptLattice, null)).get(0);
		IRepresentation initialRepresentation = ProblemSpaceExplorer.getRepresentationBuilder().apply(initialTree, productions);
		DirectedAcyclicGraph<IRepresentation, AProblemStateTransition> problemGraph = new DirectedAcyclicGraph<>(null, null, true);
		problemGraph.addVertex(initialRepresentation);
		return problemGraph;
	}

}
