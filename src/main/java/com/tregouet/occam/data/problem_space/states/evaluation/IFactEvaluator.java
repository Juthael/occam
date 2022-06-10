package com.tregouet.occam.data.problem_space.states.evaluation;

import com.tregouet.occam.data.logical_structures.automata.heads.IPushdownAutomatonHead;
import com.tregouet.occam.data.logical_structures.languages.alphabets.AVariable;
import com.tregouet.occam.data.problem_space.states.descriptions.differentiae.properties.applications.IApplication;
import com.tregouet.occam.data.problem_space.states.evaluation.tapes.IFactTape;
import com.tregouet.occam.data.problem_space.states.evaluation.tapes.IRepresentationTapeSet;
import com.tregouet.occam.data.problem_space.states.productions.IProduction;
import com.tregouet.occam.data.problem_space.states.transitions.IConceptTransition;
import com.tregouet.occam.data.problem_space.states.transitions.IConceptTransitionIC;
import com.tregouet.occam.data.problem_space.states.transitions.IConceptTransitionOIC;
import com.tregouet.occam.data.problem_space.states.transitions.IRepresentationTransitionFunction;

public interface IFactEvaluator extends
		IPushdownAutomatonHead<IRepresentationTransitionFunction, IApplication, AVariable, IConceptTransitionIC, IConceptTransitionOIC, IConceptTransition, IRepresentationTapeSet, IFactTape, IFactEvaluator> {

	int getActiveStateID();

	IRepresentationTapeSet getTapeSet();

	IRepresentationTransitionFunction getTransitionFunction();

}
