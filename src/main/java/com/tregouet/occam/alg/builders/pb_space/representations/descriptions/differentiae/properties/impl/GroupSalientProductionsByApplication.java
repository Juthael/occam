package com.tregouet.occam.alg.builders.pb_space.representations.descriptions.differentiae.properties.impl;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Set;

import com.tregouet.occam.alg.builders.pb_space.representations.descriptions.differentiae.properties.PropertyBuilder;
import com.tregouet.occam.alg.builders.pb_space.representations.descriptions.differentiae.properties.util.AppCluster;
import com.tregouet.occam.data.problem_space.states.classifications.IClassification;
import com.tregouet.occam.data.problem_space.states.descriptions.differentiae.properties.IProperty;
import com.tregouet.occam.data.problem_space.states.productions.IClassificationProductions;
import com.tregouet.occam.data.problem_space.states.productions.IContextualizedProduction;

public class GroupSalientProductionsByApplication implements PropertyBuilder {

	public GroupSalientProductionsByApplication() {
	}

	@Override
	public Set<IProperty> apply(IClassification classification, IClassificationProductions classificationProductions) {
		List<AppCluster> appClusters = new ArrayList<>();
		Set<IProperty> properties = new HashSet<>();
		for (IContextualizedProduction production : classificationProductions.getSalientProductions()) {
			boolean clustered = false;
			Iterator<AppCluster> clusterIte = appClusters.iterator();
			while (!clustered && clusterIte.hasNext())
				clustered = clusterIte.next().add(production);
			if (!clustered)
				appClusters.add(new AppCluster(production, classification.getGenusID(production.getSubordinateID())));
		}
		for (AppCluster cluster : appClusters) {
			IProperty property = cluster.asProperty();
			PropertyBuilder.propertyWeigher().accept(property);
			properties.add(property);
		}
		return properties;
	}

}
