package com.tregouet.occam.data.languages.specific.impl;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.HashSet;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.stream.Collectors;

import org.jgrapht.graph.DirectedMultigraph;
import org.jgrapht.traverse.TopologicalOrderIterator;

import com.tregouet.occam.data.abstract_machines.states.IState;
import com.tregouet.occam.data.abstract_machines.transitions.IOperator;
import com.tregouet.occam.data.abstract_machines.transitions.IProduction;
import com.tregouet.occam.data.denotations.IDenotation;
import com.tregouet.occam.data.languages.specific.IDomainSpecificLanguage;

public class DomainSpecificLanguage implements IDomainSpecificLanguage {

	List<IOperator> topologicalSorting = new ArrayList<>();
	IOperator startVariable;
	List<IOperator> variables = new ArrayList<>();
	List<IOperator> terminals = new ArrayList<>(); 
	Map<IOperator, Set<IOperator>> productions = new LinkedHashMap<>();
	
	public DomainSpecificLanguage(List<IState> states, List<IOperator> operators) {
		DirectedMultigraph<IState, IOperator> dag = new DirectedMultigraph<>(null, null, false);
		states.stream().forEach(s -> dag.addVertex(s));
		operators.stream().forEach(o -> dag.addEdge(o.getOperatingState(), o.getNextState(), o));
		TopologicalOrderIterator<IState, IOperator> topoStateIte = new TopologicalOrderIterator<>(dag);
		while (topoStateIte.hasNext()) {
			IState currState = topoStateIte.next();
			Set<IOperator> incomingEdges = dag.incomingEdgesOf(currState);
			topologicalSorting.addAll(incomingEdges);
			Set<IOperator> outgoingEdges = dag.outgoingEdgesOf(currState);
			for (IOperator outEdge : outgoingEdges) {
				productions.put(outEdge, new HashSet<>());
				List<IDenotation> outEdgeAcceptedInputs = outEdge.operation()
						.stream()
						.map(p -> p.getSource())
						.collect(Collectors.toList());
				for (IOperator inEdge : incomingEdges) {
					List<IDenotation> inEdgeOutputs = inEdge.operation()
							.stream()
							.map(p -> p.getTarget())
							.collect(Collectors.toList());
					if (inEdgeOutputs.removeAll(outEdgeAcceptedInputs)) {
						productions.get(outEdge).add(inEdge);
					}
				}
			}
		}
		Collections.reverse(topologicalSorting);
		startVariable = topologicalSorting.get(0);
		for (IOperator operator : productions.keySet()) {
			if (productions.get(operator).isEmpty())
				terminals.add(operator);
			else variables.add(operator);
		}
		Collections.reverse(variables);
	}

	@Override
	public String getDomainSpecificGrammarOfOperators() {
		StringBuilder sB = new StringBuilder();
		Map<IOperator, String> label = new HashMap<>();
		for (IOperator operator : topologicalSorting) {
			label.put(operator, nameAndFrame(operator));
		}
		for (IOperator operator : variables) {
				sB.append(label.get(operator));
				List<IOperator> rightTerms = new ArrayList<>(productions.get(operator));
				sB.append(" ::= ");
				for (int i = 0 ; i < rightTerms.size() ; i++) {
					sB.append(label.get(rightTerms.get(i)));
					if (i < rightTerms.size() - 1) {
						sB.append(" | ");
					}
				}
			sB.append(System.lineSeparator());
		}
		return sB.toString();
	}

	@Override
	public String getOperatorsDescription() {
		StringBuilder sB = new StringBuilder();
		for (IOperator operator : topologicalSorting) {
			sB.append("-operator " + operator.getName() + System.lineSeparator());
			sB.append("   operation : ");
			List<IProduction> prods = operator.operation();
			for (int i = 0 ; i < prods.size() ; i++) {
				sB.append(prods.get(i).toString());
				if (i < prods.size() - 1)
					sB.append(" ");
				else sB.append(System.lineSeparator());
			}
		}
		return sB.toString();
	}
	
	@Override
	public String toString() {
		StringBuilder sB = new StringBuilder();
		sB.append("***** DOMAIN SPECIFIC CONTEXT FREE GRAMMAR *****" + System.lineSeparator() + System.lineSeparator());
		sB.append("-start variable : <" + startVariable.getName() + ">" 
				+ System.lineSeparator() + System.lineSeparator());
		sB.append("-variables : ");
		for (int i = 0 ; i < variables.size() ; i++) {
			sB.append("<" + variables.get(i).getName() + ">");
			if (i < variables.size() - 1)
				sB.append(", ");
			if (i > 0 && i % 10 == 0) {
				sB.append(System.lineSeparator());
			}
		}
		sB.append(System.lineSeparator() + System.lineSeparator());
		sB.append("-terminals : ");
		for (int i = 0 ; i < variables.size() ; i++) {
			sB.append(terminals.get(i).getName());
			if (i < terminals.size() - 1)
				sB.append(", ");
			if (i > 0 && i % 10 == 0) {
				sB.append(System.lineSeparator());
			}
		}
		sB.append(System.lineSeparator() + System.lineSeparator());
		sB.append("-productions : " + System.lineSeparator());
		sB.append(getDomainSpecificGrammarOfOperators());
		sB.append(System.lineSeparator() + System.lineSeparator());
		sB.append("***** OPERATORS' DESCRIPTIONS *****" + System.lineSeparator() + System.lineSeparator());
		sB.append(getOperatorsDescription() + System.lineSeparator() + System.lineSeparator());
		return sB.toString();
	}
	
	private String nameAndFrame(IOperator operator) {
		if (variables.contains(operator)) {
			return "<" + operator.getName() + ">";
		}
		else return operator.getName();
	}

}
