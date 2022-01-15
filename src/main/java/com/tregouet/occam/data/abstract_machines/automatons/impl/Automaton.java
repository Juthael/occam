package com.tregouet.occam.data.abstract_machines.automatons.impl;

import java.util.Iterator;
import java.util.List;
import java.util.Objects;
import java.util.Set;

import com.tregouet.occam.data.abstract_machines.automatons.IAutomaton;
import com.tregouet.occam.data.abstract_machines.states.IState;
import com.tregouet.occam.data.abstract_machines.transition_functions.ITransitionFunction;
import com.tregouet.occam.data.languages.generic.AVariable;
import com.tregouet.occam.data.languages.specific.IDomainSpecificLanguageDisplayer;
import com.tregouet.occam.data.languages.specific.IProductionAsEdge;

public class Automaton implements IAutomaton {

	private final List<IState> states;
	private final Set<IProductionAsEdge> inputAlphabet;
	private final Set<AVariable> stackAlphabet;
	private final ITransitionFunction transitionFunction;
	private final IState startState;
	private final AVariable initialStackSymbol = null;
	private final List<IState> acceptStates;
	private final List<IState> objectStates;
	
	public Automaton(List<IState> states, ITransitionFunction transitionFunction, IState startState, List<IState> acceptStates, 
			List<IState> objectStates) {
		this.states = states;
		inputAlphabet = transitionFunction.getInputAlphabet();
		stackAlphabet = transitionFunction.getStackAlphabet();
		this.transitionFunction = transitionFunction;
		this.startState = startState;
		this.acceptStates = acceptStates;
		this.objectStates = objectStates;
	}
	
	@Override
	public IState getStateWithID(int iD) {
		IState returnedState = null;
		Iterator<IState> stateIte = states.iterator();
		while (returnedState == null && stateIte.hasNext()) {
			IState nextState = stateIte.next();
			if (nextState.iD() == iD)
				returnedState = nextState;
		}
		return returnedState;
	}

	@Override
	public Double getCost() {
		//NOT IMPLEMENTED YET
		return null;
	}

	@Override
	public void setCost(double cost) {
		//NOT IMPLEMENTED YET
		
	}

	@Override
	public Double getScore() {
		//NOT IMPLEMENTED YET
		return null;
	}

	@Override
	public void setScore(double score) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public List<IState> getStates() {
		return states;
	}

	@Override
	public Set<IProductionAsEdge> getInputAlphabet() {
		return inputAlphabet;
	}

	@Override
	public Set<AVariable> getStackAlphabet() {
		return stackAlphabet;
	}

	@Override
	public ITransitionFunction getTransitionFunction() {
		return transitionFunction;
	}

	@Override
	public IState getStartState() {
		return startState;
	}

	@Override
	public AVariable getInitialStackSymbol() {
		return initialStackSymbol;
	}

	@Override
	public List<IState> getAcceptStates() {
		return acceptStates;
	}

	@Override
	public List<IState> getObjectStates() {
		return objectStates;
	}

	@Override
	public IDomainSpecificLanguageDisplayer getDomainSpecificLanguageDisplayer() {
		//NOT IMPLEMENTED YET
		return null;
	}

	@Override
	public int hashCode() {
		return Objects.hash(transitionFunction);
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		Automaton other = (Automaton) obj;
		return Objects.equals(transitionFunction, other.transitionFunction);
	}


}

