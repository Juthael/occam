package com.tregouet.occam.alg.builders.representations.descriptions.differentiae.properties.impl;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Set;

import com.tregouet.occam.alg.builders.representations.descriptions.differentiae.properties.PropertyBuilder;
import com.tregouet.occam.alg.builders.representations.descriptions.differentiae.properties.util.CompCluster;
import com.tregouet.occam.alg.builders.representations.descriptions.differentiae.properties.util.ICompCluster;
import com.tregouet.occam.alg.builders.representations.descriptions.differentiae.properties.util.RelationalCompCluster;
import com.tregouet.occam.alg.setters.weights.properties.PropertyWeigher;
import com.tregouet.occam.data.structures.representations.classifications.IClassification;
import com.tregouet.occam.data.structures.representations.descriptions.differentiae.properties.IProperty;
import com.tregouet.occam.data.structures.representations.productions.IContextualizedProduction;
import com.tregouet.occam.data.structures.representations.productions.Salience;

public class GroupSalientProductionsByComputation implements PropertyBuilder {

	private IClassification classification = null;
	private PropertyWeigher propWeigher = null;

	public GroupSalientProductionsByComputation() {
	}

	@Override
	public Set<IProperty> apply(Set<IContextualizedProduction> productions) {
		Set<IContextualizedProduction> salientRelationalProds = new HashSet<>();
		Set<IContextualizedProduction> salientSimpleProds = new HashSet<>();
		List<ICompCluster> compClusters = new ArrayList<>();
		Set<IProperty> properties = new HashSet<>();
		for (IContextualizedProduction production : productions) {
			if (production.getSalience() == Salience.COMMON_FEATURE || production.getSalience() == Salience.TRANSITION_RULE) {
				if (production.isRelational())
					salientRelationalProds.add(production);
				else salientSimpleProds.add(production);
			}
		}
		for (IContextualizedProduction relProd : salientRelationalProds) {
			boolean clustered = false;
			Iterator<ICompCluster> clusterIte = compClusters.iterator();
			while (!clustered && clusterIte.hasNext())
				clustered = clusterIte.next().add(relProd);
			if (!clustered) {
				compClusters.add(new RelationalCompCluster(relProd, classification.getGenusID(relProd.getSubordinateID())));
			}
		}
		for (IContextualizedProduction simpleProd : salientSimpleProds) {
			boolean clustered = false;
			Iterator<ICompCluster> clusterIte = compClusters.iterator();
			while (!clustered && clusterIte.hasNext())
				clustered = clusterIte.next().add(simpleProd);
			if (!clustered) {
				compClusters.add(new CompCluster(simpleProd, classification.getGenusID(simpleProd.getSubordinateID())));
			}
		}
		for (ICompCluster cluster : compClusters) {
			IProperty property = cluster.asProperty();
			propWeigher.accept(property);
			properties.add(property);
		}
		return properties;
	}

	@Override
	public PropertyBuilder setUp(IClassification classification, PropertyWeigher propWeigher) {
		this.classification = classification;
		this.propWeigher = propWeigher;
		return this;
	}

}
