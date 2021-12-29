package com.tregouet.occam.alg.scoring.costs.functions.impl;

import com.tregouet.occam.alg.scoring.costs.functions.IFunctionCoster;
import com.tregouet.occam.data.abstract_machines.functions.ITransitionFunction;
import com.tregouet.occam.data.abstract_machines.transitions.IBasicOperator;
import com.tregouet.occam.data.abstract_machines.transitions.IBasicProduction;
import com.tregouet.occam.data.abstract_machines.transitions.ICompositeProduction;
import com.tregouet.occam.data.abstract_machines.transitions.IConjunctiveTransition;
import com.tregouet.occam.data.abstract_machines.transitions.IProduction;
import com.tregouet.occam.data.abstract_machines.transitions.ITransition;

public class NbOfInstantiations implements IFunctionCoster {

	public static final NbOfInstantiations INSTANCE = new NbOfInstantiations();
	private ITransitionFunction transitionFunction = null;
	
	private NbOfInstantiations() {
	}

	@Override
	public IFunctionCoster input(ITransitionFunction transitionFunction) {
		this.transitionFunction = transitionFunction;
		return this;
	}

	@Override
	public void setCost() {
		int nbOfInstantiations = 0;
		for (IConjunctiveTransition conjTransition : transitionFunction.getConjunctiveTransitions()) {
			for (ITransition transition : conjTransition.getComponents()) {
				if (!transition.isReframer()) {
					IBasicOperator basicOperator = (IBasicOperator) transition;
					for (IProduction production : basicOperator.operation()) {
						if (production instanceof IBasicProduction) {
							if (!production.isBlank() && !production.isVariableSwitcher())
								nbOfInstantiations++;
						}
						else {
							ICompositeProduction compositeProd = (ICompositeProduction) production;
							for (IBasicProduction basicProd : compositeProd.getComponents()) {
								if (!basicProd.isBlank() && !basicProd.isVariableSwitcher()) {
									nbOfInstantiations++;
								}
							}
						}
					}
				}
			}
		}
		transitionFunction.setCost(nbOfInstantiations);
	}

}
