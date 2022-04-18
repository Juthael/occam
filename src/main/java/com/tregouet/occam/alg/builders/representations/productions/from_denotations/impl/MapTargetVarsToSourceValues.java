package com.tregouet.occam.alg.builders.representations.productions.from_denotations.impl;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Set;

import com.tregouet.occam.alg.builders.representations.productions.from_denotations.ProdBuilderFromDenotations;
import com.tregouet.occam.alg.builders.representations.productions.from_denotations.utils.MapVariablesToValues;
import com.tregouet.occam.data.logical_structures.languages.alphabets.AVariable;
import com.tregouet.occam.data.logical_structures.languages.alphabets.ISymbol;
import com.tregouet.occam.data.logical_structures.languages.alphabets.ITerminal;
import com.tregouet.occam.data.logical_structures.languages.alphabets.impl.Terminal;
import com.tregouet.occam.data.logical_structures.languages.words.construct.IConstruct;
import com.tregouet.occam.data.logical_structures.languages.words.construct.impl.Construct;
import com.tregouet.occam.data.representations.concepts.denotations.IDenotation;
import com.tregouet.occam.data.representations.transitions.productions.IContextualizedProduction;
import com.tregouet.occam.data.representations.transitions.productions.IProduction;
import com.tregouet.occam.data.representations.transitions.productions.impl.ContextualizedEpsilonProd;
import com.tregouet.occam.data.representations.transitions.productions.impl.ContextualizedProd;
import com.tregouet.occam.data.representations.transitions.productions.impl.Production;

public class MapTargetVarsToSourceValues implements ProdBuilderFromDenotations {

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

	public MapTargetVarsToSourceValues() {
	}

	@Override
	public Set<IContextualizedProduction> apply(IDenotation source, IDenotation target) {
		Set<IContextualizedProduction> productions = new HashSet<>();
		if (source.asList().equals(target.asList()))
			productions.add(new ContextualizedEpsilonProd(source, target));
		else if (subSequenceOf(target.getListOfTerminals(), source.getListOfTerminals())) {
			//then source may be an instance of target
			List<ISymbol> sourceSymbolSeq = source.asList();
			List<ISymbol> targetSymbolSeq = target.asList();
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
		return productions;
	}

}
