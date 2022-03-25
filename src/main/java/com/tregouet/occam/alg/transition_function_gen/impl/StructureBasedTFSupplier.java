package com.tregouet.occam.alg.transition_function_gen.impl;

import java.io.IOException;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;
import java.util.TreeSet;

import org.jgrapht.graph.DirectedAcyclicGraph;

import com.tregouet.occam.alg.transition_function_gen.IStructureBasedTFSupplier;
import com.tregouet.occam.data.automata.IAutomaton;
import com.tregouet.occam.data.automata.machines.IIsomorphicAutomatons;
import com.tregouet.occam.data.automata.machines.deprec.Automaton_dep;
import com.tregouet.occam.data.automata.machines.impl.IsomorphicAutomatons;
import com.tregouet.occam.data.automata.transition_functions.utils.TransitionFunctionValidator;
import com.tregouet.occam.data.concepts.IDenotation;
import com.tregouet.occam.data.concepts.IIsA;
import com.tregouet.occam.data.concepts.IConcept;
import com.tregouet.occam.data.concepts.IConceptLattice;
import com.tregouet.occam.data.languages.specific.IStronglyContextualized;
import com.tregouet.tree_finder.algo.hierarchical_restriction.IHierarchicalRestrictionFinder;
import com.tregouet.tree_finder.algo.hierarchical_restriction.impl.RestrictorOpt;
import com.tregouet.tree_finder.data.Tree;

public class StructureBasedTFSupplier extends TransitionFunctionSupplier 
	implements IStructureBasedTFSupplier {

	private final TreeSet<IIsomorphicAutomatons> transFunctionSets = new TreeSet<>();
	private final Map<IConcept, String> objectDenotationSetToName = new HashMap<>();
	private Iterator<IIsomorphicAutomatons> ite;
	
	public StructureBasedTFSupplier(IConceptLattice conceptLattice, 
			DirectedAcyclicGraph<IDenotation, IStronglyContextualized> denotations) throws IOException {
		super(conceptLattice, denotations);
		populateSetsOfRelatedTransFunctions();
		for (IConcept objDenotationSet : conceptLattice.getObjectConcepts())
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
		while (conceptTreeBuilder.hasNext()) {
			Tree<IConcept, IIsA> currTreeOfDenotationSets = conceptTreeBuilder.next();
			IIsomorphicAutomatons currSetOfIsomorphicTransFunctions = new IsomorphicAutomatons(
					currTreeOfDenotationSets, objectDenotationSetToName);
			DirectedAcyclicGraph<IDenotation, IStronglyContextualized> filteredDenotationGraph = 
					getDenotationGraphFilteredByTreeOfDenotationSets(currTreeOfDenotationSets, denotations);
			IHierarchicalRestrictionFinder<IDenotation, IStronglyContextualized> denotationTreeSupplier = 
					new RestrictorOpt<>(filteredDenotationGraph, true);
			while (denotationTreeSupplier.hasNext()) {
				Tree<IDenotation, IStronglyContextualized> denotationTree = denotationTreeSupplier.nextTransitiveReduction();
				IAutomaton automaton = new Automaton_dep(
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
