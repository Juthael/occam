package com.tregouet.occam.data.structures.languages.alphabets.impl;

import com.tregouet.occam.data.structures.languages.alphabets.AVariable;
import com.tregouet.subseq_finder.ISymbolSeq;

public class Variable extends AVariable {

	private String name = ISymbolSeq.PLACEHOLDER;

	public Variable(boolean deferredNaming) {
		if (!deferredNaming)
			setName();
	}

	protected Variable(String name) {
		this.name = name;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if ((obj == null) || (getClass() != obj.getClass()))
			return false;
		Variable other = (Variable) obj;
		if (name == null) {
			if (other.name != null)
				return false;
		} else if (!name.equals(other.name))
			return false;
		return true;
	}

	@Override
	public String getName() {
		return name;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((name == null) ? 0 : name.hashCode());
		return result;
	}

	@Override
	public void setName() {
		name = provideName();
	}

	@Override
	public String toString() {
		return name;
	}

}
