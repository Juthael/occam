package com.tregouet.occam.alg.builders.pb_space.representations.production_sets.productions.impl;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Set;

import com.tregouet.occam.alg.builders.pb_space.representations.production_sets.productions.ProductionBuilder;
import com.tregouet.occam.alg.builders.pb_space.representations.production_sets.productions.utils.MapVariablesToValues;
import com.tregouet.occam.data.logical_structures.languages.alphabets.AVariable;
import com.tregouet.occam.data.logical_structures.languages.alphabets.ISymbol;
import com.tregouet.occam.data.logical_structures.languages.alphabets.ITerminal;
import com.tregouet.occam.data.logical_structures.languages.alphabets.impl.Terminal;
import com.tregouet.occam.data.logical_structures.languages.words.construct.IConstruct;
import com.tregouet.occam.data.logical_structures.languages.words.construct.impl.Construct;
import com.tregouet.occam.data.problem_space.states.classifications.concepts.IConcept;
import com.tregouet.occam.data.problem_space.states.classifications.concepts.denotations.IDenotation;
import com.tregouet.occam.data.problem_space.states.productions.IContextualizedProduction;
import com.tregouet.occam.data.problem_space.states.productions.IProduction;
import com.tregouet.occam.data.problem_space.states.productions.impl.ContextualizedEpsilonProd;
import com.tregouet.occam.data.problem_space.states.productions.impl.ContextualizedProd;
import com.tregouet.occam.data.problem_space.states.productions.impl.Production;

public class MapTargetVarsToSourceValues implements ProductionBuilder {

	public MapTargetVarsToSourceValues() {
	}

	@Override
	public Set<IContextualizedProduction> apply(IDenotation source, IDenotation target) {
		Set<IContextualizedProduction> productions = new HashSet<>();
		if (subSequenceOf(target.getListOfTerminals(), source.getListOfTerminals()) && isValid(source, target)) {
			// then source may be an instance of target
			if (target.getVariables().size() > 0) {
				//since if no variable can be bound, then nothing to produce
				List<ISymbol> sourceSymbolSeq = source.asList();
				List<ISymbol> targetSymbolSeq = target.asList();
				Map<AVariable, List<ISymbol>> varToValue = MapVariablesToValues.of(sourceSymbolSeq, targetSymbolSeq);
				if (varToValue != null) {
					for (AVariable variable : varToValue.keySet()) {
						IConstruct value;
						List<ISymbol> valueList = varToValue.get(variable);
						if (valueList.isEmpty()) {
							List<ISymbol> emptyString = new ArrayList<>(
									Arrays.asList(new ISymbol[] { new Terminal(IConstruct.EMPTY_CONSTRUCT_SYMBOL) }));
							value = new Construct(emptyString);
						}
						else {
							value = new Construct(valueList);
							IProduction production = new Production(variable, value);
							productions.add(new ContextualizedProd(source, target, production));
						}
					}
				}
			}
			else {
				productions.add(new ContextualizedEpsilonProd(source, target));
			}
		}
		return productions;
	}

	@Override
	public ProductionBuilder setUp(IConcept sourceConcept) {
		return this;
	}

	protected boolean isValid(IDenotation source, IDenotation target) {
		return true;
	}

	private static boolean subSequenceOf(List<ITerminal> targetTerminals, List<ITerminal> sourceTerminals) {
		if (targetTerminals.isEmpty())
			return true;
		if (sourceTerminals.isEmpty())
			return false;
		if (targetTerminals.equals(sourceTerminals))
			return true;
		Iterator<ITerminal> sourceIte = sourceTerminals.iterator();
		Iterator<ITerminal> targetIte = targetTerminals.iterator();
		ITerminal targetCurr = targetIte.next();
		while (sourceIte.hasNext()) {
			if (targetCurr.equals(sourceIte.next())) {
				if (targetIte.hasNext())
					targetCurr = targetIte.next();
				else
					return true;
			}
		}
		return false;
	}

}
