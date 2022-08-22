package com.tregouet.occam.data.representations.transitions;

import com.tregouet.occam.data.representations.descriptions.differentiae.properties.computations.abstr_app.IAbstractionApplication;
import com.tregouet.occam.data.structures.automata.transition_functions.transitions.input_config.IPushdownAutomatonIC;
import com.tregouet.occam.data.structures.lambda_terms.IBindings;

public interface IConceptTransitionIC extends IPushdownAutomatonIC<IAbstractionApplication, IBindings> {

}
