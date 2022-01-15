package com.tregouet.occam.alg.transition_function_gen.impl;

import java.io.IOException;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;
import java.util.TreeSet;

import org.jgrapht.graph.DirectedAcyclicGraph;

import com.tregouet.occam.alg.transition_function_gen.IStructureBasedTFSupplier;
import com.tregouet.occam.data.denotations.IDenotationSet;
import com.tregouet.occam.data.denotations.IDenotationSets;
import com.tregouet.occam.data.abstract_machines.automatons.IIsomorphicAutomatons;
import com.tregouet.occam.data.abstract_machines.automatons.IAutomaton;
import com.tregouet.occam.data.abstract_machines.automatons.impl.IsomorphicAutomatons;
import com.tregouet.occam.data.abstract_machines.transition_functions.utils.TransitionFunctionValidator;
import com.tregouet.occam.data.abstract_machines.automatons.impl.Automaton;
import com.tregouet.occam.data.denotations.IDenotation;
import com.tregouet.occam.data.denotations.IIsA;
import com.tregouet.occam.data.languages.specific.IEdgeProduction;
import com.tregouet.tree_finder.algo.hierarchical_restriction.IHierarchicalRestrictionFinder;
import com.tregouet.tree_finder.algo.hierarchical_restriction.impl.RestrictorOpt;
import com.tregouet.tree_finder.data.Tree;

public class StructureBasedTFSupplier extends TransitionFunctionSupplier 
	implements IStructureBasedTFSupplier {

	private final TreeSet<IIsomorphicAutomatons> transFunctionSets = new TreeSet<>();
	private final Map<IDenotationSet, String> objectDenotationSetToName = new HashMap<>();
	private Iterator<IIsomorphicAutomatons> ite;
	
	public StructureBasedTFSupplier(IDenotationSets denotationSets, 
			DirectedAcyclicGraph<IDenotation, IEdgeProduction> denotations) throws IOException {
		super(denotationSets, denotations);
		populateSetsOfRelatedTransFunctions();
		for (IDenotationSet objDenotationSet : denotationSets.getObjectDenotationSets())
			objectDenotationSetToName.put(objDenotationSet, objDenotationSet.getExtent().iterator().next().getName());
		ite = transFunctionSets.iterator();
	}

	@Override
	public String getObjectDenotationsAsString() {
		return IStructureBasedTFSupplier.getObjectsDenotationsAsString(objectDenotationSetToName);
	}

	@Override
	public IAutomaton getOptimalTransitionFunction() {
		return transFunctionSets.first().getOptimalAutomaton();
	}

	@Override
	public boolean hasNext() {
		return ite.hasNext();
	}
	
	@Override
	public IIsomorphicAutomatons next() {
		return ite.next();
	}
	
	@Override
	public void reset() {
		ite = transFunctionSets.iterator();
	}	
	
	private void populateSetsOfRelatedTransFunctions() {
		while (denotationSetsTreeSupplier.hasNext()) {
			Tree<IDenotationSet, IIsA> currTreeOfDenotationSets = denotationSetsTreeSupplier.next();
			IIsomorphicAutomatons currSetOfIsomorphicTransFunctions = new IsomorphicAutomatons(
					currTreeOfDenotationSets, objectDenotationSetToName);
			DirectedAcyclicGraph<IDenotation, IEdgeProduction> filteredDenotationGraph = 
					getDenotationGraphFilteredByTreeOfDenotationSets(currTreeOfDenotationSets, denotations);
			IHierarchicalRestrictionFinder<IDenotation, IEdgeProduction> denotationTreeSupplier = 
					new RestrictorOpt<>(filteredDenotationGraph, true);
			while (denotationTreeSupplier.hasNext()) {
				Tree<IDenotation, IEdgeProduction> denotationTree = denotationTreeSupplier.nextTransitiveReduction();
				IAutomaton automaton = new Automaton(
						currTreeOfDenotationSets, denotationTree);
				if (automaton.validate(TransitionFunctionValidator.INSTANCE))
					currSetOfIsomorphicTransFunctions.addIsomorphicAutomaton(automaton);
			}
			if (currSetOfIsomorphicTransFunctions.isValid()) {
				if (transFunctionSets.size() <= MAX_CAPACITY)
					transFunctionSets.add(currSetOfIsomorphicTransFunctions);	
				else if (currSetOfIsomorphicTransFunctions.getCoherenceScore() 
							> transFunctionSets.last().getCoherenceScore()) {
					transFunctionSets.add(currSetOfIsomorphicTransFunctions);
					transFunctionSets.pollLast();
				}
			}
		}
	}

}
