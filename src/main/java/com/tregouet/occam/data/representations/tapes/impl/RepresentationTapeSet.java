package com.tregouet.occam.data.representations.tapes.impl;

import java.util.Objects;

import com.tregouet.occam.data.alphabets.generic.AVariable;
import com.tregouet.occam.data.languages.specific.IFact;
import com.tregouet.occam.data.languages.specific.impl.Fact;
import com.tregouet.occam.data.representations.ConceptType;
import com.tregouet.occam.data.representations.IConcept;
import com.tregouet.occam.data.representations.impl.WhatIsThere;
import com.tregouet.occam.data.representations.tapes.IRepresentationTapeSet;
import com.tregouet.occam.data.representations.tapes.IRepresentationTapeSets;
import com.tregouet.occam.data.representations.tapes.IVarBinder;
import com.tregouet.occam.data.representations.transitions.IApplication;
import com.tregouet.occam.data.representations.transitions.IConceptTransition;
import com.tregouet.occam.data.representations.transitions.IConceptTransitionIC;
import com.tregouet.occam.data.representations.transitions.IConceptTransitionOIC;

public class RepresentationTapeSet implements IRepresentationTapeSet {

	private IConcept state = null;
	private IFact inputTape = null;
	private IVarBinder stackTape = null;
	
	public RepresentationTapeSet() {
		state = WhatIsThere.INSTANCE;
		inputTape = new Fact();
		stackTape = new VarBinder();
	}
	
	public RepresentationTapeSet(IConcept state, IFact inputTape, IVarBinder stackTape) {
		this.state = state;
		this.inputTape = inputTape;
		this.stackTape = stackTape;
	}

	private IRepresentationTapeSet apply(IApplication application) {
		IConceptTransitionIC required = application.getInputConfiguration();
		RepresentationTapeSet evaluated = (RepresentationTapeSet) this.copy();
		if (evaluated.inputTape.hasNext() 
				&& evaluated.inputTape.next().equals(required.getInputSymbol())) {
			if (evaluated.stackTape.popOff().equals(required.getInputStackSymbol())) {
				IConceptTransitionOIC next = application.getOutputInternConfiguration();
				for (AVariable var : next.getOutputStackSymbols())
					evaluated.stackTape.pushDown(var);
				evaluated.state = next.getOutputState();
				return evaluated;
			}
		}
		return null;
	}

	@Override
	public IRepresentationTapeSet copy() {
		return new RepresentationTapeSet(state, inputTape.copy(), stackTape.copy());
	}

	@Override
	public int hashCode() {
		return Objects.hash(inputTape, stackTape, state);
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		RepresentationTapeSet other = (RepresentationTapeSet) obj;
		return Objects.equals(inputTape, other.inputTape) && Objects.equals(stackTape, other.stackTape)
				&& Objects.equals(state, other.state);
	}

	@Override
	public boolean accepted() {
		return state.type() == ConceptType.PARTICULAR;
	}
	
	@Override
	public IConcept acceptedBy(IConcept particular) {
		if (state.type() == ConceptType.PARTICULAR)
			return state;
		return null;
	}

	@Override
	public IRepresentationTapeSets evaluate() {
		IRepresentationTapeSets tapeSets = new RepresentationTapeSets();
	}

	@Override
	public IRepresentationTapeSet proceed(IConceptTransition transition) {
		// TODO Auto-generated method stub
		return null;
	}

}
