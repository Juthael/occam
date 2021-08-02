package com.tregouet.occam.data.operators.impl.util;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.tregouet.occam.data.categories.IIntentAttribute;
import com.tregouet.occam.data.constructs.AVariable;
import com.tregouet.occam.data.constructs.IConstruct;
import com.tregouet.occam.data.constructs.ISymbol;
import com.tregouet.occam.data.constructs.impl.Construct;
import com.tregouet.occam.data.constructs.impl.Terminal;
import com.tregouet.occam.data.operators.IProduction;
import com.tregouet.occam.data.operators.impl.Production;

public class ProductionGenerator {

	private final List<ISymbol> source;
	private final List<ISymbol> target;
	private final Map<AVariable, List<ISymbol>> varToValue;
	private final List<IProduction> productions;
	
	public ProductionGenerator(IIntentAttribute operatorInput, IIntentAttribute operatorOutput) {
		source = operatorInput.getListOfSymbols();
		target = operatorOutput.getListOfSymbols();
		varToValue = mapVariablesToValues(source, target);
		if (varToValue == null) {
			productions = null;
		}
		else {
			productions = new ArrayList<>();
			for (AVariable variable : varToValue.keySet()) {
				IConstruct value;
				if (varToValue.get(variable).isEmpty()) {
					List<ISymbol> emptyString = new ArrayList<>(Arrays.asList(new ISymbol[] {new Terminal("ε")}));
					value = new Construct(emptyString);
				}
				else value = new Construct(varToValue.get(variable));
				productions.add(new Production(variable, value, operatorInput, operatorOutput));
			}
		}
	}
	
	public List<IProduction> getProduction() {
		return productions;
	}
	
	public static Map<AVariable, List<ISymbol>> mapVariablesToValues(List<ISymbol> source, List<ISymbol> target) {
		return continueMapping(source, target, new HashMap<AVariable, List<ISymbol>>(), -1, -1);
	}
	
	//start at -1, -1
	private static Map<AVariable, List<ISymbol>> continueMapping(List<ISymbol> source, List<ISymbol> target, 
			Map<AVariable, List<ISymbol>> varToValue, int srcIdx, int targetIdx) {
		if (srcIdx == source.size() - 1 && targetIdx == target.size() - 1)
			return varToValue;
		if (srcIdx == source.size() - 1 || targetIdx == target.size() - 1)
			return null;		
		targetIdx ++;
		if (target.get(targetIdx) instanceof AVariable) {
			AVariable variable = (AVariable) target.get(targetIdx);
			int varSpan = 0;
			Map<AVariable, List<ISymbol>> nextMap = null;
			while (nextMap == null && (srcIdx + varSpan < source.size())) {
				nextMap = deepCopy(varToValue);
				nextMap.put(variable, new ArrayList<ISymbol>());
				int srcAdvance = 0;
				while (srcAdvance < varSpan) {
					nextMap.get(variable).add(source.get(srcIdx + srcAdvance));
					srcAdvance++;
				}					
				nextMap = continueMapping(source, target, nextMap, srcIdx + srcAdvance, targetIdx);
				varSpan++;
			}
			return nextMap;
		}
		else {
			srcIdx++;
			if (source.get(srcIdx).equals(target.get(targetIdx))) {
				return continueMapping(source, target, varToValue, srcIdx, targetIdx);
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
