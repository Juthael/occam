package com.tregouet.occam.data.problem_space.states.transitions.impl;

import java.util.Set;

import com.tregouet.occam.data.problem_space.states.transitions.AConceptTransitionSet;
import com.tregouet.occam.data.problem_space.states.transitions.IConceptTransition;

public class ConceptTransitionSet extends AConceptTransitionSet {

	private static final long serialVersionUID = -815277218914511013L;

	private Integer source;
	private Integer target;
	private Set<IConceptTransition> transitions;
	private String asString;

	public ConceptTransitionSet(Integer source, Integer target, Set<IConceptTransition> transitions, String asString) {
		this.source = source;
		this.target = target;
		this.transitions = transitions;
		this.asString = asString;
	}

	@Override
	public Integer getSource() {
		return source;
	}

	@Override
	public Integer getTarget() {
		return target;
	}

	@Override
	public Set<IConceptTransition> getTransitions() {
		return transitions;
	}

	@Override
	public String toString() {
		return asString;
	}

}
