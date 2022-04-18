package com.tregouet.occam.data.logical_structures.automata.tapes;

import com.tregouet.occam.data.logical_structures.languages.alphabets.ISymbol;

public interface IPushdownAutomatonTapeSet<
	InputSymbol extends ISymbol,
	StackSymbol extends ISymbol,
	TapeSet extends IPushdownAutomatonTapeSet<InputSymbol, StackSymbol, TapeSet>
	>
	extends ITapeSet<InputSymbol, TapeSet> {

	@Override
	boolean equals(Object o);

	@Override
	int hashCode();

	StackSymbol popOff();

	void pushDown(StackSymbol stackSymbol);

}
