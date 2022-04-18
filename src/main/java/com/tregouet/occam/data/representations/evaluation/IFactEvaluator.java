package com.tregouet.occam.data.representations.evaluation;

import com.tregouet.occam.data.logical_structures.automata.heads.IPushdownAutomatonHead;
import com.tregouet.occam.data.logical_structures.languages.alphabets.AVariable;
import com.tregouet.occam.data.representations.evaluation.tapes.IFactTape;
import com.tregouet.occam.data.representations.evaluation.tapes.IRepresentationTapeSet;
import com.tregouet.occam.data.representations.transitions.IConceptTransition;
import com.tregouet.occam.data.representations.transitions.IConceptTransitionIC;
import com.tregouet.occam.data.representations.transitions.IConceptTransitionOIC;
import com.tregouet.occam.data.representations.transitions.IRepresentationTransitionFunction;
import com.tregouet.occam.data.representations.transitions.productions.IContextualizedProduction;

public interface IFactEvaluator extends
	IPushdownAutomatonHead<
		IRepresentationTransitionFunction,
		IContextualizedProduction,
		AVariable,
		IConceptTransitionIC,
		IConceptTransitionOIC,
		IConceptTransition,
		IRepresentationTapeSet,
		IFactTape,
		IFactEvaluator> {

	int getActiveStateID();

	IRepresentationTapeSet getTapeSet();

	IRepresentationTransitionFunction getTransitionFunction();

}
