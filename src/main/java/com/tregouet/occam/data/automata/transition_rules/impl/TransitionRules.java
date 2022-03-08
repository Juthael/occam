package com.tregouet.occam.data.automata.transition_rules.impl;

import java.util.ArrayList;
import java.util.List;

import com.tregouet.occam.data.automata.transition_rules.ITransition;
import com.tregouet.occam.data.automata.transition_rules.ITransitionRules;

public class TransitionRules extends ITransitionRules {
	
	private static final long serialVersionUID = -5459273395018768008L;
	
	private List<ITransition> transitions = new ArrayList<>();
	
	public TransitionRules(List<ITransition> transitions) {
		this.transitions = transitions;
	}
	
	public List<ITransition> getTransitionRules(){
		return transitions;
	}

}
