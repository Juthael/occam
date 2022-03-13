package com.tregouet.occam.alg.scoring_dep.costs.functions.impl;

import java.util.HashSet;
import java.util.Set;

import com.tregouet.occam.alg.scoring_dep.costs.functions.IFunctionCoster;
import com.tregouet.occam.data.automata.machines.IAutomaton;
import com.tregouet.occam.data.automata.transitions.IBasicOperator;
import com.tregouet.occam.data.automata.transitions.IConjunctiveTransition;
import com.tregouet.occam.data.automata.transitions.ITransition;
import com.tregouet.occam.data.languages.generic.AVariable;
import com.tregouet.occam.data.languages.specific.ISimpleEdgeProduction;
import com.tregouet.occam.data.languages.specific.ICompositeEdgeProduction;
import com.tregouet.occam.data.languages.specific.IStronglyContextualized;

public class NbOfInstantiatedVariables implements IFunctionCoster {

	public static final NbOfInstantiatedVariables INSTANCE = new NbOfInstantiatedVariables();
	private IAutomaton automaton = null;
	
	private NbOfInstantiatedVariables() {
	}

	@Override
	public IFunctionCoster input(IAutomaton automaton) {
		this.automaton = automaton;
		return this;
	}

	@Override
	public void setCost() {
		Set<AVariable> instantiatedVariables = new HashSet<>();
		for (IConjunctiveTransition conjTransition : automaton.getConjunctiveTransitions()) {
			for (ITransition transition : conjTransition.getComponents()) {
				if (!transition.isReframer()) {
					IBasicOperator basicOperator = (IBasicOperator) transition;
					for (IStronglyContextualized stronglyContextualized : basicOperator.operation()) {
						if (stronglyContextualized instanceof ISimpleEdgeProduction) {
							if (!stronglyContextualized.isBlank() && !stronglyContextualized.isVariableSwitcher())
								instantiatedVariables.add(((ISimpleEdgeProduction) stronglyContextualized).getVariable());
						}
						else {
							ICompositeEdgeProduction compositeProd = (ICompositeEdgeProduction) stronglyContextualized;
							for (ISimpleEdgeProduction basicProd : compositeProd.getComponents()) {
								if (!basicProd.isBlank() && !basicProd.isVariableSwitcher()) {
									instantiatedVariables.add(basicProd.getVariable());
								}
							}
						}
					}
				}
			}
		}
		automaton.setCost((double) instantiatedVariables.size());
	}

}