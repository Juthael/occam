package com.tregouet.occam.data.modules.similarity;

import java.util.List;

import com.tregouet.occam.data.modules.IModule;
import com.tregouet.occam.data.representations.IRepresentation;
import com.tregouet.occam.data.representations.classifications.concepts.IContextObject;

public interface ISimilarityAssessor extends IModule {
	
	Boolean display(int particularID1, int particularID2);
	
	IRepresentation getActiveRepresentationOfSimilarity();
	
	IRepresentation getActiveRepresentationOfDifferences();
	
	List<IContextObject> getContext();
	
	double[][] getSimilarityMatrix();
	
	double[][] getAsymmetricalSimilarityMatrix();
	
	double[][] getDifferenceMatrix();
	
	double[] getTypicalityVector();

}
