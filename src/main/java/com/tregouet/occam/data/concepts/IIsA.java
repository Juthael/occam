package com.tregouet.occam.data.concepts;

import org.jgrapht.graph.DefaultEdge;

import com.tregouet.occam.data.concepts.impl.IsA;

public abstract class IIsA extends DefaultEdge {

	private static final long serialVersionUID = -9211476597831526082L;

	@Override
	public int hashCode() {
		return getSource().hashCode() + getTarget().hashCode();
	}	
	
	@Override
	public boolean equals(Object o) {
		if (getClass() != o.getClass())
			return false;
		IsA other = (IsA) o;
		return (getSource().equals(other.getSource()) && getTarget().equals(other.getTarget()));
	}

}
