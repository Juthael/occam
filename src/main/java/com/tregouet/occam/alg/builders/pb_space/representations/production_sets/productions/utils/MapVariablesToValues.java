package com.tregouet.occam.alg.builders.pb_space.representations.production_sets.productions.utils;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.tregouet.occam.data.logical_structures.languages.alphabets.AVariable;
import com.tregouet.occam.data.logical_structures.languages.alphabets.ISymbol;

public interface MapVariablesToValues {

	/**
	 * @param valueProvider
	 * @param varProvider
	 * @return null if valueProvider is not an instance of varProvider
	 */
	public static Map<AVariable, List<ISymbol>> of(List<ISymbol> valueProvider, List<ISymbol> varProvider) {
		if (valueProvider.equals(varProvider)) {
			Map<AVariable, List<ISymbol>> varToValue = new HashMap<>();
			for (ISymbol symbol : varProvider) {
				if (symbol instanceof AVariable) {
					varToValue.put((AVariable) symbol, Arrays.asList(new ISymbol[] {symbol}));
				}
			}
			return varToValue;
		}
		return continueMapping(valueProvider, varProvider, new HashMap<AVariable, List<ISymbol>>(), 0, 0);
	}

	private static Map<AVariable, List<ISymbol>> continueMapping(List<ISymbol> valueProvider, List<ISymbol> varProvider,
			Map<AVariable, List<ISymbol>> varToValue, int srcIdx, int targetIdx) {
		if (targetIdx == varProvider.size()) {
			if (srcIdx == valueProvider.size())
				return varToValue;
			return null;
		}
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
		if (srcIdx < valueProvider.size() && valueProvider.get(srcIdx).equals(varProvider.get(targetIdx)))
			return continueMapping(valueProvider, varProvider, varToValue, srcIdx + 1, targetIdx + 1);
		return null;
	}

	private static Map<AVariable, List<ISymbol>> deepCopy(Map<AVariable, List<ISymbol>> map) {
		Map<AVariable, List<ISymbol>> mapDeepCopy = new HashMap<>();
		for (AVariable key : map.keySet()) {
			mapDeepCopy.put(key, new ArrayList<>(map.get(key)));
		}
		return mapDeepCopy;
	}

}
