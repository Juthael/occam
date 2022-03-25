package com.tregouet.occam.alg.builders.representations.productions.from_denotations.utils;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.tregouet.occam.data.languages.alphabets.ISymbol;
import com.tregouet.occam.data.languages.alphabets.generic.AVariable;

public interface MapVariablesToValues {
	
	/**
	 * @param valueProvider
	 * @param varProvider
	 * @return null if source is not a target's instance
	 */
	public static Map<AVariable, List<ISymbol>> of(List<ISymbol> valueProvider, List<ISymbol> varProvider) {
		return continueMapping(valueProvider, varProvider, new HashMap<AVariable, List<ISymbol>>(), 0, 0);
	}
	
	private static Map<AVariable, List<ISymbol>> continueMapping(List<ISymbol> valueProvider, List<ISymbol> varProvider, 
			Map<AVariable, List<ISymbol>> varToValue, int srcIdx, int targetIdx) {
		if (srcIdx == valueProvider.size() && targetIdx == varProvider.size())
			return varToValue;
		if (srcIdx == valueProvider.size() || targetIdx == varProvider.size())
			return null;		
		if (varProvider.get(targetIdx) instanceof AVariable) {
			AVariable variable = (AVariable) varProvider.get(targetIdx);
			int varSpan = 0;
			Map<AVariable, List<ISymbol>> nextMap = null;
			while (nextMap == null && (srcIdx + varSpan <= valueProvider.size())) {
				nextMap = deepCopy(varToValue);
				nextMap.put(variable, new ArrayList<ISymbol>());
				int srcAdvance = 0;
				while (srcAdvance < varSpan) {
					nextMap.get(variable).add(valueProvider.get(srcIdx + srcAdvance));
					srcAdvance++;
				}					
				nextMap = continueMapping(valueProvider, varProvider, nextMap, srcIdx + srcAdvance, targetIdx + 1);
				varSpan++;
			}
			return nextMap;
		}
		else {
			if (valueProvider.get(srcIdx).equals(varProvider.get(targetIdx))) {
				return continueMapping(valueProvider, varProvider, varToValue, srcIdx + 1, targetIdx + 1);
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
