package com.tregouet.occam.alg.concepts_gen.transitions_gen.utils;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.tregouet.occam.data.alphabets.ISymbol;
import com.tregouet.occam.data.languages.generic.AVariable;

public interface MapVariablesToValues {
	
	/**
	 * @param source
	 * @param target
	 * @return null if source is not a target's instance
	 */
	public static Map<AVariable, List<ISymbol>> of(List<ISymbol> source, List<ISymbol> target) {
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
