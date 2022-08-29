package com.tregouet.occam.alg.displayers.formatters.descriptions.differentiae.impl;

import java.math.BigDecimal;
import java.math.MathContext;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import com.tregouet.occam.alg.displayers.formatters.descriptions.differentiae.DifferentiaeFormatter;
import com.tregouet.occam.alg.displayers.visualizers.descriptions.DescriptionFormat;
import com.tregouet.occam.data.structures.representations.descriptions.differentiae.ADifferentiae;
import com.tregouet.occam.data.structures.representations.descriptions.differentiae.differentiations.IDifferentiationSet;
import com.tregouet.occam.data.structures.representations.descriptions.differentiae.properties.IProperty;

public class ManageDiffFormat implements DifferentiaeFormatter {

	public static final ManageDiffFormat INSTANCE = new ManageDiffFormat();
	private static final MathContext mathContext = new MathContext(3);

	private ManageDiffFormat() {
	}

	@Override
	public String apply(ADifferentiae differentiae, DescriptionFormat format) {
		String nL = System.lineSeparator();
		List<IProperty> properties = null;
		String score = null;
		IDifferentiationSet diffSet;
		switch(format) {
		case EXHAUSTIVE :
			properties = new ArrayList<>(differentiae.getProperties());
			score = new String();
			break;
		case OPTIMAL :
			diffSet = differentiae.getDifferentiationSet();
			if (diffSet == null) {
				properties = new ArrayList<>(differentiae.getProperties());
			}
			else {
				properties = new ArrayList<>(
						diffSet.getDifferentiationsWithGreatestWeight().get(0).getProperties());
				score = "weight : " + new BigDecimal(differentiae.weight()).round(mathContext).toString() + nL;
			}
			break;
		}
		Iterator<IProperty> propIte = properties.iterator();
		StringBuilder sBProp = new StringBuilder();
		while (propIte.hasNext()) {
			IProperty nextProp = propIte.next();
			if (!nextProp.isBlank()) {
				if (sBProp.length() > 0)
					sBProp.append(nL);
				sBProp.append(DifferentiaeFormatter.getPropertyDisplayer().apply(nextProp));
			}
		}
		return score + sBProp.toString();
	}

}
