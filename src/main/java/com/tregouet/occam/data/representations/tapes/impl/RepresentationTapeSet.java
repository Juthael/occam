package com.tregouet.occam.data.representations.tapes.impl;

import com.tregouet.occam.data.alphabets.generic.AVariable;
import com.tregouet.occam.data.alphabets.productions.IContextualizedProduction;
import com.tregouet.occam.data.automata.tapes.IInputTape;
import com.tregouet.occam.data.automata.tapes.IPushdownAutomatonTapeSet;
import com.tregouet.occam.data.automata.tapes.IStackTape;
import com.tregouet.occam.data.representations.IConcept;
import com.tregouet.occam.data.representations.tapes.IRepresentationTapeSet;
import com.tregouet.occam.data.representations.transitions.IConceptTransition;
import com.tregouet.occam.data.representations.transitions.IConceptTransitionIC;
import com.tregouet.occam.data.representations.transitions.IConceptTransitionOIC;

public class RepresentationTapeSet implements IRepresentationTapeSet {

	private IConcept state = null;
	private IInputTape<IContextualizedProduction> fact = null;
	private IStackTape<AVariable> dimension = null;
	
	public RepresentationTapeSet() {
	}
	
	public RepresentationTapeSet(IConcept state, IInputTape<IContextualizedProduction> fact, IStackTape<AVariable> dimension) {
		this.state = state;
		this.fact = fact;
		this.dimension = dimension;
	}
	
	@Override
	public void loadIntoNext(IConcept state) {
		// TODO Auto-generated method stub

	}

	@Override
	public IPushdownAutomatonTapeSet<
		IContextualizedProduction, 
		AVariable, 
		IInputTape<IContextualizedProduction>, 
		IConceptTransitionIC, 
		IConceptTransitionOIC, 
		IConceptTransition, 
		IConcept> apply(
			IConceptTransition transition) {
		// TODO Auto-generated method stub
		return null;
	}

}
