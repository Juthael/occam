package com.tregouet.occam.alg.transition_function_gen.impl;

import java.io.IOException;
import java.util.Iterator;
import java.util.TreeSet;

import org.jgrapht.graph.DirectedAcyclicGraph;

import com.tregouet.occam.alg.transition_function_gen.IBasicTFSupplier;
import com.tregouet.occam.data.denotations.IDenotationSet;
import com.tregouet.occam.data.denotations.IDenotationSets;
import com.tregouet.occam.data.abstract_machines.automatons.IAutomaton;
import com.tregouet.occam.data.abstract_machines.automatons.impl.Automaton;
import com.tregouet.occam.data.abstract_machines.transition_functions.utils.TransitionFunctionValidator;
import com.tregouet.occam.data.denotations.IDenotation;
import com.tregouet.occam.data.denotations.IIsA;
import com.tregouet.occam.data.languages.specific.IEdgeProduction;
import com.tregouet.tree_finder.algo.hierarchical_restriction.IHierarchicalRestrictionFinder;
import com.tregouet.tree_finder.algo.hierarchical_restriction.impl.RestrictorOpt;
import com.tregouet.tree_finder.data.Tree;

public class BasicTFSupplier extends TransitionFunctionSupplier implements IBasicTFSupplier {

	private final TreeSet<IAutomaton> automatons = new TreeSet<>(functionComparator);
	private Iterator<IAutomaton> ite;
	
	public BasicTFSupplier(IDenotationSets denotationSets, DirectedAcyclicGraph<IDenotation, IEdgeProduction> constructs) 
			throws IOException {
		super(denotationSets, constructs);
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
		while (denotationSetsTreeSupplier.hasNext()) {
			Tree<IDenotationSet, IIsA> currTreeOfDenotationSets = denotationSetsTreeSupplier.next();
			DirectedAcyclicGraph<IDenotation, IEdgeProduction> filteredDenotationGraph = 
					getDenotationGraphFilteredByTreeOfDenotationSets(currTreeOfDenotationSets, denotations);
			IHierarchicalRestrictionFinder<IDenotation, IEdgeProduction> denotationTreeSupplier = 
					new RestrictorOpt<IDenotation, IEdgeProduction>(filteredDenotationGraph, true);
			while (denotationTreeSupplier.hasNext()) {
				Tree<IDenotation, IEdgeProduction> denotationTree = denotationTreeSupplier.nextTransitiveReduction();
				IAutomaton automaton = new Automaton(currTreeOfDenotationSets, denotationTree);
				if (automaton.validate(TransitionFunctionValidator.INSTANCE)) {
					automatons.add(automaton);
					if (automatons.size() > MAX_CAPACITY)
						automatons.remove(automatons.first());
				}
			}
		}
	}	
}
