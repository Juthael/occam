package com.tregouet.occam.data.representations.transitions.impl;

import java.util.HashSet;
import java.util.Set;

import org.jgrapht.graph.DirectedAcyclicGraph;

import com.google.common.collect.Sets;
import com.tregouet.occam.alg.displayers.formatters.FormattersAbstractFactory;
import com.tregouet.occam.alg.displayers.formatters.transition_functions.TransitionFunctionLabeller;
import com.tregouet.occam.data.representations.descriptions.differentiae.properties.computations.abstr_app.IAbstractionApplication;
import com.tregouet.occam.data.representations.transitions.AConceptTransitionSet;
import com.tregouet.occam.data.representations.transitions.IConceptProductiveTransition;
import com.tregouet.occam.data.representations.transitions.IConceptTransition;
import com.tregouet.occam.data.representations.transitions.IRepresentationTransitionFunction;
import com.tregouet.occam.data.structures.lambda_terms.IBindings;

public class RepresentationTransitionFunction implements IRepresentationTransitionFunction {

	private final IConceptTransition initial;
	private final Set<IConceptProductiveTransition> conceptProductiveTransitions;
	private final Set<IConceptTransition> closures;
	private final Set<IConceptTransition> inheritances;
	private final Set<Integer> acceptStateIDs;

	public RepresentationTransitionFunction(Set<IConceptTransition> transitions) {
		IConceptTransition initialTemp = null;
		conceptProductiveTransitions = new HashSet<>();
		closures = new HashSet<>();
		inheritances = new HashSet<>();
		Set<Integer> inputStateIDs = new HashSet<>();
		Set<Integer> outputStateIDs = new HashSet<>();
		for (IConceptTransition transition : transitions) {
			switch (transition.type()) {
				case INITIAL:
					initialTemp = transition;
					break;
				case PRODUCTIVE_TRANS:
					conceptProductiveTransitions.add((IConceptProductiveTransition) transition);
					break;
				case CLOSURE:
					closures.add(transition);
					break;
				case INHERITANCE:
					inheritances.add(transition);
					break;
			}
			inputStateIDs.add(transition.getInputConfiguration().getInputStateID());
			outputStateIDs.add(transition.getOutputInternConfiguration().getOutputStateID());
		}
		acceptStateIDs = new HashSet<>(Sets.difference(outputStateIDs, inputStateIDs));
		initial = initialTemp;
	}

	@Override
	public DirectedAcyclicGraph<Integer, AConceptTransitionSet> asGraph() {
		TransitionFunctionLabeller displayer = FormattersAbstractFactory.INSTANCE.getTransitionFunctionDisplayer();
		return(displayer.apply(this));
	}

	@Override
	public Set<Integer> getAcceptStateIDs() {
		return new HashSet<>(acceptStateIDs);
	}

	@Override
	public IBindings getInitialStackSymbol() {
		return initial.getInputConfiguration().getStackSymbol();
	}

	@Override
	public Set<IAbstractionApplication> getInputAlphabet() {
		Set<IAbstractionApplication> inputAlphabet = new HashSet<>();
		for (IConceptTransition transition : getTransitions()) {
			inputAlphabet.add(transition.getInputConfiguration().getInputSymbol());
		}
		return inputAlphabet;
	}

	@Override
	public Set<IBindings> getStackAlphabet() {
		Set<IBindings> stackAlphabet = new HashSet<>();
		for (IConceptTransition transition : getTransitions()) {
			stackAlphabet.add(transition.getInputConfiguration().getStackSymbol());
			stackAlphabet.addAll(transition.getOutputInternConfiguration().getPushedStackSymbols());
		}
		return stackAlphabet;
	}

	@Override
	public int getStartStateID() {
		return initial.getInputConfiguration().getInputStateID();
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
		transitions.addAll(conceptProductiveTransitions);
		transitions.addAll(closures);
		transitions.addAll(inheritances);
		return transitions;
	}

}
