package com.tregouet.occam.alg.conceptual_structure_gen;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.jgrapht.Graphs;
import org.jgrapht.graph.DirectedAcyclicGraph;

import com.tregouet.occam.data.abstract_machines.functions.ITransitionFunction;
import com.tregouet.occam.data.abstract_machines.transitions.ITransition;
import com.tregouet.occam.data.concepts.IConcept;
import com.tregouet.occam.data.concepts.IGenusDifferentiaDefinition;
import com.tregouet.occam.data.concepts.IIsA;
import com.tregouet.occam.data.concepts.impl.GenusDifferentiaDefinition;
import com.tregouet.tree_finder.data.Tree;

public interface IOntologist {
	
	public static Tree<IConcept, IGenusDifferentiaDefinition> getPorphyrianTree(
			ITransitionFunction transitionFunction){
		Tree<IConcept, IIsA> conceptTree = transitionFunction.getTreeOfConcepts();
		DirectedAcyclicGraph<IConcept, IGenusDifferentiaDefinition> porphyrianTree = 
				new DirectedAcyclicGraph<IConcept, IGenusDifferentiaDefinition>(IGenusDifferentiaDefinition.class);
		for (IConcept concept : conceptTree.vertexSet())
			porphyrianTree.addVertex(concept);
		Map<Integer, List<ITransition>> sourceIDToTransitions = new HashMap<>();
		for (ITransition transition : transitionFunction.getTransitions()) {
			Integer sourceID = transition.getOperatingState().getStateID();
			if (sourceIDToTransitions.containsKey(sourceID))
				sourceIDToTransitions.get(sourceID).add(transition);
			else {
				List<ITransition> transitions = new ArrayList<>();
				transitions.add(transition);
				sourceIDToTransitions.put(sourceID, transitions);
			}
		}
		for (IIsA isA : conceptTree.edgeSet()) {
			IConcept species = conceptTree.getEdgeSource(isA);
			IConcept genus = conceptTree.getEdgeTarget(isA);
			IGenusDifferentiaDefinition genusDiff = 
					new GenusDifferentiaDefinition(sourceIDToTransitions.get(species.getID()));
			//HERE
			try {
				porphyrianTree.addEdge(species, genus, genusDiff);
			}
			catch (Exception e) {
				System.out.println("here");
			}
		}
		return new Tree<IConcept, IGenusDifferentiaDefinition>(
				porphyrianTree, conceptTree.getRoot(), conceptTree.getLeaves(), conceptTree.getTopologicalOrder());
	}

}
