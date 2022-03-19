package com.tregouet.occam.data.representations.properties.transitions.impl;

import java.util.Arrays;

import com.tregouet.occam.data.alphabets.generic.AVariable;
import com.tregouet.occam.data.alphabets.productions.impl.ContextualizedEpsilonProd;
import com.tregouet.occam.data.representations.properties.transitions.IConceptTransition;
import com.tregouet.occam.data.representations.properties.transitions.TransitionType;
import com.tregouet.occam.data.representations.properties.transitions.dimensions.Nothing;

public class InheritanceTransition extends ConceptTransition implements IConceptTransition {

	//for inheritance of unclosed facts. 
	public InheritanceTransition(int inputStateID, int outputStateID) {
		super(new ConceptTransitionIC(inputStateID, new ContextualizedEpsilonProd(null, null), Nothing.INSTANCE), 
				new ConceptTransitionOIC(outputStateID, Arrays.asList(new AVariable[] {Nothing.INSTANCE})));
	}
	
	//for inheritance of closed facts
	public InheritanceTransition(int inputStateID, int outputStateID, ContextualizedEpsilonProd epsilonProd) {
		super(new ConceptTransitionIC(inputStateID, epsilonProd, Nothing.INSTANCE), 
				new ConceptTransitionOIC(outputStateID, Arrays.asList(new AVariable[] {Nothing.INSTANCE})));
	}

	@Override
	public TransitionType type() {
		return TransitionType.INHERITANCE;
	}

}
