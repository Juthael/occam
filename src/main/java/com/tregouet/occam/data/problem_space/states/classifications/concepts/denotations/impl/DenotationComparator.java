package com.tregouet.occam.data.problem_space.states.classifications.concepts.denotations.impl;

import java.util.Iterator;
import java.util.List;
import java.util.Map;

import com.tregouet.occam.alg.builders.pb_space.representations.productions.from_denotations.utils.MapVariablesToValues;
import com.tregouet.occam.data.logical_structures.languages.alphabets.AVariable;
import com.tregouet.occam.data.logical_structures.languages.alphabets.ISymbol;
import com.tregouet.occam.data.logical_structures.languages.alphabets.ITerminal;
import com.tregouet.occam.data.problem_space.states.classifications.concepts.denotations.IDenotation;
import com.tregouet.occam.data.problem_space.states.classifications.concepts.denotations.IDenotationComparator;

public class DenotationComparator implements IDenotationComparator {

	public static final DenotationComparator INSTANCE = new DenotationComparator();

	private DenotationComparator() {
	}

	private static boolean strictLowerBoundOf(IDenotation d1, IDenotation d2) {
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
		if (t2.isEmpty())
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

	@Override
	public Integer compare(IDenotation d1, IDenotation d2) {
		/*
		 * implies that alpha-conversion is either supported by equals(), or is useless
		 * because it is guaranteed that two denotations can never vary by the name
		 * of their variables only.
		 */
		if (d1.equals(d2))
			return 0;
		if (strictLowerBoundOf(d1, d2))
			return -1;
		if (strictLowerBoundOf(d2, d1))
			return 1;
		return null;
	}

}
