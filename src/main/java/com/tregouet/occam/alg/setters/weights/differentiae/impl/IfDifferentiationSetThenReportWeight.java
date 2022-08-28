package com.tregouet.occam.alg.setters.weights.differentiae.impl;

import com.tregouet.occam.alg.setters.weights.differentiae.DifferentiaeWeigher;
import com.tregouet.occam.data.structures.representations.descriptions.differentiae.ADifferentiae;
import com.tregouet.occam.data.structures.representations.descriptions.differentiae.differentiations.IDifferentiationSet;

public class IfDifferentiationSetThenReportWeight implements DifferentiaeWeigher {

	@Override
	public void accept(ADifferentiae differentiae) {
		IDifferentiationSet differentiationSet = differentiae.getDifferentiationSet();
		if (differentiationSet != null)
			differentiae.setWeight(differentiationSet.weight());
	}

}
