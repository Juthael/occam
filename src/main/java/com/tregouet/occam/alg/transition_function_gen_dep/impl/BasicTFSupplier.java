package com.tregouet.occam.alg.transition_function_gen_dep.impl;

import java.io.IOException;
import java.util.Iterator;
import java.util.TreeSet;

import org.jgrapht.graph.DirectedAcyclicGraph;

import com.tregouet.occam.alg.transition_function_gen_dep.IBasicTFSupplier;
import com.tregouet.occam.data.languages.words.fact.IStronglyContextualized;
import com.tregouet.occam.data.logical_structures.automata.IAutomaton;
import com.tregouet.occam.data.logical_structures.automata.machines.deprec.Automaton_dep;
import com.tregouet.occam.data.logical_structures.automata.transition_functions.utils.TransitionFunctionValidator;
import com.tregouet.occam.data.representations.concepts.IConcept;
import com.tregouet.occam.data.representations.concepts.IConceptLattice;
import com.tregouet.occam.data.representations.concepts.IDenotation;
import com.tregouet.occam.data.representations.concepts.IIsA;
import com.tregouet.tree_finder.algo.hierarchical_restriction.IHierarchicalRestrictionFinder;
import com.tregouet.tree_finder.algo.hierarchical_restriction.impl.RestrictorOpt;
import com.tregouet.tree_finder.data.InvertedTree;

public class BasicTFSupplier extends TransitionFunctionSupplier implements IBasicTFSupplier {

	private final TreeSet<IAutomaton> automatons = new TreeSet<>(functionComparator);
	private Iterator<IAutomaton> ite;
	
	public BasicTFSupplier(IConceptLattice conceptLattice, DirectedAcyclicGraph<IDenotation, IStronglyContextualized> constructs) 
			throws IOException {
		super(conceptLattice, constructs);
		populateTransitionFunctions();
		ite = automatons.iterator();
	}
	
	@Override
	public IAutomaton getOptimalTransitionFunction() {
		return automatons.first();
	}
	
	@Override
	public boolean hasNext() {
		return ite.hasNext();
	}

	@Override
	public IAutomaton next() {
		return ite.next();
	}

	@Override
	public void reset() {
		ite = automatons.iterator();
	}

	private void populateTransitionFunctions() {
		while (conceptTreeBuilder.hasNext()) {
			InvertedTree<IConcept, IIsA> currTreeOfDenotationSets = conceptTreeBuilder.next();
			DirectedAcyclicGraph<IDenotation, IStronglyContextualized> filteredDenotationGraph = 
					getDenotationGraphFilteredByTreeOfDenotationSets(currTreeOfDenotationSets, denotations);
			IHierarchicalRestrictionFinder<IDenotation, IStronglyContextualized> denotationTreeSupplier = 
					new RestrictorOpt<IDenotation, IStronglyContextualized>(filteredDenotationGraph, true);
			while (denotationTreeSupplier.hasNext()) {
				InvertedTree<IDenotation, IStronglyContextualized> denotationTree = denotationTreeSupplier.nextTransitiveReduction();
				IAutomaton automaton = new Automaton_dep(currTreeOfDenotationSets, denotationTree);
				if (automaton.validate(TransitionFunctionValidator.INSTANCE)) {
					automatons.add(automaton);
					if (automatons.size() > MAX_CAPACITY)
						automatons.remove(automatons.first());
				}
			}
		}
	}	
}
