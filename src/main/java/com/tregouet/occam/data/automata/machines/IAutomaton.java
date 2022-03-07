package com.tregouet.occam.data.automata.machines;

import java.util.List;
import java.util.Set;

import org.jgrapht.graph.DirectedMultigraph;
import org.jgrapht.graph.SimpleDirectedGraph;

import com.tregouet.occam.alg.scoring.costs.ICosted;
import com.tregouet.occam.alg.scoring.scores.IScored;
import com.tregouet.occam.data.automata.states.IState;
import com.tregouet.occam.data.automata.transition_functions.ITransitionFunction;
import com.tregouet.occam.data.automata.transition_rules.ITransitionRule;
import com.tregouet.occam.data.automata.transition_rules.ITransitionRules;
import com.tregouet.occam.data.denotations.IPreconcept;
import com.tregouet.occam.data.languages.generic.AVariable;
import com.tregouet.occam.data.languages.specific.IDomainSpecificLanguageDisplayer;
import com.tregouet.occam.data.languages.specific.IStronglyContextualized;

public interface IAutomaton extends ICosted, IScored {
	
	@Override
	boolean equals(Object o);
	
	List<IState> getStates();
	
	Set<IStronglyContextualized> getInputAlphabet();
	
	Set<AVariable> getStackAlphabet();
	
	ITransitionFunction getTransitionFunction();
	
	IState getStartState();
	
	AVariable getInitialStackSymbol();
	
	List<IState> getAcceptStates();
	
	List<IState> getObjectStates();
	
	IState getStateWithID(int iD);
	
	IDomainSpecificLanguageDisplayer getDomainSpecificLanguageDisplayer();
	
	@Override
	int hashCode();
	
}
