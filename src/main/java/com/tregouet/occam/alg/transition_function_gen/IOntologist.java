package com.tregouet.occam.alg.transition_function_gen;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

import org.jgrapht.Graphs;
import org.jgrapht.graph.DirectedAcyclicGraph;

import com.tregouet.occam.data.abstract_machines.automatons.IAutomaton;
import com.tregouet.occam.data.abstract_machines.automatons.descriptions.IGenusDifferentiaDefinition;
import com.tregouet.occam.data.abstract_machines.automatons.descriptions.impl.GenusDifferentiaDefinition;
import com.tregouet.occam.data.abstract_machines.states.IState;
import com.tregouet.occam.data.abstract_machines.transition_rules.IConjunctiveTransition;
import com.tregouet.occam.data.denotations.IDenotationSet;
import com.tregouet.occam.data.denotations.IIsA;
import com.tregouet.tree_finder.data.Tree;

public interface IOntologist {
	
	public static Tree<IState, IGenusDifferentiaDefinition> getPorphyrianTree(
			IAutomaton automaton){
		DirectedAcyclicGraph<IState, IGenusDifferentiaDefinition> porphyrianTree = 
				new DirectedAcyclicGraph<>(null, null, false);
		Graphs.addAllVertices(porphyrianTree, automaton.getStates());
		Tree<IDenotationSet, IIsA> treeOfDenotationSets = automaton.getTreeOfDenotationSets();
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
			IGenusDifferentiaDefinition genusDiff = 
					new GenusDifferentiaDefinition(speciesState, genusState, conjTransitions);
			porphyrianTree.addEdge(speciesState, genusState, genusDiff);
		}
		IState startState = automaton.getAssociatedStateOf(treeOfDenotationSets.getRoot());
		Set<IState> finalStates = new HashSet<>();
		List<IState> topoOrderedStates = new ArrayList<>();
		for (IDenotationSet denotationSet : treeOfDenotationSets.getTopologicalOrder()) {
			IState associatedState = automaton.getAssociatedStateOf(denotationSet);
			topoOrderedStates.add(associatedState);
			if (denotationSet.type() == IDenotationSet.OBJECT)
				finalStates.add(associatedState);
		}
		return new Tree<IState, IGenusDifferentiaDefinition>(porphyrianTree, startState, 
				finalStates, topoOrderedStates);
	}

}
