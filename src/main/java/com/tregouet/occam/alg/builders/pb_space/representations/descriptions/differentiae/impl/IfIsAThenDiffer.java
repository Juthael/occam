package com.tregouet.occam.alg.builders.pb_space.representations.descriptions.differentiae.impl;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Set;

import com.tregouet.occam.alg.builders.pb_space.representations.descriptions.differentiae.DifferentiaeBuilder;
import com.tregouet.occam.data.representations.classifications.IClassification;
import com.tregouet.occam.data.representations.classifications.concepts.IConcept;
import com.tregouet.occam.data.representations.classifications.concepts.IIsA;
import com.tregouet.occam.data.representations.descriptions.differentiae.ADifferentiae;
import com.tregouet.occam.data.representations.descriptions.differentiae.impl.Differentiae;
import com.tregouet.occam.data.representations.descriptions.differentiae.properties.IProperty;
import com.tregouet.occam.data.representations.productions.IContextualizedProduction;
import com.tregouet.tree_finder.data.InvertedTree;

public class IfIsAThenDiffer implements DifferentiaeBuilder {

	public IfIsAThenDiffer() {
	}

	@Override
	public Set<ADifferentiae> apply(IClassification classification, Set<IContextualizedProduction> productions) {
		Set<ADifferentiae> differentiae = new HashSet<>();
		Set<IProperty> properties = DifferentiaeBuilder.propertyBuilder().apply(classification, productions);
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
