package com.tregouet.occam.io.output;

import java.io.IOException;
import java.nio.file.Path;

import com.tregouet.tree_finder.error.InvalidInputException;

public interface IOntologicalCommitment {
	
	boolean whatIsThere(Path contextPath) throws IOException, InvalidInputException;
	
	double getCoherenceScore();
	
	String generateInputHTMLTranslation(String alinea);
	
	void generateCategoryLatticeGraph() throws IOException;
	
	void generateCategoryTreeGraph() throws IOException;
	
	void generateTransitionFunctionGraph() throws IOException;
	
	String generateSimilarityMatrix(String alinea);
	
	String generateAsymmetricalSimilarityMatrix(String alinea);
	
	String generateCategoricalCoherenceArray(String alinea);
	
	boolean hasNextCategoricalStructure();
	
	boolean hasNextTransitionFunctionOverCurrentCategoricalStructure();
	
	void nextCategoryTree() throws IOException;
	
	void nextTransitionFunctionOverCurrentCategoricalStructure() throws IOException;
	
	void generateHTML() throws IOException;
	
	int getCategoryTreeIndex();
	
	int getTransitionFunctionIndex();

}
