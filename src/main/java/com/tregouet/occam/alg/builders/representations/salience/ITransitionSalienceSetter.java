package com.tregouet.occam.alg.builders.representations.salience;

import java.util.Set;

import com.tregouet.occam.data.representations.properties.transitions.IConceptTransition;

public interface ITransitionSalienceSetter {
	
	void setTransitionSaliencesOf(Set<IConceptTransition> transitions);

}
