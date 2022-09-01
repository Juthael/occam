package com.tregouet.occam.data.structures.representations.evaluation;

import com.tregouet.occam.data.structures.automata.heads.IPushdownAutomatonHead;
import com.tregouet.occam.data.structures.lambda_terms.IBindings;
import com.tregouet.occam.data.structures.representations.descriptions.differentiae.properties.computations.abstr_app.IAbstractionApplication;
import com.tregouet.occam.data.structures.representations.evaluation.tapes.IFactTape;
import com.tregouet.occam.data.structures.representations.evaluation.tapes.IRepresentationTapeSet;
import com.tregouet.occam.data.structures.representations.transitions.IConceptTransition;
import com.tregouet.occam.data.structures.representations.transitions.IConceptTransitionIC;
import com.tregouet.occam.data.structures.representations.transitions.IConceptTransitionOIC;
import com.tregouet.occam.data.structures.representations.transitions.IRepresentationTransitionFunction;

public interface IFactEvaluator extends
		IPushdownAutomatonHead<IRepresentationTransitionFunction, IAbstractionApplication, IBindings, IConceptTransitionIC, IConceptTransitionOIC, IConceptTransition, IRepresentationTapeSet, IFactTape, IFactEvaluator> {

	int getActiveStateID();

	IRepresentationTapeSet getTapeSet();

	IRepresentationTransitionFunction getTransitionFunction();

}
