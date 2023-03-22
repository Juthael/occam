package com.tregouet.occam.alg.builders.representations.descriptions.differentiae.properties.util;

import java.util.HashSet;
import java.util.Iterator;
import java.util.Set;

import com.tregouet.occam.data.structures.languages.alphabets.ITerminal;
import com.tregouet.occam.data.structures.representations.classifications.concepts.denotations.IDenotation;
import com.tregouet.occam.data.structures.representations.descriptions.differentiae.properties.IProperty;
import com.tregouet.occam.data.structures.representations.descriptions.differentiae.properties.computations.IComputation;
import com.tregouet.occam.data.structures.representations.descriptions.differentiae.properties.impl.RelationalProperty;
import com.tregouet.occam.data.structures.representations.productions.IContextualizedProduction;

public class RelationalCompCluster implements ICompCluster {

	protected int genusID;
	protected int speciesID;
	private Set<IDenotation> denotationSet = new HashSet<>();
	protected Set<ProdCluster> prodClusters = new HashSet<>();
	private ITerminal relationalTerminal;


	public RelationalCompCluster(IContextualizedProduction production, int genusID) {
		this.genusID = genusID;
		speciesID = production.getSubordinateID();
		denotationSet.add(production.getTarget());
		prodClusters.add(new ProdCluster(production));
		relationalTerminal = production.getValue().getListOfTerminals().get(0);
	}

	@Override
	public boolean add(IContextualizedProduction production) {
		if (production.getSubordinateID() == speciesID &&
				(production.getValue().getListOfTerminals().contains(relationalTerminal)
						|| denotationSet.contains(production.getTarget()))) {
			denotationSet.add(production.getTarget());
			boolean clustered = false;
			Iterator<ProdCluster> prodClusterIte = prodClusters.iterator();
			while (!clustered && prodClusterIte.hasNext())
				clustered = prodClusterIte.next().add(production, false);
			if (!clustered)
				prodClusters.add(new ProdCluster(production));
			return true;
		}
		return false;
	}

	@Override
	public IProperty asProperty() {
		Set<IComputation> computations = new HashSet<>();
		for (ProdCluster prodCluster : prodClusters)
			computations.add(prodCluster.asComputation());
		int nbOfSignificantComp = ICompCluster.nbOfSignificantComp(computations);
		return new RelationalProperty(genusID, speciesID, denotationSet, computations, nbOfSignificantComp);
	}

}
