package com.tregouet.occam.data.modules.similarity;

import java.util.Collection;
import java.util.List;
import java.util.Set;

import com.tregouet.occam.alg.builders.BuildersAbstractFactory;
import com.tregouet.occam.alg.builders.similarity_assessor.SimAssessorSetter;
import com.tregouet.occam.alg.displayers.formatters.FormattersAbstractFactory;
import com.tregouet.occam.alg.displayers.formatters.matrices.MatrixFormatter;
import com.tregouet.occam.data.modules.IModule;
import com.tregouet.occam.data.structures.representations.IRepresentation;
import com.tregouet.occam.data.structures.representations.classifications.concepts.IContextObject;

public interface ISimilarityAssessor extends IModule {
	
	ISimilarityAssessor process(Collection<IContextObject> context);
	
	Boolean display(int particularID1, int particularID2);
	
	IRepresentation getActiveRepresentationOfSimilarity();
	
	IRepresentation getActiveRepresentationOfDifferences();
	
	List<IContextObject> getContext();
	
	Double[][] getSimilarityMatrix();
	
	String[][] getSimilarityStringMatrix();
	
	Double[][] getAsymmetricalSimilarityMatrix();
	
	String[][] getAsymmetricalSimilarityStringMatrix();
	
	Double[][] getDifferenceMatrix();
	
	String[][] getDifferenceStringMatrix();
	
	double[] getTypicalityVector();
	
	String[] getTypicalityStringVector();
	
	public static MatrixFormatter matrixFormatter() {
		return FormattersAbstractFactory.INSTANCE.getMatrixFormatter();
	}
	
	public static SimAssessorSetter simAssessorSetter() {
		return BuildersAbstractFactory.INSTANCE.getSimAssessorSetter();
	}

}
