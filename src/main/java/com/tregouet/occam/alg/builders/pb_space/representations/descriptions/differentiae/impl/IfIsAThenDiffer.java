package com.tregouet.occam.alg.builders.pb_space.representations.descriptions.differentiae.impl;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Set;

import com.tregouet.occam.alg.builders.pb_space.representations.descriptions.differentiae.DifferentiaeBuilder;
import com.tregouet.occam.data.problem_space.states.classifications.IClassification;
import com.tregouet.occam.data.problem_space.states.classifications.concepts.IConcept;
import com.tregouet.occam.data.problem_space.states.classifications.concepts.IIsA;
import com.tregouet.occam.data.problem_space.states.descriptions.properties.ADifferentiae;
import com.tregouet.occam.data.problem_space.states.descriptions.properties.IProperty;
import com.tregouet.occam.data.problem_space.states.descriptions.properties.impl.Differentiae;
import com.tregouet.occam.data.problem_space.states.productions.IClassificationProductions;
import com.tregouet.tree_finder.data.InvertedTree;

public class IfIsAThenDiffer implements DifferentiaeBuilder {

	public IfIsAThenDiffer() {
	}

	@Override
	public Set<ADifferentiae> apply(IClassification classification, IClassificationProductions classificationProductions) {
		Set<ADifferentiae> differentiae = new HashSet<>();
		Set<IProperty> properties = DifferentiaeBuilder.propertyBuilder().apply(classification, classificationProductions);
		InvertedTree<IConcept, IIsA> conceptTree = classification.asGraph();
		for (IIsA transition : classification.asGraph().edgeSet()) {
			int genusID = conceptTree.getEdgeTarget(transition).iD();
			int speciesID = conceptTree.getEdgeSource(transition).iD();
			Set<IProperty> thisDiffProperties = new HashSet<>();
			Iterator<IProperty> propIte = properties.iterator();
			List<IProperty> toBeRemoved = new ArrayList<>();
			while (propIte.hasNext()) {
				IProperty property = propIte.next();
				if (property.getGenusID() == genusID && property.getSpeciesID() == speciesID) {
					thisDiffProperties.add(property);
					toBeRemoved.add(property);
				}
			}
			differentiae.add(
					new Differentiae(genusID, speciesID, thisDiffProperties));
			properties.removeAll(toBeRemoved);
		}
		return differentiae;
	}

}
