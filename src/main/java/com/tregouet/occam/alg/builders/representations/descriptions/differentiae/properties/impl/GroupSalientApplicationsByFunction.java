package com.tregouet.occam.alg.builders.representations.descriptions.differentiae.properties.impl;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Set;

import com.tregouet.occam.alg.builders.representations.descriptions.differentiae.properties.PropertyBuilder;
import com.tregouet.occam.alg.builders.representations.descriptions.differentiae.properties.util.AppCluster;
import com.tregouet.occam.data.representations.descriptions.properties.IProperty;
import com.tregouet.occam.data.representations.transitions.IApplication;
import com.tregouet.occam.data.representations.transitions.IRepresentationTransitionFunction;

public class GroupSalientApplicationsByFunction implements PropertyBuilder {

	private List<AppCluster> appClusters;

	public GroupSalientApplicationsByFunction() {
	}

	@Override
	public Set<IProperty> apply(IRepresentationTransitionFunction transFunction) {
		init();
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
		for (AppCluster cluster : appClusters)
			properties.add(cluster.asProperty());
		return properties;
	}

	private void init() {
		appClusters = new ArrayList<>();
	}

}
