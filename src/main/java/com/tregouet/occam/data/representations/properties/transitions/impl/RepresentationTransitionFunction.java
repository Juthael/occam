package com.tregouet.occam.data.representations.properties.transitions.impl;

import java.util.Collection;
import java.util.Set;

import com.tregouet.occam.data.alphabets.generic.AVariable;
import com.tregouet.occam.data.alphabets.productions.IContextualizedProduction;
import com.tregouet.occam.data.representations.concepts.IConcept;
import com.tregouet.occam.data.representations.properties.transitions.IConceptTransition;
import com.tregouet.occam.data.representations.properties.transitions.IRepresentationTransitionFunction;

public class RepresentationTransitionFunction implements IRepresentationTransitionFunction {
	
	private final Set<IConceptTransition> applications;
	private final Set<IConceptTransition> closures;
	private final Set<IConceptTransition> inheritances;
	private final Set<IConceptTransition> spontaneous;
	
	public RepresentationTransitionFunction() {
		
	}

	@Override
	public Set<AVariable> getStackAlphabet() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public AVariable getInitialStackSymbol() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Set<IContextualizedProduction> getInputAlphabet() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Collection<IConcept> getStates() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Collection<IConceptTransition> getTransitions() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public IConcept getStartState() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Collection<IConcept> getAcceptStates() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public IConcept getStateWithID(int iD) {
		// TODO Auto-generated method stub
		return null;
	}

}
