package com.tregouet.occam.alg.builders.representations.descriptions.differentiae.impl;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Set;

import com.tregouet.occam.alg.builders.representations.descriptions.differentiae.DifferentiaeBuilder;
import com.tregouet.occam.alg.setters.weights.properties.PropertyWeigher;
import com.tregouet.occam.data.structures.representations.classifications.IClassification;
import com.tregouet.occam.data.structures.representations.classifications.concepts.IConcept;
import com.tregouet.occam.data.structures.representations.classifications.concepts.IIsA;
import com.tregouet.occam.data.structures.representations.descriptions.differentiae.ADifferentiae;
import com.tregouet.occam.data.structures.representations.descriptions.differentiae.differentiations.IDifferentiationSet;
import com.tregouet.occam.data.structures.representations.descriptions.differentiae.impl.Differentiae;
import com.tregouet.occam.data.structures.representations.descriptions.differentiae.properties.IProperty;
import com.tregouet.occam.data.structures.representations.productions.IContextualizedProduction;
import com.tregouet.tree_finder.data.InvertedTree;

public abstract class ADifferentiaeBuilder implements DifferentiaeBuilder {

	public ADifferentiaeBuilder() {
	}

	@Override
	public Set<ADifferentiae> apply(IClassification classification, Set<IContextualizedProduction> productions) {
		Set<ADifferentiae> differentiae = new HashSet<>();
		PropertyWeigher propWeigher = DifferentiaeBuilder.propertyWeigher().setUp(classification);
		Set<IProperty> properties = DifferentiaeBuilder.propertyBuilder().setUp(classification, propWeigher).apply(productions);
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
			IDifferentiationSet differentiationSet = buildDifferentiationSet(thisDiffProperties, propWeigher);
			differentiae.add(
					new Differentiae(genusID, speciesID, thisDiffProperties, differentiationSet));
			properties.removeAll(toBeRemoved);
		}
		return differentiae;
	}

	abstract protected IDifferentiationSet buildDifferentiationSet(Set<IProperty> properties, PropertyWeigher propWeigher);

}
