package com.tregouet.occam.alg.scoring.costs.functions.impl;

import java.util.HashSet;
import java.util.Set;

import com.tregouet.occam.alg.scoring.costs.functions.IFunctionCoster;
import com.tregouet.occam.data.abstract_machines.automatons.IAutomaton;
import com.tregouet.occam.data.abstract_machines.transition_rules.IBasicOperator;
import com.tregouet.occam.data.abstract_machines.transition_rules.IConjunctiveTransition;
import com.tregouet.occam.data.abstract_machines.transition_rules.ITransitionRule;
import com.tregouet.occam.data.languages.generic.AVariable;
import com.tregouet.occam.data.languages.specific.ISimpleEdgeProduction;
import com.tregouet.occam.data.languages.specific.ICompositeEdgeProduction;
import com.tregouet.occam.data.languages.specific.IProductionAsEdge;

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
			for (ITransitionRule transitionRule : conjTransition.getComponents()) {
				if (!transitionRule.isReframer()) {
					IBasicOperator basicOperator = (IBasicOperator) transitionRule;
					for (IProductionAsEdge productionAsEdge : basicOperator.operation()) {
						if (productionAsEdge instanceof ISimpleEdgeProduction) {
							if (!productionAsEdge.isBlank() && !productionAsEdge.isVariableSwitcher())
								instantiatedVariables.add(((ISimpleEdgeProduction) productionAsEdge).getVariable());
						}
						else {
							ICompositeEdgeProduction compositeProd = (ICompositeEdgeProduction) productionAsEdge;
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
