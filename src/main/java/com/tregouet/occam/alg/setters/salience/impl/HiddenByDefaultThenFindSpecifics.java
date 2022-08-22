package com.tregouet.occam.alg.setters.salience.impl;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Set;

import com.tregouet.occam.alg.setters.salience.ProductionSalienceSetter;
import com.tregouet.occam.alg.setters.salience.rule_detector.RuleDetector;
import com.tregouet.occam.data.representations.classifications.IClassification;
import com.tregouet.occam.data.representations.classifications.concepts.IConcept;
import com.tregouet.occam.data.representations.productions.IContextualizedProduction;
import com.tregouet.occam.data.representations.productions.Salience;
import com.tregouet.occam.data.structures.languages.alphabets.AVariable;

public class HiddenByDefaultThenFindSpecifics implements ProductionSalienceSetter {

	private IClassification classification;
	private List<Integer> genusIDs;
	private List<Set<Integer>> setsOfSpeciesIDs;
	private List<Set<IContextualizedProduction>> setsOfProductions;

	public HiddenByDefaultThenFindSpecifics(){
	}

	@Override
	public void accept(Set<IContextualizedProduction> classificationProductions) {
		// set salience default value as HIDDEN, group productions by input concept,
		// find input/output relation
		for (IContextualizedProduction production : classificationProductions) {
			if (!production.isEpsilon()) {
				// default value, may be changed later
				production.setSalience(Salience.HIDDEN);
				Integer speciesStateID = production.getSubordinateID();
				Integer genusID = classification.getGenusID(speciesStateID);
				int genusIdx = genusIDs.indexOf(genusID);
				setsOfProductions.get(genusIdx).add(production);
			}
		}
		// set common features
		Set<Integer> particularIDs = classification.getParticularIDs();
		for (Set<IContextualizedProduction> productions : setsOfProductions) {
			for (IContextualizedProduction production : productions) {
				if (!particularIDs.contains(production.getSubordinateID()))
					production.setSalience(Salience.COMMON_FEATURE);
			}
		}
		// set partition rules
		for (int i = 0; i < genusIDs.size(); i++) {
			setPartitionRulesSalience(i);
		}
	}

	@Override
	public ProductionSalienceSetter setUp(IClassification classification) {
		this.classification = classification;
		genusIDs = new ArrayList<>();
		Set<Integer> classificationLeafIDs = new HashSet<>();
		for (IConcept leaf : classification.getMostSpecificConcepts())
			classificationLeafIDs.add(leaf.iD());
		for (IConcept concept : classification.asGraph().vertexSet()) {
			Integer conceptID = concept.iD();
			if (!classificationLeafIDs.contains(conceptID))
				genusIDs.add(conceptID);
		}
		setsOfSpeciesIDs = new ArrayList<>();
		setsOfProductions = new ArrayList<>();
		for (int i = 0 ; i < genusIDs.size() ; i++) {
			setsOfSpeciesIDs.add(new HashSet<>());
			setsOfProductions.add(new HashSet<>());
		}
		Map<Integer, Integer> speciesID2genusID = classification.mapSpeciesID2GenusID();
		for (Entry<Integer, Integer> speciesIDgenusID : speciesID2genusID.entrySet())
			setsOfSpeciesIDs.get(genusIDs.indexOf(speciesIDgenusID.getValue())).add(speciesIDgenusID.getKey());
		return this;
	}

	private void setPartitionRuleSalienceOf(Entry<AVariable, Set<IContextualizedProduction>> var2Productions, int genusIdx) {
		List<Integer> speciesIDs = new ArrayList<>(setsOfSpeciesIDs.get(genusIdx));
		List<Set<IContextualizedProduction>> values = new ArrayList<>(speciesIDs.size());
		for (int i = 0 ; i < speciesIDs.size() ; i++)
			values.add(new HashSet<>());
		for (IContextualizedProduction production : var2Productions.getValue())
			values.get(speciesIDs.indexOf(production.getSubordinateID())).add(production);
		RuleDetector ruleDetector = ProductionSalienceSetter.ruleDetector();
		if (ruleDetector.apply(values)) {
			for (Set<IContextualizedProduction> value : values) {
				for (IContextualizedProduction prod : value)
					prod.setSalience(Salience.TRANSITION_RULE);
			}
		}
	}

	private void setPartitionRulesSalience(int genusIdx) {
		Map<AVariable, Set<IContextualizedProduction>> var2Productions = new HashMap<>();
		for (IContextualizedProduction production : setsOfProductions.get(genusIdx)) {
			AVariable instantiatedVar = production.getVariable();
			if (var2Productions.containsKey(instantiatedVar))
				var2Productions.get(instantiatedVar).add(production);
			else var2Productions.put(instantiatedVar, new HashSet<>(Arrays.asList(new IContextualizedProduction[] {production})));
		}
		for (Entry<AVariable, Set<IContextualizedProduction>> entry : var2Productions.entrySet())
			setPartitionRuleSalienceOf(entry, genusIdx);
	}

}
