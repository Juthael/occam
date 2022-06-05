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
	private List<Set<IContextualizedProduction>> setsOfApplications;
	private Map<IContextualizedProduction, Salience> production2Salience = new HashMap<>();
	private Set<Integer> particularIDs;
	
	public HiddenByDefaultThenFindSpecifics(){
	}

	@Override
	public Map<IContextualizedProduction, Salience> apply(IClassification classification, 
			Set<IContextualizedProduction> classificationProductions) {
		init();
		particularIDs.addAll(classification.getParticularIDs());
		// set salience default value as HIDDEN, group applications by input concept,
		// find input/output relation
		for (IContextualizedProduction production : classificationProductions) {
			// default value, may be changed later
			production2Salience.put(production, Salience.HIDDEN);
			Integer speciesStateID = production.getSubordinateID();
			Integer genusID = classification.getGenusID(speciesStateID);
			int genusIdx = genusIDs.indexOf(genusID);
			if (genusIdx != -1) {
				setsOfSpeciesIDs.get(genusIdx).add(speciesStateID);
				if (!production.isBlank() && !production.isEpsilon()) {
					setsOfApplications.get(genusIdx).add(production);
				}
			}
			else {
				genusIDs.add(genusID);
				setsOfSpeciesIDs.add(new HashSet<>(Arrays.asList(new Integer[] {speciesStateID})));
				if (!production.isBlank() && !production.isEpsilon())
					setsOfApplications.add(new HashSet<>(Arrays.asList(new IContextualizedProduction[] {production})));
				else setsOfApplications.add(new HashSet<>());
			}
		}
		// set common features
		for (Set<IContextualizedProduction> applications : setsOfApplications) {
			for (IContextualizedProduction application : applications) {
				if (!particularIDs.contains(application.getSubordinateID()))
					production2Salience.put(application, Salience.COMMON_FEATURE);
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
		setsOfApplications = new ArrayList<>();
		production2Salience = new HashMap<>();
		particularIDs = new HashSet<>();
	}
	
	private void setPartitionRulesSalience(int genusIdx) {
		Map<AVariable, Set<IContextualizedProduction>> var2Applications = new HashMap<>();
		for (IContextualizedProduction application : setsOfApplications.get(genusIdx)) {
			AVariable instantiatedVar = application.getVariable();
			if (var2Applications.containsKey(instantiatedVar))
				var2Applications.get(instantiatedVar).add(application);
			else var2Applications.put(instantiatedVar, new HashSet<>(Arrays.asList(new IContextualizedProduction[] {application})));
		}
		for (Entry<AVariable, Set<IContextualizedProduction>> entry : var2Applications.entrySet())
			setPartitionRuleSalienceOf(entry, genusIdx);
	}
	
	private void setPartitionRuleSalienceOf(Entry<AVariable, Set<IContextualizedProduction>> var2Applications, int genusIdx) {
		List<Integer> speciesIDs = new ArrayList<>(setsOfSpeciesIDs.get(genusIdx));
		List<Set<IProduction>> productions = new ArrayList<>(speciesIDs.size());
		for (int i = 0 ; i < speciesIDs.size() ; i++)
			productions.add(new HashSet<>());
		for (IContextualizedProduction application : var2Applications.getValue())
			productions.get(speciesIDs.indexOf(application.getSubordinateID())).add(application.getUncontextualizedProduction());
		if (everySubConceptInstantiatesThisVariable(productions) 
				&& everySubConceptGivesThisVariableADistinctValue(productions)) {
			for (IContextualizedProduction application : var2Applications.getValue())
				production2Salience.put(application, Salience.TRANSITION_RULE);
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
