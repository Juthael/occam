package com.tregouet.occam.alg.setters.weighs.properties.impl;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

import com.tregouet.occam.alg.setters.weighs.properties.PropertyWeigher;
import com.tregouet.occam.data.problem_space.states.classifications.IClassification;
import com.tregouet.occam.data.problem_space.states.classifications.concepts.IConcept;
import com.tregouet.occam.data.problem_space.states.descriptions.differentiae.properties.IProperty;
import com.tregouet.occam.data.problem_space.states.descriptions.differentiae.properties.computations.IComputation;

public class Ruleness implements PropertyWeigher {

	private IClassification classification;
	private Map<Integer, IConcept> particularID2Particular;
	
	public Ruleness() {
	}
	
	@Override
	public void accept(IProperty p) {
		if (!p.isBlank()) {
			int genusID = p.getGenusID();
			int speciesID = p.getGenusID();
			List<Integer> speciesExtentIDs = classification.getExtentIDs(speciesID);
			Set<Integer> speciesComplementaryExtentIDs = new HashSet<>(classification.getExtentIDs(genusID));
			speciesComplementaryExtentIDs.removeAll(speciesExtentIDs);
			List<IConcept> speciesComplementaryExtent = new ArrayList<>();
			for (Integer iD : speciesComplementaryExtentIDs)
				speciesComplementaryExtent.add(classification.getConceptWithSpecifiedID(iD));
			int propertyCardinality = speciesExtentIDs.size();
			int minRuleCardinality = particularID2Particular.size();
			for (IComputation computation : p.getComputations()) {
				if (!computation.isIdentity()) {
					int ruleCardinality = propertyCardinality;
					for (IConcept complementary : speciesComplementaryExtent) {
						if (complementary.meets(computation.getOutput()))
							ruleCardinality++;
					}
					if (ruleCardinality < minRuleCardinality)
						minRuleCardinality = ruleCardinality;	
				}
			}
			if (propertyCardinality == minRuleCardinality)
				p.setWeight(1.0);
			else p.setWeight((double) propertyCardinality / (double) minRuleCardinality);	
		}
	}

	@Override
	public PropertyWeigher setUp(IClassification classification) {
		this.classification = classification;
		particularID2Particular = classification.getParticularID2Particular();
		return this;
	}

}
