package com.tregouet.occam.data.problem_space.states.productions;

import java.util.Set;

public interface IClassificationProductions {
	
	Set<IContextualizedProduction> getSalientProductions();
	
	Set<IContextualizedProduction> getProductions();
	
	Salience salienceOf(IContextualizedProduction production);

}
