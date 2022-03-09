package com.tregouet.occam.data.concepts;

import com.tregouet.occam.data.alphabets.productions.impl.ContextualizedProduction;
import com.tregouet.occam.data.automata.states.IPushdownAutomatonState;
import com.tregouet.occam.data.languages.generic.AVariable;

public interface IConcept extends IPushdownAutomatonState<ContextualizedProduction, AVariable> {

}
