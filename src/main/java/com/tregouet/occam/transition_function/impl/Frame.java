package com.tregouet.occam.transition_function.impl;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import com.tregouet.occam.data.constructs.ISymbol;
import com.tregouet.occam.data.constructs.ITerminal;
import com.tregouet.occam.data.constructs.impl.Construct;
import com.tregouet.occam.data.constructs.impl.Variable;
import com.tregouet.occam.transition_function.IFrame;

public class Frame extends Construct implements IFrame {

	public Frame() {
		super(new ArrayList<ISymbol>(Arrays.asList(new ISymbol[] {new Variable(false)})));
	}
	
	public Frame(List<ISymbol> prog) {
		super(prog);
	}

	@Override
	public void restrictFrameWith(IFrame other) {
		int insertionIndex = prog.size() - 1;
		prog.addAll(insertionIndex, other.getListOfSymbols());
		setNbOfTerminals();
	}

}
