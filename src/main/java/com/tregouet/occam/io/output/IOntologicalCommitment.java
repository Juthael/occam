package com.tregouet.occam.io.output;

public interface IOntologicalCommitment {
	
	boolean whatIsThere(String contextPathName);
	
	double getCoherenceScore();
	
	void generateInputHTMLTranslation(String pathName, boolean asHTMLPage);
	
	void generateCategoryStructureGraph(String pathName);
	
	void generateTransitionFunctionGraph(String pathName);
	
	void generateSimilarityMatrix(String pathName, boolean asHTMLPage);
	
	void generateAsymmetricalSimilarityMatrix(String pathName, boolean asHTMLPage);
	
	void generateCategoricalCoherenceArray(String pathName, boolean asHTMLPage);
	
	boolean hasNextCategoricalStructure();
	
	boolean hasNextTransitionFunctionOverCurrentCategoricalStructure();
	
	void nextCategoricalStructure();
	
	void nextTransitionFunctionOverCurrentCategoricalStructure();

}
