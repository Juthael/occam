package com.tregouet.occam.data.abstract_machines.transition_functions.impl;

import java.util.HashSet;
import java.util.List;
import java.util.Set;

import org.jgrapht.graph.DirectedMultigraph;
import org.jgrapht.graph.SimpleDirectedGraph;

import com.tregouet.occam.data.abstract_machines.states.IState;
import com.tregouet.occam.data.abstract_machines.transition_functions.ITransitionFunction;
import com.tregouet.occam.data.abstract_machines.transition_rules.ITransitionRule;
import com.tregouet.occam.data.abstract_machines.transition_rules.ITransitionRules;
import com.tregouet.occam.data.concepts.IDifferentiae;
import com.tregouet.occam.data.languages.generic.AVariable;
import com.tregouet.occam.data.languages.specific.IProduction;
import com.tregouet.occam.data.languages.specific.IProperty;

public class TransitionFunction implements ITransitionFunction {

	private final DirectedMultigraph<IState, ITransitionRule> transitionGraph;
	private final IState startState;
	private final Map<AVariable, ITransition>
	
	public TransitionFunction(List<IState> states, List<ITransitionRule> transitionRules, IState startState) {
	}
	
	@Override
	public Set<IProduction> getInputAlphabet() {
		//NOT IMPLEMENTED YET
	}

	@Override
	public Set<AVariable> getStackAlphabet() {
		//NOT IMPLEMENTED YET
	}

	@Override
	public Set<IProperty> getStateLanguage(int iD) {
		
	}

	@Override
	public Set<IProperty> getMachineLanguage() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Set<IDifferentiae> getDifferentiae(int iD) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public DirectedMultigraph<IState, ITransitionRule> getTransitionFunctionMultiGraph() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public SimpleDirectedGraph<IState, ITransitionRules> getTransitionFunctionGraph() {
		// TODO Auto-generated method stub
		return null;
	}

}
