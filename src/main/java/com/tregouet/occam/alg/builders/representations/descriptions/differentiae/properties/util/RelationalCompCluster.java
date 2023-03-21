package com.tregouet.occam.alg.builders.representations.descriptions.differentiae.properties.util;

import java.util.Iterator;

import com.tregouet.occam.data.structures.languages.alphabets.ITerminal;
import com.tregouet.occam.data.structures.representations.productions.IContextualizedProduction;

public class RelationalCompCluster extends CompCluster {
	
	private ITerminal relationalTerminal;

	public RelationalCompCluster(IContextualizedProduction production, int genusID) {
		super(production, genusID);
		relationalTerminal = production.getValue().getListOfTerminals().get(0);
	}
	
	@Override
	public boolean add(IContextualizedProduction production) {
		if (production.getSubordinateID() == speciesID && production.getValue().getListOfTerminals().contains(relationalTerminal)) {
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

}
