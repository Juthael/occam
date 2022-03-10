package com.tregouet.occam.alg.concepts_gen.transitions_gen.impl;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import com.tregouet.occam.alg.concepts_gen.transitions_gen.IProductionBuilder;
import com.tregouet.occam.alg.concepts_gen.transitions_gen.utils.MapVariablesToValues;
import com.tregouet.occam.data.alphabets.ISymbol;
import com.tregouet.occam.data.alphabets.productions.IContextualizedProduction;
import com.tregouet.occam.data.alphabets.productions.IProduction;
import com.tregouet.occam.data.alphabets.productions.impl.ContextualizedEpsilon;
import com.tregouet.occam.data.alphabets.productions.impl.ContextualizedProduction;
import com.tregouet.occam.data.alphabets.productions.impl.Production;
import com.tregouet.occam.data.languages.generic.AVariable;
import com.tregouet.occam.data.languages.generic.IConstruct;
import com.tregouet.occam.data.languages.generic.ITerminal;
import com.tregouet.occam.data.languages.generic.impl.Construct;
import com.tregouet.occam.data.languages.generic.impl.Terminal;
import com.tregouet.occam.data.preconcepts.IDenotation;

public class ContextualizedProductionBuilder implements IProductionBuilder<IContextualizedProduction> {
	
	public static final ContextualizedProductionBuilder INSTANCE = new ContextualizedProductionBuilder();
	
	private List<IContextualizedProduction> productions = null;
	
	private ContextualizedProductionBuilder() {
	}

	@Override
	public IProductionBuilder<IContextualizedProduction> input(IDenotation source, IDenotation target) {
		productions = new ArrayList<>();
		if (source.getListOfSymbols().equals(target.getListOfSymbols()))
			productions.add(new ContextualizedEpsilon(source, target));
		else if (subSequenceOf(target.getListOfTerminals(), source.getListOfTerminals())) {
			//then source may be an instance of target
			List<ISymbol> sourceSymbolSeq = source.getListOfSymbols();
			List<ISymbol> targetSymbolSeq = target.getListOfSymbols();
			Map<AVariable, List<ISymbol>> varToValue = MapVariablesToValues.of(sourceSymbolSeq, targetSymbolSeq);
			if (varToValue != null) {
				for (AVariable variable : varToValue.keySet()) {
					IConstruct value;
					List<ISymbol> listOfSymbols = varToValue.get(variable);
					if (listOfSymbols.isEmpty()) {
						List<ISymbol> emptyString = 
								new ArrayList<>(Arrays.asList(new ISymbol[] {new Terminal(IConstruct.EMPTY_CONSTRUCT_SYMBOL)}));
						value = new Construct(emptyString);
					}
					else value = new Construct(listOfSymbols);
					IProduction production = new Production(variable, value);
					productions.add(new ContextualizedProduction(source, target, production));
				}
			}
		}
		return this;
	}

	@Override
	public List<IContextualizedProduction> output() {
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
