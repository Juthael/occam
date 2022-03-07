package com.tregouet.occam.alg.transition_function_gen.impl;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import com.tregouet.occam.alg.transition_function_gen.IProductionBuilder;
import com.tregouet.occam.data.alphabets.ISymbol;
import com.tregouet.occam.data.alphabets.productions.IContextualizedProduction;
import com.tregouet.occam.data.alphabets.productions.IProduction;
import com.tregouet.occam.data.alphabets.productions.impl.ContextualizedEpsilon;
import com.tregouet.occam.data.alphabets.productions.impl.ContextualizedProduction;
import com.tregouet.occam.data.alphabets.productions.impl.Production;
import com.tregouet.occam.data.denotations.IDenotation;
import com.tregouet.occam.data.languages.generic.AVariable;
import com.tregouet.occam.data.languages.generic.IConstruct;
import com.tregouet.occam.data.languages.generic.ITerminal;
import com.tregouet.occam.data.languages.generic.impl.Construct;
import com.tregouet.occam.data.languages.generic.impl.Terminal;

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
			//then source is an instance of target
			List<ISymbol> sourceSymbolSeq = source.getListOfSymbols();
			List<ISymbol> targetSymbolSeq = target.getListOfSymbols();
			Map<AVariable, List<ISymbol>> varToValue = mapVariablesToValues(sourceSymbolSeq, targetSymbolSeq);
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
	
	/**
	 * Public for test use
	 * @param source
	 * @param target
	 * @return
	 */
	public static Map<AVariable, List<ISymbol>> mapVariablesToValues(List<ISymbol> source, List<ISymbol> target) {
		return continueMapping(source, target, new HashMap<AVariable, List<ISymbol>>(), 0, 0);
	}
	
	private static Map<AVariable, List<ISymbol>> continueMapping(List<ISymbol> source, List<ISymbol> target, 
			Map<AVariable, List<ISymbol>> varToValue, int srcIdx, int targetIdx) {
		if (srcIdx == source.size() && targetIdx == target.size())
			return varToValue;
		if (srcIdx == source.size() || targetIdx == target.size())
			return null;		
		if (target.get(targetIdx) instanceof AVariable) {
			AVariable variable = (AVariable) target.get(targetIdx);
			int varSpan = 0;
			Map<AVariable, List<ISymbol>> nextMap = null;
			while (nextMap == null && (srcIdx + varSpan <= source.size())) {
				nextMap = deepCopy(varToValue);
				nextMap.put(variable, new ArrayList<ISymbol>());
				int srcAdvance = 0;
				while (srcAdvance < varSpan) {
					nextMap.get(variable).add(source.get(srcIdx + srcAdvance));
					srcAdvance++;
				}					
				nextMap = continueMapping(source, target, nextMap, srcIdx + srcAdvance, targetIdx + 1);
				varSpan++;
			}
			return nextMap;
		}
		else {
			if (source.get(srcIdx).equals(target.get(targetIdx))) {
				return continueMapping(source, target, varToValue, srcIdx + 1, targetIdx + 1);
			}
			return null;
		}
	}	
	
	private static Map<AVariable, List<ISymbol>> deepCopy(Map<AVariable, List<ISymbol>> map){
		Map<AVariable, List<ISymbol>> mapDeepCopy = new HashMap<>();
		for (AVariable key : map.keySet()) {
			mapDeepCopy.put(key, new ArrayList<ISymbol>(map.get(key)));
		}
		return mapDeepCopy;
	}	

}
