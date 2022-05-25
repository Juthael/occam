package com.tregouet.occam.data.problem_space.states.transitions.impl;

import java.util.HashSet;
import java.util.Set;

import org.jgrapht.graph.DirectedAcyclicGraph;

import com.google.common.collect.Sets;
import com.tregouet.occam.alg.displayers.formatters.FormattersAbstractFactory;
import com.tregouet.occam.alg.displayers.formatters.transition_functions.TransitionFunctionLabeller;
import com.tregouet.occam.data.logical_structures.languages.alphabets.AVariable;
import com.tregouet.occam.data.problem_space.states.transitions.AConceptTransitionSet;
import com.tregouet.occam.data.problem_space.states.transitions.IApplication;
import com.tregouet.occam.data.problem_space.states.transitions.IConceptTransition;
import com.tregouet.occam.data.problem_space.states.transitions.IRepresentationTransitionFunction;
import com.tregouet.occam.data.problem_space.states.transitions.Salience;
import com.tregouet.occam.data.problem_space.states.transitions.productions.IContextualizedProduction;

public class RepresentationTransitionFunction implements IRepresentationTransitionFunction {

	private final IConceptTransition initial;
	private final Set<IApplication> applications;
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
				case INITIAL:
					initialTemp = transition;
					break;
				case APPLICATION:
					applications.add((IApplication) transition);
					break;
				case CLOSURE:
					closures.add(transition);
					break;
				case INHERITANCE:
					inheritances.add(transition);
					break;
				case SPONTANEOUS:
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
	public Set<Integer> getAcceptStateIDs() {
		return new HashSet<>(acceptStateIDs);
	}

	@Override
	public AVariable getInitialStackSymbol() {
		return initial.getInputConfiguration().getStackSymbol();
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
	public Set<IApplication> getSalientApplications() {
		Set<IApplication> salientApplications = new HashSet<>();
		for (IApplication application : applications) {
			Salience salienceVal = application.getSalience();
			if (salienceVal == Salience.COMMON_FEATURE || salienceVal == Salience.TRANSITION_RULE)
				salientApplications.add(application);
		}
		return salientApplications;
	}

	@Override
	public Set<AVariable> getStackAlphabet() {
		Set<AVariable> stackAlphabet = new HashSet<>();
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
		transitions.addAll(applications);
		transitions.addAll(closures);
		transitions.addAll(inheritances);
		transitions.addAll(spontaneous);
		return transitions;
	}

	@Override
	public DirectedAcyclicGraph<Integer, AConceptTransitionSet> asGraph() {
		TransitionFunctionLabeller displayer = FormattersAbstractFactory.INSTANCE.getTransitionFunctionDisplayer();
		return(displayer.apply(this));
	}

}
