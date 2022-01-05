package com.tregouet.occam.alg.scoring.costs.functions.impl;

import java.util.HashSet;
import java.util.Set;

import com.tregouet.occam.alg.scoring.costs.functions.IFunctionCoster;
import com.tregouet.occam.data.abstract_machines.functions.ITransitionFunction;
import com.tregouet.occam.data.abstract_machines.transitions.IBasicOperator;
import com.tregouet.occam.data.abstract_machines.transitions.IBasicProduction;
import com.tregouet.occam.data.abstract_machines.transitions.ICompositeProduction;
import com.tregouet.occam.data.abstract_machines.transitions.IConjunctiveTransition;
import com.tregouet.occam.data.abstract_machines.transitions.IProduction;
import com.tregouet.occam.data.abstract_machines.transitions.ITransition;
import com.tregouet.occam.data.languages.generic.AVariable;

public class NbOfInstantiatedVariables implements IFunctionCoster {

	public static final NbOfInstantiatedVariables INSTANCE = new NbOfInstantiatedVariables();
	private ITransitionFunction transitionFunction = null;
	
	private NbOfInstantiatedVariables() {
	}

	@Override
	public IFunctionCoster input(ITransitionFunction transitionFunction) {
		this.transitionFunction = transitionFunction;
		return this;
	}

	@Override
	public void setCost() {
		Set<AVariable> instantiatedVariables = new HashSet<>();
		for (IConjunctiveTransition conjTransition : transitionFunction.getConjunctiveTransitions()) {
			for (ITransition transition : conjTransition.getComponents()) {
				if (!transition.isReframer()) {
					IBasicOperator basicOperator = (IBasicOperator) transition;
					for (IProduction production : basicOperator.operation()) {
						if (production instanceof IBasicProduction) {
							if (!production.isBlank() && !production.isVariableSwitcher())
								instantiatedVariables.add(((IBasicProduction) production).getVariable());
						}
						else {
							ICompositeProduction compositeProd = (ICompositeProduction) production;
							for (IBasicProduction basicProd : compositeProd.getComponents()) {
								if (!basicProd.isBlank() && !basicProd.isVariableSwitcher()) {
									instantiatedVariables.add(basicProd.getVariable());
								}
							}
						}
					}
				}
			}
		}
		transitionFunction.setCost((double) instantiatedVariables.size());
	}

}
