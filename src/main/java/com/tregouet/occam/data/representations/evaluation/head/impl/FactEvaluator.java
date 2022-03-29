package com.tregouet.occam.data.representations.evaluation.head.impl;

import java.util.HashSet;
import java.util.Set;

import com.tregouet.occam.data.languages.alphabets.domain_specific.IContextualizedProduction;
import com.tregouet.occam.data.languages.alphabets.generic.AVariable;
import com.tregouet.occam.data.languages.words.fact.IFact;
import com.tregouet.occam.data.representations.evaluation.head.IFactEvaluator;
import com.tregouet.occam.data.representations.evaluation.tapes.IRepresentationTapeSet;
import com.tregouet.occam.data.representations.properties.transitions.IConceptTransition;
import com.tregouet.occam.data.representations.properties.transitions.IConceptTransitionIC;
import com.tregouet.occam.data.representations.properties.transitions.IConceptTransitionOIC;
import com.tregouet.occam.data.representations.properties.transitions.IRepresentationTransitionFunction;
import com.tregouet.occam.data.representations.properties.transitions.dimensions.EpsilonDimension;

public class FactEvaluator implements IFactEvaluator {

	private IRepresentationTransitionFunction transitionFunction = null;
	private IRepresentationTapeSet tapeSet = null;
	private int activeStateID;
	private boolean halted = false;
	
	public FactEvaluator() {
	}
	
	private FactEvaluator(
			IRepresentationTransitionFunction transitionFunction, 
			IRepresentationTapeSet tapeSet,
			Integer activeState) {
		this.transitionFunction = transitionFunction;
		this.tapeSet = tapeSet;
		this.activeStateID = activeState;
	}
	
	@Override
	public void input(IFact fact) {
		// NOT IMPLEMENTED YET
	}

	@Override
	public void set(IRepresentationTransitionFunction transitionFunction, IRepresentationTapeSet tapeSet) {
		this.transitionFunction = transitionFunction;
		this.tapeSet = tapeSet;
		activeStateID = transitionFunction.getStartStateID();
	}

	@Override
	public Set<IFactEvaluator> evaluate() {
		//NOT IMPLEMENTED YET
		return null;
	}

	@Override
	public Set<IFactEvaluator> generateEverySuccessfulEvaluation() {
		Set<IFactEvaluator> acceptHeads = new HashSet<>();
		if (transitionFunction.getAcceptStateIDs().contains(activeStateID)) {
			halted = true;
			acceptHeads.add(this);
			return acceptHeads;
		}
		AVariable poppedStackSymbol = tapeSet.popOff();
		for (IConceptTransition transition : transitionFunction.getTransitions()) {
			IConceptTransitionIC requiredInputConfig = transition.getInputConfiguration();
			if (requiredInputConfig.getRequiredInputStateID() == activeStateID
					&& requiredInputConfig.getRequiredStackSymbol().equals(poppedStackSymbol)) {
				IFactEvaluator nextHead = 
						proceedPrintingTransition(
								requiredInputConfig.getInputSymbol(), 
								transition.getOutputInternConfiguration());
				acceptHeads.addAll(nextHead.generateEverySuccessfulEvaluation());
			}
		}
		return acceptHeads;
	}

	@Override
	public boolean halted() {
		return halted;
	}

	@Override
	public boolean accepts() {
		return halted && transitionFunction.getAcceptStateIDs().contains(activeStateID);
	}
	
	private IFactEvaluator proceedPrintingTransition(IContextualizedProduction nextPrint, 
			IConceptTransitionOIC outputInternConfig) {
		IRepresentationTapeSet nextTapeSet = tapeSet.copy();
		if (!nextPrint.isEpsilon())
			nextTapeSet.printNext(nextPrint);
		for (AVariable pushedDown : outputInternConfig.getPushedStackSymbols()) {
			if (!pushedDown.equals(EpsilonDimension.INSTANCE))
				nextTapeSet.pushDown(pushedDown);
		}
		int nextState = outputInternConfig.getOutputStateID();
		return new FactEvaluator(transitionFunction, nextTapeSet, nextState);
	}

	@Override
	public IRepresentationTransitionFunction getTransitionFunction() {
		return transitionFunction;
	}

	@Override
	public IRepresentationTapeSet getTapeSet() {
		return tapeSet;
	}

}
