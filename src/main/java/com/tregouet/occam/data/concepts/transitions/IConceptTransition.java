package com.tregouet.occam.data.concepts.transitions;

import com.tregouet.occam.data.alphabets.productions.IContextualizedProduction;
import com.tregouet.occam.data.automata.transitions.IPushdownAutomatonTransition;
import com.tregouet.occam.data.languages.generic.AVariable;

public interface IConceptTransition extends IPushdownAutomatonTransition<IContextualizedProduction, AVariable> {

}
