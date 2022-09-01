package com.tregouet.occam.data.structures.representations.transitions;

import org.jgrapht.graph.DirectedAcyclicGraph;

import com.tregouet.occam.data.structures.automata.transition_functions.IPushdownAutomatonTF;
import com.tregouet.occam.data.structures.lambda_terms.IBindings;
import com.tregouet.occam.data.structures.representations.descriptions.differentiae.properties.computations.abstr_app.IAbstractionApplication;

public interface IRepresentationTransitionFunction extends
		IPushdownAutomatonTF<IAbstractionApplication, IBindings, IConceptTransitionIC, IConceptTransitionOIC, IConceptTransition> {

	DirectedAcyclicGraph<Integer, AConceptTransitionSet> asGraph();

}
