package com.tregouet.occam.alg.setters.weighs.properties.impl;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

import com.google.common.collect.Sets;
import com.tregouet.occam.alg.setters.weighs.properties.PropertyWeigher;
import com.tregouet.occam.data.problem_space.states.classifications.IClassification;
import com.tregouet.occam.data.problem_space.states.classifications.concepts.IConcept;
import com.tregouet.occam.data.problem_space.states.descriptions.differentiae.properties.IProperty;
import com.tregouet.occam.data.problem_space.states.descriptions.differentiae.properties.computations.IComputation;

public class Ruleness implements PropertyWeigher {

	private IClassification classification;
	private Map<Integer, List<IConcept>> conceptID2CompExtent;
	
	public Ruleness() {
	}
	
	@Override
	public void accept(IProperty p) {
		if (!p.isBlank()) {
			int speciesID = p.getSpeciesID();
			Set<Integer> speciesComplementaryExtentIDs = new HashSet<>();
			List<IConcept> speciesComplementaryExtent = conceptID2CompExtent.get(speciesID);
			for (Integer iD : speciesComplementaryExtentIDs)
				speciesComplementaryExtent.add(classification.getConceptWithSpecifiedID(iD));
			int propertyCardinality = classification.getExtentIDs(speciesID).size();
			int minRuleCardinality = -1;
			for (IComputation computation : p.getComputations()) {
				if (!computation.isIdentity()) {
					int ruleCardinality = propertyCardinality;
					for (IConcept complementary : speciesComplementaryExtent) {
						if (complementary.meets(computation.getOutput()))
							ruleCardinality++;
					}
					if (minRuleCardinality == -1 || ruleCardinality < minRuleCardinality)
						minRuleCardinality = ruleCardinality;	
				}
			}
			p.setWeight((double) propertyCardinality / (double) minRuleCardinality);	
		}
		else p.setWeight(0.0);
	}

	@Override
	public PropertyWeigher setUp(IClassification classification) {
		this.classification = classification;
		conceptID2CompExtent = new HashMap<>();
		Map<Integer, IConcept> particularID2Particular = classification.getParticularID2Particular();
		for (IConcept species : classification.asGraph().vertexSet()) {
			if (species.iD() != IConcept.ONTOLOGICAL_COMMITMENT_ID) {
				int speciesID = species.iD();
				Set<Integer> extentIDs = new HashSet<>(classification.getExtentIDs(speciesID));
				Integer genusID = classification.getGenusID(speciesID);
				Set<Integer> genusExtentIDs = new HashSet<>(classification.getExtentIDs(genusID));
				List<IConcept> complementaryExtent = new ArrayList<>();
				for (Integer particularID : Sets.difference(genusExtentIDs, extentIDs))
					complementaryExtent.add(particularID2Particular.get(particularID));
				conceptID2CompExtent.put(speciesID, complementaryExtent);
			}
		}
		return this;
	}

}
