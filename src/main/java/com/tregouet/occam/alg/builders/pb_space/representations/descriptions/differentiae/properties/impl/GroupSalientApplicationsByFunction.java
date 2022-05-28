package com.tregouet.occam.alg.builders.pb_space.representations.descriptions.differentiae.properties.impl;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Set;

import com.tregouet.occam.alg.builders.pb_space.representations.descriptions.differentiae.properties.PropertyBuilder;
import com.tregouet.occam.alg.builders.pb_space.representations.descriptions.differentiae.properties.util.AppCluster;
import com.tregouet.occam.data.problem_space.states.descriptions.properties.IProperty;
import com.tregouet.occam.data.problem_space.states.transitions.IApplication;
import com.tregouet.occam.data.problem_space.states.transitions.IRepresentationTransitionFunction;

public class GroupSalientApplicationsByFunction implements PropertyBuilder {

	public GroupSalientApplicationsByFunction() {
	}

	@Override
	public Set<IProperty> apply(IRepresentationTransitionFunction transFunction) {
		List<AppCluster> appClusters = new ArrayList<>();
		Set<IProperty> properties = new HashSet<>();
		for (IApplication application : transFunction.getSalientApplications()) {
			boolean clustered = false;
			Iterator<AppCluster> clusterIte = appClusters.iterator();
			while (!clustered && clusterIte.hasNext()) {
				clustered = clusterIte.next().add(application);
			}
			if (!clustered)
				appClusters.add(new AppCluster(application));
		}
		for (AppCluster cluster : appClusters) {
			IProperty property = cluster.asProperty();
			PropertyBuilder.propertyWeigher().accept(property);
			properties.add(property);
		}
		return properties;
	}

}
