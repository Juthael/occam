package com.tregouet.occam.alg.builders.representations.descriptions.differentiae.properties.util;

import java.util.HashSet;
import java.util.Iterator;
import java.util.Set;

import com.tregouet.occam.data.structures.representations.classifications.concepts.denotations.IDenotation;
import com.tregouet.occam.data.structures.representations.descriptions.differentiae.properties.IProperty;
import com.tregouet.occam.data.structures.representations.descriptions.differentiae.properties.computations.IComputation;
import com.tregouet.occam.data.structures.representations.descriptions.differentiae.properties.impl.Property;
import com.tregouet.occam.data.structures.representations.productions.IContextualizedProduction;

public class CompCluster {

	private int genusID;
	private int speciesID;
	private IDenotation denotation;
	private Set<ProdCluster> prodClusters = new HashSet<>();

	public CompCluster(IContextualizedProduction production, int genusID) {
		this.genusID = genusID;
		speciesID = production.getSubordinateID();
		denotation = production.getTarget();
		prodClusters.add(new ProdCluster(production));
	}

	public boolean add(IContextualizedProduction production) {
		if (production.getSubordinateID() == speciesID && production.getTarget().asList().equals(denotation.asList())) {
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
		Set<IComputation> computations = new HashSet<>();
		for (ProdCluster prodCluster : prodClusters)
			computations.add(prodCluster.asComputation());
		return new Property(genusID, speciesID, denotation, computations);
	}

}
