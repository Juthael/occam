package com.tregouet.occam.data.structures.representations.descriptions.differentiae.differentiations.impl;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import com.tregouet.occam.data.structures.representations.descriptions.differentiae.differentiations.IDifferentiation;
import com.tregouet.occam.data.structures.representations.descriptions.differentiae.differentiations.IDifferentiationSet;

/**
 * Weighing not supported for now
 * @author Gael Tregouet
 *
 */
public class DifferentiationSet implements IDifferentiationSet {

	private final List<IDifferentiation> weightOptDiff = new ArrayList<>();
	private final List<IDifferentiation> propOptDiff = new ArrayList<>();
	private double optWeight = 0.0;
	private int optNbOfProperties = 0;

	/**
	 *
	 * @param differentiations must be weighed already, with each element being unique
	 */
	public DifferentiationSet(Collection<IDifferentiation> differentiations) {
		for (IDifferentiation diff : differentiations) {
			update(diff);
		}
	}

	@Override
	public List<IDifferentiation> getDifferentiationsGreatestNbOfProp() {
		return propOptDiff;
	}

	@Override
	public List<IDifferentiation> getDifferentiationsWithGreatestWeight() {
		return weightOptDiff;
	}

	@Override
	public int getMaxNbOfProperties() {
		return optNbOfProperties;
	}

	@Override
	public Double weight() {
		return optWeight;
	}

	private void update(IDifferentiation diff) {
		double diffWeight = diff.weight();
		if (diffWeight < optWeight) {
			//do nothing
		}
		else if (diffWeight == optWeight)
			weightOptDiff.add(diff);
		else if (diffWeight > optWeight) {
			optWeight = diffWeight;
			weightOptDiff.clear();
			weightOptDiff.add(diff);
		}
		int diffNbOfProp = diff.nbOfProperties();
		if (diffNbOfProp < optNbOfProperties) {
			//do nothing
		}
		else if (diffNbOfProp == optNbOfProperties)
			propOptDiff.add(diff);
		else if (diffNbOfProp > optNbOfProperties) {
			optNbOfProperties = diffNbOfProp;
			propOptDiff.clear();
			propOptDiff.add(diff);
		}
	}

}
