package com.tregouet.occam.io.output;

import java.io.IOException;
import java.nio.file.Path;

public interface IRepresentationDisplayer {
	
	String generateAsymmetricalSimilarityMatrix(String alinea);
	
	void generateConceptLatticeGraph() throws IOException;
	
	String generateConceptualCoherenceArray(String alinea);
	
	void generateHTML() throws IOException;
	
	String generateInputHTMLTranslation(String alinea);
	
	void generatePorphyrianTree() throws IOException;
	
	String generateSimilarityMatrix(String alinea);
	
	void generateTransitionFunctionGraph() throws IOException;
	
	void generateTreeOfConcepts() throws IOException;
	
	double getCoherenceScore();
	
	int getConceptTreeIndex();
	
	int getTransitionFunctionIndex();
	
	boolean hasNextConceptualStructure();
	
	boolean hasNextTransitionFunctionOverCurrentConceptualStructure();
	
	void nextConceptTree() throws IOException;
	
	void nextTransitionFunctionOverCurrentCategoricalStructure() throws IOException;
	
	boolean represent(Path contextPath) throws IOException;

}
