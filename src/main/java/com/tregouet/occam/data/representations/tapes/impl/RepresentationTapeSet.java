package com.tregouet.occam.data.representations.tapes.impl;

import com.tregouet.occam.data.languages.specific.IFact;
import com.tregouet.occam.data.languages.specific.impl.Fact;
import com.tregouet.occam.data.representations.IConcept;
import com.tregouet.occam.data.representations.tapes.IRepresentationTapeSet;
import com.tregouet.occam.data.representations.tapes.IVarBinder;
import com.tregouet.occam.data.representations.transitions.IConceptTransition;

public class RepresentationTapeSet implements IRepresentationTapeSet {

	private IConcept state = null;
	private IFact inputTape = null;
	private IVarBinder stackTape = null;
	
	public RepresentationTapeSet() {
		inputTape = new Fact();
		stackTape = new VarBinder();
	}
	
	public RepresentationTapeSet(IConcept state, IFact inputTape, IVarBinder stackTape) {
		this.state = state;
		this.inputTape = inputTape;
		this.stackTape = stackTape;
	}
	
	@Override
	public void loadIntoNext(IConcept state) {
		// TODO Auto-generated method stub
	}

	@Override
	public IRepresentationTapeSet apply(IConceptTransition transition) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public RepresentationTapeSet copy() {
		// TODO Auto-generated method stub
		return null;
	}

}
