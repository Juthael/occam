package com.tregouet.occam.data.abstract_machines.transition_functions;

import java.util.Set;

import org.jgrapht.graph.DirectedMultigraph;
import org.jgrapht.graph.SimpleDirectedGraph;

import com.tregouet.occam.data.abstract_machines.states.IState;
import com.tregouet.occam.data.abstract_machines.transition_rules.ITransitionRule;
import com.tregouet.occam.data.abstract_machines.transition_rules.ITransitionRules;
import com.tregouet.occam.data.concepts.IDifferentiae;
import com.tregouet.occam.data.languages.generic.AVariable;
import com.tregouet.occam.data.languages.specific.IProductionAsEdge;
import com.tregouet.occam.data.languages.specific.IProperty;

public interface ITransitionFunction {
	
	Set<IProductionAsEdge> getInputAlphabet();
	
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
