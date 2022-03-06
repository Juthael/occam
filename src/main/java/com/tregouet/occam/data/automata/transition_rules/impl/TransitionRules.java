package com.tregouet.occam.data.automata.transition_rules.impl;

import java.util.ArrayList;
import java.util.List;

import com.tregouet.occam.data.automata.transition_rules.ITransitionRule;
import com.tregouet.occam.data.automata.transition_rules.ITransitionRules;

public class TransitionRules extends ITransitionRules {
	
	private static final long serialVersionUID = -5459273395018768008L;
	
	private List<ITransitionRule> transitionRules = new ArrayList<>();
	
	public TransitionRules(List<ITransitionRule> transitionRules) {
		this.transitionRules = transitionRules;
	}
	
	public List<ITransitionRule> getTransitionRules(){
		return transitionRules;
	}

}
