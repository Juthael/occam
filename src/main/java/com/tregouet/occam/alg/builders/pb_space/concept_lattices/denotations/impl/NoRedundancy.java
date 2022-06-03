package com.tregouet.occam.alg.builders.pb_space.concept_lattices.denotations.impl;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Set;

import com.tregouet.occam.alg.builders.pb_space.concept_lattices.denotations.DenotationBuilder;
import com.tregouet.occam.alg.builders.pb_space.representations.productions.from_denotations.utils.MapVariablesToValues;
import com.tregouet.occam.data.logical_structures.languages.alphabets.AVariable;
import com.tregouet.occam.data.logical_structures.languages.alphabets.ISymbol;
import com.tregouet.occam.data.logical_structures.languages.alphabets.ITerminal;
import com.tregouet.occam.data.logical_structures.languages.words.construct.IConstruct;

public class NoRedundancy extends MaxSymbolSubsequences implements DenotationBuilder {
	
	private static Set<IConstruct> removeRedundantConstructs(Set<IConstruct> constructs) {
		Set<IConstruct> redundantConstructs = new HashSet<>();
		List<IConstruct> constructList = new ArrayList<>(constructs);
		for (int i = 0 ; i < constructList.size() ; i++) {
			IConstruct iConstruct = constructList.get(i);
			boolean iConceptIsRedundant = false;
			int j = 0;
			while (!iConceptIsRedundant && j < constructList.size()) {
				if (i != j) {
					IConstruct jConstruct = constructList.get(j);
					iConceptIsRedundant = inCodomainOf(jConstruct, iConstruct);
				}
				j++;
			}
			if (iConceptIsRedundant)
				redundantConstructs.add(iConstruct);
		}
		constructs.removeAll(redundantConstructs);
		return constructs;
	}
	
	@Override
	protected Set<IConstruct> complyToAdditionalConstraints(Set<IConstruct> constructs) {
		return removeRedundantConstructs(constructs);
	}
	
	private static boolean inCodomainOf(IConstruct value, IConstruct function) {
		if (subSequenceOf(function.getListOfTerminals(), value.getListOfTerminals())) {
			List<ISymbol> c1ValueProvider = value.asList();
			List<ISymbol> c2VarProvider = function.asList();
			Map<AVariable, List<ISymbol>> varToValue = MapVariablesToValues.of(c1ValueProvider, c2VarProvider);
			if (varToValue != null) {
				return true;
			}
		}
		return false;
	}
	
	private static boolean subSequenceOf(List<ITerminal> t1, List<ITerminal> t2) {
		if (t1.isEmpty())
			return true;
		if (t2.isEmpty())
			return false;
		Iterator<ITerminal> sourceIte = t2.iterator();
		Iterator<ITerminal> targetIte = t1.iterator();
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
