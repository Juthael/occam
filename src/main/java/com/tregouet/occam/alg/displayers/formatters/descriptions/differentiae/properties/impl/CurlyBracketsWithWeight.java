package com.tregouet.occam.alg.displayers.formatters.descriptions.differentiae.properties.impl;

import java.math.BigDecimal;
import java.math.MathContext;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Iterator;
import java.util.List;
import java.util.Set;

import com.tregouet.occam.alg.displayers.formatters.descriptions.differentiae.properties.PropertyLabeller;
import com.tregouet.occam.alg.displayers.formatters.descriptions.differentiae.properties.impl.util.ComputationSorter;
import com.tregouet.occam.data.structures.representations.descriptions.differentiae.properties.IProperty;
import com.tregouet.occam.data.structures.representations.descriptions.differentiae.properties.computations.IComputation;

public class CurlyBracketsWithWeight implements PropertyLabeller {

	public static final CurlyBracketsWithWeight INSTANCE = new CurlyBracketsWithWeight();
	private static final String nL = System.lineSeparator();
	private static final MathContext mathContext = new MathContext(3);

	private CurlyBracketsWithWeight() {
	}

	@Override
	public String apply(IProperty property) {
		StringBuilder sB = new StringBuilder();
		sB.append("{");
		List<IComputation> computations = new ArrayList<>(property.getComputations());
		Collections.sort(computations, ComputationSorter.INSTANCE);
		Iterator<IComputation> appIte = computations.iterator();
		while (appIte.hasNext()) {
			sB.append(PropertyLabeller.computationLabeller().apply(appIte.next()));
			if (appIte.hasNext())
				sB.append("," + nL);
		}
		sB.append("}");
		if (property.weight() != null) {
			sB.append(", " + new BigDecimal(property.weight()).round(mathContext).toString());
		}
		return sB.toString();
	}

}
