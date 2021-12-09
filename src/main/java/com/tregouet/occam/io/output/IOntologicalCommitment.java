package com.tregouet.occam.io.output;

import java.io.IOException;
import java.nio.file.Path;

public interface IOntologicalCommitment {
	
	String generateAsymmetricalSimilarityMatrix(String alinea);
	
	String generateCategoricalCoherenceArray(String alinea);
	
	void generateCategoryLatticeGraph() throws IOException;
	
	void generateCategoryTreeGraph() throws IOException;
	
	void generateHTML() throws IOException;
	
	String generateInputHTMLTranslation(String alinea);
	
	String generateSimilarityMatrix(String alinea);
	
	void generateTransitionFunctionGraph() throws IOException;
	
	int getCategoryTreeIndex();
	
	double getCoherenceScore();
	
	int getTransitionFunctionIndex();
	
	boolean hasNextCategoricalStructure();
	
	boolean hasNextTransitionFunctionOverCurrentCategoricalStructure();
	
	void nextCategoryTree() throws IOException;
	
	void nextTransitionFunctionOverCurrentCategoricalStructure() throws IOException;
	
	boolean whatIsThere(Path contextPath) throws IOException;

}
