package com.tregouet.occam.data.preconcepts.impl;

import java.util.Iterator;
import java.util.List;
import java.util.Map;

import com.tregouet.occam.alg.concepts_gen.transitions_gen.utils.MapVariablesToValues;
import com.tregouet.occam.data.alphabets.ISymbol;
import com.tregouet.occam.data.languages.generic.AVariable;
import com.tregouet.occam.data.languages.generic.ITerminal;
import com.tregouet.occam.data.preconcepts.IDenotation;
import com.tregouet.occam.data.preconcepts.IDenotationComparator;

public class DenotationComparator implements IDenotationComparator {
	
	public static final DenotationComparator INSTANCE = new DenotationComparator();
	
	private DenotationComparator() {
	}
	
	private static boolean strictLowerBoundOf(IDenotation d1, IDenotation d2) {
		if (subSequenceOf(d2.getListOfTerminals(), d1.getListOfTerminals())) {
			List<ISymbol> d1SymbolSeq = d1.getListOfSymbols();
			List<ISymbol> d2SymbolSeq = d2.getListOfSymbols();
			Map<AVariable, List<ISymbol>> varToValue = MapVariablesToValues.of(d1SymbolSeq, d2SymbolSeq);
			if (varToValue != null) {
				return true;
			}
		}
		return false;
	}
	
	private static boolean subSequenceOf(List<ITerminal> targetTerminals, List<ITerminal> sourceTerminals) {
		if (targetTerminals.isEmpty())
			return true;
		if (sourceTerminals.isEmpty())
			return false;
		Iterator<ITerminal> sourceIte = sourceTerminals.iterator();
		Iterator<ITerminal> targetIte = targetTerminals.iterator();
		ITerminal targetCurr = targetIte.next();
		while (sourceIte.hasNext()) {
			if (targetCurr.equals(sourceIte.next())) {
				if (targetIte.hasNext())
					targetCurr = targetIte.next();
				else return true;
			}
		}
		return false;
	}

	@Override
	public Integer compare(IDenotation d1, IDenotation d2) {
		/* supposes that alpha-conversion is either supported by equals(), or is useless because 
		 * it is guaranteed that two denotations can never vary only by the name of their variables.  
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
