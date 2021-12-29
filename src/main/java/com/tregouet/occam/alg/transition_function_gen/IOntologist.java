package com.tregouet.occam.alg.transition_function_gen;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

import org.jgrapht.graph.DirectedAcyclicGraph;

import com.tregouet.occam.data.abstract_machines.functions.ITransitionFunction;
import com.tregouet.occam.data.abstract_machines.functions.descriptions.IGenusDifferentiaDefinition;
import com.tregouet.occam.data.abstract_machines.functions.descriptions.impl.GenusDifferentiaDefinition;
import com.tregouet.occam.data.abstract_machines.states.IState;
import com.tregouet.occam.data.abstract_machines.transitions.IConjunctiveTransition;
import com.tregouet.occam.data.concepts.IConcept;
import com.tregouet.occam.data.concepts.IIsA;
import com.tregouet.tree_finder.data.Tree;

public interface IOntologist {
	
	public static Tree<IState, IGenusDifferentiaDefinition> getPorphyrianTree(
			ITransitionFunction transitionFunction){
		DirectedAcyclicGraph<IState, IGenusDifferentiaDefinition> porphyrianTree = 
				new DirectedAcyclicGraph<>(null, null, false);
		Tree<IConcept, IIsA> conceptTree = transitionFunction.getTreeOfConcepts();
		Map<IState, List<IConjunctiveTransition>> sourceToTransitions = new HashMap<>();
		for (IConjunctiveTransition transition : transitionFunction.getConjunctiveTransitions()) {
			IState source = transition.getOperatingState();
			if (sourceToTransitions.containsKey(source))
				sourceToTransitions.get(source).add(transition);
			else {
				List<IConjunctiveTransition> transitions = new ArrayList<>();
				transitions.add(transition);
				sourceToTransitions.put(source, transitions);
			}
		}
		for (IIsA isA : conceptTree.edgeSet()) {
			IState species = transitionFunction.getAssociatedStateOf(conceptTree.getEdgeSource(isA));
			IState genus = transitionFunction.getAssociatedStateOf(conceptTree.getEdgeTarget(isA));
			IGenusDifferentiaDefinition genusDiff = 
					new GenusDifferentiaDefinition(species, genus, sourceToTransitions.get(species));
			porphyrianTree.addEdge(species, genus, genusDiff);
		}
		IState startState = transitionFunction.getAssociatedStateOf(conceptTree.getRoot());
		Set<IState> finalStates = new HashSet<>();
		List<IState> topoOrderedStates = new ArrayList<>();
		for (IConcept concept : conceptTree.getTopologicalOrder()) {
			IState associatedState = transitionFunction.getAssociatedStateOf(concept);
			topoOrderedStates.add(associatedState);
			if (concept.type() == IConcept.SINGLETON)
				finalStates.add(associatedState);
		}
		return new Tree<IState, IGenusDifferentiaDefinition>(porphyrianTree, startState, 
				finalStates, topoOrderedStates);
	}

}
