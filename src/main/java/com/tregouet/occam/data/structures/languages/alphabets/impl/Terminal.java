package com.tregouet.occam.data.structures.languages.alphabets.impl;

import com.tregouet.occam.data.structures.languages.alphabets.ITerminal;
import com.tregouet.occam.io.input.impl.GenericFileReader;

public class Terminal implements ITerminal {

	private final String symbol;

	public Terminal(String symbol) {
		this.symbol = symbol;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if ((obj == null) || (getClass() != obj.getClass()))
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
	
	@Override
	public boolean isRelational() {
		return symbol.charAt(0) == GenericFileReader.RELATIONAL_SYMBOL;
	}

}
