package com.tregouet.occam.data.operators.impl.util;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.tregouet.occam.data.categories.ICategories;
import com.tregouet.occam.data.categories.IIntentAttribute;
import com.tregouet.occam.data.constructs.AVariable;
import com.tregouet.occam.data.constructs.IConstruct;
import com.tregouet.occam.data.constructs.ISymbol;
import com.tregouet.occam.data.constructs.impl.Construct;
import com.tregouet.occam.data.constructs.impl.Terminal;
import com.tregouet.occam.data.operators.IProduction;
import com.tregouet.occam.data.operators.impl.BlankProduction;
import com.tregouet.occam.data.operators.impl.Production;

public class ProductionGenerator {

	private List<IProduction> productions = null;
	
	/**
	 * Meaningless if Categories.isA(operatorInput.cat, operatorOutput.cat) == false
	 * @param operatorInput
	 * @param operatorOutput
	 */
	public ProductionGenerator(
			ICategories categories, IIntentAttribute operatorInput, IIntentAttribute operatorOutput) {
		if (!categories.isA(operatorInput.getCategory(), operatorOutput.getCategory()))
			productions = null;
		else {
			if (operatorInput.getListOfSymbols().equals(operatorOutput.getListOfSymbols()))
				productions = new ArrayList<>(
						Arrays.asList(new IProduction[] {new BlankProduction(operatorInput, operatorOutput)}));
			else if (operatorInput.getListOfTerminals().containsAll(operatorOutput.getListOfTerminals())) {
				List<ISymbol> source = operatorInput.getListOfSymbols();
				List<ISymbol> target = operatorOutput.getListOfSymbols();
				Map<AVariable, List<ISymbol>> varToValue = mapVariablesToValues(source, target);
				if (varToValue != null) {
					productions = new ArrayList<>();
					for (AVariable variable : varToValue.keySet()) {
						IConstruct value;
						if (varToValue.get(variable).isEmpty()) {
							List<ISymbol> emptyString = 
									new ArrayList<>(Arrays.asList(new ISymbol[] {new Terminal("ε")}));
							value = new Construct(emptyString);
						}
						else value = new Construct(varToValue.get(variable));
						productions.add(new Production(variable, value, operatorInput, operatorOutput));
					}
				}
			}
		}
	}
	
	/**
	 * 
	 * @return null if constr param1 can't be generated by constr param2, a list only containing a BlankProduction if 
	 * they have the same list of symbols, a list of Productions otherwise
	 */
	public List<IProduction> getProduction() {
		return productions;
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
