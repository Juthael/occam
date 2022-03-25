package com.tregouet.occam.alg.builders.representations.productions.from_denotations.impl;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Set;

import com.tregouet.occam.alg.builders.representations.productions.from_denotations.IProdBuilderFromDenotations;
import com.tregouet.occam.alg.builders.representations.productions.from_denotations.utils.MapVariablesToValues;
import com.tregouet.occam.data.alphabets.ISymbol;
import com.tregouet.occam.data.alphabets.generic.AVariable;
import com.tregouet.occam.data.alphabets.generic.ITerminal;
import com.tregouet.occam.data.alphabets.generic.impl.Terminal;
import com.tregouet.occam.data.alphabets.productions.IContextualizedProduction;
import com.tregouet.occam.data.alphabets.productions.IProduction;
import com.tregouet.occam.data.alphabets.productions.impl.ContextualizedEpsilonProd;
import com.tregouet.occam.data.alphabets.productions.impl.ContextualizedProd;
import com.tregouet.occam.data.alphabets.productions.impl.Production;
import com.tregouet.occam.data.concepts.IDenotation;
import com.tregouet.occam.data.languages.generic.IConstruct;
import com.tregouet.occam.data.languages.generic.impl.Construct;

public class MapTargetVarsToSourceValues implements IProdBuilderFromDenotations {
	
	private Set<IContextualizedProduction> productions = null;
	
	public MapTargetVarsToSourceValues() {
	}

	@Override
	public IProdBuilderFromDenotations input(IDenotation source, IDenotation target) {
		productions = new HashSet<>();
		if (source.getListOfSymbols().equals(target.getListOfSymbols()))
			productions.add(new ContextualizedEpsilonProd(source, target));
		else if (subSequenceOf(target.getListOfTerminals(), source.getListOfTerminals())) {
			//then source may be an instance of target
			List<ISymbol> sourceSymbolSeq = source.getListOfSymbols();
			List<ISymbol> targetSymbolSeq = target.getListOfSymbols();
			Map<AVariable, List<ISymbol>> varToValue = MapVariablesToValues.of(sourceSymbolSeq, targetSymbolSeq);
			if (varToValue != null) {
				for (AVariable variable : varToValue.keySet()) {
					IConstruct value;
					List<ISymbol> valueList = varToValue.get(variable);
					if (valueList.isEmpty()) {
						List<ISymbol> emptyString = 
								new ArrayList<>(Arrays.asList(new ISymbol[] {new Terminal(IConstruct.EMPTY_CONSTRUCT_SYMBOL)}));
						value = new Construct(emptyString);
					}
					else value = new Construct(valueList);
					IProduction production = new Production(variable, value);
					productions.add(new ContextualizedProd(source, target, production));
				}
			}
		}
		return this;
	}

	@Override
	public Set<IContextualizedProduction> output() {
		return productions;
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

}
