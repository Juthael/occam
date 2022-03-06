package com.tregouet.occam.alg.scoring.scores.functions.impl;

import com.tregouet.occam.alg.scoring.scores.functions.IFunctionScorer;
import com.tregouet.occam.data.automata.machines.IAutomaton;

public class ConceptualCoherence implements IFunctionScorer {

	public static final ConceptualCoherence INSTANCE = new ConceptualCoherence();
	private IAutomaton automaton = null;
	
	private ConceptualCoherence() {
	}

	@Override
	public IFunctionScorer input(IAutomaton automaton) {
		this.automaton = automaton;
		return this;
	}

	@Override
	public void setScore() {
		automaton.setScore(automaton.getSimilarityCalculator().getCoherenceScore());
	}

}
