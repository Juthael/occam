package com.tregouet.occam.alg.builders.representations.transition_functions.transition_saliences;

import java.util.Set;
import java.util.function.Consumer;

import com.tregouet.occam.data.representations.properties.transitions.IConceptTransition;

@FunctionalInterface
public interface TransitionSalienceSetter extends Consumer<Set<IConceptTransition>> {

}
