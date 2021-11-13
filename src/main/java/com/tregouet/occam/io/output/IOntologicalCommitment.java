package com.tregouet.occam.io.output;

import java.io.IOException;
import java.nio.file.Path;

public interface IOntologicalCommitment {
	
	boolean whatIsThere(Path contextPath);
	
	double getCoherenceScore();
	
	String generateInputHTMLTranslation(String alinea);
	
	void generateCategoryLatticeGraph() throws IOException;
	
	void generateCategoryStructureGraph() throws IOException;
	
	void generateTransitionFunctionGraph() throws IOException;
	
	String generateSimilarityMatrix(String alinea);
	
	String generateAsymmetricalSimilarityMatrix(String alinea);
	
	String generateCategoricalCoherenceArray();
	
	boolean hasNextCategoricalStructure();
	
	boolean hasNextTransitionFunctionOverCurrentCategoricalStructure();
	
	void nextCategoricalStructure();
	
	void nextTransitionFunctionOverCurrentCategoricalStructure();
	
	void generateHTML();
	
	int getCategoryTreeIndex();
	
	int getTransitionFunctionIndex();

}
