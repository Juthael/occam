package com.tregouet.occam.alg.setters.weights.properties.impl;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

import com.google.common.collect.Sets;
import com.tregouet.occam.alg.setters.weights.properties.PropertyWeigher;
import com.tregouet.occam.data.structures.languages.words.construct.IConstruct;
import com.tregouet.occam.data.structures.representations.classifications.IClassification;
import com.tregouet.occam.data.structures.representations.classifications.concepts.IConcept;
import com.tregouet.occam.data.structures.representations.descriptions.differentiae.properties.IProperty;
import com.tregouet.occam.data.structures.representations.descriptions.differentiae.properties.computations.IComputation;
import com.tregouet.occam.data.structures.representations.productions.IProduction;
import com.tregouet.occam.data.structures.representations.productions.Salience;

public class RulenessDimensionality implements PropertyWeigher {

	private IClassification classification;
	private Map<Integer, List<IConcept>> conceptID2CompExtent;

	@Override
	public void accept(IProperty p) {
		if (!p.isBlank()) {
			double dimensionalityCoeff = (isDimensionalRule(p) ? 2.0 : 1.0);
			int speciesID = p.getSpeciesID();
			List<IConcept> speciesComplementaryExtent = conceptID2CompExtent.get(speciesID);
			List<IConstruct> computationOutputs = new ArrayList<>();
			for (IComputation computation : p.getComputations()) {
				if (!computation.isIdentity() && !computation.getOutput().isRedundant())
					computationOutputs.add(computation.getOutput());
			}
			int propertyCardinality = classification.getExtentIDs(speciesID).size();
			int ruleCardinality = propertyCardinality;
			for (IConcept complementary : speciesComplementaryExtent) {
				boolean complies = true;
				for (IConstruct construct : computationOutputs) {
					if (!complementary.meets(construct)) {
						complies = false;
						break;
					}
				}
				if (complies)
					ruleCardinality++;
			}
			p.setWeight(dimensionalityCoeff * propertyCardinality / ruleCardinality);
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

	private static boolean isDimensionalRule(IProperty p) {
		if (p.getGenusID() == IConcept.ONTOLOGICAL_COMMITMENT_ID)
			return false;
		for (IComputation computation : p.getComputations()) {
			for (IProduction prod : computation.getOperator().getArguments()) {
				if (prod.getSalience().equals(Salience.TRANSITION_RULE))
					return true;
			}
		}
		return false;
	}

}