package com.tregouet.occam.alg.transition_function_gen.impl;

import java.io.IOException;
import java.util.Iterator;
import java.util.TreeSet;

import org.jgrapht.graph.DirectedAcyclicGraph;

import com.tregouet.occam.alg.transition_function_gen.IBasicTFSupplier;
import com.tregouet.occam.data.abstract_machines.automatons.IAutomaton;
import com.tregouet.occam.data.abstract_machines.automatons.impl.Automaton;
import com.tregouet.occam.data.abstract_machines.transition_functions.utils.TransitionFunctionValidator;
import com.tregouet.occam.data.concepts.IConcept;
import com.tregouet.occam.data.concepts.IConcepts;
import com.tregouet.occam.data.concepts.IDenotation;
import com.tregouet.occam.data.concepts.IIsA;
import com.tregouet.occam.data.languages.specific.IProductionAsEdge;
import com.tregouet.tree_finder.algo.hierarchical_restriction.IHierarchicalRestrictionFinder;
import com.tregouet.tree_finder.algo.hierarchical_restriction.impl.RestrictorOpt;
import com.tregouet.tree_finder.data.Tree;

public class BasicTFSupplier extends TransitionFunctionSupplier implements IBasicTFSupplier {

	private final TreeSet<IAutomaton> automatons = new TreeSet<>(functionComparator);
	private Iterator<IAutomaton> ite;
	
	public BasicTFSupplier(IConcepts concepts, DirectedAcyclicGraph<IDenotation, IProductionAsEdge> constructs) 
			throws IOException {
		super(concepts, constructs);
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
		while (conceptTreeSupplier.hasNext()) {
			Tree<IConcept, IIsA> currTreeOfDenotationSets = conceptTreeSupplier.next();
			DirectedAcyclicGraph<IDenotation, IProductionAsEdge> filteredDenotationGraph = 
					getDenotationGraphFilteredByTreeOfDenotationSets(currTreeOfDenotationSets, denotations);
			IHierarchicalRestrictionFinder<IDenotation, IProductionAsEdge> denotationTreeSupplier = 
					new RestrictorOpt<IDenotation, IProductionAsEdge>(filteredDenotationGraph, true);
			while (denotationTreeSupplier.hasNext()) {
				Tree<IDenotation, IProductionAsEdge> denotationTree = denotationTreeSupplier.nextTransitiveReduction();
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
