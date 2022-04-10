package com.tregouet.occam.data.logical_structures.languages.alphabets.impl;

import com.tregouet.occam.data.logical_structures.languages.alphabets.ITerminal;

public class Terminal implements ITerminal {

	private final String symbol;
	
	public Terminal(String symbol) {
		this.symbol = symbol;
	}
	
	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		Terminal other = (Terminal) obj;
		if (symbol == null) {
			if (other.symbol != null)
				return false;
		} else if (!symbol.equals(other.symbol))
			return false;
		return true;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((symbol == null) ? 0 : symbol.hashCode());
		return result;
	}

	@Override
	public String toString() {
		return symbol;
	}

}
