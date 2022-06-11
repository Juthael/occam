package com.tregouet.occam.alg.builders.pb_space.representations.descriptions.differentiae.properties.util;

import java.util.HashSet;
import java.util.Iterator;
import java.util.Set;

import com.tregouet.occam.data.problem_space.states.classifications.concepts.denotations.IDenotation;
import com.tregouet.occam.data.problem_space.states.descriptions.differentiae.properties.IProperty;
import com.tregouet.occam.data.problem_space.states.descriptions.differentiae.properties.applications.IApplication;
import com.tregouet.occam.data.problem_space.states.descriptions.differentiae.properties.impl.Property;
import com.tregouet.occam.data.problem_space.states.productions.IContextualizedProduction;

public class AppCluster {
	
	private int genusID;
	private int speciesID;
	private IDenotation denotation;
	private Set<ProdCluster> prodClusters = new HashSet<>();
	
	public AppCluster(IContextualizedProduction production, int genusID) {
		this.genusID = genusID;
		speciesID = production.getSubordinateID();
		denotation = production.getTarget();
		prodClusters.add(new ProdCluster(production));
	}
	
	public boolean add(IContextualizedProduction production) {
		if (production.getSubordinateID() == speciesID && production.getTarget().equals(denotation)) {
			boolean clustered = false;
			Iterator<ProdCluster> prodClusterIte = prodClusters.iterator();
			while (!clustered && prodClusterIte.hasNext())
				clustered = prodClusterIte.next().add(production, true);
			if (!clustered)
				prodClusters.add(new ProdCluster(production));
			return true;
		}
		return false;
	}
	
	public IProperty asProperty() {
		Set<IApplication> applications = new HashSet<>();
		for (ProdCluster prodCluster : prodClusters)
			applications.add(prodCluster.asApplication());
		return new Property(genusID, speciesID, denotation, applications);
	}

}
