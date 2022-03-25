package com.tregouet.occam.alg.transition_function_gen_dep;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

import org.jgrapht.Graphs;
import org.jgrapht.graph.DirectedAcyclicGraph;

import com.tregouet.occam.data.logical_structures.automata.IAutomaton;
import com.tregouet.occam.data.logical_structures.automata.machines.deprec.GenusDifferentiaDefinition_dep;
import com.tregouet.occam.data.logical_structures.automata.machines.deprec.IGenusDifferentiaDefinition_dep;
import com.tregouet.occam.data.logical_structures.automata.states.IState;
import com.tregouet.occam.data.logical_structures.automata.transition_functions.transitions.IConjunctiveTransition;
import com.tregouet.occam.data.representations.concepts.IConcept;
import com.tregouet.occam.data.representations.concepts.IIsA;
import com.tregouet.tree_finder.data.Tree;

public interface IOntologist {
	
	public static Tree<IState, IGenusDifferentiaDefinition_dep> getPorphyrianTree(
			IAutomaton automaton){
		DirectedAcyclicGraph<IState, IGenusDifferentiaDefinition_dep> porphyrianTree = 
				new DirectedAcyclicGraph<>(null, null, false);
		Graphs.addAllVertices(porphyrianTree, automaton.getStates());
		Tree<IConcept, IIsA> treeOfDenotationSets = automaton.getTreeOfDenotationSets();
		Map<IState, List<IConjunctiveTransition>> sourceToTransitions = new HashMap<>();
		for (IConjunctiveTransition transition : automaton.getConjunctiveTransitions()) {
			IState sourceState = transition.getInputState();
			if (sourceToTransitions.containsKey(sourceState))
				sourceToTransitions.get(sourceState).add(transition);
			else {
				List<IConjunctiveTransition> transitions = new ArrayList<>();
				transitions.add(transition);
				sourceToTransitions.put(sourceState, transitions);
			}
		}
		for (IIsA isA : treeOfDenotationSets.edgeSet()) {
			IState speciesState = automaton.getAssociatedStateOf(treeOfDenotationSets.getEdgeSource(isA));
			IState genusState = automaton.getAssociatedStateOf(treeOfDenotationSets.getEdgeTarget(isA));
			List<IConjunctiveTransition> conjTransitions = sourceToTransitions.get(speciesState);
			if (conjTransitions == null)
				//then the species is bypassed by all transitions.
				conjTransitions = new ArrayList<>();
			IGenusDifferentiaDefinition_dep genusDiff = 
					new GenusDifferentiaDefinition_dep(speciesState, genusState, conjTransitions);
			porphyrianTree.addEdge(speciesState, genusState, genusDiff);
		}
		IState startState = automaton.getAssociatedStateOf(treeOfDenotationSets.getRoot());
		Set<IState> finalStates = new HashSet<>();
		List<IState> topoOrderedStates = new ArrayList<>();
		for (IConcept concept : treeOfDenotationSets.getTopologicalOrder()) {
			IState associatedState = automaton.getAssociatedStateOf(concept);
			topoOrderedStates.add(associatedState);
			if (concept.type() == IConcept.OBJECT)
				finalStates.add(associatedState);
		}
		return new Tree<IState, IGenusDifferentiaDefinition_dep>(porphyrianTree, startState, 
				finalStates, topoOrderedStates);
	}

}
