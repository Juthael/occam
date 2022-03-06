package com.tregouet.occam.data.automata.transition_functions;

import java.util.Set;

import org.jgrapht.graph.DirectedMultigraph;
import org.jgrapht.graph.SimpleDirectedGraph;

import com.tregouet.occam.data.alphabets.productions.IProduction;
import com.tregouet.occam.data.alphabets.productions.IProperty;
import com.tregouet.occam.data.automata.states.IState;
import com.tregouet.occam.data.automata.transition_rules.ITransitionRule;
import com.tregouet.occam.data.automata.transition_rules.ITransitionRules;
import com.tregouet.occam.data.concepts_temp.IDifferentiae;
import com.tregouet.occam.data.languages.generic.AVariable;

public interface ITransitionFunction {
	
	Set<IProduction> getInputAlphabet();
	
	Set<AVariable> getStackAlphabet();
	
	Set<IProperty> getStateLanguage(int iD);
	
	Set<IProperty> getMachineLanguage();
	
	Set<IDifferentiae> getDifferentiae(int iD);
	
	DirectedMultigraph<IState, ITransitionRule> getTransitionFunctionMultiGraph();
	
	SimpleDirectedGraph<IState, ITransitionRules> getTransitionFunctionGraph();
	
	@Override
	int hashCode();
	
	@Override
	boolean equals(Object other);

}
