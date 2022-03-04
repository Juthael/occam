package com.tregouet.occam.alg.transition_function_gen.impl;

import java.io.IOException;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;
import java.util.TreeSet;

import org.jgrapht.graph.DirectedAcyclicGraph;

import com.tregouet.occam.alg.transition_function_gen.IStructureBasedTFSupplier;
import com.tregouet.occam.data.denotations.IConcept;
import com.tregouet.occam.data.denotations.IConcepts;
import com.tregouet.occam.data.abstract_machines.automatons.IIsomorphicAutomatons;
import com.tregouet.occam.data.abstract_machines.automatons.IAutomaton;
import com.tregouet.occam.data.abstract_machines.automatons.impl.IsomorphicAutomatons;
import com.tregouet.occam.data.abstract_machines.transition_functions.utils.TransitionFunctionValidator;
import com.tregouet.occam.data.abstract_machines.automatons.impl.Automaton;
import com.tregouet.occam.data.denotations.IDenotation;
import com.tregouet.occam.data.denotations.IIsA;
import com.tregouet.occam.data.languages.specific.IBasicProductionAsEdge;
import com.tregouet.tree_finder.algo.hierarchical_restriction.IHierarchicalRestrictionFinder;
import com.tregouet.tree_finder.algo.hierarchical_restriction.impl.RestrictorOpt;
import com.tregouet.tree_finder.data.Tree;

public class StructureBasedTFSupplier extends TransitionFunctionSupplier 
	implements IStructureBasedTFSupplier {

	private final TreeSet<IIsomorphicAutomatons> transFunctionSets = new TreeSet<>();
	private final Map<IConcept, String> objectDenotationSetToName = new HashMap<>();
	private Iterator<IIsomorphicAutomatons> ite;
	
	public StructureBasedTFSupplier(IConcepts concepts, 
			DirectedAcyclicGraph<IDenotation, IBasicProductionAsEdge> denotations) throws IOException {
		super(concepts, denotations);
		populateSetsOfRelatedTransFunctions();
		for (IConcept objDenotationSet : concepts.getObjectConcepts())
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
		while (conceptTreeSupplier.hasNext()) {
			Tree<IConcept, IIsA> currTreeOfDenotationSets = conceptTreeSupplier.next();
			IIsomorphicAutomatons currSetOfIsomorphicTransFunctions = new IsomorphicAutomatons(
					currTreeOfDenotationSets, objectDenotationSetToName);
			DirectedAcyclicGraph<IDenotation, IBasicProductionAsEdge> filteredDenotationGraph = 
					getDenotationGraphFilteredByTreeOfDenotationSets(currTreeOfDenotationSets, denotations);
			IHierarchicalRestrictionFinder<IDenotation, IBasicProductionAsEdge> denotationTreeSupplier = 
					new RestrictorOpt<>(filteredDenotationGraph, true);
			while (denotationTreeSupplier.hasNext()) {
				Tree<IDenotation, IBasicProductionAsEdge> denotationTree = denotationTreeSupplier.nextTransitiveReduction();
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
