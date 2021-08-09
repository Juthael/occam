package com.tregouet.occam.transition_function.impl;

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

import com.tregouet.occam.data.categories.IIntentAttribute;
import com.tregouet.occam.data.operators.IOperator;
import com.tregouet.occam.data.operators.IProduction;
import com.tregouet.occam.transition_function.IDSLanguageDisplayer;
import com.tregouet.occam.transition_function.IState;

public class DSLanguageDisplayer implements IDSLanguageDisplayer {

	List<IOperator> topologicalSorting = new ArrayList<>();
	IOperator startVariable;
	List<IOperator> variables = new ArrayList<>();
	List<IOperator> terminals = new ArrayList<>(); 
	Map<IOperator, Set<IOperator>> productions = new LinkedHashMap<>();
	
	public DSLanguageDisplayer(List<IState> states, List<IOperator> operators) {
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
				List<IIntentAttribute> outEdgeAcceptedInputs = outEdge.operation()
						.stream()
						.map(p -> p.getSource())
						.collect(Collectors.toList());
				for (IOperator inEdge : incomingEdges) {
					List<IIntentAttribute> inEdgeOutputs = inEdge.operation()
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
		Map<IOperator, String> label = new HashMap<IOperator, String>();
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
			sB.append("   cost : " + Double.toString(operator.getCost()) + System.lineSeparator());
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
		sB.append("***** TOTAL COST : ");
		double totalCost = 0.0;
		for (IOperator operator : topologicalSorting)
			totalCost += operator.getCost();
		sB.append(Double.toString(totalCost));
		return sB.toString();
	}
	
	private String nameAndFrame(IOperator operator) {
		if (variables.contains(operator)) {
			return "<" + operator.getName() + ">";
		}
		else return operator.getName();
	}

}
