package com.tregouet.occam.data.structures.representations.descriptions.differentiae.differentiations;

import java.util.List;

import com.tregouet.occam.alg.setters.weights.Weighed;

public interface IDifferentiationSet extends Weighed {
	
	List<IDifferentiation> getDifferentiationsWithGreatestWeight();
	
	List<IDifferentiation> getDifferentiationsGreatestNbOfProp();
	
	int getMaxNbOfProperties();
	
	boolean add(IDifferentiation differentiation);

}
