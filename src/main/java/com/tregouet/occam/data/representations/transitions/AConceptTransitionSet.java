package com.tregouet.occam.data.representations.transitions;

import java.util.Set;

import org.jgrapht.graph.DefaultEdge;

public abstract class AConceptTransitionSet extends DefaultEdge {

	private static final long serialVersionUID = 5856113545652650804L;

	@Override
	public abstract Integer getSource();

	@Override
	public abstract Integer getTarget();

	public abstract Set<IConceptTransition> getTransitions();

	@Override
	public abstract String toString();

}
