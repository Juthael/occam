package com.tregouet.occam.data.logical_structures.languages.words.construct.impl;

import java.util.Iterator;
import java.util.List;
import java.util.Map;

import com.tregouet.occam.alg.builders.pb_space.representations.production_sets.productions.utils.MapVariablesToValues;
import com.tregouet.occam.data.logical_structures.languages.alphabets.AVariable;
import com.tregouet.occam.data.logical_structures.languages.alphabets.ISymbol;
import com.tregouet.occam.data.logical_structures.languages.alphabets.ITerminal;
import com.tregouet.occam.data.logical_structures.languages.words.construct.IConstruct;
import com.tregouet.occam.data.logical_structures.languages.words.construct.IConstructComparator;

public class ConstructComparator implements IConstructComparator {

	public static final ConstructComparator INSTANCE = new ConstructComparator();

	private ConstructComparator() {
	}

	@Override
	public Integer compare(IConstruct c1, IConstruct c2) {
		/*
		 * supposes that two denotations can never vary by the name
		 * of their variables only.
		 */
		if (c1.asList().equals(c2.asList()))
			return 0;
		if (strictLowerBoundOf(c1, c2))
			return -1;
		if (strictLowerBoundOf(c2, c1))
			return 1;
		return null;
	}

	private static boolean strictLowerBoundOf(IConstruct d1, IConstruct d2) {
		if (subSequenceOf(d2.getListOfTerminals(), d1.getListOfTerminals())) {
			List<ISymbol> d1ValueProvider = d1.asList();
			List<ISymbol> d2VarProvider = d2.asList();
			Map<AVariable, List<ISymbol>> varToValue = MapVariablesToValues.of(d1ValueProvider, d2VarProvider);
			if (varToValue != null) {
				return true;
			}
		}
		return false;
	}

	private static boolean subSequenceOf(List<ITerminal> t1, List<ITerminal> t2) {
		if (t1.isEmpty())
			return true;
		if (t2.isEmpty() || t2.size() <= t1.size())
			return false;
		Iterator<ITerminal> sourceIte = t2.iterator();
		Iterator<ITerminal> targetIte = t1.iterator();
		ITerminal targetCurr = targetIte.next();
		while (sourceIte.hasNext()) {
			if (targetCurr.equals(sourceIte.next())) {
				if (targetIte.hasNext())
					targetCurr = targetIte.next();
				else
					return true;
			}
		}
		return false;
	}

}
