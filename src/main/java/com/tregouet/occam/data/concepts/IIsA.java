package com.tregouet.occam.data.concepts;

import org.jgrapht.graph.DefaultEdge;

public abstract class IIsA extends DefaultEdge {

	private static final long serialVersionUID = -9211476597831526082L;

	@Override
	public boolean equals(Object o) {
		if (!(o instanceof IIsA))
			return false;
		IIsA other = (IIsA) o;
		return (getSource().equals(other.getSource()) && getTarget().equals(other.getTarget()));
	}	
	
	@Override
	public int hashCode() {
		return getSource().hashCode() + getTarget().hashCode();
	}

}
