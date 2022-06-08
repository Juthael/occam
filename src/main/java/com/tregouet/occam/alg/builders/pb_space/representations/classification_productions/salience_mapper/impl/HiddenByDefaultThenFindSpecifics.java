package com.tregouet.occam.alg.builders.pb_space.representations.classification_productions.salience_mapper.impl;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Set;

import com.tregouet.occam.alg.builders.pb_space.representations.classification_productions.salience_mapper.ProductionSalienceMapper;
import com.tregouet.occam.data.logical_structures.languages.alphabets.AVariable;
import com.tregouet.occam.data.problem_space.states.classifications.IClassification;
import com.tregouet.occam.data.problem_space.states.productions.IContextualizedProduction;
import com.tregouet.occam.data.problem_space.states.productions.IProduction;
import com.tregouet.occam.data.problem_space.states.productions.Salience;

public class HiddenByDefaultThenFindSpecifics implements ProductionSalienceMapper {
	
	private List<Integer> genusIDs;
	private List<Set<Integer>> setsOfSpeciesIDs;
	private List<Set<IContextualizedProduction>> setsOfProductionss;
	private Map<IContextualizedProduction, Salience> production2Salience = new HashMap<>();
	private Set<Integer> particularIDs;
	
	public HiddenByDefaultThenFindSpecifics(){
	}

	@Override
	public Map<IContextualizedProduction, Salience> apply(IClassification classification, 
			Set<IContextualizedProduction> classificationProductions) {
		init();
		particularIDs.addAll(classification.getParticularIDs());
		// set salience default value as HIDDEN, group productions by input concept,
		// find input/output relation
		for (IContextualizedProduction production : classificationProductions) {
			// default value, may be changed later
			production2Salience.put(production, Salience.HIDDEN);
			Integer speciesStateID = production.getSubordinateID();
			Integer genusID = classification.getGenusID(speciesStateID);
			int genusIdx = genusIDs.indexOf(genusID);
			if (genusIdx != -1) {
				setsOfSpeciesIDs.get(genusIdx).add(speciesStateID);
				if (!production.isEpsilon() && !production.isBlank()) {
					setsOfProductionss.get(genusIdx).add(production);
				}
			}
			else {
				genusIDs.add(genusID);
				setsOfSpeciesIDs.add(new HashSet<>(Arrays.asList(new Integer[] {speciesStateID})));
				if (!production.isEpsilon() && !production.isBlank())
					setsOfProductionss.add(new HashSet<>(Arrays.asList(new IContextualizedProduction[] {production})));
				else setsOfProductionss.add(new HashSet<>());
			}
		}
		// set common features
		for (Set<IContextualizedProduction> productions : setsOfProductionss) {
			for (IContextualizedProduction production : productions) {
				if (!particularIDs.contains(production.getSubordinateID()))
					production2Salience.put(production, Salience.COMMON_FEATURE);
			}
		}
		// set partition rules
		for (int i = 0; i < genusIDs.size(); i++) {
			setPartitionRulesSalience(i);
		}
		return production2Salience;
	}
	
	private void init() {
		genusIDs = new ArrayList<>();
		setsOfSpeciesIDs = new ArrayList<>();
		setsOfProductionss = new ArrayList<>();
		production2Salience = new HashMap<>();
		particularIDs = new HashSet<>();
	}
	
	private void setPartitionRulesSalience(int genusIdx) {
		Map<AVariable, Set<IContextualizedProduction>> var2Productions = new HashMap<>();
		for (IContextualizedProduction production : setsOfProductionss.get(genusIdx)) {
			AVariable instantiatedVar = production.getVariable();
			if (var2Productions.containsKey(instantiatedVar))
				var2Productions.get(instantiatedVar).add(production);
			else var2Productions.put(instantiatedVar, new HashSet<>(Arrays.asList(new IContextualizedProduction[] {production})));
		}
		for (Entry<AVariable, Set<IContextualizedProduction>> entry : var2Productions.entrySet())
			setPartitionRuleSalienceOf(entry, genusIdx);
	}
	
	private void setPartitionRuleSalienceOf(Entry<AVariable, Set<IContextualizedProduction>> var2Productions, int genusIdx) {
		List<Integer> speciesIDs = new ArrayList<>(setsOfSpeciesIDs.get(genusIdx));
		List<Set<IProduction>> productions = new ArrayList<>(speciesIDs.size());
		for (int i = 0 ; i < speciesIDs.size() ; i++)
			productions.add(new HashSet<>());
		for (IContextualizedProduction production : var2Productions.getValue())
			productions.get(speciesIDs.indexOf(production.getSubordinateID())).add(production.getUncontextualizedProduction());
		if (everySubConceptInstantiatesThisVariable(productions) 
				&& everySubConceptGivesThisVariableADistinctValue(productions)) {
			for (IContextualizedProduction production : var2Productions.getValue())
				production2Salience.put(production, Salience.TRANSITION_RULE);
		}
	}
	
	private static boolean everySubConceptInstantiatesThisVariable(List<Set<IProduction>> values) {
		for (Set<IProduction> value : values) {
			if (value.isEmpty() || isAlphaConversion(value))
				return false;
		}
		return true;
	}
	
	private static boolean everySubConceptGivesThisVariableADistinctValue(List<Set<IProduction>> values) {
		Set<Set<IProduction>> uniqueValues = new HashSet<>(values);
		return uniqueValues.size() == values.size();
	}
	
	private static boolean isAlphaConversion(Set<IProduction> value) {
		for (IProduction production : value) {
			if (!production.isAlphaConversion())
				return false;
		}
		return true;
	}

}
