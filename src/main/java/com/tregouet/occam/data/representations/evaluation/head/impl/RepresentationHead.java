package com.tregouet.occam.data.representations.evaluation.head.impl;

import java.util.Set;

import com.tregouet.occam.data.languages.words.fact.IFact;
import com.tregouet.occam.data.representations.evaluation.head.IRepresentationHead;
import com.tregouet.occam.data.representations.evaluation.tapes.IRepresentationTapeSet;
import com.tregouet.occam.data.representations.properties.transitions.IRepresentationTransitionFunction;

public class RepresentationHead implements IRepresentationHead {

	@Override
	public void input(IFact fact) {
		// TODO Auto-generated method stub

	}

	@Override
	public void set(IRepresentationTransitionFunction transitionFunction, IRepresentationTapeSet tapeSet) {
		// TODO Auto-generated method stub

	}

	@Override
	public Set<? extends IRepresentationHead> evaluateNextSymbol() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Set<? extends IRepresentationHead> printNextSymbol() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public boolean halted() {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public boolean accepts() {
		// TODO Auto-generated method stub
		return false;
	}

}
