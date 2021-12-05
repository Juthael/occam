package com.tregouet.occam.data.concepts.impl;

import org.jgrapht.graph.DefaultEdge;

public class IsA extends DefaultEdge {
	
	private static final long serialVersionUID = -4786781274670318944L;

	@Override
	public boolean equals(Object o) {
		if (getClass() != o.getClass())
			return false;
		IsA other = (IsA) o;
		return (getSource().equals(other.getSource()) && getTarget().equals(other.getTarget()));
	}	
	
	@Override
	public int hashCode() {
		return getSource().hashCode() + getTarget().hashCode();
	}		

}
