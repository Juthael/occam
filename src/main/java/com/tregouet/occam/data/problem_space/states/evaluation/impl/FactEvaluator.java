package com.tregouet.occam.data.problem_space.states.evaluation.impl;

import java.util.HashSet;
import java.util.Set;

import com.tregouet.occam.data.logical_structures.languages.alphabets.AVariable;
import com.tregouet.occam.data.problem_space.states.evaluation.IFactEvaluator;
import com.tregouet.occam.data.problem_space.states.evaluation.tapes.IFactTape;
import com.tregouet.occam.data.problem_space.states.evaluation.tapes.IRepresentationTapeSet;
import com.tregouet.occam.data.problem_space.states.evaluation.tapes.impl.RepresentationTapeSet;
import com.tregouet.occam.data.problem_space.states.transitions.IConceptTransition;
import com.tregouet.occam.data.problem_space.states.transitions.IConceptTransitionIC;
import com.tregouet.occam.data.problem_space.states.transitions.IConceptTransitionOIC;
import com.tregouet.occam.data.problem_space.states.transitions.IRepresentationTransitionFunction;
import com.tregouet.occam.data.problem_space.states.transitions.dimensions.EpsilonDimension;
import com.tregouet.occam.data.problem_space.states.transitions.productions.IContextualizedProduction;

public class FactEvaluator implements IFactEvaluator {

	private IRepresentationTransitionFunction transitionFunction = null;
	private IRepresentationTapeSet tapeSet = null;
	private int activeStateID;
	private boolean halted = false;

	public FactEvaluator() {
		tapeSet = new RepresentationTapeSet();
	}

	private FactEvaluator(IRepresentationTransitionFunction transitionFunction, IRepresentationTapeSet tapeSet,
			Integer activeState) {
		this.transitionFunction = transitionFunction;
		this.tapeSet = tapeSet;
		this.activeStateID = activeState;
	}

	@Override
	public boolean accepts() {
		return halted && transitionFunction.getAcceptStateIDs().contains(activeStateID);
	}

	@Override
	public Set<IFactEvaluator> evaluate() {
		// NOT IMPLEMENTED YET
		reinitialize();
		return null;
	}

	@Override
	public Set<IFactEvaluator> factEnumerator() {
		Set<IFactEvaluator> acceptHeads = new HashSet<>();
		if (transitionFunction.getAcceptStateIDs().contains(activeStateID)) {
			halted = true;
			acceptHeads.add(this);
			return acceptHeads;
		}
		AVariable poppedStackSymbol = tapeSet.popOff();
		for (IConceptTransition transition : transitionFunction.getTransitions()) {
			IConceptTransitionIC requiredInputConfig = transition.getInputConfiguration();
			if (requiredInputConfig.getInputStateID() == activeStateID
					&& requiredInputConfig.getStackSymbol().equals(poppedStackSymbol)) {
				IFactEvaluator nextHead = proceedPrintingTransition(requiredInputConfig.getInputSymbol(),
						transition.getOutputInternConfiguration());
				acceptHeads.addAll(nextHead.factEnumerator());
			}
		}
		return acceptHeads;
	}

	@Override
	public int getActiveStateID() {
		return activeStateID;
	}

	@Override
	public IRepresentationTapeSet getTapeSet() {
		return tapeSet;
	}

	@Override
	public IRepresentationTransitionFunction getTransitionFunction() {
		return transitionFunction;
	}

	@Override
	public boolean halted() {
		return halted;
	}

	@Override
	public IFactEvaluator input(IFactTape factTape) {
		tapeSet.input(factTape);
		return this;
	}

	@Override
	public void reinitialize() {
		tapeSet = new RepresentationTapeSet();
		activeStateID = transitionFunction.getStartStateID();
		halted = false;
	}

	@Override
	public void set(IRepresentationTransitionFunction transitionFunction) {
		this.transitionFunction = transitionFunction;
		activeStateID = transitionFunction.getStartStateID();
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

}