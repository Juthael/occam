package com.tregouet.occam.data.representations.properties.transitions.impl;

import java.util.HashSet;
import java.util.Set;

import com.tregouet.occam.alg.builders.GeneratorsAbstractFactory;
import com.tregouet.occam.data.alphabets.generic.AVariable;
import com.tregouet.occam.data.alphabets.productions.IContextualizedProduction;
import com.tregouet.occam.data.preconcepts.IIsA;
import com.tregouet.occam.data.preconcepts.IPreconcept;
import com.tregouet.occam.data.representations.properties.transitions.IConceptTransition;
import com.tregouet.occam.data.representations.properties.transitions.IRepresentationTransitionFunction;
import com.tregouet.tree_finder.data.Tree;

public class RepresentationTransitionFunction implements IRepresentationTransitionFunction {
	
	private final IConceptTransition initial;
	private final Set<IConceptTransition> applications = new HashSet<>();
	private final Set<IConceptTransition> closures = new HashSet<>();
	private final Set<IConceptTransition> inheritances = new HashSet<>();
	private final Set<IConceptTransition> spontaneous = new HashSet<>();
	private final Set<Integer> acceptStatesIDs = new HashSet<>();
	
	public RepresentationTransitionFunction(Tree<IPreconcept, IIsA> treeOfPreconcepts,
			Set<IContextualizedProduction> unfilteredUnreducedProds) {
		Set<IConceptTransition> allTransitions = 
				GeneratorsAbstractFactory.INSTANCE
					.getTransitionsConstructionManager()
					.input(treeOfPreconcepts, unfilteredUnreducedProds)
					.output();
		IConceptTransition initialTemp = null;
		for (IConceptTransition transition : allTransitions) {
			switch (transition.type()) {
				case APPLICATION : 
					applications.add(transition);
					break;
				case CLOSURE : 
					closures.add(transition);
					break;
				case INHERITANCE : 
					inheritances.add(transition);
					break;
				case INITIAL : 
					initialTemp = transition;
					break;
				case SPONTANEOUS : 
					spontaneous.add(transition);
					break;
			}
		}
		initial = initialTemp;
		for (IPreconcept objPreconcept : treeOfPreconcepts.getLeaves())
			acceptStatesIDs.add(objPreconcept.getID());
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
		return new HashSet<>(acceptStatesIDs);
	}

}
