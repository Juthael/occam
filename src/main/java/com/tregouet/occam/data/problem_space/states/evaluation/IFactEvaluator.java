package com.tregouet.occam.data.problem_space.states.evaluation;

import com.tregouet.occam.data.logical_structures.automata.heads.IPushdownAutomatonHead;
import com.tregouet.occam.data.logical_structures.lambda_terms.IBindings;
import com.tregouet.occam.data.problem_space.states.descriptions.differentiae.properties.computations.applications.IAbstractionApplication;
import com.tregouet.occam.data.problem_space.states.evaluation.tapes.IFactTape;
import com.tregouet.occam.data.problem_space.states.evaluation.tapes.IRepresentationTapeSet;
import com.tregouet.occam.data.problem_space.states.transitions.IConceptTransition;
import com.tregouet.occam.data.problem_space.states.transitions.IConceptTransitionIC;
import com.tregouet.occam.data.problem_space.states.transitions.IConceptTransitionOIC;
import com.tregouet.occam.data.problem_space.states.transitions.IRepresentationTransitionFunction;

public interface IFactEvaluator extends
		IPushdownAutomatonHead<IRepresentationTransitionFunction, IAbstractionApplication, IBindings, IConceptTransitionIC, IConceptTransitionOIC, IConceptTransition, IRepresentationTapeSet, IFactTape, IFactEvaluator> {

	int getActiveStateID();

	IRepresentationTapeSet getTapeSet();

	IRepresentationTransitionFunction getTransitionFunction();

}
