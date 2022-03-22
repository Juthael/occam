package com.tregouet.occam.data.representations.properties.transitions.impl;

import java.util.HashSet;
import java.util.Set;

import com.google.common.collect.Sets;
import com.tregouet.occam.data.alphabets.generic.AVariable;
import com.tregouet.occam.data.alphabets.productions.IContextualizedProduction;
import com.tregouet.occam.data.representations.properties.transitions.IConceptTransition;
import com.tregouet.occam.data.representations.properties.transitions.IRepresentationTransitionFunction;

public class RepresentationTransitionFunction implements IRepresentationTransitionFunction {
	
	private final IConceptTransition initial;
	private final Set<IConceptTransition> applications;
	private final Set<IConceptTransition> closures;
	private final Set<IConceptTransition> inheritances;
	private final Set<IConceptTransition> spontaneous;
	private final Set<Integer> acceptStateIDs;
	
	public RepresentationTransitionFunction(Set<IConceptTransition> transitions) {
		IConceptTransition initialTemp = null;
		applications = new HashSet<>();
		closures = new HashSet<>();
		inheritances = new HashSet<>();
		spontaneous = new HashSet<>();
		Set<Integer> inputStateIDs = new HashSet<>();
		Set<Integer> outputStateIDs = new HashSet<>();
		for (IConceptTransition transition : transitions) {
			switch (transition.type()) {
				case INITIAL : 
					initialTemp = transition;
					break;
				case APPLICATION : 
					applications.add(transition);
					break;
				case CLOSURE : 
					closures.add(transition);
					break;
				case INHERITANCE : 
					inheritances.add(transition);
					break;
				case SPONTANEOUS : 
					spontaneous.add(transition);
					break;
			}
			inputStateIDs.add(transition.getInputConfiguration().getInputStateID());
			outputStateIDs.add(transition.getOutputInternConfiguration().getOutputStateID());
		}
		acceptStateIDs = new HashSet<>(Sets.difference(outputStateIDs, inputStateIDs));
		initial = initialTemp;
	}

	@Override
	public Set<AVariable> getStackAlphabet() {
		Set<AVariable> stackAlphabet = new HashSet<>();
		for (IConceptTransition transition : getTransitions()) {
			stackAlphabet.add(transition.getInputConfiguration().getPoppedStackSymbol());
			stackAlphabet.addAll(transition.getOutputInternConfiguration().getPushedStackSymbols());
		}
		return stackAlphabet;
	}

	@Override
	public AVariable getInitialStackSymbol() {
		return initial.getInputConfiguration().getPoppedStackSymbol();
	}

	@Override
	public Set<IContextualizedProduction> getInputAlphabet() {
		Set<IContextualizedProduction> inputAlphabet = new HashSet<>();
		for (IConceptTransition transition : getTransitions()) {
			inputAlphabet.add(transition.getInputConfiguration().getInputSymbol());
		}
		return inputAlphabet;
	}

	@Override
	public Set<Integer> getStateIDs() {
		Set<Integer> stateIDs = new HashSet<>();
		for (IConceptTransition transition : getTransitions()) {
			stateIDs.add(transition.getInputConfiguration().getInputStateID());
			stateIDs.add(transition.getOutputInternConfiguration().getOutputStateID());
		}
		return stateIDs;
	}

	@Override
	public Set<IConceptTransition> getTransitions() {
		Set<IConceptTransition> transitions = new HashSet<>();
		transitions.add(initial);
		transitions.addAll(applications);
		transitions.addAll(closures);
		transitions.addAll(inheritances);
		transitions.addAll(spontaneous);
		return transitions;
	}

	@Override
	public int getStartStateID() {
		return initial.getInputConfiguration().getInputStateID();
	}

	@Override
	public Set<Integer> getAcceptStateIDs() {
		return new HashSet<>(acceptStateIDs);
	}

}
