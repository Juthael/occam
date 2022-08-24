package com.tregouet.occam.data.modules.comparison;

import java.util.Collection;
import java.util.List;

import com.tregouet.occam.alg.builders.BuildersAbstractFactory;
import com.tregouet.occam.alg.builders.comparator.ComparatorSetter;
import com.tregouet.occam.alg.displayers.formatters.FormattersAbstractFactory;
import com.tregouet.occam.alg.displayers.formatters.matrices.MatrixFormatter;
import com.tregouet.occam.data.modules.IModule;
import com.tregouet.occam.data.structures.representations.IRepresentation;
import com.tregouet.occam.data.structures.representations.classifications.concepts.IContextObject;

public interface IComparator extends IModule {

	Boolean display(int particularID1, int particularID2);

	IRepresentation getActiveRepresentationOfDifferences();

	IRepresentation getActiveRepresentationOfSimilarity();

	Double[][] getAsymmetricalSimilarityMatrix();

	String[][] getAsymmetricalSimilarityStringMatrix();

	@Override
	List<IContextObject> getContext();

	Double[][] getDifferenceMatrix();

	String[][] getDifferenceStringMatrix();

	Double[][] getSimilarityMatrix();

	String[][] getSimilarityStringMatrix();

	String[] getTypicalityStringVector();

	double[] getTypicalityVector();

	@Override
	IComparator process(Collection<IContextObject> context);

	public static ComparatorSetter comparatorSetter() {
		return BuildersAbstractFactory.INSTANCE.getComparatorSetter();
	}

	public static MatrixFormatter matrixFormatter() {
		return FormattersAbstractFactory.INSTANCE.getMatrixFormatter();
	}

}
