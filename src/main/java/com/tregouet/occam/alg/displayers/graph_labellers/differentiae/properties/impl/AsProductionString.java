package com.tregouet.occam.alg.displayers.graph_labellers.differentiae.properties.impl;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import com.tregouet.occam.alg.displayers.graph_labellers.differentiae.properties.PropertyLabeller;
import com.tregouet.occam.data.representations.descriptions.properties.IProperty;
import com.tregouet.occam.data.representations.transitions.IApplication;
import com.tregouet.occam.data.representations.transitions.productions.IProduction;

public class AsProductionString implements PropertyLabeller {

	public static final AsProductionString INSTANCE = new AsProductionString();

	private AsProductionString() {
	}

	@Override
	public String apply(IProperty input) {
		StringBuilder sB = new StringBuilder();
		sB.append("{");
		List<IProduction> productions = new ArrayList<>();
		for (IApplication app : input.getApplications())
			productions.add(app.getInputConfiguration().getInputSymbol());
		Iterator<IProduction> prodIte = productions.iterator();
		while (prodIte.hasNext()) {
			sB.append(prodIte.next());
			if (prodIte.hasNext())
				sB.append(", ");
		}
		sB.append("}");
		return sB.toString();
	}

}
