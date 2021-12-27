package com.tregouet.occam.alg.calculators.costs.dep.concept_definition;

import com.tregouet.occam.data.abstract_machines.functions.descriptions.IGenusDifferentiaDefinition;

public interface IConceptDefinitionCostCalculator {
	
	double getDefinitionCost(IGenusDifferentiaDefinition definition);

}
