package com.tregouet.occam.data.problem_space.states.productions.impl;

import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Set;

import com.tregouet.occam.data.problem_space.states.productions.IContextualizedProduction;
import com.tregouet.occam.data.problem_space.states.productions.IClassificationProductions;
import com.tregouet.occam.data.problem_space.states.productions.Salience;

public class ClassificationProductions implements IClassificationProductions {
	
	private Map<IContextualizedProduction, Salience> production2Salience = new HashMap<>();
	
	public ClassificationProductions(Map<IContextualizedProduction, Salience> production2Salience) {
		this.production2Salience = production2Salience;
	}

	@Override
	public Set<IContextualizedProduction> getSalientProductions() {
		Set<IContextualizedProduction> salientProductions = new HashSet<>();
		for (Entry<IContextualizedProduction, Salience> mapEntry : production2Salience.entrySet()) {
			Salience salience = mapEntry.getValue();
			if (salience == Salience.COMMON_FEATURE || salience == Salience.TRANSITION_RULE)
				salientProductions.add(mapEntry.getKey());
		}
		return salientProductions;
	}

	@Override
	public Set<IContextualizedProduction> getProductions() {
		return new HashSet<>(production2Salience.keySet());
	}

	@Override
	public Salience salienceOf(IContextualizedProduction production) {
		if (!production2Salience.containsKey(production))
			return null;
		return production2Salience.get(production);
	}

}
