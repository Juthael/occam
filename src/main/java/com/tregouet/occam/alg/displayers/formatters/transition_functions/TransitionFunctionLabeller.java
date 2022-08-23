package com.tregouet.occam.alg.displayers.formatters.transition_functions;

import java.util.function.Function;

import org.jgrapht.graph.DirectedAcyclicGraph;

import com.tregouet.occam.alg.displayers.formatters.FormattersAbstractFactory;
import com.tregouet.occam.alg.displayers.formatters.transition_functions.transitions.TransitionLabeller;
import com.tregouet.occam.data.structures.representations.transitions.AConceptTransitionSet;
import com.tregouet.occam.data.structures.representations.transitions.IRepresentationTransitionFunction;

public interface TransitionFunctionLabeller
		extends Function<IRepresentationTransitionFunction, DirectedAcyclicGraph<Integer, AConceptTransitionSet>> {

	public static TransitionLabeller transitionLabeller() {
		return FormattersAbstractFactory.INSTANCE.getTransitionLabeller();
	}

}
